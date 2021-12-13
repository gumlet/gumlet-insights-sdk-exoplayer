package com.gumlet.insights.data

import com.gumlet.insights.retryBackend.OnFailureCallback

interface CallbackBackend {
    fun send(eventData: EventData, callback: OnFailureCallback?)
    fun sendAd(eventData: AdEventData, callback: OnFailureCallback?)
}
