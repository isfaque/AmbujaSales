package com.lnsel.ambujaneotiasales.views.LoginScreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.cert.Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lnsel.ambujaneotiasales.helpers.Service.GPSService;
import com.lnsel.ambujaneotiasales.notification.NotificationConfig;
import com.lnsel.ambujaneotiasales.presenters.LoginPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 4/20/2017.
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    Button btn_login;
    EditText et_username, et_password;
    TextInputLayout til_username, til_password;
    CheckBox cb_remeber;
    SharedManagerUtil session;

    private LoginPresenter presenter;

    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private static final int REQUEST_ACCESS_FINE_LOCATION_LOG_IN = 112;
    boolean hasPermissionLocation;
    boolean gpsEnabled;

    private ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new LoginPresenter(this);

        et_username = (EditText) findViewById(R.id.activity_login_et_username);
        et_password = (EditText) findViewById(R.id.activity_login_et_password);
        cb_remeber = (CheckBox) findViewById(R.id.activity_login_cb_remember);

        til_username = (TextInputLayout) findViewById(R.id.activity_login_til_username);
        til_password = (TextInputLayout) findViewById(R.id.activity_login_til_password);

        et_username.addTextChangedListener(new MyTextWatcher(et_username));
        et_password.addTextChangedListener(new MyTextWatcher(et_password));

        btn_login = (Button) findViewById(R.id.activity_login_btn_login);

        if(session.isRemember()){
            Toast.makeText(LoginActivity.this, "Remember True", Toast.LENGTH_SHORT).show();
            et_username.setText(session.getRemUserName().toString());
            et_password.setText(session.getRemUserPassword().toString());
        }

        //GPS Permission for Marshmallow 6.0
        hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }


        //GPS Permission for before Marshmallow version

        final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        gpsEnabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            buildAlertMessageNoGps();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionLocation) {
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION_LOG_IN);
                }else {
                    gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(gpsEnabled) {
                        if (!validateUsername()) {
                            return;
                        }
                        if (!validatePassword()) {
                            return;
                        }
                        if(isNetworkAvailable()){
                            loginService();
                        }else{
                            Toast.makeText(getApplicationContext(),"Network Not Available", Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        buildAlertMessageNoGps();
                    }
                }
            }
        });
    }

    public void loginService(){

        GPSService mGPSService = new GPSService(this);
        mGPSService.getLocation();

        String userName = et_username.getText().toString().trim();
        String userPassword = et_password.getText().toString().trim();

        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
        String current_time = stf.format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = sdf.format(new Date());

        double latitude = mGPSService.getLatitude();
        double longitude = mGPSService.getLongitude();

        String loginLat = Double.toString(latitude);
        String loginLong = Double.toString(longitude);

        progress = new ProgressDialog(this);
        progress.setMessage("loading...");
        //progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        if(isNetworkAvailable()){
            if(loginLat.equals("0.0")){
                progress.dismiss();
                Toast.makeText(this, "GPS not Responding, check your GPS", Toast.LENGTH_LONG).show();
            }else {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(NotificationConfig.SHARED_PREF, 0);
                String regId = pref.getString("regId", "");
                presenter.getLoginService(UrlUtil.LOGIN_URL+"?userName="+userName+"&userPassword="+userPassword+"&loginDate="+current_date+"&loginTime="+current_time+"&loginLat="+loginLat+"&loginLong="+loginLong);
            }


        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
            progress.dismiss();
        }

    }

    @Override
    public void startMainActivity(){
        progress.dismiss();
        new ActivityUtil(this).startMainActivity();
    }

    public void errorInfo(String msg){
        progress.dismiss();
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void updateSession(
            String userId,
            String userLoginRecordId,
            String userName,
            String userPassword,
            String userFirstName,
            String userLastName,
            String userEmail,
            String userContactNo,
            String userDesignation,
            String userParentId,
            String userParentPath,
            String userPushToken
    ){
        session.createLoginSession(
                userId,
                userLoginRecordId,
                userName,
                userFirstName,
                userLastName,
                userEmail,
                userContactNo,
                userDesignation,
                userParentId,
                userParentPath,
                userPushToken
        );

        if(cb_remeber.isChecked()){
            Toast.makeText(LoginActivity.this, "Login Save", Toast.LENGTH_SHORT).show();
            session.createRemember(userName, userPassword);
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(LoginActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(LoginActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

            case REQUEST_ACCESS_FINE_LOCATION_LOG_IN: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(LoginActivity.this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(LoginActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
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
                case R.id.activity_login_et_username:
                    validateUsername();
                    break;
                case R.id.activity_login_et_password:
                    validatePassword();
                    break;
            }
        }
    }



    private boolean validateUsername() {
        if (et_username.getText().toString().trim().isEmpty()) {
            til_username.setError("Please enter username");
            requestFocus(et_username);
            return false;
        } else {
            til_username.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            til_password.setError("Please enter password");
            requestFocus(et_password);
            return false;
        } else {
            til_password.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
