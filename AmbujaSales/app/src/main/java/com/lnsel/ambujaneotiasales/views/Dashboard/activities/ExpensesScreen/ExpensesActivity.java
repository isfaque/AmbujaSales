package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter.ExpensesBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.ExpensesAdapter.ExpensesData;
import com.lnsel.ambujaneotiasales.presenters.ExpensesPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/3/2017.
 */
public class ExpensesActivity extends AppCompatActivity implements ExpensesActivityView {

    private ExpensesPresenter presenter;
    private ProgressDialog progress;

    ExpensesBaseAdapter adapter;

    ListView list;
    EditText et_expense_search;
    Button btn_clear;

    SharedManagerUtil session;



    FloatingActionButton btn_add_expense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        // Session Manager
        session = new SharedManagerUtil(this);

        list=(ListView) findViewById(R.id.list_view);
        et_expense_search = (EditText) findViewById(R.id.activity_expenses_et_expense_search);
        btn_clear = (Button) findViewById(R.id.activity_expenses_btn_clear);
        btn_add_expense = (FloatingActionButton) findViewById(R.id.activity_expenses_btn_add_expense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Expenses");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        presenter = new ExpensesPresenter(this);

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getExpensesService(UrlUtil.GET_EXPENSES_URL+"?userId="+session.getUserID()+"&userParentPath="+session.getUserParentPath());

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_expense_search.setText("");
            }
        });

        btn_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExpenseAddActivity();
            }
        });


    }

    public void startGetExpenses() {
        progress.dismiss();

        adapter=new ExpensesBaseAdapter(this, ExpensesData.expensesList);
        list.setAdapter(adapter);

        et_expense_search.addTextChangedListener(new TextWatcher() {

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
                String text = et_expense_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });

    }

    public void deleteExpense(String expId){

        String userId = session.getUserID();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.deleteExpenseService(UrlUtil.DELETE_EXPENSE_URL, userId, expId);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    public void startExpenseAddActivity() {
        new ActivityUtil(this).startExpenseAddActivity();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void errorExpenseDeleteInfo(String msg){
        progress.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void successExpenseDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        new ActivityUtil(this).startExpensesActivity();
    }


    public void completeExpense(String expId){

        String userId = session.getUserID();
        String expCompleted = "yes";

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.updateExpenseCompletedService(UrlUtil.EXPENSE_COMPLETED_UPDATE_URL, userId, expId, expCompleted);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void errorExpenseCompletedInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
    }

    public void successExpenseCompletedInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        new ActivityUtil(this).startExpensesActivity();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
