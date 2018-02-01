package com.lnsel.ambujaneotiasales.views.Dashboard.activities.AttendanceScreen;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter.AttendanceBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter.AttendanceData;
import com.lnsel.ambujaneotiasales.presenters.AttendancePresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/3/2017.
 */
public class AttendanceActivity extends AppCompatActivity implements AttendanceActivityView {

    private AttendancePresenter presenter;
    private ProgressDialog progress;

    AttendanceBaseAdapter adapter;

    SharedManagerUtil session;

    ExpandableRelativeLayout expandableLayout2;

    private EditText et_from_date, et_to_date, et_attendance_search;
    private TextInputLayout til_from_date, til_to_date;
    Button btn_submit, btn_clear;
    ListView list;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        presenter = new AttendancePresenter(this);
        // Session Manager
        session = new SharedManagerUtil(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance");

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        btn_submit = (Button) findViewById(R.id.activity_attendance_btn_submit);
        et_from_date = (EditText) findViewById(R.id.activity_attendance_et_attendance_from_date);
        et_to_date = (EditText) findViewById(R.id.activity_attendance_et_attendance_to_date);

        list=(ListView) findViewById(R.id.list_view);

        et_attendance_search = (EditText) findViewById(R.id.activity_attendance_et_attendance_search);
        btn_clear = (Button) findViewById(R.id.activity_attendance_btn_clear);

        til_from_date = (TextInputLayout) findViewById(R.id.activity_attendance_til_attendance_from_date);
        til_to_date = (TextInputLayout) findViewById(R.id.activity_attendance_til_attendance_to_date);

        et_from_date.addTextChangedListener(new MyTextWatcher(et_from_date));
        et_to_date.addTextChangedListener(new MyTextWatcher(et_to_date));

        et_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_from_date.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                //mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        et_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_to_date.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                //mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFromDate()) {
                    return;
                }
                if (!validateToDate()) {
                    return;
                }

                submitLoginRecord();
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_attendance_search.setText("");
            }
        });

    }

    public void startGetAttendance() {
        progress.dismiss();

        adapter=new AttendanceBaseAdapter(this, AttendanceData.attendanceList);
        list.setAdapter(adapter);

        et_attendance_search.addTextChangedListener(new TextWatcher() {

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
                String text = et_attendance_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });

    }

    public void submitLoginRecord(){
        String fromDate = et_from_date.getText().toString();
        String toDate = et_to_date.getText().toString();
        fromDate.compareTo(toDate);
        int compareReturn = fromDate.compareTo(toDate);
        if(compareReturn <= 0){
            if(isNetworkAvailable()){
                progress = new ProgressDialog(this);
                progress.setMessage("loading...");
                progress.show();
                progress.setCanceledOnTouchOutside(false);
                presenter.getAttendanceService(UrlUtil.GET_ATTENDANCE_URL+"?userId="+session.getUserID()+"&fromDate="+fromDate+"&toDate="+toDate);

            }else{
                Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Date Range is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    public void expandableButton2(View view) {
        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
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
                case R.id.activity_attendance_et_attendance_from_date:
                    validateFromDate();
                    break;
                case R.id.activity_attendance_et_attendance_to_date:
                    validateToDate();
                    break;
            }
        }
    }

    private boolean validateFromDate() {
        if (et_from_date.getText().toString().trim().isEmpty()) {
            til_from_date.setError("from date is blank");
            requestFocus(et_from_date);
            return false;
        } else {
            til_from_date.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateToDate() {
        if (et_to_date.getText().toString().trim().isEmpty()) {
            til_to_date.setError("to date is blank");
            requestFocus(et_to_date);
            return false;
        } else {
            til_to_date.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
