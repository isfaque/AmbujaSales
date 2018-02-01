package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen;

/**
 * Created by apps2 on 5/3/2017.
 */
public interface MeetingsActivityView {
    void startMainActivity();

    void startMeetingDetailsActivity();

    void startMeetingAddActivity();

    void errorInfo(String msg);

    void errorMeetingCompletedInfo(String msg);
    void successMeetingCompletedInfo(String msg);


    void startGetMeetings();
}
