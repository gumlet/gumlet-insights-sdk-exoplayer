package com.gumlet.insights;

import static com.gumlet.insights.utils.DataSerializer.serialize;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.gumlet.insights.adapters.AdAdapter;
import com.gumlet.insights.adapters.PlayerAdapter;
import com.gumlet.insights.calls.AnalyticsCallback;
import com.gumlet.insights.calls.InsightsReporter;
import com.gumlet.insights.calls.presenter.ViewerSessionEventPresenter;
import com.gumlet.insights.config.SourceMetadata;
import com.gumlet.insights.data.AdEventData;
import com.gumlet.insights.data.BackendFactory;
import com.gumlet.insights.data.CustomData;
import com.gumlet.insights.data.DebuggingEventDataDispatcher;
import com.gumlet.insights.data.DeviceInformation;
import com.gumlet.insights.data.DeviceInformationProvider;
import com.gumlet.insights.data.ErrorCode;
import com.gumlet.insights.data.EventData;
import com.gumlet.insights.data.EventDataFactory;
import com.gumlet.insights.data.IEventDataDispatcher;
import com.gumlet.insights.data.RandomizedUserIdIdProvider;
import com.gumlet.insights.data.SecureSettingsAndroidIdUserIdProvider;
import com.gumlet.insights.data.SimpleEventDataDispatcher;
import com.gumlet.insights.data.UserIdProvider;
import com.gumlet.insights.data.manipulators.ManifestUrlEventDataManipulator;
import com.gumlet.insights.enums.VideoStartFailedReason;
import com.gumlet.insights.features.Feature;
import com.gumlet.insights.features.FeatureManager;
import com.gumlet.insights.features.errordetails.OnErrorDetailEventListener;
import com.gumlet.insights.license.FeatureConfigContainer;
import com.gumlet.insights.license.LicenseCallback;
import com.gumlet.insights.stateMachines.PlayerStateMachine;
import com.gumlet.insights.stateMachines.PlayerStates;
import com.gumlet.insights.stateMachines.StateMachineListener;
import com.gumlet.insights.utils.GumletLog;
import com.gumlet.insights.utils.GumletPreferenceManager;
import com.gumlet.insights.utils.NetworkUtil;
import com.gumlet.insights.utils.Util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Collection;

/**
 * An insights plugin that sends video playback insights to Gumlet Analytics servers. Currently
 * supports insights of ExoPlayer video players
 */
