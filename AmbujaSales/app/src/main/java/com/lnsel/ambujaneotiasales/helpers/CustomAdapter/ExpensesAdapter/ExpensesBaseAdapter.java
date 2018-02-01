package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.helpers.ImageZoom.TouchImageView;
import com.lnsel.ambujaneotiasales.helpers.VolleyLibrary.AppController;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen.ExpensesActivity;
import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 5/10/2017.
 */
public class ExpensesBaseAdapter extends BaseAdapter {

    TouchImageView tiv_image_view;

    Context context;
    private static LayoutInflater inflater=null;

    private List<ExpensesSetterGetter> expensesList = null;
    private ArrayList<ExpensesSetterGetter> arraylist;

    public ExpensesBaseAdapter(Activity context, List<ExpensesSetterGetter> expensesList) {

        this.context = context;

        this.expensesList = expensesList;
        this.arraylist = new ArrayList<ExpensesSetterGetter>();
        this.arraylist.addAll(expensesList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_expenses, null,true);

        TextView tv_expense_title = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_expense_title);
        TextView tv_expense_amount = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_expense_amount);
        TextView tv_expense_meeting_name = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_meeting_name);
        TextView tv_expense_customer_name = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_customer_name);
        TextView tv_expense_description = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_expense_description);
        TextView tv_expense_payment_status = (TextView) rowView.findViewById(R.id.cardview_expenses_tv_payment_status);
        ImageButton ib_edit_expense = (ImageButton) rowView.findViewById(R.id.cardview_expenses_ib_edit_expense);
        ImageButton ib_delete_expense = (ImageButton) rowView.findViewById(R.id.cardview_expenses_ib_delete_expense);
        ImageButton ib_complete_expense = (ImageButton) rowView.findViewById(R.id.cardview_expenses_ib_complete_expense);
        final ImageView iv_expense_image = (ImageView) rowView.findViewById(R.id.cardview_expenses_iv_expense_image);

        LinearLayout ll_meeting_name = (LinearLayout) rowView.findViewById(R.id.cardview_expenses_ll_meeting_name);
        LinearLayout ll_customer_name = (LinearLayout) rowView.findViewById(R.id.cardview_expenses_ll_customer_name);

        tv_expense_title.setText(expensesList.get(position).getExpTitle());
        tv_expense_amount.setText(expensesList.get(position).getExpAmount());
        tv_expense_meeting_name.setText(expensesList.get(position).getExpMeetingName());
        tv_expense_customer_name.setText(expensesList.get(position).getExpMeetingCustomerName());
        tv_expense_description.setText(expensesList.get(position).getExpDescription());
        tv_expense_payment_status.setText(expensesList.get(position).getExpPaymentStatus().toUpperCase());

        if(expensesList.get(position).getExpIsMeetingAssociated().equals("no")){
            ll_meeting_name.setVisibility(View.GONE);
            ll_customer_name.setVisibility(View.GONE);
        }

        if(expensesList.get(position).getExpPaymentStatus().equals("paid")){
            tv_expense_payment_status.setTextColor(Color.rgb(17,161,77));
        }else if(expensesList.get(position).getExpPaymentStatus().equals("unpaid")){
            tv_expense_payment_status.setTextColor(Color.RED);
        }

        if(expensesList.get(position).getExpImageStatus().equals("true")){

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(expensesList.get(position).getExpImage(), new ImageLoader.ImageListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Image Load Error: ",error.getMessage());
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        iv_expense_image.setImageBitmap(response.getBitmap());
                    }
                }
            });

        }

        iv_expense_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog((Activity) context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.dialog_image_zoom_view);

                tiv_image_view = (TouchImageView) dialog.findViewById(R.id.dialog_image_zoom_view_tiv_image_view);
                Button btn_close = (Button) dialog.findViewById(R.id.dialog_image_zoom_view_btn_close);

                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                imageLoader.get(expensesList.get(position).getExpImage(), new ImageLoader.ImageListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Image Load Error: ",error.getMessage());
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                        if (response.getBitmap() != null) {
                            tiv_image_view.setImageBitmap(response.getBitmap());
                        }
                    }
                });

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        ib_edit_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(expensesList.get(position).getExpPaymentStatus().equals("pending")){

                    ExpensesData.current_expense_id = expensesList.get(position).getExpId();
                    ExpensesData.current_expense_title = expensesList.get(position).getExpTitle();
                    ExpensesData.current_expense_amount = expensesList.get(position).getExpAmount();
                    ExpensesData.current_expense_description = expensesList.get(position).getExpDescription();
                    ExpensesData.current_expense_image = expensesList.get(position).getExpImage();
                    ExpensesData.current_expense_image_status = expensesList.get(position).getExpImageStatus();

                    new ActivityUtil((Activity) context).startExpenseEditActivity();
                }else{
                    Toast.makeText(((Activity) context), "Payment Status is not pending, you can not edit", Toast.LENGTH_LONG).show();
                }
            }
        });

        ib_delete_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expensesList.get(position).getExpPaymentStatus().equals("pending")){
                    String expId = expensesList.get(position).getExpId();
                    deleteDialog(expId);
                }else{
                    Toast.makeText(((Activity) context), "Payment Status is not pending, you can not delete", Toast.LENGTH_LONG).show();
                }
            }
        });

        ib_complete_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expensesList.get(position).getExpPaymentStatus().equals("pending")){
                    Toast.makeText(((Activity) context), "Payment Status is pending, you can not complete this expense", Toast.LENGTH_LONG).show();
                }else{
                    String expId = expensesList.get(position).getExpId();
                    expenseCompletedDialog(expId);
                }
            }
        });

        return rowView;

    }

    // Filter Class
    public void filter(String charText, View btn_clear) {
        charText = charText.toLowerCase(Locale.getDefault());
        expensesList.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            expensesList.addAll(arraylist);
            btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (ExpensesSetterGetter wp : arraylist)
            {
                if (wp.getExpId().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getExpAmount().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getExpTitle().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getExpDescription().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    expensesList.add(wp);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return expensesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private void deleteDialog(final String expId){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder((Activity) context);
        builder.setMessage("Are you sure, you want to delete?")
                .setTitle("Delete Expense")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        ((ExpensesActivity) ((Activity) context))
                                .deleteExpense(expId);

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

    private void expenseCompletedDialog(final String expId){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder((Activity) context);
        builder.setMessage("Are you sure, you want to complete this expense?")
                .setTitle("Meeting Completed")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        ((ExpensesActivity) ((Activity) context))
                                .completeExpense(expId);

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
