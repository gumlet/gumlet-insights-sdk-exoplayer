package com.gumlet.insights;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.gumlet.insights.data.CustomData;
import com.gumlet.insights.data.PlayerData;
import com.gumlet.insights.data.UserData;
import com.gumlet.insights.data.VideoMetadata;
import com.gumlet.insights.enums.CDNProvider;
import com.gumlet.insights.enums.PlayerType;

import org.jetbrains.annotations.NotNull;

public class GumletInsightsConfig implements Parcelable {
    private String cdnProvider = CDNProvider.GUMLET;

    private CustomData customData;
    private PlayerData playerData;
    private UserData userData;
    private VideoMetadata videoMetadata;

    private String experimentName;
    private String mpdUrl;
    private String m3u8Url;
    private int heartbeatInterval = 1000;//59700;
    private String key;
    private String propertyId;
    private String title;
    private String path;
    private String playerKey;
    private PlayerType playerType;
    private String videoId;
    private Boolean ads = true;
    private Context context;
    private Boolean isLive;
    private Boolean randomizeUserId = false;
    private CollectorConfig config = new CollectorConfig();

    public static final Creator<GumletInsightsConfig> CREATOR =
            new Creator<GumletInsightsConfig>() {
                @Override
                public GumletInsightsConfig createFromParcel(Parcel in) {
                    return new GumletInsightsConfig(in);
                }

                @Override
                public GumletInsightsConfig[] newArray(int size) {
                    return new GumletInsightsConfig[size];
                }
            };


    public GumletInsightsConfig(@NotNull String propertyId, @NotNull Context context) throws IllegalArgumentException{
        this.propertyId = propertyId;
        this.context = context;

        if(TextUtils.isEmpty(propertyId)){
            throw new IllegalArgumentException("Invalid property id");
        }
        if(context == null){
            throw new IllegalArgumentException("Context is null or empty");
        }
    }


    protected GumletInsightsConfig(Parcel in) {
        cdnProvider = in.readString();
        experimentName = in.readString();
        mpdUrl = in.readString();
        m3u8Url = in.readString();
        heartbeatInterval = in.readInt();
        key = in.readString();
        title = in.readString();
        path = in.readString();
        playerKey = in.readString();
        playerType = in.readParcelable(PlayerType.class.getClassLoader());
        videoId = in.readString();
        isLive = (Boolean) in.readSerializable();
        config = in.readParcelable(CollectorConfig.class.getClassLoader());
        ads = in.readInt() == 1;
        randomizeUserId = (Boolean) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cdnProvider);
        dest.writeString(experimentName);
        dest.writeString(mpdUrl);
        dest.writeString(m3u8Url);
        dest.writeInt(heartbeatInterval);
        dest.writeString(key);
        dest.writeString(title);
        dest.writeString(path);
        dest.writeString(playerKey);
        dest.writeParcelable(playerType, flags);
        dest.writeString(videoId);
        dest.writeSerializable(isLive);
        dest.writeParcelable(config, config.describeContents());
        dest.writeInt(ads ? 1 : 0);
        dest.writeSerializable(randomizeUserId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public String getKey() {
        return key;
    }

    public String getPlayerKey() {
        return playerKey;
    }

    public String getCdnProvider() {
        return cdnProvider;
    }

    public String getVideoId() {
        return videoId;
    }

    /**
     * ID of the Video in the Customer System
     *
     * @param videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * Human readable title of the video asset currently playing
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Human readable title of the video asset currently playing
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getMpdUrl() {
        return this.mpdUrl;
    }

    public String getM3u8Url() {
        return this.m3u8Url;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public String getPath() {
        return path;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }


    public Context getContext() {
        return context;
    }

    /**
     * Configuration options for the Analytics collector
     *
     * @return collector configuration {@link CollectorConfig}
     */
    public CollectorConfig getConfig() {
        return config;
    }

    /**
     * Returns a value indicating if ads tracking is enabled
     *
     * @return
     */
    public Boolean getAds() {
        return ads;
    }

    /**
     * Enable or disable ads tracking
     *
     * @param ads
     */
    public void setAds(Boolean ads) {
        this.ads = ads;
    }

    /**
     * Returns true if the stream is marked as live before stream metadata is available.
     *
     * @return
     */
    public Boolean isLive() {
        return isLive;
    }

    /**
     * Returns true if random UserId value will be generated
     *
     * @return
     */
    public Boolean getRandomizeUserId() {
        return randomizeUserId;
    }

    public CustomData getCustomData() {
        return this.customData;
    }

    public void setCustomData(CustomData customData) {
        this.customData = customData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public VideoMetadata getVideoMetadata() {
        return videoMetadata;
    }

    public void setVideoMetadata(VideoMetadata videoMetadata) {
        this.videoMetadata = videoMetadata;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Handler playbackUpdateHandler = new Handler();

    public Handler getPlaybackUpdateHandler() {
        return playbackUpdateHandler;
    }

}
