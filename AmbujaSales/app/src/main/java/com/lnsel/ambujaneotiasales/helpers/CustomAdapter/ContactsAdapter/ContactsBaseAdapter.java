package com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactsScreen.ContactsActivity;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;

/**
 * Created by apps2 on 5/29/2017.
 */
public class ContactsBaseAdapter extends BaseAdapter {

    Context context;
    private static LayoutInflater inflater=null;

    private List<ContactsSetterGetter> contactsList = null;
    private ArrayList<ContactsSetterGetter> arraylist;

    public ContactsBaseAdapter(Activity context, List<ContactsSetterGetter> contactsList) {
        this.context = context;

        this.contactsList = contactsList;
        this.arraylist = new ArrayList<ContactsSetterGetter>();
        this.arraylist.addAll(contactsList);

        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(final int position, final View view, ViewGroup parent) {
        View rowView=inflater.inflate(R.layout.cardview_contacts, null,true);

        TextView tv_contact_person_name = (TextView) rowView.findViewById(R.id.cardview_contacts_tv_person_name);
        TextView tv_contact_no = (TextView) rowView.findViewById(R.id.cardview_contacts_tv_contact_no);
        TextView tv_contact_address = (TextView) rowView.findViewById(R.id.cardview_contacts_tv_address);
        TextView tv_contact_other_details = (TextView) rowView.findViewById(R.id.cardview_contacts_tv_other_details);
        TextView tv_contact_other_details_large = (TextView) rowView.findViewById(R.id.cardview_contacts_tv_other_details_large);

        ImageButton ib_call_contact = (ImageButton) rowView.findViewById(R.id.cardview_contacts_ib_call_contact);
        ImageButton ib_edit_contact = (ImageButton) rowView.findViewById(R.id.cardview_contacts_ib_edit_contact);
        ImageButton ib_delete_contact = (ImageButton) rowView.findViewById(R.id.cardview_contacts_ib_delete_contact);

        int other_details_length = contactsList.get(position).getCntOtherDetails().toString().length();

        tv_contact_person_name.setText(contactsList.get(position).getCntPersonName());
        tv_contact_no.setText(contactsList.get(position).getCntContactNo());
        tv_contact_address.setText(contactsList.get(position).getCntAddress());
        tv_contact_other_details.setText(contactsList.get(position).getCntOtherDetails());
        tv_contact_other_details_large.setText(contactsList.get(position).getCntOtherDetails());

        if(other_details_length > 24){
            tv_contact_other_details.setVisibility(View.GONE);
            tv_contact_other_details_large.setVisibility(View.VISIBLE);
        }else{
            tv_contact_other_details.setVisibility(View.VISIBLE);
            tv_contact_other_details_large.setVisibility(View.GONE);
        }

        ib_call_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContactsActivity)context).callingMethod(contactsList.get(position).getCntContactNo());
            }
        });


        ib_edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsData.current_contact_id = contactsList.get(position).getCntId();
                ContactsData.current_contact_person_name = contactsList.get(position).getCntPersonName();
                ContactsData.current_contact_no= contactsList.get(position).getCntContactNo();
                ContactsData.current_contact_address = contactsList.get(position).getCntAddress();
                ContactsData.current_contact_other_details = contactsList.get(position).getCntOtherDetails();

                new ActivityUtil((Activity) context).startContactEditActivity();

            }
        });

        ib_delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cntId = contactsList.get(position).getCntId();
                deleteDialog(cntId);
            }
        });

        return rowView;

    }


    // Filter Class
    public void filter(String charText, View btn_clear) {
        charText = charText.toLowerCase(Locale.getDefault());
        contactsList.clear();
        if (charText.length() == 0||charText.equalsIgnoreCase("")) {
            contactsList.addAll(arraylist);
            btn_clear.setVisibility(View.GONE);
        }
        else
        {
            for (ContactsSetterGetter wp : arraylist)
            {
                if (wp.getCntPersonName().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getCntContactNo().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getCntAddress().toLowerCase(Locale.getDefault()).contains(charText)||
                        wp.getCntOtherDetails().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    contactsList.add(wp);
                }
            }
            btn_clear.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }

    private void deleteDialog(final String cntId){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder((Activity) context);
        builder.setMessage("Are you sure, you want to delete?")
                .setTitle("Delete Expense")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        ((ContactsActivity) ((Activity) context))
                                .deleteContact(cntId);

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

    @Override
    public int getCount() {
        return contactsList.size();
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
