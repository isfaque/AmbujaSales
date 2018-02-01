package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactsScreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Locale;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter.ContactsBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter.ContactsData;
import com.lnsel.ambujaneotiasales.presenters.ContactsPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/19/2017.
 */
public class ContactsActivity extends AppCompatActivity implements ContactsActivityView {

    private ContactsPresenter presenter;
    private ProgressDialog progress;

    ContactsBaseAdapter adapter;

    ListView list;
    EditText et_contact_search;
    Button btn_clear;

    SharedManagerUtil session;

    FloatingActionButton fab_add_contact;

    private static final int PHONE_CALL = 115;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        presenter = new ContactsPresenter(this);
        // Session Manager
        session = new SharedManagerUtil(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Contacts");

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        list=(ListView) findViewById(R.id.list_view);
        et_contact_search = (EditText) findViewById(R.id.activity_contacts_et_contact_search);
        btn_clear = (Button) findViewById(R.id.activity_contacts_btn_clear);

        fab_add_contact = (FloatingActionButton) findViewById(R.id.activity_contacts_btn_add_contact);

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getContactsService(UrlUtil.GET_CONTACTS_URL+"?userId="+session.getUserID());

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_contact_search.setText("");
            }
        });

        fab_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactAddActivity();
            }
        });
    }


    public void startGetContacts() {
        progress.dismiss();

        adapter=new ContactsBaseAdapter(this, ContactsData.contactsList);
        list.setAdapter(adapter);
        et_contact_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // When user changed the Text
                String text = et_contact_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });

    }

    public void callingMethod(String number){
        boolean hasPermissionCall = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCall) {
            ActivityCompat.requestPermissions(ContactsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PHONE_CALL);
        }else{
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+number));
            if (ActivityCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(phoneIntent);
        }
    }


    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }
    public void startContactAddActivity() {
        new ActivityUtil(this).startContactAddActivity();
    }


    public void deleteContact(String cntId){

        String userId = session.getUserID();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.deleteContactService(UrlUtil.DELETE_CONTACT_URL, userId, cntId);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void errorContactDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
    }

    public void successContactDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        new ActivityUtil(this).startContactsActivity();
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
