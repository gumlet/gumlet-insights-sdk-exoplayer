# gumlet-insights-sdk-exoplayer

# Getting started
## Gradle
Gumlet Insights integration with Exoplayer for Android native applications helps to collect video insights. 

And this line to your main project(app) `build.gradle`

For Gumlet Exoplayer Collector:
```
dependencies {
    implementation 'com.gumlet.gumlet-insights-sdk-exoplayer:collector-exoplayer:0.0.2'
}
```

## Examples

The following example create a Gumlet Insights Config object and attach it to Exoplayer instance.

#### Basic video insights monitoring with Gumlet Exoplayer SDK
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