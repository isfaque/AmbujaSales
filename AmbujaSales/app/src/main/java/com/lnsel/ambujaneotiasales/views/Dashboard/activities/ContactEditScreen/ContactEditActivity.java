package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ContactEditScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ContactsAdapter.ContactsData;
import com.lnsel.ambujaneotiasales.presenters.ContactEditPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;

/**
 * Created by apps2 on 5/29/2017.
 */
public class ContactEditActivity extends AppCompatActivity implements ContactEditActivityView {
    private ContactEditPresenter presenter;

    SharedManagerUtil session;

    private ProgressDialog progress;

    EditText et_contact_person_name, et_contact_no, et_contact_address, et_contact_other_details;
    TextInputLayout til_contact_person_name, til_contact_no, til_contact_address, til_contact_other_details;
    Button btn_cancel, btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new ContactEditPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Contact");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactsActivity();
            }
        });

        et_contact_person_name = (EditText) findViewById(R.id.activity_contact_edit_et_person_name);
        et_contact_no = (EditText) findViewById(R.id.activity_contact_edit_et_contact_no);
        et_contact_address = (EditText) findViewById(R.id.activity_contact_edit_et_address);
        et_contact_other_details = (EditText) findViewById(R.id.activity_contact_edit_et_other_details);

        btn_cancel = (Button) findViewById(R.id.activity_contact_edit_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_contact_edit_btn_submit);

        til_contact_person_name = (TextInputLayout) findViewById(R.id.activity_contact_edit_til_person_name);
        til_contact_no = (TextInputLayout) findViewById(R.id.activity_contact_edit_til_contact_no);
        til_contact_address = (TextInputLayout) findViewById(R.id.activity_contact_edit_til_address);
        til_contact_other_details = (TextInputLayout) findViewById(R.id.activity_contact_edit_til_other_details);

        et_contact_person_name.addTextChangedListener(new MyTextWatcher(et_contact_person_name));
        et_contact_no.addTextChangedListener(new MyTextWatcher(et_contact_no));
        et_contact_address.addTextChangedListener(new MyTextWatcher(et_contact_address));

        et_contact_person_name.setText(ContactsData.current_contact_person_name);
        et_contact_no.setText(ContactsData.current_contact_no);
        et_contact_address.setText(ContactsData.current_contact_address);
        et_contact_other_details.setText(ContactsData.current_contact_other_details);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactsActivity();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePersonName()) {
                    return;
                }
                if (!validateContactNo()) {
                    return;
                }
                if (!validateAddress()) {
                    return;
                }

                updateContact();

            }
        });
    }


    public void updateContact(){

        if(isNetworkAvailable()){

            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);

            final String userId = session.getUserID();
            String cntId = ContactsData.current_contact_id;

            String cntPersonName = et_contact_person_name.getText().toString();
            String cntContactNo = et_contact_no.getText().toString();
            String cntAddress = et_contact_address.getText().toString();
            String cntOtherDetails = et_contact_other_details.getText().toString();

            presenter.updateContactService(UrlUtil.UPDATE_CONTACT_URL, userId, cntId, cntPersonName, cntContactNo, cntAddress, cntOtherDetails);


        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }


    public void startContactsActivity() {
        new ActivityUtil(this).startContactsActivity();
    }

    @Override
    public void onBackPressed() {
        startContactsActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        startContactsActivity();
    }


    //********** Text Watcher for Validation *******************//
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.activity_contact_edit_et_person_name:
                    validatePersonName();
                    break;
                case R.id.activity_contact_edit_et_contact_no:
                    validateContactNo();
                    break;
                case R.id.activity_contact_edit_et_address:
                    validateAddress();
                    break;
            }
        }
    }


    private boolean validatePersonName() {
        if (et_contact_person_name.getText().toString().trim().isEmpty()) {
            til_contact_person_name.setError("person name can not be blank");
            requestFocus(et_contact_person_name);
            return false;
        } else {
            til_contact_person_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateContactNo() {
        if (et_contact_no.getText().toString().trim().isEmpty()) {
            til_contact_no.setError("contact no can not be blank");
            requestFocus(et_contact_no);
            return false;
        } else {
            til_contact_no.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (et_contact_address.getText().toString().trim().isEmpty()) {
            til_contact_address.setError("address can not be blank");
            requestFocus(et_contact_address);
            return false;
        } else {
            til_contact_address.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
