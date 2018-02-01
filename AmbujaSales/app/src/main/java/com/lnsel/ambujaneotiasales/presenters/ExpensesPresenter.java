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

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter.ExpensesSetterGetter;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen.ExpensesActivityView;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter.ExpensesData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;

/**
 * Created by apps2 on 5/10/2017.
 */
public class ExpensesPresenter {
    private ExpensesActivityView view;

    private static final String TAG = "EXPENSES_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public ExpensesPresenter(ExpensesActivityView view) {
        this.view = view;
    }

    public void getExpensesService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        ExpensesData.expensesList.clear();

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

                                    String expId = e.getString("expId");
                                    String expTitle = e.getString("expTitle");
                                    String expAmount = e.getString("expAmount");
                                    String expDescription = e.getString("expDescription");
                                    String expImage = e.getString("expImage");
                                    String expImageStatus = e.getString("expImageStatus");
                                    String expParentId = e.getString("expParentId");
                                    String expParentPath = e.getString("expParentPath");
                                    String expStatus = e.getString("expStatus");
                                    String expIsMeetingAssociated = e.getString("expIsMeetingAssociated");
                                    String expMeetingName = e.getString("expMeetingName");
                                    String expMeetingCustomerName = e.getString("expMeetingCustomerName");
                                    String expPaymentStatus = e.getString("expPaymentStatus");

                                    ExpensesSetterGetter wp = new ExpensesSetterGetter(expId, expTitle, expAmount, expDescription, expImage, expImageStatus, expParentId, expParentPath, expStatus, expIsMeetingAssociated, expMeetingName, expMeetingCustomerName, expPaymentStatus);

                                    // Binds all strings into an array
                                    ExpensesData.expensesList.add(wp);

                                }

                                view.startGetExpenses();

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


    public void deleteExpenseService(String url, final String userId, final String expId){

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
                                view.errorExpenseDeleteInfo(message);
                            }else{
                                view.successExpenseDeleteInfo(message);
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
                        view.errorExpenseDeleteInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("expId",expId);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }


    public void updateExpenseCompletedService(String url, final String userId, final String expId, final String expCompleted){

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
                                view.errorExpenseCompletedInfo(message);
                            }else{
                                view.successExpenseCompletedInfo(message);
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
                        view.errorExpenseCompletedInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("expId",expId);
                params.put("expCompleted",expCompleted);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
