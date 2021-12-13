package com.gumlet.insights.license;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.gumlet.insights.GumletInsightsConfig;
import com.gumlet.insights.data.LicenseCallData;
import com.gumlet.insights.data.LicenseResponse;
import com.gumlet.insights.utils.ClientFactory;
import com.gumlet.insights.utils.DataSerializer;
import com.gumlet.insights.utils.GumletLog;
import com.gumlet.insights.utils.HttpClient;
import com.gumlet.insights.utils.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LicenseCall {
    private static final String TAG = "LicenseCall";
    private final String backendUrl;
    private final GumletInsightsConfig config;
    private final Context context;
    private final HttpClient httpClient;

    public LicenseCall(GumletInsightsConfig config, Context context) {
        this.config = config;
        this.context = context;
        this.backendUrl =
                Uri.parse(config.getConfig().getBackendUrl())
                        .buildUpon()
                        .appendEncodedPath("licensing")
                        .build()
                        .toString();


       GumletLog.d(TAG, String.format("Initialized License Call with backendUrl: %s", backendUrl));
        this.httpClient =
                new HttpClient(context, new ClientFactory().createClient(config.getConfig()));
    }

    public void authenticate(final AuthenticationCallback callback) {
        final LicenseCallData data = new LicenseCallData();
        data.setKey(this.config.getKey());
        data.setAnalyticsVersion(Util.getAnalyticsVersion());
        data.setDomain(Util.getDomain(context));
        String json = DataSerializer.serialize(data);
        httpClient.post(
                this.backendUrl,
                json,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        GumletLog.e(TAG, "\n\nLicense call failed due to connectivity issues\n\n", e);
                        //callback.authenticationCompleted(false, null);
                        callback.authenticationCompleted(true, null);
                        GumletLog.e(TAG, "\n\nLicense call failed due to connectivity issues\n\n", e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response == null || response.body() == null) {
                            GumletLog.e(TAG, "\n\nLicense call was denied without providing a response body\n\n");
                            //callback.authenticationCompleted(false, null);
                            callback.authenticationCompleted(true, null);
                            GumletLog.e(TAG, "\n\nLicense call was denied without providing a response body\n\n");
                            return;
                        }

                        String licensingResponseBody = response.body().string();
                        LicenseResponse licenseResponse =
                                DataSerializer.deserialize(
                                        licensingResponseBody, LicenseResponse.class);
                        if (licenseResponse == null) {
                            GumletLog.e(TAG, "\n\nLicense call was denied without providing a response body\n\n");
                            //callback.authenticationCompleted(false, null);
                            callback.authenticationCompleted(true, null);
                            GumletLog.e(TAG, "\n\nLicense call was denied without providing a response body\n\n");
                            return;
                        }

                        if (licenseResponse.getStatus() == null) {
                            GumletLog.e(TAG, String.format("\n\nLicense response was denied without status\n\n"));
                            //callback.authenticationCompleted(false, null);
                            callback.authenticationCompleted(true, null);
                            GumletLog.e(TAG, String.format("\n\nLicense response was denied without status\n\n"));
                            return;
                        }

                        if (!licenseResponse.getStatus().equals("granted")) {
                            GumletLog.e(
                                    TAG,
                                    String.format(
                                            "\n\n*************License response was denied: %s**************\n\n",
                                            licenseResponse.getMessage()));

                            //callback.authenticationCompleted(false, null);
                            callback.authenticationCompleted(true, null);


                            GumletLog.e(
                                    TAG,
                                    String.format(
                                            "\n\n*************License response was denied: %s**************\n\n",
                                            licenseResponse.getMessage()));
                            return;
                        }
                        GumletLog.e(TAG, "License response was granted");
                        callback.authenticationCompleted(true, licenseResponse.getFeatures());
                    }
                });
    }
}
