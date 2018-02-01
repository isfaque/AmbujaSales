package com.lnsel.ambujaneotiasales.presenters;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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

import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingAddScreen.MeetingAddActivityView;

/**
 * Created by apps2 on 5/12/2017.
 */
public class MeetingAddPresenter {

    private MeetingAddActivityView view;
    private static final String TAG = "REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public MeetingAddPresenter(MeetingAddActivityView view) {
        this.view = view;
    }

    public void addNewCustomerMeetingService(
            String url,
            final String userId,
            final String userParentPath,
            final String cusFirstName,
            final String cusLastName,
            final String cusGenderId,
            final String cusGender,
            final String cusDOB,
            final String cusDOA,
            final String cusCustomerTypeId,
            final String cusCustomerType,
            final String cusIndustryTypeId,
            final String cusIndustryType,
            final String cusCompanyName,
            final String cusDepartment,
            final String cusDesignation,
            final String cusEmail,
            final String cusMobileNo,
            final String cusAlternateNo,
            final String cusCountryId,
            final String cusCountry,
            final String cusStateId,
            final String cusState,
            final String cusCityId,
            final String cusCity,
            final String cusAddress,
            final String cusLandmark,
            final String cusArea,
            final String cusPinCode,
            final String mtnName,
            final String mtnMeetingTypeId,
            final String mtnMeetingType,
            final String mtnDate,
            final String mtnTime
    ) {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

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
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorInfo("Server not Responding, Please check your Internet Connection");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userId);
                params.put("userParentPath", userParentPath);
                params.put("cusFirstName", cusFirstName);
                params.put("cusLastName", cusLastName);
                params.put("cusGenderId", cusGenderId);
                params.put("cusGender", cusGender);
                params.put("cusDOB", cusDOB);
                params.put("cusDOA", cusDOA);
                params.put("cusCustomerTypeId", cusCustomerTypeId);
                params.put("cusCustomerType", cusCustomerType);
                params.put("cusIndustryTypeId", cusIndustryTypeId);
                params.put("cusIndustryType", cusIndustryType);
                params.put("cusCompanyName", cusCompanyName);
                params.put("cusDepartment", cusDepartment);
                params.put("cusDesignation", cusDesignation);
                params.put("cusEmail", cusEmail);
                params.put("cusMobileNo", cusMobileNo);
                params.put("cusAlternateNo", cusAlternateNo);
                params.put("cusCountryId", cusCountryId);
                params.put("cusCountry", cusCountry);
                params.put("cusStateId", cusStateId);
                params.put("cusState", cusState);
                params.put("cusCityId", cusCityId);
                params.put("cusCity", cusCity);
                params.put("cusAddress", cusAddress);
                params.put("cusLandmark", cusLandmark);
                params.put("cusArea", cusArea);
                params.put("cusPinCode", cusPinCode);
                params.put("mtnName", mtnName);
                params.put("mtnMeetingTypeId", mtnMeetingTypeId);
                params.put("mtnMeetingType", mtnMeetingType);
                params.put("mtnDate", mtnDate);
                params.put("mtnTime", mtnTime);

                Log.d("userId", userId);
                Log.d("userParentPath", userParentPath);
                Log.d("cusFirstName", cusFirstName);
                Log.d("cusLastName", cusLastName);
                Log.d("cusGenderId", cusGenderId);
                Log.d("cusGender", cusGender);
                Log.d("cusDOB", cusDOB);
                Log.d("cusDOA", cusDOA);
                Log.d("cusCustomerTypeId", cusCustomerTypeId);
                Log.d("cusCustomerType", cusCustomerType);
                Log.d("cusIndustryTypeId", cusIndustryTypeId);
                Log.d("cusIndustryType", cusIndustryType);
                Log.d("cusCompanyName", cusCompanyName);
                Log.d("cusDepartment", cusDepartment);
                Log.d("cusDesignation", cusDesignation);
                Log.d("cusEmail", cusEmail);
                Log.d("cusMobileNo", cusMobileNo);
                Log.d("cusAlternateNo", cusAlternateNo);
                Log.d("cusCountryId", cusCountryId);
                Log.d("cusCountry", cusCountry);
                Log.d("cusStateId", cusStateId);
                Log.d("cusState", cusState);
                Log.d("cusCityId", cusCityId);
                Log.d("cusCity", cusCity);
                Log.d("cusAddress", cusAddress);
                Log.d("cusLandmark", cusLandmark);
                Log.d("cusArea", cusArea);
                Log.d("cusPinCode", cusPinCode);
                Log.d("mtnName", mtnName);
                Log.d("mtnMeetingTypeId", mtnMeetingTypeId);
                Log.d("mtnMeetingType", mtnMeetingType);
                Log.d("mtnDate", mtnDate);
                Log.d("mtnTime", mtnTime);


                return params;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
