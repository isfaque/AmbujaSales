package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.LeaveRequestAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen.LeaveRequestActivity;


/**
 * Created by apps2 on 5/16/2017.
 */
public class LeaveRequestBaseAdapter extends BaseAdapter{
    Context context;
    private static LayoutInflater inflater=null;

    private List<LeaveRequestSetterGetter> leaveRequestList = null;
    private ArrayList<LeaveRequestSetterGetter> arraylist;

    public LeaveRequestBaseAdapter(Activity context, List<LeaveRequestSetterGetter> leaveRequestList) {

        this.context = context;

        this.leaveRequestList = leaveRequestList;
        this.arraylist = new ArrayList<LeaveRequestSetterGetter>();
        this.arraylist.addAll(leaveRequestList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_leave_request, null,true);

        TextView tv_leave_request_subject = (TextView) rowView.findViewById(R.id.cardview_leave_request_tv_leave_request_subject);
        TextView tv_leave_request_description = (TextView) rowView.findViewById(R.id.cardview_leave_request_tv_leave_request_description);
        TextView tv_leave_request_description_large = (TextView) rowView.findViewById(R.id.cardview_leave_request_tv_leave_request_description_large);
        TextView tv_leave_request_status = (TextView) rowView.findViewById(R.id.cardview_leave_request_tv_leave_request_status);
        TextView tv_leave_request_message = (TextView) rowView.findViewById(R.id.cardview_leave_request_tv_leave_request_message);
        LinearLayout ll_leave_request_message_layout = (LinearLayout) rowView.findViewById(R.id.cardview_leave_request_ll_status_message);

        ImageButton ib_edit_leave_request = (ImageButton) rowView.findViewById(R.id.cardview_leave_request_ib_edit_leave_request);
        ImageButton ib_delete_leave_request = (ImageButton) rowView.findViewById(R.id.cardview_leave_request_ib_delete_leave_request);

        tv_leave_request_subject.setText(leaveRequestList.get(position).getLreqSubject());
        tv_leave_request_description.setText(leaveRequestList.get(position).getLreqDescription());
        tv_leave_request_description_large.setText(leaveRequestList.get(position).getLreqDescription());
        tv_leave_request_status.setText(leaveRequestList.get(position).getLreqStatus().toUpperCase());
        tv_leave_request_message.setText(leaveRequestList.get(position).getLreqStatusMessage());

        if(leaveRequestList.get(position).getLreqStatus().equals("pending")){
            ll_leave_request_message_layout.setVisibility(View.GONE);
        }else{
            ll_leave_request_message_layout.setVisibility(View.VISIBLE);
        }

        int description_length = leaveRequestList.get(position).getLreqDescription().toString().length();

        if(leaveRequestList.get(position).getLreqStatus().equals("accepted")){
            tv_leave_request_status.setTextColor(Color.rgb(50,205,50));
        }else if(leaveRequestList.get(position).getLreqStatus().equals("rejected")){
            tv_leave_request_status.setTextColor(Color.RED);
        }

        if(description_length > 25){
            tv_leave_request_description.setVisibility(View.GONE);
            tv_leave_request_description_large.setVisibility(View.VISIBLE);
        }else{
            tv_leave_request_description.setVisibility(View.VISIBLE);
            tv_leave_request_description_large.setVisibility(View.GONE);
        }

        ib_edit_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leaveRequestList.get(position).getLreqStatus().equals("pending")){
                    LeaveRequestData.current_leave_request_id = leaveRequestList.get(position).getLreqId();
                    LeaveRequestData.current_leave_request_subject = leaveRequestList.get(position).getLreqSubject();
                    LeaveRequestData.current_leave_request_description = leaveRequestList.get(position).getLreqDescription();
                    LeaveRequestData.current_leave_request_status = leaveRequestList.get(position).getLreqStatus();

                    new ActivityUtil((Activity) context).startLeaveRequestEditActivity();
                }else{
                    Toast.makeText(((Activity) context), "Status is not pending, you can not edit", Toast.LENGTH_LONG).show();
                }

            }
        });

        ib_delete_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leaveRequestList.get(position).getLreqStatus().equals("pending")){
                    String lreqId = leaveRequestList.get(position).getLreqId();
                    deleteDialog(lreqId);
                }else{
                    Toast.makeText(((Activity) context), "Status is not pending, you can not delete", Toast.LENGTH_LONG).show();
                }

            }
        });


        return rowView;

    }

    // Filter Class
    public void filter(String charText, View btn_clear) {
        charText = charText.toLowerCase(Locale.getDefault());
        leaveRequestList.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            leaveRequestList.addAll(arraylist);
            btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (LeaveRequestSetterGetter wp : arraylist)
            {
                if (wp.getLreqSubject().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLreqDescription().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLreqStatus().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getLreqStatusMessage().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    leaveRequestList.add(wp);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return leaveRequestList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private void deleteDialog(final String lreqId){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder((Activity) context);
        builder.setMessage("Are you sure, you want to delete?")
                .setTitle("Delete Expense")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        ((LeaveRequestActivity) ((Activity) context))
                                .deleteLeaveRequest(lreqId);

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
