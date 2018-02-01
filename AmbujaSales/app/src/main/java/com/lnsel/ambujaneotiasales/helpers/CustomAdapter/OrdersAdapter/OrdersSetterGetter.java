package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.OrdersAdapter;

/**
 * Created by apps2 on 5/25/2017.
 */
public class OrdersSetterGetter {
    private String ordId;
    private String ordName;
    private String ordUnitId;
    private String ordUnit;
    private String ordVenueId;
    private String ordVenue;
    private String ordQuantity;
    private String ordAmount;
    private String ordDescription;
    private String ordForDate;
    private String ordStatusId;
    private String ordStatus;
    private String ordDate;
    private String ordTime;
    private String ordMeetingName;
    private String ordCustomerName;


    public OrdersSetterGetter(
            String ordId,
            String ordName,
            String ordUnitId,
            String ordUnit,
            String ordVenueId,
            String ordVenue,
            String ordQuantity,
            String ordAmount,
            String ordDescription,
            String ordForDate,
            String ordStatusId,
            String ordStatus,
            String ordDate,
            String ordTime,
            String ordMeetingName,
            String ordCustomerName
    ) {
        this.ordId = ordId;
        this.ordName = ordName;
        this.ordUnitId = ordUnitId;
        this.ordUnit = ordUnit;
        this.ordVenueId = ordVenueId;
        this.ordVenue = ordVenue;
        this.ordQuantity = ordQuantity;
        this.ordAmount = ordAmount;
        this.ordDescription = ordDescription;
        this.ordForDate = ordForDate;
        this.ordStatusId = ordStatusId;
        this.ordStatus = ordStatus;
        this.ordDate = ordDate;
        this.ordTime = ordTime;
        this.ordMeetingName = ordMeetingName;
        this.ordCustomerName = ordCustomerName;
    }

    public String getOrdId() {
        return this.ordId;
    }

    public String getOrdName() {
        return this.ordName;
    }

    public String getOrdUnitId() {
        return this.ordUnitId;
    }

    public String getOrdUnit() {
        return this.ordUnit;
    }

    public String getOrdVenueId() {
        return this.ordVenueId;
    }

    public String getOrdVenue() {
        return this.ordVenue;
    }

    public String getOrdQuantity() {
        return this.ordQuantity;
    }

    public String getOrdAmount() {
        return this.ordAmount;
    }

    public String getOrdDescription() {
        return this.ordDescription;
    }

    public String getOrdForDate() {
        return this.ordForDate;
    }

    public String getOrdStatusId() {
        return this.ordStatusId;
    }

    public String getOrdStatus() {
        return this.ordStatus;
    }

    public String getOrdDate() {
        return this.ordDate;
    }

    public String getOrdTime() {
        return this.ordTime;
    }

    public String getOrdMeetingName() {
        return this.ordMeetingName;
    }

    public String getOrdCustomerName() {
        return this.ordCustomerName;
    }
}
