package com.gumlet.insights.features.errordetails

import com.gumlet.insights.features.FeatureConfig

data class ErrorDetailTrackingConfig(override val enabled: Boolean = false, val numberOfHttpRequests: Int? = null) : FeatureConfig
