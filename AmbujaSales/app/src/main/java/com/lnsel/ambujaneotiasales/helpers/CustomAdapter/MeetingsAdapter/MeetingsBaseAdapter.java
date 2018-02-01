package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen.MeetingsActivity;

/**
 * Created by apps2 on 5/4/2017.
 */
public class MeetingsBaseAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater=null;

    private List<MeetingsSetterGetter> meetingsList = null;
    private ArrayList<MeetingsSetterGetter> arraylist;

    public MeetingsBaseAdapter(Activity context, List<MeetingsSetterGetter> meetingsList) {

        this.context = context;

        this.meetingsList = meetingsList;
        this.arraylist = new ArrayList<MeetingsSetterGetter>();
        this.arraylist.addAll(meetingsList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_meetings, null,true);

        TextView tv_meeting_name = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_meeting_name);
        TextView tv_meeting_type = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_meeting_type);
        TextView tv_customer_name = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_customer_name);
        TextView tv_customer_company_name = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_customer_company_name);
        TextView tv_customer_address = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_customer_address);
        TextView tv_meeting_date = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_meeting_date);
        TextView tv_meeting_time = (TextView) rowView.findViewById(R.id.cardview_meetings_tv_meeting_time);
        RelativeLayout rl_main_layout = (RelativeLayout) rowView.findViewById(R.id.cardview_meetings_rl_mainlayout);

        ImageButton ib_meeting_completed = (ImageButton) rowView.findViewById(R.id.cardview_meetings_ib_completed);
        ImageButton cardview_meetings_ib_add_reminder=(ImageButton)rowView.findViewById(R.id.cardview_meetings_ib_add_reminder);

        CheckBox cb_visited = (CheckBox) rowView.findViewById(R.id.cardview_meetings_cb_visited);
        CheckBox cb_picture = (CheckBox) rowView.findViewById(R.id.cardview_meetings_cb_picture);
        CheckBox cb_signature = (CheckBox) rowView.findViewById(R.id.cardview_meetings_cb_signature);

        if(meetingsList.get(position).getMtnVisited().equals("yes")){
            cb_visited.setChecked(true);
            //cb_visited.setEnabled(false);
        }else{
            cb_visited.setChecked(false);
            //cb_visited.setEnabled(false);
        }

        if(meetingsList.get(position).getMtnPicture().equals("yes")){
            cb_picture.setChecked(true);
            //cb_picture.setEnabled(false);
        }else{
            cb_picture.setChecked(false);
            //cb_picture.setEnabled(false);
        }

        if(meetingsList.get(position).getMtnSignature().equals("yes")){
            cb_signature.setChecked(true);
            //cb_signature.setEnabled(false);
        }else{
            cb_signature.setChecked(false);
            //cb_signature.setEnabled(false);
        }

        tv_meeting_name.setText(meetingsList.get(position).getMtnName());
        tv_meeting_type.setText(meetingsList.get(position).getMtnMeetingType());
        tv_customer_name.setText(meetingsList.get(position).getMtnCustomerName());
        tv_customer_company_name.setText(meetingsList.get(position).getMtnCustomerCompanyName());
        tv_customer_address.setText(meetingsList.get(position).getMtnCustomerAddress());
        tv_meeting_date.setText(meetingsList.get(position).getMtnDate());
        tv_meeting_time.setText(meetingsList.get(position).getMtnTime());

        rl_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingsData.current_meeting_id = meetingsList.get(position).getMtnId();
                new ActivityUtil((Activity) context).startMeetingDetailsActivity();
            }
        });

        ib_meeting_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mtnId = meetingsList.get(position).getMtnId();
                meetingCompletedDialog(mtnId);
            }
        });

        cardview_meetings_ib_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof MeetingsActivity){
                    ((MeetingsActivity)context).addCalendarEvent(meetingsList.get(position));
                }
            }
        });

        return rowView;

    }

    // Filter Class
    public void filter(String charText, View btn_clear) {
        charText = charText.toLowerCase(Locale.getDefault());
        meetingsList.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            meetingsList.addAll(arraylist);
            btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (MeetingsSetterGetter wp : arraylist)
            {
                if (wp.getMtnName().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnCustomerName().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnCustomerCompanyName().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnCustomerMobileNo().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnCustomerAddress().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnDate().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getMtnTime().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    meetingsList.add(wp);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return meetingsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void meetingCompletedDialog(final String mtnId){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder((Activity) context);
        builder.setMessage("Are you sure, you want to complete this meeting?")
                .setTitle("Meeting Completed")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        ((MeetingsActivity) ((Activity) context))
                                .completeMeeting(mtnId);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


}
