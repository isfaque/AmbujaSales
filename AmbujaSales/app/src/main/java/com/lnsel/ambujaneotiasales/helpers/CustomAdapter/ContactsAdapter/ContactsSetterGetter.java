package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter;

/**
 * Created by apps2 on 5/29/2017.
 */
public class ContactsSetterGetter {
    private String cntId;
    private String cntPersonName;
    private String cntContactNo;
    private String cntAddress;
    private String cntOtherDetails;
    private String cntStatus;


    public ContactsSetterGetter(String cntId, String cntPersonName, String cntContactNo, String cntAddress, String cntOtherDetails, String cntStatus) {
        this.cntId = cntId;
        this.cntPersonName = cntPersonName;
        this.cntContactNo = cntContactNo;
        this.cntAddress = cntAddress;
        this.cntOtherDetails = cntOtherDetails;
        this.cntStatus = cntStatus;
    }

    public String getCntId() {
        return this.cntId;
    }

    public String getCntPersonName() {
        return this.cntPersonName;
    }

    public String getCntContactNo() {
        return this.cntContactNo;
    }

    public String getCntAddress() {
        return this.cntAddress;
    }

    public String getCntOtherDetails() {
        return this.cntOtherDetails;
    }

    public String getCntStatus() {
        return this.cntStatus;
    }

}
