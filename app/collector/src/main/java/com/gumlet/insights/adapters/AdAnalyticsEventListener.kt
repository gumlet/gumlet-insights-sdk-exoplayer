package com.gumlet.insights.adapters

import com.gumlet.insights.ads.Ad
import com.gumlet.insights.ads.AdBreak
import com.gumlet.insights.ads.AdQuartile

interface AdAnalyticsEventListener {
    fun onAdStarted(ad: Ad)
    fun onAdFinished()
    fun onAdBreakStarted(adBreak: AdBreak)
    fun onAdBreakFinished()
    fun onAdClicked(url: String?)
    fun onAdError(adBreak: AdBreak, code: Int?, message: String?)
    fun onAdSkipped()
    fun onAdManifestLoaded(adBreak: AdBreak, downloadTime: Long)
    fun onAdQuartile(quartile: AdQuartile)
    fun onPlay()
    fun onPause()
}
