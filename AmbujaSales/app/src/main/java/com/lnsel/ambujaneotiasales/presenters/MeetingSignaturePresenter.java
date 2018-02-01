package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingSignatureScreen.MeetingSignatureActivityView;

/**
 * Created by apps2 on 5/8/2017.
 */
public class MeetingSignaturePresenter {

    private MeetingSignatureActivityView view;

    private static final String TAG = "REQ";

    public MeetingSignaturePresenter(MeetingSignatureActivityView view) {
        this.view = view;
    }

    public void updateMeetingSignatureService(String url, final String userId, final String mtnId, final String mtnSignatureImageName, final String mtnSignatureDate, final String mtnSignatureTime, final String mtnSignatureLat, final String mtnSignatureLong, final String mtnSignatureImage){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        view.successInfo("Successfully Sent");
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
                params.put("mtnId",mtnId);
                params.put("mtnSignatureDate",mtnSignatureDate);
                params.put("mtnSignatureTime",mtnSignatureTime);
                params.put("mtnSignatureLat",mtnSignatureLat);
                params.put("mtnSignatureLong",mtnSignatureLong);
                params.put("mtnSignatureImage",mtnSignatureImage);
                params.put("mtnSignatureImageName",mtnSignatureImageName);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
