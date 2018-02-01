package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.PerformanceAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 6/2/2017.
 */
public class PerformanceBaseAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater=null;

    private List<PerformanceSetterGetter> performanceList = null;
    private ArrayList<PerformanceSetterGetter> arraylist;

    BarData data;

    public PerformanceBaseAdapter(Activity context, List<PerformanceSetterGetter> performanceList) {
        this.context = context;

        this.performanceList = performanceList;
        this.arraylist = new ArrayList<PerformanceSetterGetter>();
        this.arraylist.addAll(performanceList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_performance, null,true);

        HorizontalBarChart chart = (HorizontalBarChart) rowView.findViewById(R.id.chart);
        TextView tv_chart_title = (TextView) rowView.findViewById(R.id.cardview_performance_tv_title);

        String perId = performanceList.get(position).getPerId();
        Float perJAN = Float.valueOf(performanceList.get(position).getPerJAN());
        Float perFEB = Float.valueOf(performanceList.get(position).getPerFEB());
        Float perMAR = Float.valueOf(performanceList.get(position).getPerMAR());
        Float perAPR = Float.valueOf(performanceList.get(position).getPerAPR());
        Float perMAY = Float.valueOf(performanceList.get(position).getPerMAY());
        Float perJUN = Float.valueOf(performanceList.get(position).getPerJUN());
        Float perJUL = Float.valueOf(performanceList.get(position).getPerJUL());
        Float perAUG = Float.valueOf(performanceList.get(position).getPerAUG());
        Float perSEP = Float.valueOf(performanceList.get(position).getPerSEP());
        Float perOCT = Float.valueOf(performanceList.get(position).getPerOCT());
        Float perNOV = Float.valueOf(performanceList.get(position).getPerNOV());
        Float perDEC = Float.valueOf(performanceList.get(position).getPerDEC());

        data = new BarData(getXAxisValues(), getDataSet(perId, perJAN,perFEB,perMAR,perAPR,perMAY,perJUN,
                perJUL,perAUG,perSEP,perOCT,perNOV,perDEC));
        chart.setData(data);
        if(perId.equals("1")){
            chart.setDescription("");
            tv_chart_title.setText("Visit Performance Chart");
        }else if(perId.equals("2")){
            chart.setDescription("");
            tv_chart_title.setText("Order Performance Chart");
        }else{
            chart.setDescription("");
            tv_chart_title.setText("Order Amount Performance Chart");
        }
        chart.animateXY(2000, 2000);
        chart.invalidate();



        return rowView;

    }


    private ArrayList<BarDataSet> getDataSet(String perId, float perJAN, float perFEB, float perMAR, float perAPR, float perMAY, float perJUN,
                                             float perJUL, float perAUG, float perSEP, float perOCT, float perNOV, float perDEC) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(perJAN, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(perFEB, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(perMAR, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(perAPR, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(perMAY, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(perJUN, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(perJUL, 6); // Jul
        valueSet1.add(v1e7);
        BarEntry v1e8 = new BarEntry(perAUG, 7); // Aug
        valueSet1.add(v1e8);
        BarEntry v1e9 = new BarEntry(perSEP, 8); // Sep
        valueSet1.add(v1e9);
        BarEntry v1e10 = new BarEntry(perOCT, 9); // Oct
        valueSet1.add(v1e10);
        BarEntry v1e11 = new BarEntry(perNOV, 10); // Nov
        valueSet1.add(v1e11);
        BarEntry v1e12 = new BarEntry(perDEC, 11); // Dec
        valueSet1.add(v1e12);

        if(perId.equals("1")){
            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "No. of Visits");
            barDataSet1.setColor(Color.rgb(0, 155, 0));
            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
        }else if(perId.equals("2")){
            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "No. of Orders");
            barDataSet1.setColor(Color.rgb(255, 0, 0));
            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
        }else{
            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Order Amount(INR)");
            barDataSet1.setColor(Color.rgb(51, 153, 255));
            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
        }


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



    @Override
    public int getCount() {
        return performanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
