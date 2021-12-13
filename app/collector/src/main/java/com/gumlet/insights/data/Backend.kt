package com.gumlet.insights.data

interface Backend {
    fun send(eventData: EventData)
    fun sendAd(eventData: AdEventData)
}
