package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter;

/**
 * Created by apps2 on 5/24/2017.
 */
public class AttendanceSetterGetter {

    private String lgnrSNo;
    private String lgnrUserName;
    private String lgnrLoginDate;
    private String lgnrLoginTime;
    private String lgnrLogoutDate;
    private String lgnrLogoutTime;
    private String lgnrTotalTime;
    private String lgnrLoginStatus;


    public AttendanceSetterGetter(String lgnrSNo, String lgnrUserName, String lgnrLoginDate, String lgnrLoginTime, String lgnrLogoutDate, String lgnrLogoutTime, String lgnrTotalTime, String lgnrLoginStatus) {
        this.lgnrSNo = lgnrSNo;
        this.lgnrUserName = lgnrUserName;
        this.lgnrLoginDate = lgnrLoginDate;
        this.lgnrLoginTime = lgnrLoginTime;
        this.lgnrLogoutDate = lgnrLogoutDate;
        this.lgnrLogoutTime = lgnrLogoutTime;
        this.lgnrTotalTime = lgnrTotalTime;
        this.lgnrLoginStatus = lgnrLoginStatus;
    }

    public String getLgnrSNo() {
        return this.lgnrSNo;
    }

    public String getLgnrUserName() {
        return this.lgnrUserName;
    }

    public String getLgnrLoginDate() {
        return this.lgnrLoginDate;
    }

    public String getLgnrLoginTime() {
        return this.lgnrLoginTime;
    }

    public String getLgnrLogoutDate() {
        return this.lgnrLogoutDate;
    }

    public String getLgnrLogoutTime() {
        return this.lgnrLogoutTime;
    }

    public String getLgnrTotalTime() {
        return this.lgnrTotalTime;
    }

    public String getLgnrLoginStatus() {
        return this.lgnrLoginStatus;
    }
}
