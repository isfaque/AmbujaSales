package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingOrderScreen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.presenters.MeetingOrderPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/25/2017.
 */
public class MeetingOrderActivity extends AppCompatActivity implements MeetingOrderActivityView {

    private MeetingOrderPresenter presenter;
    SharedManagerUtil session;

    private ProgressDialog progress;

    private EditText
            et_order_name,
            et_order_quantity,
            et_order_amount,
            et_order_date,
            et_order_description;

    private TextInputLayout
            til_order_name,
            til_order_quantity,
            til_order_amount,
            til_order_date,
            til_order_description;

    Button btn_cancel, btn_submit;

    private Spinner
            spinner_venue,
            spinner_unit,
            spinner_status;

    ArrayList<String> unitList = new ArrayList<String>();
    ArrayList<String> unitId = new ArrayList<String>();
    ArrayAdapter<String> unitAdapter;
    String unitSelected="";
    String unitIdSelected="";


    ArrayList<String> venueList = new ArrayList<String>();
    ArrayList<String> venueId = new ArrayList<String>();
    ArrayAdapter<String> venueAdapter;
    String venueSelected="";
    String venueIdSelected="";


    ArrayList<String> ostList = new ArrayList<String>();
    ArrayList<String> ostId = new ArrayList<String>();
    ArrayAdapter<String> ostAdapter;
    String ostSelected="";
    String ostIdSelected="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_order);

        // Session Manager
        session = new SharedManagerUtil(this);


