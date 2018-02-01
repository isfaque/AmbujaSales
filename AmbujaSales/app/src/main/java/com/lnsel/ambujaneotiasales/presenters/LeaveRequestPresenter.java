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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter.LeaveRequestData;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen.LeaveRequestActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter.LeaveRequestSetterGetter;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;

/**
 * Created by apps2 on 5/16/2017.
 */
public class LeaveRequestPresenter {
    private LeaveRequestActivityView view;

    private static final String TAG = "EXPENSES_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public LeaveRequestPresenter(LeaveRequestActivityView view) {
        this.view = view;
    }

    public void getLeaveRequestService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        LeaveRequestData.leaveRequestList.clear();

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

                                    String lreqId = e.getString("lreqId");
                                    String lreqSubject = e.getString("lreqSubject");
                                    String lreqDescription = e.getString("lreqDescription");
                                    String lreqStatus = e.getString("lreqStatus");
                                    String lreqStatusMessage = e.getString("lreqStatusMessage");

                                    LeaveRequestSetterGetter wp = new LeaveRequestSetterGetter(lreqId, lreqSubject, lreqDescription, lreqStatus, lreqStatusMessage);

                                    // Binds all strings into an array
                                    LeaveRequestData.leaveRequestList.add(wp);

                                }

                                view.startGetLeaveRequest();

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


    public void deleteLeaveRequestService(String url, final String userId, final String lreqId){

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
                                view.errorLeaveRequestDeleteInfo(message);
                            }else{
                                view.successLeaveRequestDeleteInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            view.errorInfo("Server not Responding, Please check your Internet Connection");
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        view.errorLeaveRequestDeleteInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("lreqId",lreqId);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
