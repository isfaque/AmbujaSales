package com.lnsel.ambujaneotiasales.presenters;

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
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestAddScreen.LeaveRequestAddActivityView;

/**
 * Created by apps2 on 5/16/2017.
 */
public class LeaveRequestAddPresenter {

    private LeaveRequestAddActivityView view;

    private static final String TAG = "REQ";

    public LeaveRequestAddPresenter(LeaveRequestAddActivityView view) {
        this.view = view;
    }

    public void addLeaveRequest(String url, final String userId, final String userParentPath, final String lreqSubject, final String lreqDescription){

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
                params.put("lreqSubject",lreqSubject);
                params.put("lreqDescription",lreqDescription);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
