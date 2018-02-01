package com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerDetailsScreen;

/**
 * Created by apps2 on 5/13/2017.
 */
public interface CustomerDetailsActivityView {
    void errorInfo(String msg);

    void startGetCustomerInfoDetail();

    void successAddCustomerMeetingInfo(String msg);
    void errorAddCustomerMeetingInfo(String msg);
}
