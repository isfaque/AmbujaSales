package com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestAddScreen;

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
import android.widget.EditText;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.presenters.LeaveRequestAddPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/16/2017.
 */
public class LeaveRequestAddActivity extends AppCompatActivity implements LeaveRequestAddActivityView {

    private LeaveRequestAddPresenter presenter;
    SharedManagerUtil session;

    private ProgressDialog progress;

    EditText et_request_subject, et_request_description;
    TextInputLayout til_request_subject, til_request_description;
    Button btn_cancel, btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request_add);

        // Session Manager
        session = new SharedManagerUtil(this);


        presenter = new LeaveRequestAddPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Send Leave Request");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaveRequestActivity();
            }
        });


        et_request_subject = (EditText) findViewById(R.id.activity_leave_request_add_et_subject);
        et_request_description = (EditText) findViewById(R.id.activity_leave_request_add_et_description);

        btn_cancel = (Button) findViewById(R.id.activity_leave_request_add_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_leave_request_add_btn_submit);

        til_request_subject = (TextInputLayout) findViewById(R.id.activity_leave_request_add_til_subject);
        til_request_description = (TextInputLayout) findViewById(R.id.activity_leave_request_add_til_description);

        et_request_subject.addTextChangedListener(new MyTextWatcher(et_request_subject));
        et_request_description.addTextChangedListener(new MyTextWatcher(et_request_description));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaveRequestActivity();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateRequestSubject()) {
                    return;
                }
                if (!validateRequestDescription()) {
                    return;
                }

                submitLeaveRequest();

            }
        });

    }

    public void submitLeaveRequest(){
        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);

            final String userId = session.getUserID();
            final String userParentPath = session.getUserParentPath();

            String lreqSubject = et_request_subject.getText().toString();
            String lreqDescription = et_request_description.getText().toString();

            presenter.addLeaveRequest(UrlUtil.ADD_LEAVE_REQUEST_URL, userId, userParentPath, lreqSubject, lreqDescription);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startLeaveRequestActivity();
    }

    public void startLeaveRequestActivity() {
        new ActivityUtil(this).startLeaveRequestActivity();
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
        startLeaveRequestActivity();
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
                case R.id.activity_leave_request_add_et_subject:
                    validateRequestSubject();
                    break;
                case R.id.activity_leave_request_add_et_description:
                    validateRequestDescription();
                    break;
            }
        }
    }


    private boolean validateRequestSubject() {
        if (et_request_subject.getText().toString().trim().isEmpty()) {
            til_request_subject.setError("Subject can not be blank");
            requestFocus(et_request_subject);
            return false;
        } else {
            til_request_subject.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateRequestDescription() {
        if (et_request_description.getText().toString().trim().isEmpty()) {
            til_request_description.setError("Request Details can not be blank");
            requestFocus(et_request_description);
            return false;
        } else {
            til_request_description.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
