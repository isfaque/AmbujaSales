package com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen;

/**
 * Created by apps2 on 5/3/2017.
 */
public interface LeaveRequestActivityView {
    void startMainActivity();
    void errorInfo(String msg);

    void startGetLeaveRequest();

    void errorLeaveRequestDeleteInfo(String msg);
    void successLeaveRequestDeleteInfo(String msg);
}
