package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ChangePasswordScreen;

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

import com.lnsel.ambujaneotiasales.presenters.ChangePasswordPresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 6/9/2017.
 */
public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordActivityView {

    private ChangePasswordPresenter presenter;
    private ProgressDialog progress;

    SharedManagerUtil session;

    private EditText et_current_password, et_new_password, et_retype_new_password;

    private TextInputLayout til_current_password, til_new_password, til_retype_new_password;

    Button btn_change_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new ChangePasswordPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Change Password");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        btn_change_password = (Button) findViewById(R.id.activity_change_password_btn_change_password);

        til_current_password = (TextInputLayout) findViewById(R.id.activity_change_password_til_current_password);
        til_new_password = (TextInputLayout) findViewById(R.id.activity_change_password_til_new_password);
        til_retype_new_password = (TextInputLayout) findViewById(R.id.activity_change_password_til_retype_new_password);

        et_current_password = (EditText) findViewById(R.id.activity_change_password_et_current_password);
        et_new_password = (EditText) findViewById(R.id.activity_change_password_et_new_password);
        et_retype_new_password = (EditText) findViewById(R.id.activity_change_password_et_retype_new_password);

        et_current_password.addTextChangedListener(new MyTextWatcher(et_current_password));
        et_new_password.addTextChangedListener(new MyTextWatcher(et_new_password));
        et_retype_new_password.addTextChangedListener(new MyTextWatcher(et_retype_new_password));

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateCurrentPassword()) {
                    return;
                }
                if (!validateNewPassword()) {
                    return;
                }
                if (!validateRetypeNewPassword()) {
                    return;
                }

                submitChangePassword();
            }
        });

    }

    public void submitChangePassword(){
        String userId = session.getUserID();
        String userCurrentPassword = et_current_password.getText().toString().trim();
        String userNewPassword = et_new_password.getText().toString().trim();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);

            presenter.updatePasswordService(UrlUtil.UPDATE_PASSWORD_URL, userId, userCurrentPassword, userNewPassword);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        startMainActivity();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
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
                case R.id.activity_change_password_et_current_password:
                    validateCurrentPassword();
                    break;
                case R.id.activity_change_password_et_new_password:
                    validateNewPassword();
                    break;
                case R.id.activity_change_password_et_retype_new_password:
                    validateRetypeNewPassword();
                    break;
            }
        }
    }



    private boolean validateCurrentPassword() {
        if (et_current_password.getText().toString().trim().isEmpty()) {
            til_current_password.setError("Please enter current password");
            requestFocus(et_current_password);
            return false;
        } else {
            til_current_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNewPassword() {
        if (et_new_password.getText().toString().trim().isEmpty()) {
            til_new_password.setError("Please enter new password");
            requestFocus(et_new_password);
            return false;
        } else {
            til_new_password.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateRetypeNewPassword() {
        if (et_retype_new_password.getText().toString().trim().isEmpty()) {
            til_retype_new_password.setError("Please retype new password");
            requestFocus(et_retype_new_password);
            return false;
        }
        else {

            if (et_new_password.getText().toString().equals(et_retype_new_password.getText().toString())) {

                til_retype_new_password.setErrorEnabled(false);

            } else {
                til_retype_new_password.setError("Password does not match");
                return false;
            }
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
