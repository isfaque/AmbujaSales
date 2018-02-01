package com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomerEditScreen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomersAdapter.CustomersData;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.presenters.CustomerAddPresenter;
import com.lnsel.ambujaneotiasales.presenters.CustomerEditPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by apps2 on 2/1/2018.
 */
public class CustomerEditActivity extends AppCompatActivity implements CustomerEditActivityView {

    private CustomerEditPresenter presenter;

    SharedManagerUtil session;
    int india_position, west_bengal_position, kolkata_position, gender_position, industry_type_position, customer_type_position;
    public static String customer_available_type;

    private ProgressDialog progress;

    private EditText
            et_first_name,
            et_last_name,
            et_dob,
            et_doa,
            et_mobile_no,
            et_alternate_no,
            et_email,
            et_address,
            et_landmark,
            et_pin_code;

    private TextInputLayout
            til_first_name,
            til_last_name,
            til_company_name,
            til_mobile_no,
            til_email,
            til_address,
            til_area,
            til_pin_code;

    private Spinner
            spinner_gender,
            spinner_customer_type,
            spinner_industry_type,
            spinner_country,
            spinner_state,
            spinner_city;


    private Button
            btn_submit,
            btn_cancel;

    private AutoCompleteTextView
            act_company_name,
            act_department,
            act_designation,
            act_area;

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

    ArrayList<String> departmentList = new ArrayList<String>();
    ArrayList<String> designationList = new ArrayList<String>();
    ArrayList<String> areaList = new ArrayList<String>();
    ArrayList<String> companyList = new ArrayList<String>();


    ArrayAdapter<String> genderAdapter;
    ArrayAdapter<String> customerTypeAdapter;
    ArrayAdapter<String> industryTypeAdapter;
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> stateAdapter;
    ArrayAdapter<String> cityAdapter;

    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> designationAdapter;
    ArrayAdapter<String> areaAdapter;
    ArrayAdapter<String> companyAdapter;

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


