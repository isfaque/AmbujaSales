package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter;

/**
 * Created by apps2 on 5/16/2017.
 */
public class LeaveRequestSetterGetter {

    private String lreqId;
    private String lreqSubject;
    private String lreqDescription;
    private String lreqStatus;
    private String lreqStatusMessage;


    public LeaveRequestSetterGetter(String lreqId, String lreqSubject, String lreqDescription, String lreqStatus, String lreqStatusMessage) {
        this.lreqId = lreqId;
        this.lreqSubject = lreqSubject;
        this.lreqDescription = lreqDescription;
        this.lreqStatus = lreqStatus;
        this.lreqStatusMessage = lreqStatusMessage;
    }

    public String getLreqId() {
        return this.lreqId;
    }

    public String getLreqSubject() {
        return this.lreqSubject;
    }

    public String getLreqDescription() {
        return this.lreqDescription;
    }

    public String getLreqStatus() {
        return this.lreqStatus;
    }

    public String getLreqStatusMessage() {
        return this.lreqStatusMessage;
    }

}