        presenter = new MeetingOrderPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Meeting Query");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingDetailsActivity();
            }
        });


        if(isNetworkAvailable()){
            doOrderStatusTypeList();
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        spinner_venue=(Spinner)findViewById(R.id.activity_meeting_order_spinner_venue);
        spinner_unit=(Spinner)findViewById(R.id.activity_meeting_order_spinner_unit);
        spinner_status=(Spinner)findViewById(R.id.activity_meeting_order_spinner_status);

        et_order_name = (EditText) findViewById(R.id.activity_meeting_order_et_title);
        et_order_amount = (EditText) findViewById(R.id.activity_meeting_order_et_amount);
        et_order_description = (EditText) findViewById(R.id.activity_meeting_order_et_description);
        et_order_quantity=(EditText)findViewById(R.id.activity_meeting_order_et_quantity);
        et_order_date=(EditText)findViewById(R.id.activity_meeting_order_et_order_date);

        btn_cancel = (Button) findViewById(R.id.activity_meeting_order_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_meeting_order_btn_submit);

        til_order_name = (TextInputLayout) findViewById(R.id.activity_meeting_order_til_title);
        til_order_amount = (TextInputLayout) findViewById(R.id.activity_meeting_order_til_amount);
        til_order_description = (TextInputLayout) findViewById(R.id.activity_meeting_order_til_description);
        til_order_quantity=(TextInputLayout)findViewById(R.id.activity_meeting_order_til_quantity);
        til_order_date=(TextInputLayout)findViewById(R.id.activity_meeting_order_til_order_date);

        et_order_name.addTextChangedListener(new MyTextWatcher(et_order_name));
        et_order_amount.addTextChangedListener(new MyTextWatcher(et_order_amount));
        et_order_description.addTextChangedListener(new MyTextWatcher(et_order_description));
        et_order_quantity.addTextChangedListener(new MyTextWatcher(et_order_quantity));
        et_order_date.addTextChangedListener(new MyTextWatcher(et_order_date));

        et_order_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MeetingOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_order_date.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingDetailsActivity();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateOrderName()) {
                    return;
                }
                if(unitIdSelected.isEmpty()||unitIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_unit);
                    Toast.makeText(MeetingOrderActivity.this,"please select Unit",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(venueIdSelected.isEmpty()||venueIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_venue);
                    Toast.makeText(MeetingOrderActivity.this,"please select Venue",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateOrderQuantity()) {
                    return;
                }
                if (!validateOrderAmount()) {
                    return;
                }
                if (!validateOrderDate()) {
                    return;
                }
                if (!validateOrderDescription()) {
                    return;
                }
                if(ostIdSelected.isEmpty()||ostIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_status);
                    Toast.makeText(MeetingOrderActivity.this,"please select status",Toast.LENGTH_SHORT).show();
                    return;
                }

                submitMeetingOrder();

            }
        });


        unitAdapter = new ArrayAdapter<String>(MeetingOrderActivity.this,R.layout.spinner_rows, unitList);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_unit.setAdapter(unitAdapter);

        spinner_unit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                unitIdSelected=unitId.get(position);
                unitSelected=unitList.get(position);

                if(!unitIdSelected.equalsIgnoreCase("-1"))
                    doVenueList(unitIdSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });


        venueAdapter = new ArrayAdapter<String>(MeetingOrderActivity.this,R.layout.spinner_rows, venueList);
        venueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_venue.setAdapter(venueAdapter);

        spinner_venue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                venueIdSelected = venueId.get(position);
                venueSelected=venueList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });


        ostAdapter = new ArrayAdapter<String>(MeetingOrderActivity.this,R.layout.spinner_rows, ostList);
        ostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(ostAdapter);

        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                ostIdSelected=ostId.get(position);
                ostSelected=ostList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });


    }

    public void submitMeetingOrder(){
        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);

            String ordMeetingId = MeetingsData.current_meeting_id;

            final String userId = session.getUserID();
            final String userParentPath = session.getUserParentPath();

            String ordName = et_order_name.getText().toString();
            String ordUnitId = unitIdSelected;
            String ordUnit = unitSelected;
            String ordVenueId = venueIdSelected;
            String ordVenue = venueSelected;
            String ordQuantity = et_order_quantity.getText().toString().trim();
            String ordAmount = et_order_amount.getText().toString();
            String ordDescription = et_order_description.getText().toString();
            String ordForDate = et_order_date.getText().toString();
            String ordStatusId = ostIdSelected;
            String ordStatus = ostSelected;

            SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
            String ordTime = stf.format(new Date());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final String ordDate = sdf.format(new Date());

            presenter.addMeetingOrderRequest(
                    UrlUtil.ADD_MEETING_ORDER_URL,
                    ordMeetingId,
                    userId,
                    userParentPath,
                    ordName,
                    ordUnitId,
                    ordUnit,
                    ordVenueId,
                    ordVenue,
                    ordQuantity,
                    ordAmount,
                    ordDescription,
                    ordDate,
                    ordTime,
                    ordForDate,
                    ordStatusId,
                    ordStatus
            );

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void startMeetingDetailsActivity() {
        new ActivityUtil(this).startMeetingDetailsActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        startMeetingDetailsActivity();
    }

    @Override
    public void onBackPressed() {
        startMeetingDetailsActivity();
    }

    //********** Text Watcher for Validation *******************//
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.activity_meeting_order_et_title:
                    validateOrderName();
                    break;
                case R.id.activity_meeting_order_et_quantity:
                    validateOrderQuantity();
                    break;
                case R.id.activity_meeting_order_et_amount:
                    validateOrderAmount();
                    break;
                case R.id.activity_meeting_order_et_description:
                    validateOrderDescription();
                    break;
                case R.id.activity_meeting_order_et_order_date:
                    validateOrderDate();
                    break;
            }
        }
    }


    private boolean validateOrderName() {
        if (et_order_name.getText().toString().trim().isEmpty()) {
            til_order_name.setError("order name can not be blank");
            requestFocus(et_order_name);
            return false;
        } else {
            til_order_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOrderQuantity() {
        if (et_order_quantity.getText().toString().trim().isEmpty()) {
            til_order_quantity.setError("order quantity can not be blank");
            requestFocus(et_order_quantity);
            return false;
        } else {
            til_order_quantity.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOrderAmount() {
        if (et_order_amount.getText().toString().trim().isEmpty()) {
            til_order_amount.setError("order amount can not be blank");
            requestFocus(et_order_amount);
            return false;
        } else {
            til_order_amount.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOrderDescription() {
        if (et_order_description.getText().toString().trim().isEmpty()) {
            til_order_description.setError("order details can not be blank");
            requestFocus(et_order_description);
            return false;
        } else {
            til_order_description.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOrderDate() {
        if (et_order_date.getText().toString().trim().isEmpty()) {
            til_order_date.setError("order date can not be blank");
            requestFocus(et_order_date);
            return false;
        } else {
            til_order_date.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    //*********** Web services for Order Status Type ***********************//
    public void doOrderStatusTypeList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_ORDER_STATUS_TYPES;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseOrderStatusTypeResponse(response);
                        Log.d("TAG", response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    public void parseOrderStatusTypeResponse(final JSONArray response){
        try {
            if(ostList.size()>0){

                ostList.clear();
                ostId.clear();
            }
            ostList.add("Select Status");
            ostId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);
                Log.d("ostName: ", records.optString("ostName"));

                ostList.add(records.optString("ostName"));
                ostId.add(records.optString("ostId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ostAdapter.notifyDataSetChanged();
        doUnitList();
    }


    //*********** Web services for unit ***********************//
    public void doUnitList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_USER_UNITS_URL+"?userId="+session.getUserID()+"&userParentId="+session.getUserParentId()+"&userParentPath="+session.getUserParentPath();

        StringRequest req = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseUnitResponse(response);
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    public void parseUnitResponse(final String response){
        try {

            JSONObject jsonObj = new JSONObject(response);
            String status = jsonObj.getString("status");

            if(status.equalsIgnoreCase("success")){

                JSONArray data = jsonObj.getJSONArray("data");

                if(unitList.size()>0){

                    unitList.clear();
                    unitId.clear();
                }
                if(data.length()>1){
                    unitList.add("Select Unit");
                    unitId.add("-1");
                }

                for (int i = 0; i < data.length(); i++) {

                    JSONObject records = data.getJSONObject(i);

                    unitList.add(records.getString("untName"));
                    unitId.add(records.getString("untId"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        unitAdapter.notifyDataSetChanged();
    }


    //*********** Web services for Venue ***********************//
    public void doVenueList(String untId){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_VENUES_BY_UNIT_URL+"?userId="+session.getUserID()+"&userParentId="+session.getUserParentId()+"&userParentPath="+session.getUserParentPath()+"&userUnitId="+untId;

        StringRequest req = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseVenueResponse(response);
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req, tag_json_arry);
    }

    public void parseVenueResponse(final String response){
        try {

            JSONObject jsonObj = new JSONObject(response);
            String status = jsonObj.getString("status");

            if(status.equalsIgnoreCase("success")){

                JSONArray data = jsonObj.getJSONArray("data");

                if(venueList.size()>0){

                    venueList.clear();
                    venueId.clear();
                }
                if(data.length()>1){
                    venueList.add("Select Venue");
                    venueId.add("-1");
                }

                for (int i = 0; i < data.length(); i++) {

                    JSONObject records = data.getJSONObject(i);

                    venueList.add(records.getString("venShortName"));
                    venueId.add(records.getString("venId"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        venueAdapter.notifyDataSetChanged();
        spinner_venue.setSelection(0);
    }

}
