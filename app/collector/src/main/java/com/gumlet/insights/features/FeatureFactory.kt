package com.gumlet.insights.features

import com.gumlet.insights.license.FeatureConfigContainer

interface FeatureFactory {
    fun createFeatures(): Collection<Feature<FeatureConfigContainer, *>>
}
