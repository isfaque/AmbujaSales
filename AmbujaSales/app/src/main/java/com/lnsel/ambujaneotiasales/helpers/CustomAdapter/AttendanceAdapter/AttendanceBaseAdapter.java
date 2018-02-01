package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.AttendanceAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.R;
/**
 * Created by apps2 on 5/24/2017.
 */
public class AttendanceBaseAdapter extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;

    private List<AttendanceSetterGetter> attendanceList = null;
    private ArrayList<AttendanceSetterGetter> arraylist;

    public AttendanceBaseAdapter(Activity context, List<AttendanceSetterGetter> attendanceList) {

        this.context = context;

        this.attendanceList = attendanceList;
        this.arraylist = new ArrayList<AttendanceSetterGetter>();
        this.arraylist.addAll(attendanceList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_attendance, null,true);

        TextView tv_attendance_date = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_attendance_date);
        TextView tv_login_date = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_login_date);
        TextView tv_login_time = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_login_time);
        TextView tv_logout_date = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_logout_date);
        TextView tv_logout_time = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_logout_time);
        TextView tv_total_time = (TextView) rowView.findViewById(R.id.cardview_attendance_tv_total_time);

        tv_attendance_date.setText("Date: "+attendanceList.get(position).getLgnrLoginDate());
        tv_login_date.setText(attendanceList.get(position).getLgnrLoginDate());
        tv_login_time.setText(attendanceList.get(position).getLgnrLoginTime());
        tv_logout_date.setText(attendanceList.get(position).getLgnrLogoutDate());
        tv_logout_time.setText(attendanceList.get(position).getLgnrLogoutTime());
        tv_total_time.setText(attendanceList.get(position).getLgnrTotalTime());

        return rowView;

    }

    // Filter Class
    public void filter(String charText, View btn_clear) {
        charText = charText.toLowerCase(Locale.getDefault());
        attendanceList.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            attendanceList.addAll(arraylist);
            btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (AttendanceSetterGetter wp : arraylist)
            {
                if (wp.getLgnrLoginDate().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLgnrLoginTime().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLgnrLogoutDate().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLgnrLogoutTime().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    attendanceList.add(wp);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return attendanceList.size();
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
