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
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactEditScreen.ContactEditActivityView;

/**
 * Created by apps2 on 5/30/2017.
 */
public class ContactEditPresenter {
    private ContactEditActivityView view;

    private static final String TAG = "REQ";

    public ContactEditPresenter(ContactEditActivityView view) {
        this.view = view;
    }

    public void updateContactService(String url, final String userId, final String cntId, final String cntPersonName, final String cntContactNo, final String cntAddress, final String cntOtherDetails){

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
                params.put("cntId",cntId);
                params.put("cntPersonName",cntPersonName);
                params.put("cntContactNo",cntContactNo);
                params.put("cntAddress",cntAddress);
                params.put("cntOtherDetails",cntOtherDetails);
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
