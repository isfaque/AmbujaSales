package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingAddScreen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.presenters.MeetingAddPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/11/2017.
 */
public class MeetingAddActivity extends AppCompatActivity implements MeetingAddActivityView {

    private MeetingAddPresenter presenter;

    SharedManagerUtil session;

    int india_position, west_bengal_position, kolkata_position;

    private ProgressDialog progress;

    private EditText
            et_first_name,
            et_last_name,
            et_dob,
            et_doa,
            et_email,
            et_mobile_no,
            et_alternate_no,
            et_address,
            et_landmark,
            et_area,
            et_pin_code,
            et_meeting_name,
            et_meeting_date,
            et_meeting_time;

    private TextInputLayout
            til_first_name,
            til_last_name,
            til_dob,
            til_doa,
            til_company_name,
            til_department,
            til_designation,
            til_email,
            til_mobile_no,
            til_alternate_no,
            til_address,
            til_landmark,
            til_area,
            til_pin_code,
            til_meeting_name,
            til_meeting_date,
            til_meeting_time;


    private AutoCompleteTextView
            act_company_name,
            act_department,
            act_designation,
            act_area;

    private Spinner
            spinner_gender,
            spinner_customer_type,
            spinner_industry_type,
            spinner_country,
            spinner_state,
            spinner_city,
            spinner_meeting_type;

    private Button
            btn_submit,
            btn_cancel;

    ArrayList<String> genderList = new ArrayList<String>();
    ArrayList<String> genderId = new ArrayList<String>();

    ArrayList<String> customerTypeList = new ArrayList<String>();
    ArrayList<String> customerTypeId = new ArrayList<String>();

    ArrayList<String> industryTypeList = new ArrayList<String>();
    ArrayList<String> industryTypeId = new ArrayList<String>();

    ArrayList<String> countryList = new ArrayList<String>();
    ArrayList<String> countryId = new ArrayList<String>();

    ArrayList<String> stateList = new ArrayList<String>();
    ArrayList<String> stateId = new ArrayList<String>();

    ArrayList<String> cityList = new ArrayList<String>();
    ArrayList<String> cityId = new ArrayList<String>();

    ArrayList<String> companyList = new ArrayList<String>();

    ArrayList<String> departmentList = new ArrayList<String>();

    ArrayList<String> designationList = new ArrayList<String>();

    ArrayList<String> areaList = new ArrayList<String>();

    ArrayList<String> meetingTypeList = new ArrayList<String>();
    ArrayList<String> meetingTypeId = new ArrayList<String>();

    ArrayAdapter<String> genderAdapter;
    ArrayAdapter<String> customerTypeAdapter;
    ArrayAdapter<String> industryTypeAdapter;
    ArrayAdapter<String> companyAdapter;
    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> designationAdapter;
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> areaAdapter;
    ArrayAdapter<String> meetingTypeAdapter;

    String genderIdSelected = "";
    String genderSelected = "";

    String customerTypeIdSelected = "";
    String customerTypeSelected = "";

    String industryTypeIdSelected = "";
    String industryTypeSelected = "";

    String countryIdSelected = "";
    String countrySelected = "";

    String stateIdSelected = "";
    String stateSelected = "";

    String cityIdSelected = "";
    String citySelected = "";

    String meetingTypeIdSelected = "";
    String meetingTypeSelected = "";


