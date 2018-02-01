package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingDetailsAdapter;

/**
 * Created by apps2 on 6/5/2017.
 */
public class MeetingDetailsSetterGetter {
    private String mtnId;
    private String mtnName;
    private String mtnDate;
    private String mtnTime;
    private String mtnUserName;
    private String mtnVisited;
    private String mtnCompleted;
    private String mtnRemarksMessage;

    public MeetingDetailsSetterGetter(String mtnId, String mtnName, String mtnDate, String mtnTime, String mtnUserName, String mtnVisited, String mtnCompleted, String mtnRemarksMessage) {
        this.mtnId = mtnId;
        this.mtnName = mtnName;
        this.mtnDate = mtnDate;
        this.mtnTime = mtnTime;
        this.mtnUserName = mtnUserName;
        this.mtnVisited = mtnVisited;
        this.mtnCompleted = mtnCompleted;
        this.mtnRemarksMessage = mtnRemarksMessage;
    }

    public String getMtnId() {
        return this.mtnId;
    }

    public String getMtnName() {
        return this.mtnName;
    }

    public String getMtnDate() {
        return this.mtnDate;
    }

    public String getMtnTime() {
        return this.mtnTime;
    }

    public String getMtnUserName() {
        return this.mtnUserName;
    }

    public String getMtnVisited() {
        return this.mtnVisited;
    }

    public String getMtnCompleted() {
        return this.mtnCompleted;
    }

    public String getMtnRemarksMessage() {
        return this.mtnRemarksMessage;
    }
}
