package com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomersScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Locale;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomersAdapter.CustomersBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomersAdapter.CustomersData;
import com.lnsel.ambujaneotiasales.presenters.CustomersPresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/3/2017.
 */
public class CustomersActivity extends AppCompatActivity implements CustomersActivityView {

    private CustomersPresenter presenter;
    ListView list;
    private ProgressDialog progress;
    EditText et_customer_info_search;
    Button btn_clear;
    TextView tv_search_message;

    CustomersBaseAdapter adapter;
    SharedManagerUtil session;

    FloatingActionButton fab_add_new_customer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customers");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        // Session Manager
        session = new SharedManagerUtil(this);

        list=(ListView) findViewById(R.id.list_view);
        et_customer_info_search = (EditText) findViewById(R.id.fragment_customer_info_et_search);
        btn_clear = (Button) findViewById(R.id.fragment_customer_info_btn_clear);
        tv_search_message = (TextView) findViewById(R.id.fragment_customer_info_tv_search_message);
        fab_add_new_customer = (FloatingActionButton) findViewById(R.id.activity_customers_fab_add_customer);

        presenter = new CustomersPresenter(this);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_customer_info_search.setText("");
                tv_search_message.setVisibility(View.GONE);
            }
        });

        fab_add_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomerAddActivity();
            }
        });



        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getCustomersService(UrlUtil.GET_ALL_CUSTOMERS+"?userId="+session.getUserID());

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }



    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    public void startCustomerDetailsActivity() {
        new ActivityUtil(this).startCustomerDetailsActivity();
    }

    public void startCustomerAddActivity() {
        new ActivityUtil(this).startCustomerAddActivity();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    public void startGetCustomerInfo() {
        progress.dismiss();

        adapter=new CustomersBaseAdapter(this, CustomersData.customersList);
        list.setAdapter(adapter);
        tv_search_message.setVisibility(View.GONE);

        et_customer_info_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // When user changed the Text
                String text = et_customer_info_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear, tv_search_message);
                //list.setVisibility(View.VISIBLE);

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String customername = adapter.getCustomerFirstName(position).toString();
                //Toast.makeText(getActivity(), customername, Toast.LENGTH_SHORT).show();

                CustomersData.selectedCustomerId = adapter.getCustomerId(position).toString();
                CustomersData.selectedCustomerCode = adapter.getCustomerCode(position).toString();
                CustomersData.selectedCustomerFirstName = adapter.getCustomerFirstName(position).toString();
                CustomersData.selectedCustomerLastName = adapter.getCustomerLastName(position).toString();
                CustomersData.selectedCustomerGenderId = adapter.getCustomerGenderId(position).toString();
                CustomersData.selectedCustomerGender = adapter.getCustomerGender(position).toString();
                CustomersData.selectedCustomerDOB = adapter.getCustomerDOB(position).toString();
                CustomersData.selectedCustomerDOA = adapter.getCustomerDOA(position).toString();
                CustomersData.selectedCustomerTypeId = adapter.getCustomerTypeId(position).toString();
                CustomersData.selectedCustomerType = adapter.getCustomerType(position).toString();
                CustomersData.selectedCustomerIndustryTypeId = adapter.getCustomerIndustryTypeId(position).toString();
                CustomersData.selectedCustomerIndustryType = adapter.getCustomerIndustryType(position).toString();
                CustomersData.selectedCustomerCompanyName = adapter.getCustomerCompanyName(position).toString();
                CustomersData.selectedCustomerDepartment = adapter.getCustomerDepartment(position).toString();
                CustomersData.selectedCustomerDesignation = adapter.getCustomerDesignation(position).toString();
                CustomersData.selectedCustomerAddress = adapter.getCustomerAddress(position).toString();
                CustomersData.selectedCustomerLandmark = adapter.getCustomerLandmark(position).toString();
                CustomersData.selectedCustomerArea = adapter.getCustomerArea(position).toString();
                CustomersData.selectedCustomerCountry = adapter.getCustomerCountry(position).toString();
                CustomersData.selectedCustomerState = adapter.getCustomerState(position).toString();
                CustomersData.selectedCustomerCity = adapter.getCustomerCity(position).toString();
                CustomersData.selectedCustomerPinCode = adapter.getCustomerPinCode(position).toString();
                CustomersData.selectedCustomerEmail = adapter.getCustomerEmail(position).toString();
                CustomersData.selectedCustomerMobileNo = adapter.getCustomerMobileNo(position).toString();
                CustomersData.selectedCustomerAlternateNo = adapter.getCustomerAlternateNo(position).toString();
                CustomersData.selectedCustomerType = adapter.getCustomerType(position).toString();
                CustomersData.selectedCustomerCreatedByUserId = adapter.getCustomerCreatedByUserId(position).toString();
                CustomersData.selectedCustomerCreatedByUserName = adapter.getCustomerCreatedByUserName(position).toString();

                et_customer_info_search.setText("");

                startCustomerDetailsActivity();

            }
        });


    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
