package com.gumlet.insights

import com.gumlet.insights.data.CustomData

interface Collector<TPlayer> {
    var customData: CustomData
    val impressionId: String
    val config: GumletInsightsConfig

    fun attachPlayer(player: TPlayer)
    fun detachPlayer()
    fun setCustomDataOnce(customData: CustomData)

    fun addDebugListener(listener: GumletInsights.DebugListener)
    fun removeDebugListener(listener: GumletInsights.DebugListener)
}
