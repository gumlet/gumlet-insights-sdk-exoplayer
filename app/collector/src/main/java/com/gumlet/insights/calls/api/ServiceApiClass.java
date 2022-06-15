package com.gumlet.insights.calls.api;

import com.gumlet.insights.PropertyCheckResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApiClass {

    @GET("insights/property/validate")
    Call<PropertyCheckResponse> checkPropertyId(@Query("property_id") String property_id);
}
