package com.gumlet.insights.data;

import com.gumlet.insights.license.FeatureConfigContainer;

public class LicenseResponse {
    private String status;
    private String message;
    private FeatureConfigContainer features;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FeatureConfigContainer getFeatures() {
        return features;
    }

    public void setFeatures(FeatureConfigContainer features) {
        this.features = features;
    }
}
