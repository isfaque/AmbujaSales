package com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerDetailsScreen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomerDetailsAdapter.CustomerDetailsBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomerDetailsAdapter.CustomerDetailsData;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomersAdapter.CustomersData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.presenters.CustomerDetailsPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 5/13/2017.
 */
public class CustomerDetailsActivity extends AppCompatActivity implements CustomerDetailsActivityView {

    private ProgressDialog progress;
    private CustomerDetailsPresenter presenter;

    CustomerDetailsBaseAdapter adapter;
    SharedManagerUtil session;

    private Dialog dialog;

    TextInputLayout
            til_next_visit_meeting_name,
            til_next_visit_meeting_date,
            til_next_visit_meeting_time;

    EditText
            et_next_visit_meeting_name,
            et_next_visit_meeting_date,
            et_next_visit_meeting_time;

    Spinner dialog_customer_meeting_spinner_meeting_type;

    ExpandableRelativeLayout
            expandableLayout1,
            expandableLayout2;

    TextView
            tv_customer_code,
            tv_customer_name,
            tv_customer_gender,
            tv_customer_dob,
            tv_customer_doa,
            tv_customer_type,
            tv_customer_industry_type,
            tv_customer_company_name,
            tv_customer_department,
            tv_customer_designation,
            tv_customer_address,
            tv_customer_landmark,
            tv_customer_area,
            tv_customer_country,
            tv_customer_state,
            tv_customer_city,
            tv_customer_pin_code,
            tv_customer_email,
            tv_customer_mobile_no,
            tv_customer_alternate_no;

    Button btn_back, btn_edit_customer;

    ListView list;

    FloatingActionButton fab_add_customer_meeting;

    ArrayList<String> meetingTypeList = new ArrayList<String>();
    ArrayList<String> meetingTypeId = new ArrayList<String>();

    ArrayAdapter<String> meetingTypeAdapter;

