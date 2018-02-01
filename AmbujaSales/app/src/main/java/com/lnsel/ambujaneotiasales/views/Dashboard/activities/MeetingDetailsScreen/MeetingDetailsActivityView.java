package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingDetailsScreen;

/**
 * Created by apps2 on 5/4/2017.
 */
public interface MeetingDetailsActivityView {
    void startMeetingsActivity();
    void startMeetingPictureActivity();

    void errorInfo(String msg);
    void successInfo(String msg);

    void errorMeetingVisitedInfo(String msg);
    void successMeetingVisitedInfo(String msg);

    void errorMeetingRemarksInfo(String msg);
    void successMeetingRemarksInfo(String msg);

    void errorMeetingNextVisitInfo(String msg);
    void successMeetingNextVisitInfo(String msg);

    void startGetMeetingHistory();
    void errorMeetingHistoryInfo(String msg);
}
