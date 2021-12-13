package com.gumlet.insights.adapters

import com.gumlet.insights.Observable
import com.gumlet.insights.data.AdModuleInformation

interface AdAdapter : Observable<AdAnalyticsEventListener> {
    fun release()
    val isLinearAdActive: Boolean
    val moduleInformation: AdModuleInformation
    val isAutoplayEnabled: Boolean?
}
