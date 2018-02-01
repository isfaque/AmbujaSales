package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.MainActivityView;

/**
 * Created by apps2 on 5/5/2017.
 */
public class MainPresenter {
    private MainActivityView view;

    private static final String TAG = "REQ";

    public MainPresenter(MainActivityView view) {
        this.view = view;
    }

    public void updateLogoutService(String url, final String userId, final String lgnrId, final String lgnrLogoutDate, final String lgnrLogoutTime, final String lgnrLogoutLat, final String lgnrLogoutLong) {

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

                            if(status.equals("success")){

                                view.successInfo(message);

                            }else {
                                view.errorInfo(message);
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
                params.put("lgnrId", lgnrId);
                params.put("lgnrLogoutDate", lgnrLogoutDate);
                params.put("lgnrLogoutTime", lgnrLogoutTime);
                params.put("lgnrLogoutLat", lgnrLogoutLat);
                params.put("lgnrLogoutLong", lgnrLogoutLong);

                Log.d("userId", userId);
                Log.d("lgnrId", lgnrId);
                Log.d("lgnrLogoutDate", lgnrLogoutDate);
                Log.d("lgnrLogoutTime", lgnrLogoutTime);
                Log.d("lgnrLogoutLat", lgnrLogoutLat);
                Log.d("lgnrLogoutLong", lgnrLogoutLong);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }



    public void updateStarTripService(String url, final String userId, final String trpLoginRecordId, final String trpStartDate, final String trpStartTime, final String trpStartLat, final String trpStartLong) {

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

                            if(status.equals("success")){

                                String trpId = jsonObj.getString("trpId");

                                view.updateStarTripSession(trpId);

                            }else{
                                view.errorInfo(message);
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
                params.put("trpLoginRecordId", trpLoginRecordId);
                params.put("trpStartDate", trpStartDate);
                params.put("trpStartTime", trpStartTime);
                params.put("trpStartLat", trpStartLat);
                params.put("trpStartLong", trpStartLong);

                Log.d("userId", userId);
                Log.d("trpLoginRecordId", trpLoginRecordId);
                Log.d("trpStartDate", trpStartDate);
                Log.d("trpStartTime", trpStartTime);
                Log.d("trpStartLat", trpStartLat);
                Log.d("trpStartLong", trpStartLong);



                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void updateStopTripService(String url, final String userId, final String trpId, final String trpLoginRecordId, final String trpEndDate, final String trpEndTime, final String trpEndLat, final String trpEndLong) {

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

                            if(status.equals("success")){

                                view.updateStopTripSession();

                            }else {
                                view.errorInfo(message);
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
                params.put("trpId", trpId);
                params.put("trpLoginRecordId", trpLoginRecordId);
                params.put("trpEndDate", trpEndDate);
                params.put("trpEndTime", trpEndTime);
                params.put("trpEndLat", trpEndLat);
                params.put("trpEndLong", trpEndLong);

                Log.d("userId", userId);
                Log.d("trpId", trpId);
                Log.d("trpEndDate", trpEndDate);
                Log.d("trpEndTime", trpEndTime);
                Log.d("trpEndLat", trpEndLat);
                Log.d("trpEndLong", trpEndLong);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void sendPushTokenToServer(String url, final String userId, final String userPushToken) {

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
                            if(status.equals("success")){
                                String userPushToken = jsonObj.getString("userPushToken");
                                view.updatePushToken(userPushToken);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //errorInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("userPushToken", userPushToken);
                params.put("userDeviceType", "android");

                Log.d("userId", userId);
                Log.d("token", userPushToken);


                return params;
            }

        };

        //jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
