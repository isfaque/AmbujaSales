package com.lnsel.ambujaneotiasales.views.LoginScreen;

/**
 * Created by apps2 on 4/20/2017.
 */
public interface LoginActivityView {
    void startMainActivity();
    void errorInfo(String msg);

    void updateSession(String userId, String userLoginRecordId, String userName, String userPassword, String userFirstName, String userLastName, String userEmail, String userContactNo, String userDesignation, String userParentId, String userParentPath,String userFirebaseToken);
}
