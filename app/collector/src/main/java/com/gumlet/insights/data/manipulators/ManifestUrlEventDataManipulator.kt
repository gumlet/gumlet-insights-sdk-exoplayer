package com.gumlet.insights.data.manipulators

import com.gumlet.insights.GumletInsightsConfig
import com.gumlet.insights.adapters.PlayerAdapter
import com.gumlet.insights.data.EventData

/**
 * Decorates the event data with the m3u8 and mpd url if they are set in the insights configuration.
 */
open class ManifestUrlEventDataManipulator(
    private val playerAdapter: PlayerAdapter,
    private val gumletInsightsConfig: GumletInsightsConfig
) : EventDataManipulator {
    override fun manipulate(data: EventData) {
        val currentSourceMetadata = playerAdapter.currentSourceMetadata
        if (currentSourceMetadata != null) {
            if (currentSourceMetadata.m3u8Url != null) {
                data.m3u8Url = currentSourceMetadata.m3u8Url
            }
            if (currentSourceMetadata.mpdUrl != null) {
                data.mpdUrl = currentSourceMetadata.mpdUrl
            }
        } else {
            if (gumletInsightsConfig.m3u8Url != null) {
                data.m3u8Url = gumletInsightsConfig.m3u8Url
            }
            if (gumletInsightsConfig.mpdUrl != null) {
                data.mpdUrl = gumletInsightsConfig.mpdUrl
            }
        }
    }
}
