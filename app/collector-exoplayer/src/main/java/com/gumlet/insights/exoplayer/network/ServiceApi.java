package com.gumlet.insights.exoplayer.network;

import com.gumlet.insights.ViewerSession;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface ServiceApi {

    @GET("/&event_family=session")
    Observable<String> viewer_session(@Header("Authorization") String authorization,
                                      @Query(ViewerSession.KEY_SESSION_ID) String sessionId,
                                      @Query(ViewerSession.KEY_PROPERTY_ID) String property_id,
                                      @Query(ViewerSession.KEY_USER_ID) String user_id,
                                      @Query(ViewerSession.KEY_CUSTOM_USER_ID) String custom_user_id,
                                      @Query(ViewerSession.KEY_VIEWER_CLIENT_VERSION) String viewer_client_version,
                                      @Query(ViewerSession.KEY_META_ASN) String meta_asn,
                                      @Query(ViewerSession.KEY_META_IP_ADDRESS) String meta_ip_address,
                                      @Query(ViewerSession.KEY_META_BROWSER) String meta_browser,
                                      @Query(ViewerSession.KEY_META_BROWSER_VERSION) String meta_browser_version,
                                      @Query(ViewerSession.KEY_META_USER_AGENT) String meta_user_agent,
                                      @Query(ViewerSession.KEY_META_DEVICE_DISPLAY_WIDTH) Integer meta_device_display_width,
                                      @Query(ViewerSession.KEY_META_DEVICE_DISPLAY_HEIGHT) Integer meta_device_display_height,
                                      @Query(ViewerSession.KEY_META_DEVICE_DISPLAY_DPR) Integer meta_device_display_dpr,
                                      @Query(ViewerSession.KEY_META_DEVICE_IS_TOUCHSCREEN) Boolean meta_device_is_touchscreen,
                                      @Query(ViewerSession.KEY_META_OPERATING_SYSTEM) String meta_operating_system,
                                      @Query(ViewerSession.KEY_META_OPERATING_SYSTEM_VERSION) String meta_operating_system_version,
                                      @Query(ViewerSession.KEY_META_DEVICE_CATEGORY) String meta_device_category,
                                      @Query(ViewerSession.KEY_META_CONNECTION_TYPE) String meta_connection_type,
                                      @Query(ViewerSession.KEY_META_CONNECTION_SPEED) String meta_connection_speed,
                                      @Query(ViewerSession.KEY_META_DEVICE_MANUFACTURER) String meta_device_manufacturer,
                                      @Query(ViewerSession.KEY_META_DEVICE_NAME) String meta_device_name,
                                      @Query(ViewerSession.KEY_META_CITY) String meta_city,
                                      @Query(ViewerSession.KEY_META_COUNTRY_CODE) String meta_country_code,
                                      @Query(ViewerSession.KEY_META_COUNTRY) String meta_country,
                                      @Query(ViewerSession.KEY_META_CONTINENT_CODE) String meta_continent_code,
                                      @Query(ViewerSession.KEY_META_REGION) String meta_region,
                                      @Query(ViewerSession.KEY_META_LATITUDE) String meta_latitude,
                                      @Query(ViewerSession.KEY_META_LONGITUDE) String meta_longitude,
                                      @Query(ViewerSession.KEY_META_DEVICE_ARCHITECTURE) String meta_device_architecture,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_1) String custom_data_1,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_2) String custom_data_2,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_3) String custom_data_3,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_4) String custom_data_4,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_5) String custom_data_5,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_6) String custom_data_6,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_7) String custom_data_7,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_8) String custom_data_8,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_9) String custom_data_9,
                                      @Query(ViewerSession.KEY_CUSTOM_DATA_10) String custom_data_10,
                                      @Query(ViewerSession.KEY_ERROR_CODE) String error_code,
                                      @Query(ViewerSession.KEY_ERROR) String error,
                                      @Query(ViewerSession.KEY_ERROR_TEXT) String error_text,
                                      @Query(ViewerSession.KEY_USER_NAME) String user_name,
                                      @Query(ViewerSession.KEY_USER_EMAIL) String user_email,
                                      @Query(ViewerSession.KEY_USER_PHONE) String user_phone,
                                      @Query(ViewerSession.KEY_USER_PROFILE_IMAGE) String user_profile_image,
                                      @Query(ViewerSession.KEY_USER_ADDRESS_LINE1) String user_address_line1,
                                      @Query(ViewerSession.KEY_USER_ADDRESS_LINE2) String user_address_line2,
                                      @Query(ViewerSession.KEY_USER_CITY) String user_city,
                                      @Query(ViewerSession.KEY_USER_STATE) String user_state,
                                      @Query(ViewerSession.KEY_USER_COUNTRY) String user_country,
                                      @Query(ViewerSession.KEY_USER_ZIPCODE) String user_zipcode,
                                      @Query(ViewerSession.KEY_TIMESTAMP) Long Z);

}
