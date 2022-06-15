package com.gumlet.insights.calls.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofitClient = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(ApiConfig.PRODUCTION_ENV){
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }else{
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getClientForPropertyCheck() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(ApiConfig.PRODUCTION_ENV){
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }else{
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        //OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofitClient = new Retrofit.Builder().baseUrl("https://api.gumlet.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client)
                .build();

        return retrofitClient;
    }
}
