package com.gumlet.insights.data;

import android.content.Context;
import android.os.Handler;

import com.gumlet.insights.GumletInsightsConfig;
import com.gumlet.insights.retryBackend.RetryBackend;

public class BackendFactory {

    public Backend createBackend(GumletInsightsConfig config, Context context) {
        HttpBackend httpBackend = new HttpBackend(config.getConfig(), context);
        if (!config.getConfig().getTryResendDataOnFailedConnection()) {
            return httpBackend;
        }

        return new RetryBackend(httpBackend, new Handler());
    }
}
