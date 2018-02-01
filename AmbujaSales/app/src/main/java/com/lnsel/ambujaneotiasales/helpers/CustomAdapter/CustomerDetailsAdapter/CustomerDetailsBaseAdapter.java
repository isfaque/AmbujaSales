package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.CustomerDetailsAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 5/13/2017.
 */
public class CustomerDetailsBaseAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater=null;

    private List<CustomerDetailsSetterGetter> customerDetailsList = null;
    private ArrayList<CustomerDetailsSetterGetter> arraylist;

    public CustomerDetailsBaseAdapter(Activity context, List<CustomerDetailsSetterGetter> customerDetailsList) {

        this.context = context;

        this.customerDetailsList = customerDetailsList;
        this.arraylist = new ArrayList<CustomerDetailsSetterGetter>();
        this.arraylist.addAll(customerDetailsList);

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.listview_customerinfo_detail, null,true);

        TextView tv_meeting_name = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_meeting_name);
        TextView tv_meeting_assignto = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_assign_to);
        TextView tv_meeting_date = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_date);
        TextView tv_meeting_time = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_time);
        TextView tv_meeting_visited = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_visited);
        TextView tv_meeting_completed = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_completed);
        TextView tv_meeting_remarks = (TextView) rowView.findViewById(R.id.listview_customerinfo_detail_tv_remarks);

        tv_meeting_name.setText(customerDetailsList.get(position).getMtnName());
        tv_meeting_assignto.setText(customerDetailsList.get(position).getMtnUserName());
        tv_meeting_date.setText(customerDetailsList.get(position).getMtnDate());
        tv_meeting_time.setText(customerDetailsList.get(position).getMtnTime());
        tv_meeting_visited.setText(customerDetailsList.get(position).getMtnVisited());
        tv_meeting_completed.setText(customerDetailsList.get(position).getMtnCompleted());
        tv_meeting_remarks.setText(customerDetailsList.get(position).getMtnRemarksMessage());


        return rowView;

    }

    @Override
    public int getCount() {
        return customerDetailsList.size();
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