    String meetingTypeIdSelected = "";
    String meetingTypeSelected="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customer All Details");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomersActivity();
            }
        });

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new CustomerDetailsPresenter(this);

        tv_customer_code = (TextView) findViewById(R.id.activity_customer_details_tv_customer_code);
        tv_customer_name = (TextView) findViewById(R.id.activity_customer_details_tv_customer_name);
        tv_customer_gender = (TextView) findViewById(R.id.activity_customer_details_tv_gender);
        tv_customer_dob = (TextView) findViewById(R.id.activity_customer_details_tv_dob);
        tv_customer_doa = (TextView) findViewById(R.id.activity_customer_details_tv_anniversary);
        tv_customer_type = (TextView) findViewById(R.id.activity_customer_details_tv_customer_type);
        tv_customer_industry_type = (TextView) findViewById(R.id.activity_customer_details_tv_industry_type);
        tv_customer_company_name = (TextView) findViewById(R.id.activity_customer_details_tv_company_name);
        tv_customer_department = (TextView) findViewById(R.id.activity_customer_details_tv_department);
        tv_customer_designation = (TextView) findViewById(R.id.activity_customer_details_tv_designation);
        tv_customer_country = (TextView) findViewById(R.id.activity_customer_details_tv_country);
        tv_customer_state = (TextView) findViewById(R.id.activity_customer_details_tv_state);
        tv_customer_city = (TextView) findViewById(R.id.activity_customer_details_tv_city);
        tv_customer_mobile_no = (TextView) findViewById(R.id.activity_customer_details_tv_mobile_no);
        tv_customer_alternate_no = (TextView) findViewById(R.id.activity_customer_details_tv_alternate_no);
        tv_customer_email = (TextView) findViewById(R.id.activity_customer_details_tv_email);
        tv_customer_address = (TextView) findViewById(R.id.activity_customer_details_tv_address);
        tv_customer_landmark = (TextView) findViewById(R.id.activity_customer_details_tv_landmark);
        tv_customer_area = (TextView) findViewById(R.id.activity_customer_details_tv_area);
        tv_customer_pin_code = (TextView) findViewById(R.id.activity_customer_details_tv_pin_code);


        //btn_back = (Button) v.findViewById(R.id.fragment_customer_info_detail_btn_back);
        btn_edit_customer = (Button) findViewById(R.id.activity_customer_details_btn_edit_customer);
        list = (ListView) findViewById(R.id.list_view);
        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);

        fab_add_customer_meeting = (FloatingActionButton) findViewById(R.id.activity_customer_details_fab_add_meeting);

        fab_add_customer_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerMeetingDialog();
            }
        });


        tv_customer_code.setText(CustomersData.selectedCustomerCode);
        tv_customer_name.setText(CustomersData.selectedCustomerFirstName + " "+ CustomersData.selectedCustomerLastName);
        tv_customer_gender.setText(CustomersData.selectedCustomerGender);
        tv_customer_dob.setText(CustomersData.selectedCustomerDOB);
        tv_customer_doa.setText(CustomersData.selectedCustomerDOA);
        tv_customer_type.setText(CustomersData.selectedCustomerType);
        tv_customer_industry_type.setText(CustomersData.selectedCustomerIndustryType);
        tv_customer_company_name.setText(CustomersData.selectedCustomerCompanyName);
        tv_customer_department.setText(CustomersData.selectedCustomerDepartment);
        tv_customer_designation.setText(CustomersData.selectedCustomerDesignation);
        tv_customer_mobile_no.setText(CustomersData.selectedCustomerMobileNo);
        tv_customer_alternate_no.setText(CustomersData.selectedCustomerAlternateNo);
        tv_customer_email.setText(CustomersData.selectedCustomerEmail);
        tv_customer_country.setText(CustomersData.selectedCustomerCountry);
        tv_customer_state.setText(CustomersData.selectedCustomerState);
        tv_customer_city.setText(CustomersData.selectedCustomerCity);
        tv_customer_address.setText(CustomersData.selectedCustomerAddress);
        tv_customer_landmark.setText(CustomersData.selectedCustomerLandmark);
        tv_customer_area.setText(CustomersData.selectedCustomerArea);
        tv_customer_pin_code.setText(CustomersData.selectedCustomerPinCode);

        if(CustomersData.selectedCustomerCreatedByUserId.equalsIgnoreCase(session.getUserID())){
            btn_edit_customer.setVisibility(View.VISIBLE);
        }else{
            btn_edit_customer.setVisibility(View.GONE);
        }

        meetingTypeList.add("Select Type");
        meetingTypeList.add("Hot");
        meetingTypeList.add("Cold");
        getCustomerInfoDetails();
        if(isNetworkAvailable()){
            doMeetingTypeList();
        }

        btn_edit_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomerEditActivity();
            }
        });

    }

    public void getCustomerInfoDetails(){
        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getCustomerInfoDetailService(UrlUtil.GET_CUSTOMER_MEETINGS+"?userId="+session.getUserID()+"&cusId="+CustomersData.selectedCustomerId);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void expandableButton1(View view) {
        expandableLayout1.toggle(); // toggle expand and collapse
    }

    public void expandableButton2(View view) {
        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void startCustomersActivity() {
        new ActivityUtil(this).startCustomersActivity();
    }

    public void startCustomerEditActivity() {
        new ActivityUtil(this).startCustomerEditActivity();
    }

    @Override
    public void onBackPressed() {
        startCustomersActivity();
    }

    public void startGetCustomerInfoDetail() {
        progress.dismiss();

        adapter=new CustomerDetailsBaseAdapter(this, CustomerDetailsData.customerDetailsList);
        list.setAdapter(adapter);

        //expandableLayout2.toggle();

    }


    public void errorInfo(String msg){
        progress.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void openCustomerMeetingDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_customer_meeting);

        dialog_customer_meeting_spinner_meeting_type=(Spinner)dialog.findViewById(R.id.dialog_customer_meeting_spinner_meeting_type);
        et_next_visit_meeting_name = (EditText) dialog.findViewById(R.id.dialog_meeting_next_visit_et_meeting_name);
        et_next_visit_meeting_date = (EditText) dialog.findViewById(R.id.dialog_meeting_next_visit_et_meeting_date);
        et_next_visit_meeting_time = (EditText) dialog.findViewById(R.id.dialog_meeting_next_visit_et_meeting_time);

        til_next_visit_meeting_name = (TextInputLayout) dialog.findViewById(R.id.dialog_meeting_next_visit_til_meeting_name);
        til_next_visit_meeting_date = (TextInputLayout) dialog.findViewById(R.id.dialog_meeting_next_visit_til_meeting_date);
        til_next_visit_meeting_time = (TextInputLayout) dialog.findViewById(R.id.dialog_meeting_next_visit_til_meeting_time);

        ImageButton btn_cancel = (ImageButton) dialog.findViewById(R.id.dialog_meeting_next_visit_ib_cancel);
        Button btn_submit = (Button) dialog.findViewById(R.id.dialog_meeting_next_visit_btn_submit);

        et_next_visit_meeting_name.addTextChangedListener(new MyTextWatcher(et_next_visit_meeting_name));
        et_next_visit_meeting_date.addTextChangedListener(new MyTextWatcher(et_next_visit_meeting_date));
        et_next_visit_meeting_time.addTextChangedListener(new MyTextWatcher(et_next_visit_meeting_time));


        et_next_visit_meeting_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CustomerDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_next_visit_meeting_date.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });



        et_next_visit_meeting_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CustomerDetailsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        String select_time =  mFormat.format(Double.valueOf(selectedHour)) + ":" +  mFormat.format(Double.valueOf(selectedMinute));
                        et_next_visit_meeting_time.setText(""+ select_time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        meetingTypeAdapter = new ArrayAdapter<String>(CustomerDetailsActivity.this,R.layout.spinner_rows, meetingTypeList);
        meetingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialog_customer_meeting_spinner_meeting_type.setAdapter(meetingTypeAdapter);
        dialog_customer_meeting_spinner_meeting_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                meetingTypeIdSelected=meetingTypeId.get(position);
                meetingTypeSelected=meetingTypeList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateMeetingName()) {
                    return;
                }
                if(meetingTypeIdSelected.isEmpty()||meetingTypeIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(dialog_customer_meeting_spinner_meeting_type);
                    Toast.makeText(CustomerDetailsActivity.this,"please select meeting type",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateMeetingDate()) {
                    return;
                }
                if (!validateMeetingTime()) {
                    return;
                }

                submitCustomerMeeting();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }


    public void submitCustomerMeeting() {

        String userId = session.getUserID();
        String userParentPath = session.getUserParentPath();
        String mtnCustomerId = CustomersData.selectedCustomerId;
        String mtnName = et_next_visit_meeting_name.getText().toString().trim();
        String mtnMeetingTypeId = meetingTypeIdSelected;
        String mtnMeetingType = meetingTypeSelected;
        String mtnDate = et_next_visit_meeting_date.getText().toString().trim();
        String mtnTime = et_next_visit_meeting_time.getText().toString();

        if(isNetworkAvailable()){

                progress = new ProgressDialog(this);
                progress.setMessage("loading...");
                progress.show();
                progress.setCanceledOnTouchOutside(false);

                presenter.addCustomerMeetingService(UrlUtil.ADD_CUSTOMER_MEETING_URL, userId, mtnCustomerId, mtnName, mtnMeetingTypeId, mtnMeetingType, mtnDate, mtnTime, userParentPath);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }


    public void successAddCustomerMeetingInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        getCustomerInfoDetails();
    }

    public void errorAddCustomerMeetingInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
                case R.id.dialog_meeting_next_visit_et_meeting_name:
                    validateMeetingName();
                    break;
                case R.id.dialog_meeting_next_visit_et_meeting_date:
                    validateMeetingDate();
                    break;
                case R.id.dialog_meeting_next_visit_et_meeting_time:
                    validateMeetingTime();
                    break;
            }
        }
    }


    private boolean validateMeetingName() {
        if (et_next_visit_meeting_name.getText().toString().trim().isEmpty()) {
            til_next_visit_meeting_name.setError("meeting name can not be blank");
            requestFocus(et_next_visit_meeting_name);
            return false;
        } else {
            til_next_visit_meeting_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeetingDate() {
        if (et_next_visit_meeting_date.getText().toString().trim().isEmpty()) {
            til_next_visit_meeting_date.setError("meeting date can not be blank");
            requestFocus(et_next_visit_meeting_date);
            return false;
        } else {
            til_next_visit_meeting_date.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeetingTime() {
        if (et_next_visit_meeting_time.getText().toString().trim().isEmpty()) {
            til_next_visit_meeting_time.setError("meeting time can not be blank");
            requestFocus(et_next_visit_meeting_time);
            return false;
        } else {
            til_next_visit_meeting_time.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //*********** Web services for Meeting Type ***********************//
    public void doMeetingTypeList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_MEETING_TYPES;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseMeetingTypeResponse(response);
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

    public void parseMeetingTypeResponse(final JSONArray response){
        try {
            if(meetingTypeList.size()>0){

                meetingTypeList.clear();
                meetingTypeId.clear();
            }
            meetingTypeList.add("Meeting Type");
            meetingTypeId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);
                Log.d("mtntName: ", records.optString("mtntName"));

                meetingTypeList.add(records.optString("mtntName"));
                meetingTypeId.add(records.optString("mtntId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //meetingTypeAdapter.notifyDataSetChanged();
    }
}
