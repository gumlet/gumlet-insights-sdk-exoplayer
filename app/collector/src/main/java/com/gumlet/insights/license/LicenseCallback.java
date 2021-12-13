package com.gumlet.insights.license;

public interface LicenseCallback {
    void configureFeatures(boolean authenticated, FeatureConfigContainer featureConfigs);

    void authenticationCompleted(boolean success);
}
