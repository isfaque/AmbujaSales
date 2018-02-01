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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter.AttendanceData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.AttendanceScreen.AttendanceActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter.AttendanceSetterGetter;

/**
 * Created by apps2 on 5/16/2017.
 */
public class AttendancePresenter {
    private AttendanceActivityView view;

    private static final String TAG = "ATTENDANCE_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public AttendancePresenter(AttendanceActivityView view) {
        this.view = view;
    }

    public void getAttendanceService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        AttendanceData.attendanceList.clear();

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

                                    String lgnrSNo = e.getString("lgnrSNo");
                                    String lgnrUserName = e.getString("lgnrUserName");
                                    String lgnrLoginDate = e.getString("lgnrLoginDate");
                                    String lgnrLoginTime = e.getString("lgnrLoginTime");
                                    String lgnrLogoutDate = e.getString("lgnrLogoutDate");
                                    String lgnrLogoutTime = e.getString("lgnrLogoutTime");
                                    String lgnrTotalTime = e.getString("lgnrTotalTime");
                                    String lgnrLoginStatus = e.getString("lgnrLoginStatus");

                                    AttendanceSetterGetter wp = new AttendanceSetterGetter(lgnrSNo, lgnrUserName, lgnrLoginDate, lgnrLoginTime, lgnrLogoutDate, lgnrLogoutTime, lgnrTotalTime, lgnrLoginStatus);

                                    // Binds all strings into an array
                                    AttendanceData.attendanceList.add(wp);

                                }

                                view.startGetAttendance();

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

}
