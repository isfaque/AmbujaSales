package com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrdersScreen;

/**
 * Created by apps2 on 5/25/2017.
 */
public interface OrdersActivityView {
    void startMainActivity();
    void errorInfo(String msg);

    void startGetOrders();

    void errorOrderDeleteInfo(String msg);
    void successOrderDeleteInfo(String msg);
}