    private boolean gpsEnabled;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new CustomerEditPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Customer");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomersActivity();
            }
        });

        et_first_name = (EditText) findViewById(R.id.activity_customer_edit_et_first_name);
        et_last_name = (EditText) findViewById(R.id.activity_customer_edit_et_last_name);
        et_dob = (EditText) findViewById(R.id.activity_customer_edit_et_customer_dob);
        et_doa = (EditText) findViewById(R.id.activity_customer_edit_et_customer_anniversary);
        et_mobile_no = (EditText) findViewById(R.id.activity_customer_edit_et_mobile_no);
        et_alternate_no = (EditText) findViewById(R.id.activity_customer_edit_et_alternate_no);
        et_email = (EditText) findViewById(R.id.activity_customer_edit_et_email);
        et_address = (EditText) findViewById(R.id.activity_customer_edit_et_address);
        et_landmark = (EditText) findViewById(R.id.activity_customer_edit_et_landmark);
        et_pin_code = (EditText) findViewById(R.id.activity_customer_edit_et_pin_code);

        act_company_name = (AutoCompleteTextView) findViewById(R.id.activity_customer_edit_act_company_name);
        act_department = (AutoCompleteTextView) findViewById(R.id.activity_customer_edit_act_department);
        act_designation = (AutoCompleteTextView) findViewById(R.id.activity_customer_edit_act_designation);
        act_area = (AutoCompleteTextView) findViewById(R.id.activity_customer_edit_act_area);

        spinner_gender = (Spinner) findViewById(R.id.activity_customer_edit_spinner_gender);
        spinner_customer_type = (Spinner) findViewById(R.id.activity_customer_edit_spinner_customer_type);
        spinner_industry_type = (Spinner) findViewById(R.id.activity_customer_edit_spinner_industry_type);
        spinner_country = (Spinner) findViewById(R.id.activity_customer_edit_spinner_country);
        spinner_state = (Spinner) findViewById(R.id.activity_customer_edit_spinner_state);
        spinner_city = (Spinner) findViewById(R.id.activity_customer_edit_spinner_city);

        til_first_name = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_first_name);
        til_last_name = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_last_name);
        til_company_name = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_company_name);
        til_mobile_no = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_mobile_no);
        til_email = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_email);
        til_address = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_address);
        til_area = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_area);
        til_pin_code = (TextInputLayout) findViewById(R.id.activity_customer_edit_til_pin_code);

        et_first_name.addTextChangedListener(new MyTextWatcher(et_first_name));
        et_last_name.addTextChangedListener(new MyTextWatcher(et_last_name));
        act_company_name.addTextChangedListener(new MyTextWatcher(act_company_name));
        et_mobile_no.addTextChangedListener(new MyTextWatcher(et_mobile_no));
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_address.addTextChangedListener(new MyTextWatcher(et_address));
        act_area.addTextChangedListener(new MyTextWatcher(act_area));
        et_pin_code.addTextChangedListener(new MyTextWatcher(et_pin_code));

        btn_cancel = (Button) findViewById(R.id.activity_customer_edit_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_customer_edit_btn_submit);

        et_first_name.setText(CustomersData.selectedCustomerFirstName);
        et_last_name.setText(CustomersData.selectedCustomerLastName);
        et_dob.setText(CustomersData.selectedCustomerDOB);
        et_doa.setText(CustomersData.selectedCustomerDOA);
        et_mobile_no.setText(CustomersData.selectedCustomerMobileNo);
        et_alternate_no.setText(CustomersData.selectedCustomerAlternateNo);
        et_email.setText(CustomersData.selectedCustomerEmail);
        et_address.setText(CustomersData.selectedCustomerAddress);
        et_landmark.setText(CustomersData.selectedCustomerLandmark);
        et_pin_code.setText(CustomersData.selectedCustomerPinCode);
        act_company_name.setText(CustomersData.selectedCustomerCompanyName);
        act_department.setText(CustomersData.selectedCustomerDepartment);
        act_designation.setText(CustomersData.selectedCustomerDesignation);
        act_area.setText(CustomersData.selectedCustomerArea);



        if(isNetworkAvailable()){
            doGenderList();
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }



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

        genderAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, genderList);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(genderAdapter);

        industryTypeAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, industryTypeList);
        industryTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_industry_type.setAdapter(industryTypeAdapter);

        customerTypeAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, customerTypeList);
        customerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_customer_type.setAdapter(customerTypeAdapter);

        countryAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country.setAdapter(countryAdapter);

        stateAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(stateAdapter);

        cityAdapter = new ArrayAdapter<String>(CustomerEditActivity.this,R.layout.spinner_rows, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(cityAdapter);


        companyAdapter= new ArrayAdapter<String>(CustomerEditActivity.this, android.R.layout.simple_dropdown_item_1line, companyList);
        act_company_name.setAdapter(companyAdapter);
        act_company_name.setThreshold(1);
        act_company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_company_name.showDropDown();
            }
        });


        departmentAdapter= new ArrayAdapter<String>(CustomerEditActivity.this, android.R.layout.simple_dropdown_item_1line, departmentList);
        act_department.setAdapter(departmentAdapter);
        act_department.setThreshold(1);
        act_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_department.showDropDown();
            }
        });


        designationAdapter= new ArrayAdapter<String>(CustomerEditActivity.this, android.R.layout.simple_dropdown_item_1line, designationList);
        act_designation.setAdapter(designationAdapter);
        act_designation.setThreshold(1);
        act_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_designation.showDropDown();
            }
        });


        areaAdapter= new ArrayAdapter<String>(CustomerEditActivity.this, android.R.layout.simple_dropdown_item_1line, areaList);
        act_area.setAdapter(areaAdapter);
        act_area.setThreshold(1);
        act_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                act_area.showDropDown();
            }
        });


        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(CustomerEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                mDatePicker = new DatePickerDialog(CustomerEditActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    Toast.makeText(CustomerEditActivity.this,"please select customer type",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(industryTypeIdSelected.isEmpty()||industryTypeIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_industry_type);
                    Toast.makeText(CustomerEditActivity.this,"please select industry type",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!validateCompanyName()) {
                    return;
                }
                if (!validateMobile()) {
                    return;
                }
                if (!validateEmail()) {
                    return;
                }

                if(countryIdSelected.isEmpty()||countryIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_country);
                    Toast.makeText(CustomerEditActivity.this,"please select country",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(stateIdSelected.isEmpty()||stateIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_state);
                    Toast.makeText(CustomerEditActivity.this,"please select state",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cityIdSelected.isEmpty()||cityIdSelected.equalsIgnoreCase("-1")){
                    requestFocus(spinner_city);
                    Toast.makeText(CustomerEditActivity.this,"please select city",Toast.LENGTH_SHORT).show();
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

                submitNewCustomerMeeting();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomersActivity();
            }
        });


    }


    public void submitNewCustomerMeeting(){

        String userId = session.getUserID();
        String userParentPath = session.getUserParentPath();
        String cusId = CustomersData.selectedCustomerId;
        String cusFirstName = et_first_name.getText().toString().trim();
        String cusLastName = et_last_name.getText().toString().trim();
        String cusGenderId = genderIdSelected;
        String cusGender = genderSelected;
        String cusDOB = et_dob.getText().toString().trim();
        String cusDOA  = et_doa.getText().toString().trim();
        String cusCustomerTypeId = customerTypeIdSelected;
        String cusCustomerType = customerTypeSelected;
        String cusIndustryTypeId = industryTypeIdSelected;
        String cusIndustryType = industryTypeSelected;
        String cusCompanyName = act_company_name.getText().toString().trim();
        String cusDepartment = act_department.getText().toString().trim();
        String cusDesignation = act_designation.getText().toString().trim();
        String cusMobileNo = et_mobile_no.getText().toString().trim();
        String cusAlternateNo = et_alternate_no.getText().toString().trim();
        String cusEmail = et_email.getText().toString().trim();
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

                presenter.editNewCustomerService(
                        UrlUtil.EDIT_NEW_CUSTOMER,
                        userId,
                        userParentPath,
                        cusId,
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
                        cusMobileNo,
                        cusAlternateNo,
                        cusEmail,
                        cusCountryId,
                        cusCountry,
                        cusStateId,
                        cusState,
                        cusCityId,
                        cusCity,
                        cusAddress,
                        cusLandmark,
                        cusArea,
                        cusPinCode
                );
            }

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();

        }


    }

    public void startCustomersActivity() {new ActivityUtil(this).startCustomersActivity();}

    @Override
    public void onBackPressed() {
        startCustomersActivity();
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
        startCustomersActivity();
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
                case R.id.activity_customer_edit_et_email:
                    validateEmail();
                    break;
                case R.id.activity_customer_edit_et_mobile_no:
                    validateMobile();
                    break;
                case R.id.activity_customer_edit_et_first_name:
                    validateFirstName();
                    break;
                case R.id.activity_customer_edit_et_last_name:
                    validateLastName();
                    break;
                case R.id.activity_customer_edit_act_company_name:
                    validateCompanyName();
                    break;
                case R.id.activity_customer_edit_et_address:
                    validateAddress();
                    break;
                case R.id.activity_customer_edit_et_pin_code:
                    validatePinCode();
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

                if(records.optString("gndName").toString().equalsIgnoreCase(CustomersData.selectedCustomerGender)){
                    gender_position = i;
                }

                genderList.add(records.optString("gndName"));
                genderId.add(records.optString("gndId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        genderAdapter.notifyDataSetChanged();
        spinner_gender.setSelection(gender_position);
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

                if(records.optString("custName").toString().equalsIgnoreCase(CustomersData.selectedCustomerType)){
                    customer_type_position = i+1;
                }

                customerTypeList.add(records.optString("custName"));
                customerTypeId.add(records.optString("custId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        customerTypeAdapter.notifyDataSetChanged();
        spinner_customer_type.setSelection(customer_type_position);
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

                if(records.optString("indtName").toString().equalsIgnoreCase(CustomersData.selectedCustomerIndustryType)){
                    industry_type_position = i+1;
                }

                industryTypeList.add(records.optString("indtName"));
                industryTypeId.add(records.optString("indtId"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        industryTypeAdapter.notifyDataSetChanged();
        spinner_industry_type.setSelection(industry_type_position);
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

                if(records.optString("countryName").toString().equalsIgnoreCase(CustomersData.selectedCustomerCountry)){
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

                if(records.optString("stateName").toString().equalsIgnoreCase(CustomersData.selectedCustomerState)){
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

                if(records.optString("cityName").toString().equalsIgnoreCase(CustomersData.selectedCustomerCity)){
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
