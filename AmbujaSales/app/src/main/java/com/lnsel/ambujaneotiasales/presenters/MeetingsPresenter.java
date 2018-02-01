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

import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen.MeetingsActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsData;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsSetterGetter;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;

/**
 * Created by apps2 on 5/4/2017.
 */
public class MeetingsPresenter {
    private MeetingsActivityView view;

    private static final String TAG = "CUSTOMERS_TAB_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public MeetingsPresenter(MeetingsActivityView view) {
        this.view = view;
    }

    public void getMeetingsService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        MeetingsData.meetingsList.clear();

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
                                    String Pick_Id = e.getString("mtnName");
                                    Log.i("Author is", Pick_Id);

                                    String mtnId = e.getString("mtnId");
                                    String mtnName = e.getString("mtnName");
                                    String mtnMeetingTypeId = e.getString("mtnMeetingTypeId");
                                    String mtnMeetingType = e.getString("mtnMeetingType");
                                    String mtnVisited = e.getString("mtnVisited");
                                    String mtnNextVisit = e.getString("mtnNextVisit");
                                    String mtnSignature = e.getString("mtnSignature");
                                    String mtnPicture = e.getString("mtnPicture");
                                    String mtnRemarks = e.getString("mtnRemarks");
                                    String mtnCustomerId = e.getString("mtnCustomerId");
                                    String mtnCustomerName = e.getString("mtnCustomerName");
                                    String mtnCustomerCompanyName = e.getString("mtnCustomerCompanyName");
                                    String mtnCustomerMobileNo = e.getString("mtnCustomerMobileNo");
                                    String mtnCustomerAddress = e.getString("mtnCustomerAddress");
                                    String mtnCustomerLat = e.getString("mtnCustomerLat");
                                    String mtnCustomerLong = e.getString("mtnCustomerLong");
                                    String mtnUserId = e.getString("mtnUserId");
                                    String mtnUserName = e.getString("mtnUserName");
                                    String mtnDate = e.getString("mtnDate");
                                    String mtnTime = e.getString("mtnTime");
                                    String mtnParentName = e.getString("mtnParentName");

                                    MeetingsSetterGetter wp = new MeetingsSetterGetter(
                                            mtnId,
                                            mtnName,
                                            mtnMeetingTypeId,
                                            mtnMeetingType,
                                            mtnVisited,
                                            mtnNextVisit,
                                            mtnSignature,
                                            mtnPicture,
                                            mtnRemarks,
                                            mtnCustomerId,
                                            mtnCustomerName,
                                            mtnCustomerCompanyName,
                                            mtnCustomerMobileNo,
                                            mtnCustomerAddress,
                                            mtnCustomerLat,
                                            mtnCustomerLong,
                                            mtnUserId,
                                            mtnUserName,
                                            mtnDate,
                                            mtnTime,
                                            mtnParentName
                                    );

                                    // Binds all strings into an array
                                    MeetingsData.meetingsList.add(wp);

                                }

                                view.startGetMeetings();

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


    public void updateMeetingCompletedService(String url, final String userId, final String mtnId, final String mtnCompleted){

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
                                view.errorMeetingCompletedInfo(message);
                            }else{
                                view.successMeetingCompletedInfo(message);
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
                        view.errorMeetingCompletedInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("mtnId",mtnId);
                params.put("mtnCompleted",mtnCompleted);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
