package com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceSelectionScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Calendar;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter.PerformanceData;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter.PerformanceBaseAdapter;
import com.lnsel.ambujaneotiasales.presenters.PerformancePresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 6/2/2017.
 */
public class PerformanceSelectionActivity extends AppCompatActivity implements PerformanceSelectionActivityView{

    Toolbar toolbar;
    private PerformancePresenter presenter;
    private ProgressDialog progress;

    PerformanceBaseAdapter adapter;

    SharedManagerUtil session;

    ListView list;
    String[] yearList;

    public static String selected_year;

    Spinner spinner_select_year;
    Button btn_submit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_selection);

        presenter = new PerformancePresenter(this);
        // Session Manager
        session = new SharedManagerUtil(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Performance");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        yearList = getResources().getStringArray(R.array.select_year);
        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        String get_current_year = Integer.toString(current_year);
        int index = 0;
        for(int i=0; i<yearList.length; i++){
            if(get_current_year.equals(yearList[i])){
                index = i;
            }
        }

        list=(ListView) findViewById(R.id.listView);
        spinner_select_year = (Spinner) findViewById(R.id.activity_performance_spinner_select_year);
        btn_submit = (Button) findViewById(R.id.activity_performance_btn_submit);

        spinner_select_year.setSelection(index);

        submitYear();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitYear();
            }
        });

    }

    public void submitYear(){
        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.setCanceledOnTouchOutside(false);
            progress.show();

            selected_year = spinner_select_year.getSelectedItem().toString();
            //Toast.makeText(this, selected_year, Toast.LENGTH_LONG).show();

            presenter.getPerformanceListService(UrlUtil.GET_PERFORMANCE_LIST_URL+"?userId="+session.getUserID()+"&currentYear="+selected_year);
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void startGetPerformanceList() {
        progress.dismiss();
        toolbar.setTitle("Performance ("+selected_year+")");
        adapter=new PerformanceBaseAdapter(this, PerformanceData.performanceList);
        list.setAdapter(adapter);

    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
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
