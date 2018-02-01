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
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingPictureScreen.MeetingPictureActivityView;

/**
 * Created by apps2 on 5/9/2017.
 */
public class MeetingPicturePresenter {

    private MeetingPictureActivityView view;

    private static final String TAG = "REQ";

    public MeetingPicturePresenter(MeetingPictureActivityView view) {
        this.view = view;
    }

    public void updateMeetingPictureService(String url, final String userId, final String mtnId, final String mtnPictureImageName, final String mtnPictureDate, final String mtnPictureTime, final String mtnPictureLat, final String mtnPictureLong, final String mtnPictureImage){

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
                params.put("mtnPictureDate",mtnPictureDate);
                params.put("mtnPictureTime",mtnPictureTime);
                params.put("mtnPictureLat",mtnPictureLat);
                params.put("mtnPictureLong",mtnPictureLong);
                params.put("mtnPictureImage",mtnPictureImage);
                params.put("mtnPictureImageName",mtnPictureImageName);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
