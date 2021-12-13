package com.gumlet.insights.example

object Samples {
    val HLS_DRM_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae5371ee9511b7069d79/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")

    val VIDEO_ONE_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae2b71ee9511b7069b55/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_TWO_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/61542162498837c6ba2e1f77/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    //val VIDEO_THREE_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae5571ee9511b7069d92/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_FOUR_PROGRESSIVE = Sample("Progressive", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")


    val VIDEO_ONE = Sample("Progressive", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
    val VIDEO_TWO = Sample("Progressive", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
    val VIDEO_THREE_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae2b71ee9511b7069b55/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_FOUR_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/61542162498837c6ba2e1f77/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_FIVE_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae5571ee9511b7069d92/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_SIX_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae5371ee9511b7069d79/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")
    val VIDEO_SEVEN_WIDEVINE = Sample("Hls + Widevine", "https://video.gumlet.io/5f462c1561cf8a766464ffc4/614cae5171ee9511b7069d5a/1.m3u8", "widevine", "https://widevine-proxy.appspot.com/proxy")

    val HLS_REDBULL = Sample("HLSRedbulll", "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
    val DASH_LIVE = Sample("DashLive", "https://livesim.dashif.org/livesim/testpic_2s/Manifest.mpd")

    // These are IMA Sample Tags from https://developers.google.com/interactive-media-ads/docs/sdks/android/tags
    val IMA_AD_SOURCE_1 = Sample("ImaAdSource1", "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirecterror&nofb=1&correlator=")
    val IMA_AD_SOURCE_2 = Sample("ImaAdSource2", "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator=")
    val IMA_AD_SOURCE_3 = Sample("ImaAdSource3", "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator=")
    val IMA_AD_SOURCE_4 = Sample("ImaAdSource4", "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dredirectlinear&correlator=")
}
