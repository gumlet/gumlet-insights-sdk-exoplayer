package com.gumlet.insights.exoplayer.features

import com.gumlet.insights.features.Feature
import com.gumlet.insights.features.FeatureFactory
import com.gumlet.insights.features.errordetails.ErrorDetailBackend
import com.gumlet.insights.features.errordetails.ErrorDetailTracking
import com.gumlet.insights.features.httprequesttracking.HttpRequestTracking
import com.gumlet.insights.license.FeatureConfigContainer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.gumlet.insights.GumletInsights

class ExoPlayerFeatureFactory(private val insights: GumletInsights, private val player: ExoPlayer) : FeatureFactory {
    override fun createFeatures(): Collection<Feature<FeatureConfigContainer, *>> {
        val features = mutableListOf<Feature<FeatureConfigContainer, *>>()
        var httpRequestTracking: HttpRequestTracking? = null
        if (player is SimpleExoPlayer) {
            val httpRequestTrackingAdapter = ExoPlayerHttpRequestTrackingAdapter(player, insights.onAnalyticsReleasingObservable)
            httpRequestTracking = HttpRequestTracking(httpRequestTrackingAdapter)
        }
        val errorDetailsBackend = ErrorDetailBackend(insights.config.config, insights.context)
        val errorDetailTracking = ErrorDetailTracking(insights.context, insights.config, insights, errorDetailsBackend, httpRequestTracking, insights.onErrorDetailObservable)
        features.add(errorDetailTracking)
        return features
    }
}
