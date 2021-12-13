package com.gumlet.insights.calls;

import com.google.gson.annotations.SerializedName;

public class CommonEventResponse {
    @SerializedName("message")
    protected String message;

    @SerializedName("success")
    protected Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CommonEventResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
