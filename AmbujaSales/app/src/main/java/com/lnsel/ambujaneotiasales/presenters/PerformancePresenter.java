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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter.PerformanceData;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceScreen.PerformanceActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter.PerformanceSetterGetter;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceSelectionScreen.PerformanceSelectionActivityView;

/**
 * Created by apps2 on 5/27/2017.
 */
public class PerformancePresenter {
    private PerformanceActivityView view;
    private PerformanceSelectionActivityView view2;

    private static final String TAG = "REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public PerformancePresenter(PerformanceActivityView view) {
        this.view = view;
    }
    public PerformancePresenter(PerformanceSelectionActivityView view2) {
        this.view2 = view2;
    }

    public void getPerformanceService(String url) {

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
                                String order_JAN = jsonObj.getString("order_JAN");
                                String order_FEB = jsonObj.getString("order_FEB");
                                String order_MAR = jsonObj.getString("order_MAR");
                                String order_APR = jsonObj.getString("order_APR");
                                String order_MAY = jsonObj.getString("order_MAY");
                                String order_JUN = jsonObj.getString("order_JUN");
                                String order_JUL = jsonObj.getString("order_JUL");
                                String order_AUG = jsonObj.getString("order_AUG");
                                String order_SEP = jsonObj.getString("order_SEP");
                                String order_OCT = jsonObj.getString("order_OCT");
                                String order_NOV = jsonObj.getString("order_NOV");
                                String order_DEC = jsonObj.getString("order_DEC");

                                String visit_JAN = jsonObj.getString("visit_JAN");
                                String visit_FEB = jsonObj.getString("visit_FEB");
                                String visit_MAR = jsonObj.getString("visit_MAR");
                                String visit_APR = jsonObj.getString("visit_APR");
                                String visit_MAY = jsonObj.getString("visit_MAY");
                                String visit_JUN = jsonObj.getString("visit_JUN");
                                String visit_JUL = jsonObj.getString("visit_JUL");
                                String visit_AUG = jsonObj.getString("visit_AUG");
                                String visit_SEP = jsonObj.getString("visit_SEP");
                                String visit_OCT = jsonObj.getString("visit_OCT");
                                String visit_NOV = jsonObj.getString("visit_NOV");
                                String visit_DEC = jsonObj.getString("visit_DEC");

                                PerformanceData.current_order_JAN = Float.valueOf(order_JAN);
                                PerformanceData.current_order_FEB = Float.valueOf(order_FEB);
                                PerformanceData.current_order_MAR = Float.valueOf(order_MAR);
                                PerformanceData.current_order_APR = Float.valueOf(order_APR);
                                PerformanceData.current_order_MAY = Float.valueOf(order_MAY);
                                PerformanceData.current_order_JUN = Float.valueOf(order_JUN);
                                PerformanceData.current_order_JUL = Float.valueOf(order_JUL);
                                PerformanceData.current_order_AUG = Float.valueOf(order_AUG);
                                PerformanceData.current_order_SEP = Float.valueOf(order_SEP);
                                PerformanceData.current_order_OCT = Float.valueOf(order_OCT);
                                PerformanceData.current_order_NOV = Float.valueOf(order_NOV);
                                PerformanceData.current_order_DEC = Float.valueOf(order_DEC);

                                PerformanceData.current_visit_JAN = Float.valueOf(visit_JAN);
                                PerformanceData.current_visit_FEB = Float.valueOf(visit_FEB);
                                PerformanceData.current_visit_MAR = Float.valueOf(visit_MAR);
                                PerformanceData.current_visit_APR = Float.valueOf(visit_APR);
                                PerformanceData.current_visit_MAY = Float.valueOf(visit_MAY);
                                PerformanceData.current_visit_JUN = Float.valueOf(visit_JUN);
                                PerformanceData.current_visit_JUL = Float.valueOf(visit_JUL);
                                PerformanceData.current_visit_AUG = Float.valueOf(visit_AUG);
                                PerformanceData.current_visit_SEP = Float.valueOf(visit_SEP);
                                PerformanceData.current_visit_OCT = Float.valueOf(visit_OCT);
                                PerformanceData.current_visit_NOV = Float.valueOf(visit_NOV);
                                PerformanceData.current_visit_DEC = Float.valueOf(visit_DEC);


                                view.startGetPerformance();

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


    public void getPerformanceListService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        PerformanceData.performanceList.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if(status.equals("failed")){
                                view2.errorInfo(message);
                            }else{
                                data = jsonObj.getJSONArray(TAG_DATA);
                                for (int i = 0; i < data.length(); i++) {
                                    e = data.getJSONObject(i);

                                    String perId = e.getString("perId");
                                    String perName = e.getString("perName");
                                    String perJAN = e.getString("perJAN");
                                    String perFEB = e.getString("perFEB");
                                    String perMAR = e.getString("perMAR");
                                    String perAPR = e.getString("perAPR");
                                    String perMAY = e.getString("perMAY");
                                    String perJUN = e.getString("perJUN");
                                    String perJUL = e.getString("perJUL");
                                    String perAUG = e.getString("perAUG");
                                    String perSEP = e.getString("perSEP");
                                    String perOCT = e.getString("perOCT");
                                    String perNOV = e.getString("perNOV");
                                    String perDEC = e.getString("perDEC");



                                    PerformanceSetterGetter wp = new PerformanceSetterGetter(perId, perName, perJAN, perFEB, perMAR, perAPR, perMAY, perJUN, perJUL, perAUG, perSEP, perOCT, perNOV, perDEC);

                                    // Binds all strings into an array
                                    PerformanceData.performanceList.add(wp);

                                }

                                view2.startGetPerformanceList();

                            }



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            view2.errorInfo("Server not Responding, Please check your Internet Connection");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view2.errorInfo("Server not Responding, Please check your Internet Connection");
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }

}
