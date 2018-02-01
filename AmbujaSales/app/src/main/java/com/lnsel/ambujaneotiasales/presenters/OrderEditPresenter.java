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
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrderEditScreen.OrderEditActivityView;

/**
 * Created by apps2 on 5/26/2017.
 */
public class OrderEditPresenter {
    private OrderEditActivityView view;

    private static final String TAG = "REQ";

    public OrderEditPresenter(OrderEditActivityView view) {
        this.view = view;
    }

    public void updateOrderService(
            String url,
            final String userId,
            final String ordId,
            final String ordName,
            final String ordUnitId,
            final String ordUnit,
            final String ordVenueId,
            final String ordVenue,
            final String ordQuantity,
            final String ordAmount,
            final String ordDescription,
            final String ordForDate,
            final String ordStatusId,
            final String ordStatus
    ){

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
                params.put("ordId",ordId);
                params.put("ordName",ordName);
                params.put("ordUnitId",ordUnitId);
                params.put("ordUnit",ordUnit);
                params.put("ordVenueId",ordVenueId);
                params.put("ordVenue",ordVenue);
                params.put("ordQuantity",ordQuantity);
                params.put("ordAmount",ordAmount);
                params.put("ordDescription",ordDescription);
                params.put("ordForDate",ordForDate);
                params.put("ordStatusId",ordStatusId);
                params.put("ordStatus",ordStatus);
                return params;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
