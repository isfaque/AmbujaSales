package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.OrdersAdapter.OrdersSetterGetter;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrdersScreen.OrdersActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.OrdersAdapter.OrdersData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;

/**
 * Created by apps2 on 5/25/2017.
 */
public class OrdersPresenter {
    private OrdersActivityView view;

    private static final String TAG = "ORDERS_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public OrdersPresenter(OrdersActivityView view) {
        this.view = view;
    }

    public void getOrdersService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        OrdersData.ordersList.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if(status.equals("failed")){
                                view.errorInfo(message);
                            }else{
                                data = jsonObj.getJSONArray(TAG_DATA);
                                for (int i = 0; i < data.length(); i++) {
                                    e = data.getJSONObject(i);

                                    String ordId = e.getString("ordId");
                                    String ordName = e.getString("ordName");
                                    String ordUnitId = e.getString("ordUnitId");
                                    String ordUnit = e.getString("ordUnit");
                                    String ordVenueId = e.getString("ordVenueId");
                                    String ordVenue = e.getString("ordVenue");
                                    String ordQuantity = e.getString("ordQuantity");
                                    String ordAmount = e.getString("ordAmount");
                                    String ordDescription = e.getString("ordDescription");
                                    String ordForDate = e.getString("ordForDate");
                                    String ordStatusId = e.getString("ordStatusId");
                                    String ordStatus = e.getString("ordStatus");
                                    String ordDate = e.getString("ordDate");
                                    String ordTime = e.getString("ordTime");
                                    String ordMeetingName = e.getString("ordMeetingName");
                                    String ordCustomerName = e.getString("ordCustomerName");



                                    OrdersSetterGetter wp = new OrdersSetterGetter(
                                            ordId,
                                            ordName,
                                            ordUnitId,
                                            ordUnit,
                                            ordVenueId,
                                            ordVenue,
                                            ordQuantity,
                                            ordAmount,
                                            ordDescription,
                                            ordForDate,
                                            ordStatusId,
                                            ordStatus,
                                            ordDate,
                                            ordTime,
                                            ordMeetingName,
                                            ordCustomerName
                                    );

                                    // Binds all strings into an array
                                    OrdersData.ordersList.add(wp);

                                }

                                view.startGetOrders();

                            }



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            view.errorInfo("Server not Responding, Please check your Internet Connection");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorInfo("Server not Responding, Please check your Internet Connection");
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }


    public void deleteOrderService(String url, final String userId, final String ordId){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str_response = response;

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);
                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");
                            if(status.equals("failed")){
                                view.errorOrderDeleteInfo(message);
                            }else{
                                view.successOrderDeleteInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        view.errorOrderDeleteInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("ordId",ordId);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
