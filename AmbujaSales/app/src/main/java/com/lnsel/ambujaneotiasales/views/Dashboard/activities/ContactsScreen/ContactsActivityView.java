package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactsScreen;

/**
 * Created by apps2 on 5/19/2017.
 */
public interface ContactsActivityView {
    void startMainActivity();
    void errorInfo(String msg);

    void startGetContacts();

    void errorContactDeleteInfo(String msg);
    void successContactDeleteInfo(String msg);
}