public class GumletInsights
        implements StateMachineListener, LicenseCallback, ImpressionIdProvider {

    private static final String TAG = "GumletInsights";

    private FeatureManager<FeatureConfigContainer> featureManager = new FeatureManager<>();
    private final EventBus eventBus = new EventBus();

    private final GumletInsightsConfig gumletInsightsConfig;
    @Nullable private PlayerAdapter playerAdapter;

    private PlayerStateMachine playerStateMachine;
    private GumletAdInsights adAnalytics;
    private IEventDataDispatcher eventDataDispatcher;

    private Context context;
    private final UserIdProvider userIdProvider;
    private final EventDataFactory eventDataFactory;
    private final DeviceInformationProvider deviceInformationProvider;
    private GumletPreferenceManager gumletPreferenceManager;

    private AnalyticsCallback analyticsCallback;

    public AnalyticsCallback getAnalyticsCallback() {
        return analyticsCallback;
    }

    public void setAnalyticsCallback(AnalyticsCallback analyticsCallback) {
        this.analyticsCallback = analyticsCallback;
    }

    private InsightsReporter insightsReporter;

    public GumletInsights(
            GumletInsightsConfig gumletInsightsConfig,
            Context context,
            @NotNull DeviceInformationProvider deviceInformationProvider) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }

        GumletLog.d(TAG, "Initializing Gumlet Insights with Key: " + gumletInsightsConfig.getKey());
        this.context = context;
        this.deviceInformationProvider = deviceInformationProvider;
        this.userIdProvider =
                gumletInsightsConfig.getRandomizeUserId()
                        ? new RandomizedUserIdIdProvider()
                        : new SecureSettingsAndroidIdUserIdProvider(context);

        this.gumletInsightsConfig = gumletInsightsConfig;
        this.eventDataFactory = new EventDataFactory(gumletInsightsConfig, this.userIdProvider);

        this.playerStateMachine = new PlayerStateMachine(this.gumletInsightsConfig, this);
        this.playerStateMachine.addListener(this);

        this.gumletPreferenceManager = GumletPreferenceManager.getInstance(gumletInsightsConfig.getContext());
        this.insightsReporter = InsightsReporter.getInstance(this.gumletInsightsConfig.getPropertyId(),this.gumletInsightsConfig.getContext());

        IEventDataDispatcher innerEventDataDispatcher =
                new SimpleEventDataDispatcher(
                        this.gumletInsightsConfig, this.context, this, new BackendFactory());


        this.eventDataDispatcher = new DebuggingEventDataDispatcher(innerEventDataDispatcher, debugCallback);

        if (this.gumletInsightsConfig.getAds()) {
            this.adAnalytics = new GumletAdInsights(this);
        }
        createViewerSession();

    }

    private void createViewerSession() {

        boolean sessionCreationAllowed = false;
        if(this.gumletPreferenceManager != null
                && this.gumletPreferenceManager.getSessionTimeout() != 0){

            sessionCreationAllowed = checkSessionCreationAllowed();

            if(!sessionCreationAllowed){
                ViewerSession.setViewerSession(this.gumletPreferenceManager.restoreSession());
            }

        }else if(this.gumletInsightsConfig != null
                && this.gumletPreferenceManager.getSessionTimeout() == 0){
            sessionCreationAllowed = true;
        }

        if(sessionCreationAllowed && this.gumletInsightsConfig != null
                && this.deviceInformationProvider != null
                && this.userIdProvider != null){

            if(context == null && this.gumletInsightsConfig.getContext() != null){
                this.context = this.gumletInsightsConfig.getContext();
            }
            ViewerSession session = ViewerSession.getInstance();
            session.setPropertyId(this.gumletInsightsConfig.getPropertyId());
            session.setCustomData1(this.gumletInsightsConfig.getCustomData1());
            session.setCustomData2(this.gumletInsightsConfig.getCustomData2());
            session.setCustomData3(this.gumletInsightsConfig.getCustomData3());
            session.setCustomData4(this.gumletInsightsConfig.getCustomData4());
            session.setCustomData5(this.gumletInsightsConfig.getCustomData5());
            session.setCustomData6(this.gumletInsightsConfig.getCustomData6());
            session.setCustomData7(this.gumletInsightsConfig.getCustomData7());
            session.setMetaBrowser(this.userIdProvider.userId());
            session.setCustomUserId(this.gumletInsightsConfig.getCustomUserId());
            session.setUserId(this.userIdProvider.userId());
            session.setMetaConnectionSpeed(NetworkUtil.networkConnectionSpeed(gumletInsightsConfig.getContext()));

            DeviceInformation information = this.deviceInformationProvider.getDeviceInformation();

            if(information != null){

                if(!TextUtils.isEmpty(information.getUserAgent())) {
                    session.setMetaUserAgent(information.getUserAgent());
                }

                if(information.isTV()){
                    session.setMetaDeviceCategory("TV");
                    session.setMetaDeviceIsTouchScreen(false);
                }else{
                    session.setMetaDeviceCategory("MOBILE");
                    session.setMetaDeviceIsTouchScreen(true);
                }

                session.setMetaDeviceDisplayHeight(information.getScreenHeight());
                session.setMetaDeviceDisplayWidth(information.getScreenWidth());
                session.setMetaDeviceManufacturer(information.getManufacturer());
                session.setMetaDeviceName(information.getModel());
                session.setZ(Calendar.getInstance().getTimeInMillis());

                session.setMetaOperatingSystem("Android");
                session.setMetaOperatingSystemVersion(String.valueOf(Build.VERSION.SDK_INT));

                session.setMetaBrowser(context.getPackageName());

                try {
                    PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    String version = pInfo.versionName;
                    session.setMetaBrowserVersion(version);

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    session.setMetaBrowserVersion("Unknown");
                }

                if(context != null){
                    if(NetworkUtil.getConnectionStatus(context) == NetworkUtil.WIFI) {
                        session.setMetaConnectionType("WiFi");
                    }else if(NetworkUtil.getConnectionStatus(context) == NetworkUtil.MOBILE){
                        session.setMetaConnectionType("Mobile");
                    }else if(NetworkUtil.getConnectionStatus(context) == NetworkUtil.NOT_CONNECTED){
                        session.setMetaConnectionType("No Network");
                    }

                }else{

                    if(NetworkUtil.getConnectionStatus(gumletInsightsConfig.getContext()) == NetworkUtil.WIFI) {
                        session.setMetaConnectionType("WiFi");
                    }else if(NetworkUtil.getConnectionStatus(gumletInsightsConfig.getContext()) == NetworkUtil.MOBILE){
                        session.setMetaConnectionType("Mobile");
                    }else if(NetworkUtil.getConnectionStatus(gumletInsightsConfig.getContext()) == NetworkUtil.NOT_CONNECTED){
                        session.setMetaConnectionType("No Network");
                    }

                }

            }

            ViewerSession.setViewerSession(session);
            this.insightsReporter.setViewerSession(session);

            if(NetworkUtil.isNetworkAvailable(gumletInsightsConfig.getContext())
                    && NetworkUtil.isDataAvailable(gumletInsightsConfig.getContext())) {
                new ViewerSessionEventPresenter().LogSessionEvent(session,null);

                this.gumletPreferenceManager.setSessionTimeout(Calendar.getInstance().getTimeInMillis());

                if(session != null) {
                    this.gumletPreferenceManager.saveSession(session);
                }
            }

        }
    }

    private boolean checkSessionCreationAllowed() {

        long previousSessionTime = this.gumletPreferenceManager.getSessionTimeout();
        long currentTime = Calendar.getInstance().getTimeInMillis();

        if(currentTime>previousSessionTime){

            long timeDifference = currentTime - previousSessionTime;
            long thirtyMinutesInMillis = 30 * 60 * 1000;

            if(timeDifference>thirtyMinutesInMillis){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public Context getContext() {
        return context;
    }

    public GumletInsightsConfig getConfig() {
        return gumletInsightsConfig;
    }

    public PlayerStateMachine getPlayerStateMachine() {
        return playerStateMachine;
    }

    /**
     * Attach a player instance to this insights plugin. After this is completed, GumletInsights
     * will start monitoring and sending insights data based on the attached player adapter.
     *
     * <p>To attach a different player instance, simply call this method again.
     */
    protected void attach(PlayerAdapter adapter) {

        GumletLog.e(TAG,"GUMLET_ANALYTICS - attach()");
        detachPlayer();
        eventDataDispatcher.enable();
        this.playerAdapter = adapter;
        Collection<Feature<FeatureConfigContainer, ?>> features = this.playerAdapter.init();
        this.featureManager.registerFeatures(features);

        // this.registerEventDataManipulators(prePipelineManipulator);
        this.playerAdapter.registerEventDataManipulators(eventDataFactory);
        this.eventDataFactory.registerEventDataManipulator(
                new ManifestUrlEventDataManipulator(
                        this.playerAdapter, this.gumletInsightsConfig));
        // this.registerEventDataManipulators(postPipelineManipulator);

        tryAttachAd(adapter);

        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerAttached();
        }
    }

    private void tryAttachAd(PlayerAdapter adapter) {
        if (adAnalytics == null) {
            return;
        }
        AdAdapter adAdapter = adapter.createAdAdapter();
        if (adAdapter == null) {
            return;
        }
        adAnalytics.attachAdapter(adAdapter);
    }

    /** Detach the current player that is being used with Gumlet Analytics. */
    public void detachPlayer() {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - detachPlayer()");
        detachAd();

        featureManager.unregisterFeatures();
        eventBus.notify(
                OnAnalyticsReleasingEventListener.class,
                OnAnalyticsReleasingEventListener::onReleasing);

        if (playerAdapter != null) {
            playerAdapter.release();
        }

        if (playerStateMachine != null) {
            playerStateMachine.resetStateMachine();
        }
        eventDataDispatcher.disable();
        eventDataFactory.clearEventDataManipulators();

        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerDetached();
        }
    }

    private void detachAd() {
        if (adAnalytics != null) {
            adAnalytics.detachAdapter();
        }
    }

    @Nullable
    public EventData createEventData() {
        if (playerAdapter == null) {
            return null;
        }
        return eventDataFactory.create(
                playerStateMachine.getImpressionId(),
                playerAdapter.getCurrentSourceMetadata(),
                deviceInformationProvider.getDeviceInformation());
    }

    @Override
    public void onStartup(long videoStartupTime, long playerStartupTime) {
        try{
            GumletLog.e(TAG,"GUMLET_ANALYTICS - onStartup()");

           GumletLog.d(TAG, String.format("onStartup %s", playerStateMachine.getImpressionId()));
            EventData data = createEventData();
            data.setSupportedVideoCodecs(Util.getSupportedVideoFormats());
            data.setState("startup");
            data.setDuration(videoStartupTime + playerStartupTime);
            data.setVideoStartupTime(videoStartupTime);

            data.setDrmLoadTime(playerAdapter.getDRMDownloadTime());

            data.setPlayerStartupTime(playerStartupTime);
            data.setStartupTime(videoStartupTime + playerStartupTime);

            data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
            data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
            sendEventData(data);

            if(this.analyticsCallback != null){
                this.analyticsCallback.onPlayerStartup(data);
            }

        }catch (Exception ex){

        }
    }

    @Override
    public void onPauseExit(long duration) {
        try {
            GumletLog.e(TAG,"GUMLET_ANALYTICS - onPauseExit()");
           GumletLog.d(TAG, String.format("onPauseExit %s", playerStateMachine.getImpressionId()));
            EventData data = createEventData();
            data.setState(playerStateMachine.getCurrentState().getName());
            data.setDuration(duration);
            data.setPaused(duration);
            data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
            data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());

            if(this.analyticsCallback != null){
                this.analyticsCallback.onPlayerPauseExit(data);
            }
            sendEventData(data);
        }catch (Exception ex){

        }
    }

    @Override
    public void onPlayExit(long duration) {
        try {
            GumletLog.e(TAG,"GUMLET_ANALYTICS - onPlayExit()");
           GumletLog.d(TAG, String.format("onPlayExit %s", playerStateMachine.getImpressionId()));
            EventData data = createEventData();
            data.setState(playerStateMachine.getCurrentState().getName());
            data.setDuration(duration);
            data.setPlayed(duration);
            data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
            data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
            if(this.analyticsCallback != null){
                this.analyticsCallback.onPlayerPlayExit(data);
            }
            sendEventData(data);
        }catch (Exception ex){

        }
    }

    @Override
    public void onRebuffering(long duration) {

        GumletLog.e(TAG,"GUMLET_ANALYTICS - onRebuffering()");
       GumletLog.d(TAG, String.format("onRebuffering %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setDuration(duration);
        data.setBuffered(duration);
        data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerRebuffering(data);
        }
        sendEventData(data);
    }

    @Override
    public void onError(ErrorCode errorCode) {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onError()");
       GumletLog.d(TAG, String.format("onError %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setVideoTimeStart(playerStateMachine.getVideoTimeEnd());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());

        if (playerStateMachine.getVideoStartFailedReason() != null) {
            data.setVideoStartFailedReason(
                    playerStateMachine.getVideoStartFailedReason().getReason());
            data.setVideoStartFailed(true);
        }

        data.setErrorCode(errorCode.getErrorCode());
        data.setErrorMessage(errorCode.getDescription());
        data.setErrorData(serialize(errorCode.getLegacyErrorData()));

        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerError(data);
        }
        sendEventData(data);



        eventBus.notify(
                OnErrorDetailEventListener.class,
                listener ->
                        listener.onError(
                                errorCode.getErrorCode(),
                                errorCode.getDescription(),
                                errorCode.getErrorData()));
    }

    @Override
    public void onSeekComplete(long duration) {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onSeekComplete()");
       GumletLog.d(TAG, String.format("onSeekComplete %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setSeeked(duration);
        data.setDuration(duration);
        data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());

        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerSeekCompleted(data);
        }
        sendEventData(data);
    }

    @Override
    public void onHeartbeat(long duration) {
       GumletLog.d(
                TAG,
                String.format(
                        "onHeartbeat %s %s",
                        playerStateMachine.getCurrentState().getName(),
                        playerStateMachine.getImpressionId()));

        GumletLog.e(TAG,"GUMLET_ANALYTICS - onHeartbeat()");

        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setDuration(duration);

        if (playerStateMachine.getCurrentState() == PlayerStates.PLAYING) {
            data.setPlayed(duration);
        } else if (playerStateMachine.getCurrentState() == PlayerStates.PAUSE) {
            data.setPaused(duration);
        } else if (playerStateMachine.getCurrentState() == PlayerStates.BUFFERING) {
            data.setBuffered(duration);
        }

        data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());

        if(this.analyticsCallback != null){
            this.analyticsCallback.onPlayerHeartBeat(data);
        }
        sendEventData(data);
    }

    @Override
    public void onAd() {
       GumletLog.d(TAG, "onAd");
    }

    @Override
    public void onMute() {
       GumletLog.d(TAG, "onMute");
    }

    @Override
    public void onUnmute() {
       GumletLog.d(TAG, "onUnmute");
    }

    @Override
    public void onUpdateSample() {
       GumletLog.d(TAG, "onUpdateSample");
    }

    @Override
    public void onQualityChange() {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onQualityChange()");
       GumletLog.d(TAG, String.format("onQualityChange %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setDuration(0);
        sendEventData(data);
        data.setVideoTimeStart(playerStateMachine.getVideoTimeEnd());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
    }

    @Override
    public void onVideoChange() {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onVideoChange()");
       GumletLog.d(TAG, "onVideoChange");
    }

    @Override
    public void onSubtitleChange() {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onSubtitleChange()");
       GumletLog.d(TAG, String.format("onSubtitleChange %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setDuration(0);
        sendEventData(data);
        data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
    }

    @Override
    public void onAudioTrackChange() {
        GumletLog.e(TAG,"GUMLET_ANALYTICS - onAudioTrackChange()");
       GumletLog.d(TAG, String.format("onAudioTrackChange %s", playerStateMachine.getImpressionId()));
        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setDuration(0);
        sendEventData(data);
        data.setVideoTimeStart(playerStateMachine.getVideoTimeStart());
        data.setVideoTimeEnd(playerStateMachine.getVideoTimeEnd());
    }

    @Override
    public void onVideoStartFailed() {

        GumletLog.e(TAG,"GUMLET_ANALYTICS - onVideoStartFailed()");

        VideoStartFailedReason videoStartFailedReason =
                playerStateMachine.getVideoStartFailedReason();
        if (videoStartFailedReason == null) {
            videoStartFailedReason = VideoStartFailedReason.UNKNOWN;
        }

        EventData data = createEventData();
        data.setState(playerStateMachine.getCurrentState().getName());
        data.setVideoStartFailed(true);
        ErrorCode errorCode = videoStartFailedReason.getErrorCode();
        if (errorCode != null) {
            data.setErrorCode(errorCode.getErrorCode());
            data.setErrorMessage(errorCode.getDescription());
            data.setErrorData(serialize(errorCode.getLegacyErrorData()));
            eventBus.notify(
                    OnErrorDetailEventListener.class,
                    listener ->
                            listener.onError(
                                    errorCode.getErrorCode(),
                                    errorCode.getDescription(),
                                    errorCode.getErrorData()));
        }
        data.setVideoStartFailedReason(videoStartFailedReason.getReason());
        sendEventData(data);
        this.detachPlayer();
    }

    public final void resetSourceRelatedState() {
        if (this.eventDataDispatcher != null) {
            this.eventDataDispatcher.resetSourceRelatedState();
        }

        featureManager.resetFeatures();
        // TODO reset features and prepare for new source

        if (this.playerAdapter != null) {
            this.playerAdapter.resetSourceRelatedState();
        }
    }

    public CustomData getCustomData() {
        SourceMetadata sourceMetadata = playerAdapter.getCurrentSourceMetadata();
        if (sourceMetadata != null) {
            return SourceMetadataExtension.Companion.getCustomData(sourceMetadata);
        }
        return this.gumletInsightsConfig.getCustomData();
    }

    public void setCustomData(CustomData customData) {
        CustomDataHelpers.Setter customDataSetter = this.gumletInsightsConfig::setCustomData;

        SourceMetadata sourceMetadata = playerAdapter.getCurrentSourceMetadata();

        if (sourceMetadata != null) {
            customDataSetter =
                    (changedCustomData) ->
                            SourceMetadataExtension.Companion.setCustomData(
                                    sourceMetadata, changedCustomData);
        }

        this.playerStateMachine.changeCustomData(getPosition(), customData, customDataSetter);
    }

    public void setCustomDataOnce(CustomData customData) {
        if (playerAdapter == null) {
           GumletLog.d(TAG, "Custom data could not be set because player is not attached");
            return;
        }
        CustomDataHelpers.Getter customDataGetter = this.gumletInsightsConfig::getCustomData;
        CustomDataHelpers.Setter customDataSetter = this.gumletInsightsConfig::setCustomData;

        SourceMetadata sourceMetadata = playerAdapter.getCurrentSourceMetadata();

        if (sourceMetadata != null) {
            customDataGetter =
                    () -> SourceMetadataExtension.Companion.getCustomData(sourceMetadata);
            customDataSetter =
                    (changedCustomData) ->
                            SourceMetadataExtension.Companion.setCustomData(
                                    sourceMetadata, changedCustomData);
        }

        CustomData currentCustomData = customDataGetter.getCustomData();
        customDataSetter.setCustomData(customData);
        EventData eventData = createEventData();
        eventData.setState(PlayerStates.CUSTOMDATACHANGE.getName());
        sendEventData(eventData);
        customDataSetter.setCustomData(currentCustomData);
    }

    public void sendEventData(EventData data) {
        this.eventDataDispatcher.add(data);
        if (this.playerAdapter != null) {
            this.playerAdapter.clearValues();
        }
    }

    public void sendAdEventData(AdEventData data) {
        this.eventDataDispatcher.addAd(data);
    }

    public long getPosition() {
        if (playerAdapter == null) {
            return 0;
        }
        return playerAdapter.getPosition();
    }

    public Observable<OnAnalyticsReleasingEventListener> getOnAnalyticsReleasingObservable() {
        return eventBus.get(OnAnalyticsReleasingEventListener.class);
    }

    public Observable<OnErrorDetailEventListener> getOnErrorDetailObservable() {
        return eventBus.get(OnErrorDetailEventListener.class);
    }

    @Override
    public void configureFeatures(
            boolean authenticated, @Nullable FeatureConfigContainer featureConfigs) {
        featureManager.configureFeatures(authenticated, featureConfigs);
    }

    @Override
    public void authenticationCompleted(boolean success) {
        if (!success) {
            detachPlayer();
        }
    }

    public void addDebugListener(DebugListener listener) {
        eventBus.get(DebugListener.class).subscribe(listener);
    }

    public void removeDebugListener(DebugListener listener) {
        eventBus.get(DebugListener.class).unsubscribe(listener);
    }

    @NotNull
    @Override
    public String getImpressionId() {
        return this.playerStateMachine.getImpressionId();
    }

    public interface DebugListener {
        void onDispatchEventData(EventData data);

        void onDispatchAdEventData(AdEventData data);

        void onMessage(String message);
    }

    private DebugCallback debugCallback =
            new DebugCallback() {
                @Override
                public void dispatchEventData(@NotNull EventData data) {
                    playerStateMachine.setCurrentEventData(data);
                    GumletInsights.this.gumletPreferenceManager.setSessionTimeout(Calendar.getInstance().getTimeInMillis());

                    eventBus.notify(
                            DebugListener.class, listener -> listener.onDispatchEventData(data));
                }

                @Override
                public void dispatchAdEventData(@NotNull AdEventData data) {
                    eventBus.notify(
                            DebugListener.class, listener -> listener.onDispatchAdEventData(data));
                }

                @Override
                public void message(@NotNull String message) {
                    eventBus.notify(DebugListener.class, listener -> listener.onMessage(message));
                }
            };
}
