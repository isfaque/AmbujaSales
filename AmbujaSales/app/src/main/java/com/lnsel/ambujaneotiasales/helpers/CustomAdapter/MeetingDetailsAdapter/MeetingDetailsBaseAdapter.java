package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingDetailsAdapter;

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
 * Created by apps2 on 6/5/2017.
 */
public class MeetingDetailsBaseAdapter extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater=null;

    private List<MeetingDetailsSetterGetter> meetingDetailsList = null;
    private ArrayList<MeetingDetailsSetterGetter> arraylist;

    public MeetingDetailsBaseAdapter(Activity context, List<MeetingDetailsSetterGetter> meetingDetailsList) {

        this.context = context;

        this.meetingDetailsList = meetingDetailsList;
        this.arraylist = new ArrayList<MeetingDetailsSetterGetter>();
        this.arraylist.addAll(meetingDetailsList);

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_meeting_details_history, null,true);

        TextView tv_meeting_name = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_meeting_name);
        TextView tv_meeting_assignto = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_assign_to);
        TextView tv_meeting_date = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_date);
        TextView tv_meeting_time = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_time);
        TextView tv_meeting_visited = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_visited);
        TextView tv_meeting_completed = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_completed);
        TextView tv_meeting_remarks = (TextView) rowView.findViewById(R.id.cardview_meeting_details_history_tv_remarks);

        tv_meeting_name.setText(meetingDetailsList.get(position).getMtnName());
        tv_meeting_assignto.setText(meetingDetailsList.get(position).getMtnUserName());
        tv_meeting_date.setText(meetingDetailsList.get(position).getMtnDate());
        tv_meeting_time.setText(meetingDetailsList.get(position).getMtnTime());
        tv_meeting_visited.setText(meetingDetailsList.get(position).getMtnVisited());
        tv_meeting_completed.setText(meetingDetailsList.get(position).getMtnCompleted());
        tv_meeting_remarks.setText(meetingDetailsList.get(position).getMtnRemarksMessage());


        return rowView;

    }


    @Override
    public int getCount() {
        return meetingDetailsList.size();
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
