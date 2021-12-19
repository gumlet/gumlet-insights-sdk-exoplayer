# gumlet-insights-sdk-exoplayer

# Getting started

## Gradle

Gumlet insights integration with Exoplayer for Android native application helps to collect video
insights using Gumlet platform.

Follow below steps to integrate gumlet-insights-sdk-exoplayer in your android application.

To use "Gumlet Exoplayer Collector" add below line inside your main project (app) `build.gradle`

```
dependencies {
    implementation 'com.gumlet.gumlet-insights-sdk-exoplayer:collector-exoplayer:1.0.0'
}
```

## Examples

#### Basic video insights monitoring using Gumlet Exoplayer SDK

Generate property id from [here](https://www.gumlet.com/) and use it below

```java
// Create a Gumlet Insight's configuration using your Property Id and Context object
GumletInsightsConfig gumletInsightsConfig=new GumletInsightsConfig("<PROPERTY_ID>","<CONTEXT>");

// Create a Gumlet ExoPlayerCollector object using the GumletInsightsConfig that we have just created
        ExoPlayerCollector insightsCollector=new ExoPlayerCollector(gumletInsightsConfig,<CONTEXT object>);

// Attach exoplayer instance
        insightsCollector.attachPlayer(player);

// Detach your player when you are done. For example, call this method when you call the release() method
        insightsCollector.detachPlayer();
```

#### Switching to a new video

When switching/changing to a new video we recommend that you follow the sequence of steps given
below.

```java
//Detach your player when the first video is completed 
insightsCollector.detachPlayer();

//Update your config with new optional parameters related to the new video playback
        gumletInsightsConfig.setVideoId("NEW_VIDEO_ID");
        gumletInsightsConfig.setCustomData1("NEW_CUSTOM_DATA1");

//Reattach your player instance 
        insightsCollector.attachPlayer(newPlayer);
``` 

#### Optional Configuration Parameters

```java

                    // OPTIONAL - SET CUSTOM DATA
                    CustomData customData=new CustomData();
                    customData.setCustomData1("CUSTOM DATA 1");
                    customData.setCustomData2("CUSTOM DATA 2");
                    customData.setCustomData3("CUSTOM DATA 3");
                    customData.setCustomData4("CUSTOM DATA 4");
                    customData.setCustomData5("CUSTOM DATA 5");
                    customData.setCustomData6("CUSTOM DATA 6");
                    customData.setCustomData7("CUSTOM DATA 7");
                    customData.setCustomData8("CUSTOM DATA 8");
                    customData.setCustomData9("CUSTOM DATA 9");
                    customData.setCustomData10("CUSTOM DATA 10");
                    gumletInsightsConfig.setCustomData(customData);

                    // OPTIONAL - SET PLAYER NAME AND VERSION
                    PlayerData playerData=new PlayerData();
                    playerData.setPlayerName("PRO PLAYER");
                    playerData.setMetaPageType("IFRAME");
                    playerData.setPlayerIntegrationVersion("1.5.6");
                    gumletInsightsConfig.setPlayerData(playerData);

                    // OPTIONAL - SET USER CUSTOM DATA
                    UserData userData=new UserData();
                    userData.setCustomUserId("CUSTOM USER ID");
                    userData.setName("TEST USER");
                    userData.setEmail("demo@gumlet.com");
                    userData.setPhone("+91 9999999999");
                    userData.setProfileImage("ProfileImage.png");
                    userData.setAddressLine1("ADDRESS 1");
                    userData.setAddressLine2("ADDRESS 2");
                    userData.setCity("CITY");
                    userData.setState("STATE");
                    userData.setCountry("COUNTRY");
                    userData.setZipCode("ZIPCODE");
                    gumletInsightsConfig.setUserData(userData);

                    // OPTIONAL - SET VIDEO METADATA
                    VideoMetadata videoMetadata=new VideoMetadata();
                    videoMetadata.setCustomContentTye("CONTENT TYPE");
                    videoMetadata.setCustomVideoDurationMillis(10000L);
                    videoMetadata.setCustomEncodingVariant("ENCODING VARIANT");
                    videoMetadata.setCustomVideoLanguage("VIDEO LANGUAGE");
                    videoMetadata.setCustomVideoId("VIDEO ID");
                    videoMetadata.setCustomVideoSeries("VIDEO SERIES");
                    videoMetadata.setCustomVideoProducer("VIDEO PRODUCER");
                    videoMetadata.setCustomVideoTitle("VIDEO TITLE");
                    videoMetadata.setCustomVideoVariantName("VIDEO VARIENT NAME");
                    videoMetadata.setCustomVideoVariant("VIDEO VARIANT");
                    gumletInsightsConfig.setVideoMetadata(videoMetadata);

```

A [full example app](https://github.com/gumlet/gumlet-insights-sdk-exoplayer/tree/main/app/src) can
be seen in the github repo