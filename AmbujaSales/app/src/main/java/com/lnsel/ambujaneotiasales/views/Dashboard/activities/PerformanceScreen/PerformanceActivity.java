package com.lnsel.ambujaneotiasales.views.Dashboard.activities.PerformanceScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter.PerformanceData;
import com.lnsel.ambujaneotiasales.presenters.PerformancePresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/27/2017.
 */
public class PerformanceActivity extends AppCompatActivity implements PerformanceActivityView {

    private PerformancePresenter presenter;
    private ProgressDialog progress;
    SharedManagerUtil session;

    HorizontalBarChart chart;
    BarData data;

    Spinner spinner_select_year;
    Button btn_submit;

    public static String selected_year;

    Toolbar toolbar;
    String[] yearList;

    public static String performance_toolbar_title = "Performance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new PerformancePresenter(this);

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

        spinner_select_year = (Spinner) findViewById(R.id.activity_performance_spinner_select_year);
        btn_submit = (Button) findViewById(R.id.activity_performance_btn_submit);

        spinner_select_year.setSelection(index);

        chart = (HorizontalBarChart) findViewById(R.id.chart);

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

            presenter.getPerformanceService(UrlUtil.GET_PERFORMANCE_URL+"?userId="+session.getUserID()+"&currentYear="+selected_year);
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void startGetPerformance(){
        progress.dismiss();

        toolbar.setTitle("Performance ("+selected_year+")");

        data = new BarData(getXAxisValues(), getDataSet(PerformanceData.current_order_JAN,PerformanceData.current_order_FEB,PerformanceData.current_order_MAR,PerformanceData.current_order_APR,PerformanceData.current_order_MAY,PerformanceData.current_order_JUN,
                PerformanceData.current_order_JUL,PerformanceData.current_order_AUG,PerformanceData.current_order_SEP,PerformanceData.current_order_OCT,PerformanceData.current_order_NOV,PerformanceData.current_order_DEC,
                PerformanceData.current_visit_JAN,PerformanceData.current_visit_FEB,PerformanceData.current_visit_MAR,PerformanceData.current_visit_APR,PerformanceData.current_visit_MAY,PerformanceData.current_visit_JUN,
                PerformanceData.current_visit_JUL,PerformanceData.current_visit_AUG,PerformanceData.current_visit_SEP,PerformanceData.current_visit_OCT,PerformanceData.current_visit_NOV,PerformanceData.current_visit_DEC));
        chart.setData(data);
        chart.setDescription("Performance Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    public void errorInfo(String msg){
        progress.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<BarDataSet> getDataSet(float order_JAN, float order_FEB, float order_MAR, float order_APR, float order_MAY, float order_JUN,
                                             float order_JUL, float order_AUG, float order_SEP, float order_OCT, float order_NOV, float order_DEC,
                                             float visit_JAN, float visit_FEB, float visit_MAR, float visit_APR, float visit_MAY, float visit_JUN,
                                             float visit_JUL, float visit_AUG, float visit_SEP, float visit_OCT, float visit_NOV, float visit_DEC) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(visit_JAN, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(visit_FEB, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(visit_MAR, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(visit_APR, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(visit_MAY, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(visit_JUN, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(visit_JUL, 6); // Jul
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(visit_AUG, 7); // Aug
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry(visit_SEP, 8); // Sep
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry(visit_OCT, 9); // Oct
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry(visit_NOV, 10); // Nov
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry(visit_DEC, 11); // Dec
        valueSet1.add(v1e12);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(order_JAN, 0); // Jan
        valueSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(order_FEB, 1); // Feb
        valueSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(order_MAR, 2); // Mar
        valueSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(order_APR, 3); // Apr
        valueSet2.add(v2e4);
        BarEntry v2e5 = new BarEntry(order_MAY, 4); // May
        valueSet2.add(v2e5);
        BarEntry v2e6 = new BarEntry(order_JUN, 5); // Jun
        valueSet2.add(v2e6);
        BarEntry v2e7 = new BarEntry(order_JUL, 6); // Jul
        valueSet2.add(v2e7);
        BarEntry v2e8 = new BarEntry(order_AUG, 7); // Aug
        valueSet2.add(v2e8);
        BarEntry v2e9 = new BarEntry(order_SEP, 8); // Sep
        valueSet2.add(v2e9);
        BarEntry v2e10 = new BarEntry(order_OCT, 9); // Oct
        valueSet2.add(v2e10);
        BarEntry v2e11 = new BarEntry(order_NOV, 10); // Nov
        valueSet2.add(v2e11);
        BarEntry v2e12 = new BarEntry(order_DEC, 11); // Dec
        valueSet2.add(v2e12);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "No. of Visits");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "No. of Orders");
        barDataSet2.setColor(Color.rgb(255, 0, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        xAxis.add("JUL");
        xAxis.add("AUG");
        xAxis.add("SEP");
        xAxis.add("OCT");
        xAxis.add("NOV");
        xAxis.add("DEC");
        return xAxis;
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

    @Override
    public void onBackPressed() {
        startMainActivity();
    }
}
