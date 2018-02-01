package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.lnsel.ambujaneotiasales.helpers.Service.LocationService;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;

/**
 * Created by apps2 on 6/1/2017.
 */
public class ServicePresenter {
    private static final String TAG = "SERVICE_REQ";

    public ServicePresenter(LocationService locationService) {

    }

    public void getLocationUpdateService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }
}
