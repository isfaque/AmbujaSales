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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomerDetailsAdapter.CustomerDetailsData;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomerDetailsAdapter.CustomerDetailsSetterGetter;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerDetailsScreen.CustomerDetailsActivityView;

/**
 * Created by apps2 on 5/13/2017.
 */
public class CustomerDetailsPresenter {

    private CustomerDetailsActivityView view;

    private static final String TAG = "CUSTOMER_DETAIL_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public CustomerDetailsPresenter(CustomerDetailsActivityView view) {
        this.view = view;
    }


    public void getCustomerInfoDetailService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        CustomerDetailsData.customerDetailsList.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if (status.equals("failed")) {
                                view.errorInfo(message);
                            } else {
                                data = jsonObj.getJSONArray(TAG_DATA);
                                for (int i = 0; i < data.length(); i++) {
                                    e = data.getJSONObject(i);
                                    String Pick_Id = e.getString("mtnName");
                                    Log.i("Author is", Pick_Id);

                                    String mtnId = e.getString("mtnId");
                                    String mtnName = e.getString("mtnName");
                                    String mtnDate = e.getString("mtnDate");
                                    String mtnTime = e.getString("mtnTime");
                                    String mtnUserName = e.getString("mtnUserName");
                                    String mtnVisited = e.getString("mtnVisited");
                                    String mtnCompleted = e.getString("mtnCompleted");
                                    String mtnRemarksMessage = e.getString("mtnRemarksMessage");

                                    CustomerDetailsSetterGetter wp = new CustomerDetailsSetterGetter(mtnId, mtnName, mtnDate, mtnTime, mtnUserName, mtnVisited, mtnCompleted, mtnRemarksMessage);

                                    // Binds all strings into an array
                                    CustomerDetailsData.customerDetailsList.add(wp);

                                }

                                view.startGetCustomerInfoDetail();

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

    public void addCustomerMeetingService(String url, final String userId, final String mtnCustomerId, final String mtnName, final String mtnMeetingTypeId, final String mtnMeetingType, final String mtnDate, final String mtnTime, final String userParentPath) {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String str_response = response;
                        try {
                            JSONObject jsonObj = new JSONObject(str_response);
                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");
                            if(status.equals("failed")){
                                view.errorAddCustomerMeetingInfo(message);
                            }else{
                                view.successAddCustomerMeetingInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            view.errorInfo("Server not Responding, Please check your Internet Connection");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorAddCustomerMeetingInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("mtnCustomerId", mtnCustomerId);
                params.put("mtnName", mtnName);
                params.put("mtnMeetingTypeId", mtnMeetingTypeId);
                params.put("mtnMeetingType", mtnMeetingType);
                params.put("mtnDate", mtnDate);
                params.put("mtnTime", mtnTime);
                params.put("userParentPath", userParentPath);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
