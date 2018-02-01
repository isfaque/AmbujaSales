package com.lnsel.ambujaneotiasales.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by apps2 on 5/5/2017.
 */
public class SharedManagerUtil {
    SharedPreferences pref, rem;
    SharedPreferences.Editor editor, remember;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "SalesPref";
    public static final String REM_PREF = "SalesRem";

    // All Save Login Credentials Keys for Remember
    public static final String IS_REMEMBER = "IsRemember";
    public static final String KEY_REM_USER_NAME = "remUserName";
    public static final String KEY_REM_USER_PASSWORD = "remUserPassword";



    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_LOGIN_RECORD_ID = "userLoginRecordId";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_FIRST_NAME = "userFirstName";
    public static final String KEY_USER_LAST_NAME = "userLastName";
    public static final String KEY_USER_EMAIL = "userEmail";
    public static final String KEY_USER_CONTACT_NO = "userContactNo";
    public static final String KEY_USER_DESIGNATION = "userDesignation";
    public static final String KEY_USER_PARENT_ID = "userParentId";
    public static final String KEY_USER_PARENT_PATH = "userParentPath";

    public static final String KEY_USER_PUSH_TOKEN = "userPushNotificationToken";

    public static final String IS_TRIP_START = "IsTripStart";
    public static final String KEY_USER_CURRENT_TRIP_ID = "userTripId";



    // Constructor
    public SharedManagerUtil(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        rem = _context.getSharedPreferences(REM_PREF, PRIVATE_MODE);

        editor = pref.edit();
        remember = rem.edit();
    }


    public void createRemember(
            String userName,
            String userPassword
    ){
        remember.putBoolean(IS_REMEMBER, true);
        remember.putString(KEY_REM_USER_NAME, userName);
        remember.putString(KEY_REM_USER_PASSWORD, userPassword);
        remember.commit();
    }

    public void clearRemember(){
        remember.clear();
        remember.commit();
    }



    public void createLoginSession(
            String userId,
            String userLoginRecordId,
            String userName,
            String userFirstName,
            String userLastName,
            String userEmail,
            String userContactNo,
            String userDesignation,
            String userParentId,
            String userParentPath,
            String userPushToken
    ){
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_LOGIN_RECORD_ID, userLoginRecordId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_FIRST_NAME, userFirstName);

        editor.putString(KEY_USER_LAST_NAME, userLastName);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.putString(KEY_USER_CONTACT_NO, userContactNo);
        editor.putString(KEY_USER_DESIGNATION, userDesignation);
        editor.putString(KEY_USER_PARENT_ID, userParentId);
        editor.putString(KEY_USER_PARENT_PATH, userParentPath);
        editor.putString(KEY_USER_PUSH_TOKEN, userPushToken);

        editor.commit();
    }

    public void createTripSession(String trpId){
        editor.putBoolean(IS_TRIP_START, true);
        editor.putString(KEY_USER_CURRENT_TRIP_ID, trpId);
        editor.commit();
    }

    public void stopTripSession(){
        editor.putBoolean(IS_TRIP_START, false);
        editor.commit();
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public void updatePushToken(String userPushToken){
        editor.putString(KEY_USER_PUSH_TOKEN, userPushToken);
        editor.commit();
    }

    public boolean isRemember(){
        return rem.getBoolean(IS_REMEMBER, false);
    }

    public String getRemUserName(){
        return rem.getString(KEY_REM_USER_NAME, null);
    }

    public String getRemUserPassword(){
        return rem.getString(KEY_REM_USER_PASSWORD, null);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isTripStart(){
        return pref.getBoolean(IS_TRIP_START, false);
    }

    public String getUserID(){
        return pref.getString(KEY_USER_ID, null);
    }

    public String getUserLoginRecordId(){
        return pref.getString(KEY_USER_LOGIN_RECORD_ID, null);
    }

    public String getUserName(){
        return pref.getString(KEY_USER_NAME, null);
    }

    public String getUserFirstName(){
        return pref.getString(KEY_USER_FIRST_NAME, null);
    }

    public String getUserLastName(){
        return pref.getString(KEY_USER_LAST_NAME, null);
    }

    public String getUserEmail(){
        return pref.getString(KEY_USER_EMAIL, null);
    }

    public String getUserContactNo(){
        return pref.getString(KEY_USER_CONTACT_NO, null);
    }

    public String getUserDesignation(){
        return pref.getString(KEY_USER_DESIGNATION, null);
    }

    public String getUserParentId(){
        return pref.getString(KEY_USER_PARENT_ID, null);
    }

    public String getUserParentPath(){
        return pref.getString(KEY_USER_PARENT_PATH, null);
    }

    public String getTripId(){
        return pref.getString(KEY_USER_CURRENT_TRIP_ID, null);
    }

    public String getKeyUserPushToken() {
        return pref.getString(KEY_USER_PUSH_TOKEN, null);
    }

}
