package com.gumlet.insights

import com.gumlet.insights.data.AdEventData
import com.gumlet.insights.data.EventData

interface DebugCallback {
    fun dispatchEventData(data: EventData)
    fun dispatchAdEventData(data: AdEventData)
    fun message(message: String)
}
