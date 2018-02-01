package com.lnsel.ambujaneotiasales.presenters;

import com.android.volley.DefaultRetryPolicy;
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
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpenseAddScreen.ExpenseAddActivityView;

/**
 * Created by apps2 on 5/9/2017.
 */
public class ExpenseAddPresenter {

    private ExpenseAddActivityView view;

    private static final String TAG = "REQ";

    public ExpenseAddPresenter(ExpenseAddActivityView view) {
        this.view = view;
    }

    public void addExpenseService(String url, final String userId, final String userParentPath, final String expImageName, final String expTitle, final String expAmount, final String expDescription, final String expImageStatus, final String expImageAvailable, final String expImage, final String expIsMeetingAssociated, final String expMeetingId){

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
                                view.errorInfo(message);
                            }else{
                                view.successInfo(message);
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
                        view.errorInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("userParentPath",userParentPath);
                params.put("expTitle",expTitle);
                params.put("expAmount",expAmount);
                params.put("expDescription",expDescription);
                params.put("expImageStatus",expImageStatus);
                params.put("expImageAvailable",expImageAvailable);
                params.put("expImage",expImage);
                params.put("expImageName",expImageName);
                params.put("expIsMeetingAssociated",expIsMeetingAssociated);
                params.put("expMeetingId",expMeetingId);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

}