    private boolean gpsEnabled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_add);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new MeetingAddPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Customer Meeting");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingsActivity();
            }
        });

        if(isNetworkAvailable()){
            doGenderList();
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        til_first_name = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_first_name);
        til_last_name = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_last_name);
        til_dob = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_customer_dob);
        til_doa = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_customer_anniversary);
        til_company_name = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_company_name);
        til_department = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_department);
        til_designation = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_designation);
        til_email = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_email);
        til_mobile_no = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_mobile_no);
        til_alternate_no = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_alternate_no);
        til_address = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_address);
        til_landmark = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_landmark);
        til_area = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_area);
        til_pin_code = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_pin_code);
        til_meeting_name = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_meeting_name);
        til_meeting_date = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_meeting_date);
        til_meeting_time = (TextInputLayout) findViewById(R.id.activity_meeting_add_til_meeting_time);

        et_first_name = (EditText) findViewById(R.id.activity_meeting_add_et_first_name);
        et_last_name = (EditText) findViewById(R.id.activity_meeting_add_et_last_name);
        et_dob = (EditText) findViewById(R.id.activity_meeting_add_et_customer_dob);
        et_doa = (EditText) findViewById(R.id.activity_meeting_add_et_customer_anniversary);
        et_email = (EditText) findViewById(R.id.activity_meeting_add_et_email);
        et_mobile_no = (EditText) findViewById(R.id.activity_meeting_add_et_mobile_no);
        et_alternate_no = (EditText) findViewById(R.id.activity_meeting_add_et_alternate_no);
        et_address = (EditText) findViewById(R.id.activity_meeting_add_et_address);
        et_landmark = (EditText) findViewById(R.id.activity_meeting_add_et_landmark);
        et_pin_code = (EditText) findViewById(R.id.activity_meeting_add_et_pin_code);
        et_meeting_name = (EditText) findViewById(R.id.activity_meeting_add_et_meeting_name);
        et_meeting_date = (EditText) findViewById(R.id.activity_meeting_add_et_meeting_date);
        et_meeting_time = (EditText) findViewById(R.id.activity_meeting_add_et_meeting_time);

        act_company_name = (AutoCompleteTextView) findViewById(R.id.activity_meeting_add_act_company_name);
        act_department = (AutoCompleteTextView) findViewById(R.id.activity_meeting_add_act_department);
        act_designation = (AutoCompleteTextView) findViewById(R.id.activity_meeting_add_act_designation);
        act_area = (AutoCompleteTextView) findViewById(R.id.activity_meeting_add_act_area);

        spinner_gender = (Spinner) findViewById(R.id.activity_meeting_add_spinner_gender);
        spinner_customer_type = (Spinner) findViewById(R.id.activity_meeting_add_spinner_customer_type);
        spinner_industry_type = (Spinner) findViewById(R.id.activity_meeting_add_spinner_industry_type);
        spinner_country = (Spinner) findViewById(R.id.activity_meeting_add_spinner_country);
        spinner_state = (Spinner) findViewById(R.id.activity_meeting_add_spinner_state);
        spinner_city = (Spinner) findViewById(R.id.activity_meeting_add_spinner_city);
        spinner_meeting_type=(Spinner)findViewById(R.id.activity_meeting_add_spinner_meeting_type);


        et_first_name.addTextChangedListener(new MyTextWatcher(et_first_name));
        et_last_name.addTextChangedListener(new MyTextWatcher(et_last_name));
        et_dob.addTextChangedListener(new MyTextWatcher(et_dob));
        et_doa.addTextChangedListener(new MyTextWatcher(et_doa));
        act_company_name.addTextChangedListener(new MyTextWatcher(act_company_name));
        act_department.addTextChangedListener(new MyTextWatcher(act_department));
        act_designation.addTextChangedListener(new MyTextWatcher(act_designation));
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_mobile_no.addTextChangedListener(new MyTextWatcher(et_mobile_no));
        et_alternate_no.addTextChangedListener(new MyTextWatcher(et_alternate_no));
        et_address.addTextChangedListener(new MyTextWatcher(et_address));
        et_landmark.addTextChangedListener(new MyTextWatcher(et_landmark));
        act_area.addTextChangedListener(new MyTextWatcher(act_area));
        et_pin_code.addTextChangedListener(new MyTextWatcher(et_pin_code));
        et_meeting_name.addTextChangedListener(new MyTextWatcher(et_meeting_name));
        et_meeting_date.addTextChangedListener(new MyTextWatcher(et_meeting_date));
        et_meeting_time.addTextChangedListener(new MyTextWatcher(et_meeting_time));


        btn_cancel = (Button) findViewById(R.id.activity_meeting_add_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_meeting_add_btn_submit);


        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MeetingAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_dob.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });


        et_doa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MeetingAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_doa.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        et_meeting_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MeetingAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_meeting_date.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        et_meeting_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MeetingAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        String select_time =  mFormat.format(Double.valueOf(selectedHour)) + ":" +  mFormat.format(Double.valueOf(selectedMinute));
                        et_meeting_time.setText(""+ select_time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                genderIdSelected = genderId.get(position);
                genderSelected=genderList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });



        spinner_customer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                customerTypeIdSelected = customerTypeId.get(position);
                customerTypeSelected=customerTypeList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        spinner_industry_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                industryTypeIdSelected = industryTypeId.get(position);
                industryTypeSelected=industryTypeList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                countryIdSelected = countryId.get(position);
                countrySelected=countryList.get(position);

                if(!countryIdSelected.equalsIgnoreCase("-1"))
                    doStateList(countryIdSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                stateIdSelected = stateId.get(position);
                stateSelected=stateList.get(position);

                if(!stateIdSelected.equalsIgnoreCase("-1"))
                    doCityList(stateIdSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                cityIdSelected=cityId.get(position);
                citySelected=cityList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        spinner_meeting_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                meetingTypeIdSelected = meetingTypeId.get(position);
                meetingTypeSelected=meetingTypeList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                // TODO Auto-generated method stub

            }
        });

        genderAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(genderAdapter);

        customerTypeAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, customerTypeList);
        customerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_customer_type.setAdapter(customerTypeAdapter);

        industryTypeAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, industryTypeList);
        industryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_industry_type.setAdapter(industryTypeAdapter);

        countryAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country.setAdapter(countryAdapter);

        stateAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(stateAdapter);

        cityAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(cityAdapter);

        meetingTypeAdapter = new ArrayAdapter<String>(MeetingAddActivity.this,R.layout.spinner_rows, meetingTypeList);
        meetingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_meeting_type.setAdapter(meetingTypeAdapter);


        companyAdapter= new ArrayAdapter<String>(MeetingAddActivity.this, android.R.layout.simple_dropdown_item_1line, companyList);
        act_company_name.setAdapter(companyAdapter);
        act_company_name.setThreshold(1);
        act_company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_company_name.showDropDown();
            }
        });

        departmentAdapter= new ArrayAdapter<String>(MeetingAddActivity.this, android.R.layout.simple_dropdown_item_1line, departmentList);
        act_department.setAdapter(departmentAdapter);
        act_department.setThreshold(1);
        act_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_department.showDropDown();
            }
        });

        designationAdapter= new ArrayAdapter<String>(MeetingAddActivity.this, android.R.layout.simple_dropdown_item_1line, designationList);
        act_designation.setAdapter(designationAdapter);
        act_designation.setThreshold(1);
        act_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_designation.showDropDown();
            }
        });

        areaAdapter= new ArrayAdapter<String>(MeetingAddActivity.this, android.R.layout.simple_dropdown_item_1line, areaList);
        act_area.setAdapter(areaAdapter);
        act_area.setThreshold(1);
        act_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_area.showDropDown();
            }
        });
        /*
        activity_meeting_add_act_customer_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                areaSelect=activity_meeting_add_act_customer_area.getText().toString();
            }
        });*/


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateFirstName()) {
                    return;
                }
                if (!validateLastName()) {
                    return;
                }
                if(customerTypeIdSelected.isEmpty()||customerTypeIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_customer_type);
                    Toast.makeText(MeetingAddActivity.this,"please select customer type",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(industryTypeIdSelected.isEmpty()||industryTypeIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_industry_type);
                    Toast.makeText(MeetingAddActivity.this,"please select industry type",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateCompanyName()) {
                    return;
                }
                if (!validateEmail()) {
                    return;
                }
                if (!validateMobile()) {
                    return;
                }
                if(countryIdSelected.isEmpty()||countryIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_country);
                    Toast.makeText(MeetingAddActivity.this,"please select country",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stateIdSelected.isEmpty()||stateIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_state);
                    Toast.makeText(MeetingAddActivity.this,"please select state",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cityIdSelected.isEmpty()||cityIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_city);
                    Toast.makeText(MeetingAddActivity.this,"please select city",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateAddress()) {
                    return;
                }
                if (!validateArea()) {
                    return;
                }
                if (!validatePinCode()) {
                    return;
                }

                if (!validateMeetingName()) {
                    return;
                }
                if(meetingTypeIdSelected.isEmpty()||meetingTypeIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_meeting_type);
                    Toast.makeText(MeetingAddActivity.this,"please select meeting type",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateMeetingDate()) {
                    return;
                }
                if (!validateMeetingTime()) {
                    return;
                }

                submitNewCustomerMeeting();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingsActivity();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startMeetingsActivity();
    }

    public void submitNewCustomerMeeting(){

        String userId = session.getUserID();
        String userParentPath = session.getUserParentPath();

        String cusFirstName = et_first_name.getText().toString().trim();
        String cusLastName = et_last_name.getText().toString().trim();
        String cusGenderId = genderIdSelected;
        String cusGender = genderSelected;
        String cusDOB = et_dob.getText().toString().trim();
        String cusDOA = et_doa.getText().toString().trim();
        String cusCustomerTypeId = customerTypeIdSelected;
        String cusCustomerType = customerTypeSelected;
        String cusIndustryTypeId = industryTypeIdSelected;
        String cusIndustryType = industryTypeSelected;
        String cusCompanyName = act_company_name.getText().toString().trim();
        String cusDepartment = act_department.getText().toString().trim();
        String cusDesignation = act_designation.getText().toString().trim();
        String cusEmail = et_email.getText().toString().trim();
        String cusMobileNo = et_mobile_no.getText().toString().trim();
        String cusAlternateNo = et_alternate_no.getText().toString().trim();
        String cusCountryId = countryIdSelected;
        String cusCountry = countrySelected;
        String cusStateId = stateIdSelected;
        String cusState = stateSelected;
        String cusCityId = cityIdSelected;
        String cusCity = citySelected;
        String cusAddress = et_address.getText().toString().trim();
        String cusLandmark = et_landmark.getText().toString().trim();
        String cusArea = act_area.getText().toString().trim();
        String cusPinCode = et_pin_code.getText().toString().trim();

        String mtnName = et_meeting_name.getText().toString().trim();
        String mtnMeetingTypeId = meetingTypeIdSelected;
        String mtnMeetingType = meetingTypeSelected;
        String mtnDate = et_meeting_date.getText().toString().trim();
        String mtnTime = et_meeting_time.getText().toString().trim();

        if(isNetworkAvailable()){

            LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gpsEnabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!gpsEnabled){
                buildAlertMessageNoGps();
            }else {
                progress = new ProgressDialog(this);
                progress.setMessage("loading...");
                progress.show();
                progress.setCanceledOnTouchOutside(false);

                presenter.addNewCustomerMeetingService(
                        UrlUtil.ADD_NEW_CUSTOMER_MEETING,
                        userId,
                        userParentPath,
                        cusFirstName,
                        cusLastName,
                        cusGenderId,
                        cusGender,
                        cusDOB,
                        cusDOA,
                        cusCustomerTypeId,
                        cusCustomerType,
                        cusIndustryTypeId,
                        cusIndustryType,
                        cusCompanyName,
                        cusDepartment,
                        cusDesignation,
                        cusEmail,
                        cusMobileNo,
                        cusAlternateNo,
                        cusCountryId,
                        cusCountry,
                        cusStateId,
                        cusState,
                        cusCityId,
                        cusCity,
                        cusAddress,
                        cusLandmark,
                        cusArea,
                        cusPinCode,
                        mtnName,
                        mtnMeetingTypeId,
                        mtnMeetingType,
                        mtnDate,
                        mtnTime
                );
            }

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();

        }


    }


    public void startMeetingsActivity() {
        new ActivityUtil(this).startMeetingsActivity();
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        startMeetingsActivity();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Activate GPS to use use location services")
                .setTitle("Location not available, Open GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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
                case R.id.activity_meeting_add_et_email:
                    validateEmail();
                    break;
                case R.id.activity_meeting_add_et_mobile_no:
                    validateMobile();
                    break;
                case R.id.activity_meeting_add_et_first_name:
                    validateFirstName();
                    break;
                case R.id.activity_meeting_add_et_last_name:
                    validateLastName();
                    break;
                case R.id.activity_meeting_add_act_company_name:
                    validateCompanyName();
                    break;
                case R.id.activity_meeting_add_et_address:
                    validateAddress();
                    break;
                case R.id.activity_meeting_add_et_pin_code:
                    validatePinCode();
                    break;
                case R.id.activity_meeting_add_et_meeting_name:
                    validateMeetingName();
                    break;
                case R.id.activity_meeting_add_et_meeting_date:
                    validateMeetingDate();
                    break;
                case R.id.activity_meeting_add_et_meeting_time:
                    validateMeetingTime();
                    break;
            }
        }
    }



    //*********** Functions for Validation *********************//
    private boolean validateFirstName() {
        if (et_first_name.getText().toString().trim().isEmpty()) {
            til_first_name.setError("first name can not be blank");
            requestFocus(et_first_name);
            return false;
        } else {
            til_first_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastName() {
        if (et_last_name.getText().toString().trim().isEmpty()) {
            til_last_name.setError("last name can not be blank");
            requestFocus(et_last_name);
            return false;
        } else {
            til_last_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCompanyName() {
        if (act_company_name.getText().toString().trim().isEmpty()) {
            til_company_name.setError("company name can not be blank");
            requestFocus(act_company_name);
            return false;
        } else {
            til_company_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (et_address.getText().toString().trim().isEmpty()) {
            til_address.setError("address can not be blank");
            requestFocus(et_address);
            return false;
        } else {
            til_address.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateArea() {
        if (act_area.getText().toString().trim().isEmpty()) {
            til_area.setError("Area can not be blank");
            requestFocus(act_area);
            return false;
        } else {
            til_area.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePinCode() {
        if (et_pin_code.getText().toString().trim().isEmpty()) {
            til_pin_code.setError("pin code can not be blank");
            requestFocus(et_pin_code);
            return false;
        } else {
            til_pin_code.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeetingName() {
        if (et_meeting_name.getText().toString().trim().isEmpty()) {
            til_meeting_name.setError("meeting name can not be blank");
            requestFocus(et_meeting_name);
            return false;
        } else {
            til_meeting_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeetingDate() {
        if (et_meeting_date.getText().toString().trim().isEmpty()) {
            til_meeting_date.setError("meeting date can not be blank");
            requestFocus(et_meeting_date);
            return false;
        } else {
            til_meeting_date.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMeetingTime() {
        if (et_meeting_time.getText().toString().trim().isEmpty()) {
            til_meeting_time.setError("meeting time can not be blank");
            requestFocus(et_meeting_time);
            return false;
        } else {
            til_meeting_time.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            til_email.setError("email is not valid");
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        String mobile_no=et_mobile_no.getText().toString().trim();

        if (mobile_no.isEmpty()||!isValidMobile(mobile_no)) {
            til_mobile_no.setError("mobile no is not valid");
            requestFocus(et_mobile_no);
            return false;
        } else {
            til_mobile_no.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(phone.length() < 10 || phone.length() > 10) {
            // if(phone.length() != 10) {
            check = false;
            //txtPhone.setError("Not Valid Number");
        } else {
            check = true;
        }
        return check;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    //*********** Web services for Gender ***********************//
    public void doGenderList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_GENDERS;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseGenderResponse(response);
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

    public void parseGenderResponse(final JSONArray response){
        try {
            if(genderList.size()>0){

                genderList.clear();
                genderId.clear();
            }
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);
                Log.d("gndName: ", records.optString("gndName"));

                genderList.add(records.optString("gndName"));
                genderId.add(records.optString("gndId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        genderAdapter.notifyDataSetChanged();
        doCustomerTypeList();
    }



    //*********** Web services for Customer Type ***********************//
    public void doCustomerTypeList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_CUSTOMER_TYPES;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseCustomerTypeResponse(response);
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

    public void parseCustomerTypeResponse(final JSONArray response){
        try {
            if(customerTypeList.size()>0){

                customerTypeList.clear();
                customerTypeId.clear();
            }
            customerTypeList.add("Customer Type");
            customerTypeId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);
                Log.d("cusName: ", records.optString("custName"));

                customerTypeList.add(records.optString("custName"));
                customerTypeId.add(records.optString("custId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        customerTypeAdapter.notifyDataSetChanged();
        doIndustryTypeList();
    }

    //*********** Web services for Industry Type ***********************//
    public void doIndustryTypeList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_INDUSTRY_TYPES;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseIndustryTypeResponse(response);
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

    public void parseIndustryTypeResponse(final JSONArray response){
        try {
            if(industryTypeList.size()>0){

                industryTypeList.clear();
                industryTypeId.clear();
            }
            industryTypeList.add("Industry Type");
            industryTypeId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);
                Log.d("indtName: ", records.optString("indtName"));

                industryTypeList.add(records.optString("indtName"));
                industryTypeId.add(records.optString("indtId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        industryTypeAdapter.notifyDataSetChanged();
        doCompanyList();
    }


    //*********** Web services for Companies ***********************//
    public void doCompanyList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_COMPANIES;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseCompanyResponse(response);
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

    public void parseCompanyResponse(final JSONArray response){
        try {
            if(companyList.size()>0){

                companyList.clear();
            }
            for (int i = 0; i < response.length(); i++) {

                String cmpName = response.getString(i);
                Log.d("cmpName: ", response.getString(i));

                companyList.add(cmpName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        companyAdapter.notifyDataSetChanged();
        doDepartmentList();
    }

    //*********** Web services for Departments ***********************//
    public void doDepartmentList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_DEPARTMENTS;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseDepartmentResponse(response);
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

    public void parseDepartmentResponse(final JSONArray response){
        try {
            if(departmentList.size()>0){

                departmentList.clear();
            }
            for (int i = 0; i < response.length(); i++) {

                String deptName = response.getString(i);
                Log.d("deptName: ", response.getString(i));

                departmentList.add(deptName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        departmentAdapter.notifyDataSetChanged();
        doDesignationList();
    }

    //*********** Web services for Designations ***********************//
    public void doDesignationList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_DESIGNATIONS;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseDesignationResponse(response);
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

    public void parseDesignationResponse(final JSONArray response){
        try {
            if(designationList.size()>0){

                designationList.clear();
            }
            for (int i = 0; i < response.length(); i++) {

                String desgName = response.getString(i);
                Log.d("desgName: ", response.getString(i));

                designationList.add(desgName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        designationAdapter.notifyDataSetChanged();
        doAreaList();
    }

    //*********** Web services for Areas ***********************//
    public void doAreaList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_AREAS;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseAreaResponse(response);
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

    public void parseAreaResponse(final JSONArray response){
        try {
            if(areaList.size()>0){

                areaList.clear();
            }
            for (int i = 0; i < response.length(); i++) {

                String areaName = response.getString(i);
                Log.d("areaName: ", response.getString(i));

                areaList.add(areaName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        areaAdapter.notifyDataSetChanged();
        doMeetingTypeList();
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
        meetingTypeAdapter.notifyDataSetChanged();
        doCountryList();
    }




    //*********** Web services for country ***********************//
    public void doCountryList(){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_COUNTRIES;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseCountryResponse(response);
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

    public void parseCountryResponse(final JSONArray response){
        try {
            if(countryList.size()>0){

                countryList.clear();
                countryId.clear();
            }
            countryList.add("Select Country");
            countryId.add("-1");

            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);

                if(records.optString("countryName").toString().equalsIgnoreCase("India")){
                    india_position = i+1;
                }



                countryList.add(records.optString("countryName"));
                countryId.add(records.optString("countryId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        countryAdapter.notifyDataSetChanged();
        spinner_country.setSelection(india_position);
    }


    //*********** Web services for State ***********************//
    public void doStateList(String countryid){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_STATES_BY_ID+"?id="+countryid;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseStateResponse(response);
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

    public void parseStateResponse(final JSONArray response){
        try {
            if(stateList.size()>0){

                stateList.clear();
                stateId.clear();
            }
            stateList.add("Select State");
            stateId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);

                if(records.optString("stateName").toString().equalsIgnoreCase("West Bengal")){
                    west_bengal_position = i+1;
                }

                stateList.add(records.optString("stateName"));
                stateId.add(records.optString("stateId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        stateAdapter.notifyDataSetChanged();
        spinner_state.setSelection(west_bengal_position);
    }


    //*********** Web services for city ***********************//
    public void doCityList(String stateid){
        String tag_json_arry = "json_array_req";

        String url = UrlUtil.GET_CITIES_BY_ID+"?id="+stateid;

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseCityResponse(response);
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

    public void parseCityResponse(final JSONArray response){
        try {
            if(cityList.size()>0){

                cityList.clear();
                cityId.clear();
            }
            cityList.add("Select City");
            cityId.add("-1");
            for (int i = 0; i < response.length(); i++) {

                JSONObject records = response.optJSONObject(i);

                if(records.optString("cityName").toString().equalsIgnoreCase("Kolkata")){
                    kolkata_position = i+1;
                }

                cityList.add(records.optString("cityName"));
                cityId.add(records.optString("cityId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cityAdapter.notifyDataSetChanged();
        spinner_city.setSelection(kolkata_position);
    }


}
