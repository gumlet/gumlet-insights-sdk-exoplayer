package com.gumlet.insights.calls.api;

import com.gumlet.insights.utils.Util;

public class ApiConfig {
    public static final String BASE_URL = "https://ingest.gumlytics.com";
    public static final int RANDOM_STRING_LENGTH = 24;
    public static final String DEVICE_CATEGORY = "PHONE";
    public static final String CLIENT_VERSION = "3.0";
    public static String SESSION_ID = Util.getRandomCharacterString();
    public static String PLAYER_INSTANCE_ID = Util.getRandomCharacterString();
    public static final boolean PRODUCTION_ENV = true;
}
