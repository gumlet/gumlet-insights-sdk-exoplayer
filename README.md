# gumlet-insights-sdk-exoplayer

# Getting started
## Gradle
Gumlet insights integration with Exoplayer for Android native application helps to collect video insights using Gumlet platform. 

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
GumletInsightsConfig gumletInsightsConfig = new GumletInsightsConfig("<PROPERTY_ID>", "<CONTEXT>");

// Create a Gumlet ExoPlayerCollector object using the GumletInsightsConfig that we have just created
ExoPlayerCollector insightsCollector = new ExoPlayerCollector(gumletInsightsConfig, <CONTEXT object>);

// Attach exoplayer instance
insightsCollector.attachPlayer(player);

// Detach your player when you are done. For example, call this method when you call the release() method
insightsCollector.detachPlayer();
```

#### Switching to a new video
When switching/changing to a new video we recommend that you follow the sequence of steps given below.

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
gumletInsightsConfig.setVideoId("VIDEDOID");
gumletInsightsConfig.setCustomUserId("CUSTOM_USER_ID");
gumletInsightsConfig.setCustomData1("DATA1");
gumletInsightsConfig.setCustomData2("DATA2");
gumletInsightsConfig.setCustomData3("DATA3");
gumletInsightsConfig.setCustomData4("DATA4");
gumletInsightsConfig.setCustomData5("DATA5");
gumletInsightsConfig.setCustomData6("DATA6");
gumletInsightsConfig.setCustomData7("DATA7"); 

```

A [full example app](https://github.com/gumlet/gumlet-insights-sdk-exoplayer/tree/main/app/src) can be seen in the github repo