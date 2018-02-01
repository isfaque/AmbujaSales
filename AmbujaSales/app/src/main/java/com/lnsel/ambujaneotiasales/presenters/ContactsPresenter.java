package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter.ContactsData;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter.ContactsSetterGetter;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactsScreen.ContactsActivityView;

/**
 * Created by apps2 on 5/29/2017.
 */
public class ContactsPresenter {
    private ContactsActivityView view;

    private static final String TAG = "CONTACTS_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public ContactsPresenter(ContactsActivityView view) {
        this.view = view;
    }

    public void getContactsService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        ContactsData.contactsList.clear();

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

                                    String cntId = e.getString("cntId");
                                    String cntPersonName = e.getString("cntPersonName");
                                    String cntContactNo = e.getString("cntContactNo");
                                    String cntAddress = e.getString("cntAddress");
                                    String cntOtherDetails = e.getString("cntOtherDetails");
                                    String cntStatus = e.getString("cntStatus");



                                    ContactsSetterGetter wp = new ContactsSetterGetter(cntId, cntPersonName, cntContactNo, cntAddress, cntOtherDetails, cntStatus);

                                    // Binds all strings into an array
                                    ContactsData.contactsList.add(wp);

                                }

                                view.startGetContacts();

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
                //view.errorInfo("Server not Responding, Please check your Internet Connection");
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }

                view.errorInfo(message);
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }

    public void deleteContactService(String url, final String userId, final String cntId){

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
                                view.errorContactDeleteInfo(message);
                            }else{
                                view.successContactDeleteInfo(message);
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
                        view.errorContactDeleteInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userId",userId);
                params.put("cntId",cntId);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
