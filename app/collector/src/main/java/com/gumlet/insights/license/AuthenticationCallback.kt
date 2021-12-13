package com.gumlet.insights.license

interface AuthenticationCallback {
    fun authenticationCompleted(success: Boolean, featureConfigs: FeatureConfigContainer?)
}
