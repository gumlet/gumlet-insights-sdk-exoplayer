package com.gumlet.insights;

import android.os.Parcel;
import android.os.Parcelable;

import com.gumlet.insights.calls.api.ApiConfig;

public class CollectorConfig implements Parcelable {
    private String backendUrl = ApiConfig.BASE_URL;

    private Boolean tryResendDataOnFailedConnection = false;

    public CollectorConfig() {}

    protected CollectorConfig(Parcel in) {
        backendUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backendUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CollectorConfig> CREATOR =
            new Creator<CollectorConfig>() {
                @Override
                public CollectorConfig createFromParcel(Parcel in) {
                    return new CollectorConfig(in);
                }

                @Override
                public CollectorConfig[] newArray(int size) {
                    return new CollectorConfig[size];
                }
            };

    /**
     * Get the URL of the Gumlet Analytics backend.
     *
     * @return
     */
    public String getBackendUrl() {
        return backendUrl;
    }

    /**
     * Get if failed requests should be resent again
     *
     * @return
     */
    public Boolean getTryResendDataOnFailedConnection() {
        return tryResendDataOnFailedConnection;
    }

}
