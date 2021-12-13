package com.gumlet.insights

import android.content.Context
import com.gumlet.insights.adapters.PlayerAdapter
import com.gumlet.insights.data.CustomData
import com.gumlet.insights.data.DeviceInformationProvider

abstract class DefaultCollector<TPlayer> protected constructor(private val insights: GumletInsights) {
    var customData: CustomData
        get() = insights.customData
        set(value) { insights.customData = value }

    val impressionId: String
        get() = insights.impressionId

    val config: GumletInsightsConfig
        get() = insights.config

    protected abstract fun createAdapter(player: TPlayer, insights: GumletInsights): PlayerAdapter

    fun attachPlayer(player: TPlayer) {
        val adapter = createAdapter(player, insights)
        insights.attach(adapter)
    }

    fun detachPlayer() {
        insights.detachPlayer()
    }

    fun setCustomDataOnce(customData: CustomData) {
        insights.setCustomDataOnce(customData)
    }

    fun addDebugListener(listener: GumletInsights.DebugListener) {
        insights.addDebugListener(listener)
    }

    fun removeDebugListener(listener: GumletInsights.DebugListener) {
        insights.removeDebugListener(listener)
    }

    companion object {
        fun createAnalytics(gumletInsightsConfig: GumletInsightsConfig, context: Context, userAgent: String): GumletInsights {
            val deviceInformationProvider = DeviceInformationProvider(context, userAgent)
            return GumletInsights(
                gumletInsightsConfig,
                context,
                deviceInformationProvider
            )
        }
    }
}
