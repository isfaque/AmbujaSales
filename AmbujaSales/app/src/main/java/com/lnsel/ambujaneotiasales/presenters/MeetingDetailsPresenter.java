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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingDetailsAdapter.MeetingDetailsData;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingDetailsAdapter.MeetingDetailsSetterGetter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingDetailsScreen.MeetingDetailsActivityView;

/**
 * Created by apps2 on 5/5/2017.
 */
public class MeetingDetailsPresenter {

    private MeetingDetailsActivityView view;

    private static final String TAG = "REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public MeetingDetailsPresenter(MeetingDetailsActivityView view) {
        this.view = view;
    }

    public void getMeetingDetailsService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if(status.equals("success")){

                                MeetingsData.get_current_mtnId = jsonObj.getString("mtnId");
                                MeetingsData.get_current_mtnName = jsonObj.getString("mtnName");
                                MeetingsData.get_current_mtnMeetingTypeId = jsonObj.getString("mtnMeetingTypeId");
                                MeetingsData.get_current_mtnMeetingType = jsonObj.getString("mtnMeetingType");
                                MeetingsData.get_current_mtnVisited = jsonObj.getString("mtnVisited");
                                MeetingsData.get_current_mtnNextVisit = jsonObj.getString("mtnNextVisit");
                                MeetingsData.get_current_mtnSignature = jsonObj.getString("mtnSignature");
                                MeetingsData.get_current_mtnPicture = jsonObj.getString("mtnPicture");
                                MeetingsData.get_current_mtnRemarks = jsonObj.getString("mtnRemarks");
                                MeetingsData.get_current_mtnCompleted = jsonObj.getString("mtnCompleted");
                                MeetingsData.get_current_mtnCustomerId = jsonObj.getString("mtnCustomerId");
                                MeetingsData.get_current_mtnCustomerName = jsonObj.getString("mtnCustomerName");
                                MeetingsData.get_current_mtnCustomerGenderId = jsonObj.getString("mtnCustomerGenderId");
                                MeetingsData.get_current_mtnCustomerGender = jsonObj.getString("mtnCustomerGender");
                                MeetingsData.get_current_mtnCustomerDOB = jsonObj.getString("mtnCustomerDOB");
                                MeetingsData.get_current_mtnCustomerDOA = jsonObj.getString("mtnCustomerDOA");
                                MeetingsData.get_current_mtnCustomerTypeId = jsonObj.getString("mtnCustomerTypeId");
                                MeetingsData.get_current_mtnCustomerType = jsonObj.getString("mtnCustomerType");
                                MeetingsData.get_current_mtnCustomerIndustryTypeId = jsonObj.getString("mtnCustomerIndustryTypeId");
                                MeetingsData.get_current_mtnCustomerIndustryType = jsonObj.getString("mtnCustomerIndustryType");
                                MeetingsData.get_current_mtnCustomerCompanyName = jsonObj.getString("mtnCustomerCompanyName");
                                MeetingsData.get_current_mtnCustomerDepartment = jsonObj.getString("mtnCustomerDepartment");
                                MeetingsData.get_current_mtnCustomerDesignation = jsonObj.getString("mtnCustomerDesignation");
                                MeetingsData.get_current_mtnCustomerMobileNo = jsonObj.getString("mtnCustomerMobileNo");
                                MeetingsData.get_current_mtnCustomerAlternateNo = jsonObj.getString("mtnCustomerAlternateNo");
                                MeetingsData.get_current_mtnCustomerEmail = jsonObj.getString("mtnCustomerEmail");
                                MeetingsData.get_current_mtnCustomerAddress = jsonObj.getString("mtnCustomerAddress");
                                MeetingsData.get_current_mtnCustomerLandmark = jsonObj.getString("mtnCustomerLandmark");
                                MeetingsData.get_current_mtnCustomerLat = jsonObj.getString("mtnCustomerLat");
                                MeetingsData.get_current_mtnCustomerLong = jsonObj.getString("mtnCustomerLong");
                                MeetingsData.get_current_mtnUserId = jsonObj.getString("mtnUserId");
                                MeetingsData.get_current_mtnDate = jsonObj.getString("mtnDate");
                                MeetingsData.get_current_mtnTime = jsonObj.getString("mtnTime");


                                view.successInfo(message);

                            }else{
                               view.errorInfo(message);

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


    public void updateMeetingVisitedService(String url, final String userId, final String mtnId, final String mtnVisitedDate, final String mtnVisitedTime, final String mtnVisitedLat, final String mtnVisitedLong, final String mtnVisitedMessage) {

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
                                view.errorMeetingVisitedInfo(message);
                            }else{
                                view.successMeetingVisitedInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorMeetingVisitedInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("mtnId", mtnId);
                params.put("mtnVisitedDate", mtnVisitedDate);
                params.put("mtnVisitedTime", mtnVisitedTime);
                params.put("mtnVisitedLat", mtnVisitedLat);
                params.put("mtnVisitedLong", mtnVisitedLong);
                params.put("mtnVisitedMessage", mtnVisitedMessage);

                Log.d("mtnVisitedTime", mtnVisitedTime);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void updateMeetingRemarksService(String url, final String userId, final String mtnId, final String mtnRemarksDate, final String mtnRemarksTime, final String mtnRemarksLat, final String mtnRemarksLong, final String mtnRemarksMessage) {

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
                                view.errorMeetingRemarksInfo(message);
                            }else{
                                view.successMeetingRemarksInfo(message);
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
                view.errorInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("mtnId", mtnId);
                params.put("mtnRemarksMessage", mtnRemarksMessage);
                params.put("mtnRemarksDate", mtnRemarksDate);
                params.put("mtnRemarksTime", mtnRemarksTime);
                params.put("mtnRemarksLat", mtnRemarksLat);
                params.put("mtnRemarksLong", mtnRemarksLong);

                Log.d("userId", userId);
                Log.d("mtnId", mtnId);
                Log.d("mtnRemarksMessage", mtnRemarksMessage);
                Log.d("mtnRemarksDate", mtnRemarksDate);
                Log.d("mtnRemarksTime", mtnRemarksTime);
                Log.d("mtnRemarksLat", mtnRemarksLat);
                Log.d("mtnRemarksLong", mtnRemarksLong);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void updateMeetingNextVisitService(String url, final String userId, final String mtnId, final String mtnCustomerId, final String mtnName, final String mtnMeetingTypeId, final String mtnMeetingType, final String mtnDate, final String mtnTime, final String userParentPath) {

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
                                view.errorMeetingNextVisitInfo(message);
                            }else{
                                view.successMeetingNextVisitInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorMeetingNextVisitInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("mtnId", mtnId);
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


    public void getMeetingHistoryService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        MeetingDetailsData.meetingDetailsList.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if (status.equals("failed")) {
                                view.errorMeetingHistoryInfo(message);
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

                                    MeetingDetailsSetterGetter wp = new MeetingDetailsSetterGetter(mtnId, mtnName, mtnDate, mtnTime, mtnUserName, mtnVisited, mtnCompleted, mtnRemarksMessage);

                                    // Binds all strings into an array
                                    MeetingDetailsData.meetingDetailsList.add(wp);

                                }

                                view.startGetMeetingHistory();

                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorMeetingHistoryInfo("Server not Responding, Please check your Internet Connection");
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }
}
