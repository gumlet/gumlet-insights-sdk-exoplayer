package com.gumlet.insights.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.gumlet.insights.ViewerSession;

public class GumletPreferenceManager {

    private static final String PREF_NAME = "GumletPreferences";
    private static final String KEY_SESSION_TIMEOUT = "session_timeout";
    private static final String KEY_SESSION = "session";

    private SharedPreferences shPref;
    private SharedPreferences.Editor editor;
    private Context context;

    int PRIVATE_MODE = 0;

    private static GumletPreferenceManager preferenceManager;
    public static GumletPreferenceManager getInstance(Context context){

        if(preferenceManager == null){
            preferenceManager = new GumletPreferenceManager(context);
        }

        return  preferenceManager;
    }

    private GumletPreferenceManager(Context context) {
        this.context = context;
        shPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = shPref.edit();
    }

    public void setSessionTimeout(long timeout){
        editor.putLong(KEY_SESSION_TIMEOUT, timeout);
        editor.commit();
    }
    public long getSessionTimeout(){
        return shPref.getLong(KEY_SESSION_TIMEOUT, 0);
    }


    public void saveSession(ViewerSession viewerSession){
        if(viewerSession != null) {
            String session = new Gson().toJson(viewerSession);
            editor.putString(KEY_SESSION, session);
            editor.commit();
        }
    }

    public ViewerSession restoreSession(){
        String session = shPref.getString(KEY_SESSION, "");

        if(!TextUtils.isEmpty(session)){
            ViewerSession viewerSession = new Gson().fromJson(session,ViewerSession.class);
            return viewerSession;
        }

        return null;
    }
    public void clearPreferences(){
        editor.clear();
        editor.commit();
    }
}
