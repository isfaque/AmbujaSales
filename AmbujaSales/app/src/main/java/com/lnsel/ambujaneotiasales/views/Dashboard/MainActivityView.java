package com.lnsel.ambujaneotiasales.views.Dashboard;

/**
 * Created by apps2 on 5/5/2017.
 */
public interface MainActivityView {
    void startLoginActivity();
    void startMainActivity();
    void startAttendanceActivity();
    void startMeetingsActivity();
    void startOrdersActivity();
    void startExpensesActivity();
    void startLeaveRequestActivity();
    void startCustomersActivity();
    void errorInfo(String msg);
    void successInfo(String msg);

    void updateStarTripSession(String trpId);

    void updateStopTripSession();

    void updatePushToken(String userPushToken);
}
