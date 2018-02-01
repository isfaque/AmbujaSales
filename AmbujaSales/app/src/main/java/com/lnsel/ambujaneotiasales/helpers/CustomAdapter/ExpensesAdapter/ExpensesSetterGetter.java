package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter;

/**
 * Created by apps2 on 5/10/2017.
 */
public class ExpensesSetterGetter {

    private String expId;
    private String expTitle;
    private String expAmount;
    private String expDescription;
    private String expImage;
    private String expImageStatus;
    private String expParentId;
    private String expParentPath;
    private String expStatus;
    private String expIsMeetingAssociated;
    private String expMeetingName;
    private String expMeetingCustomerName;
    private String expPaymentStatus;


    public ExpensesSetterGetter(String expId, String expTitle, String expAmount, String expDescription, String expImage, String expImageStatus, String expParentId, String expParentPath, String expStatus, String expIsMeetingAssociated, String expMeetingName, String expMeetingCustomerName, String expPaymentStatus) {
        this.expId = expId;
        this.expTitle = expTitle;
        this.expAmount = expAmount;
        this.expDescription = expDescription;
        this.expImage = expImage;
        this.expImageStatus = expImageStatus;
        this.expParentId = expParentId;
        this.expParentPath = expParentPath;
        this.expStatus = expStatus;
        this.expIsMeetingAssociated = expIsMeetingAssociated;
        this.expMeetingName = expMeetingName;
        this.expMeetingCustomerName = expMeetingCustomerName;
        this.expPaymentStatus = expPaymentStatus;
    }

    public String getExpId() {
        return this.expId;
    }

    public String getExpTitle() {
        return this.expTitle;
    }

    public String getExpAmount() {
        return this.expAmount;
    }

    public String getExpDescription() {
        return this.expDescription;
    }

    public String getExpImage() {
        return this.expImage;
    }

    public String getExpImageStatus() {
        return this.expImageStatus;
    }

    public String getExpParentId() {
        return this.expParentId;
    }

    public String getExpParentPath() {
        return this.expParentPath;
    }

    public String getExpStatus() {
        return this.expStatus;
    }

    public String getExpIsMeetingAssociated() {
        return this.expIsMeetingAssociated;
    }

    public String getExpMeetingName() {
        return this.expMeetingName;
    }

    public String getExpMeetingCustomerName() {
        return this.expMeetingCustomerName;
    }

    public String getExpPaymentStatus() {
        return this.expPaymentStatus;
    }

}
