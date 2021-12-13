package com.gumlet.insights;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.gumlet.insights.data.CustomData;
import com.gumlet.insights.enums.CDNProvider;
import com.gumlet.insights.enums.PlayerType;

import org.jetbrains.annotations.NotNull;

public class GumletInsightsConfig implements Parcelable {
    private String cdnProvider = CDNProvider.GUMLET;
    private String customData1;
    private String customData2;
    private String customData3;
    private String customData4;
    private String customData5;
    private String customData6;
    private String customData7;
    private String customUserId;
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
        customData1 = in.readString();
        customData2 = in.readString();
        customData3 = in.readString();
        customData4 = in.readString();
        customData5 = in.readString();
        customData6 = in.readString();
        customData7 = in.readString();
        customUserId = in.readString();
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
        dest.writeString(customData1);
        dest.writeString(customData2);
        dest.writeString(customData3);
        dest.writeString(customData4);
        dest.writeString(customData5);
        dest.writeString(customData6);
        dest.writeString(customData7);
        dest.writeString(customUserId);
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

    public String getCustomUserId() {
        return customUserId;
    }

    /**
     * User-ID in the Customer System
     *
     * @param customUserId
     */
    public void setCustomUserId(String customUserId) {
        this.customUserId = customUserId;
    }

    public String getCustomData1() {
        return customData1;
    }

    /**
     * Optional free-form data
     *
     * @param customData1
     */
    public void setCustomData1(String customData1) {
        this.customData1 = customData1;
    }

    public String getCustomData2() {
        return customData2;
    }

    /**
     * Optional free-form data
     *
     * @param customData2
     */
    public void setCustomData2(String customData2) {
        this.customData2 = customData2;
    }

    public String getCustomData3() {
        return customData3;
    }

    /**
     * Optional free-form data
     *
     * @param customData3
     */
    public void setCustomData3(String customData3) {
        this.customData3 = customData3;
    }

    public String getCustomData4() {
        return customData4;
    }

    /**
     * Optional free-form data
     *
     * @param customData4
     */
    public void setCustomData4(String customData4) {
        this.customData4 = customData4;
    }

    public String getCustomData5() {
        return customData5;
    }

    /**
     * Optional free-form data
     *
     * @param customData5
     */
    public void setCustomData5(String customData5) {
        this.customData5 = customData5;
    }

    public String getCustomData6() {
        return customData6;
    }

    /**
     * Optional free-form data Not enabled by default Must be activated for your organization
     *
     * @param customData6
     */
    public void setCustomData6(String customData6) {
        this.customData6 = customData6;
    }

    public String getCustomData7() {
        return customData7;
    }

    /**
     * Optional free-form data Not enabled by default Must be activated for your organization
     *
     * @param customData7
     */
    public void setCustomData7(String customData7) {
        this.customData7 = customData7;
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

    protected CustomData getCustomData() {
        return new CustomData(
                this.getCustomData1(),
                this.getCustomData2(),
                this.getCustomData3(),
                this.getCustomData4(),
                this.getCustomData5(),
                this.getCustomData6(),
                this.getCustomData7(),"");
                //this.getExperimentName());
    }

    protected void setCustomData(CustomData customData) {
        this.setCustomData1(customData.getCustomData1());
        this.setCustomData2(customData.getCustomData2());
        this.setCustomData3(customData.getCustomData3());
        this.setCustomData4(customData.getCustomData4());
        this.setCustomData5(customData.getCustomData5());
        this.setCustomData6(customData.getCustomData6());
        this.setCustomData7(customData.getCustomData7());
        //this.setExperimentName(customData.getExperimentName());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Handler playbackUpdateHandler = new Handler();

    public Handler getPlaybackUpdateHandler() {
        return playbackUpdateHandler;
    }

}
