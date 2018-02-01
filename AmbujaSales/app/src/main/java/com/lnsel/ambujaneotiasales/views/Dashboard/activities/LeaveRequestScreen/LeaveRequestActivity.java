package com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Locale;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter.LeaveRequestBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter.LeaveRequestData;
import com.lnsel.ambujaneotiasales.presenters.LeaveRequestPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 5/3/2017.
 */
public class LeaveRequestActivity extends AppCompatActivity implements LeaveRequestActivityView {

    private LeaveRequestPresenter presenter;
    private ProgressDialog progress;

    LeaveRequestBaseAdapter adapter;

    ListView list;
    EditText et_leave_request_search;
    Button btn_clear;

    SharedManagerUtil session;

    FloatingActionButton fab_add_leave_request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        presenter = new LeaveRequestPresenter(this);
        // Session Manager
        session = new SharedManagerUtil(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Request");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        fab_add_leave_request = (FloatingActionButton) findViewById(R.id.activity_leave_request_btn_add_request);
        list=(ListView) findViewById(R.id.list_view);
        et_leave_request_search = (EditText) findViewById(R.id.activity_leave_request_et_request_search);
        btn_clear = (Button) findViewById(R.id.activity_leave_request_btn_clear);

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getLeaveRequestService(UrlUtil.GET_LEAVE_REQUEST_URL+"?userId="+session.getUserID()+"&userParentPath="+session.getUserParentPath());

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_leave_request_search.setText("");
            }
        });

        fab_add_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaveRequestAddActivity();
            }
        });


    }

    public void startGetLeaveRequest() {
        progress.dismiss();

        adapter=new LeaveRequestBaseAdapter(this, LeaveRequestData.leaveRequestList);
        list.setAdapter(adapter);

        et_leave_request_search.addTextChangedListener(new TextWatcher() {

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
                String text = et_leave_request_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });

    }


    public void deleteLeaveRequest(String lreqId){

        String userId = session.getUserID();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.deleteLeaveRequestService(UrlUtil.DELETE_LEAVE_REQUEST_URL, userId, lreqId);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void errorLeaveRequestDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void successLeaveRequestDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        new ActivityUtil(this).startLeaveRequestActivity();
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    public void startLeaveRequestEditActivity() {
        new ActivityUtil(this).startLeaveRequestEditActivity();
    }

    public void startLeaveRequestAddActivity() {
        new ActivityUtil(this).startLeaveRequestAddActivity();
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
