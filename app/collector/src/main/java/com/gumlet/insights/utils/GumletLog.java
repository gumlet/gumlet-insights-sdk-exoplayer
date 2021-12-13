package com.gumlet.insights.utils;

import android.util.Log;

import com.gumlet.insights.calls.api.ApiConfig;

public class GumletLog {

    public static void e(String tag,String message){

        if(ApiConfig.PRODUCTION_ENV){
            return;
        }
        Log.e(tag,message);

    }

    public static void e(String tag,String message,Throwable e){

        if(ApiConfig.PRODUCTION_ENV){
            return;
        }
        Log.e(tag,message,e);

    }

    public static void d(String tag,String message){
        if(ApiConfig.PRODUCTION_ENV){
            return;
        }
       Log.d(tag,message);
    }

    public static void d(String tag,String message,Throwable e){
        if(ApiConfig.PRODUCTION_ENV){
            return;
        }
       Log.d(tag,message,e);
    }
}
