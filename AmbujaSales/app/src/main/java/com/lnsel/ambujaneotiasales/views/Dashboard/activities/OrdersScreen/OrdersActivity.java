package com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrdersScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.OrdersAdapter.OrdersBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.OrdersAdapter.OrdersData;
import com.lnsel.ambujaneotiasales.presenters.OrdersPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/25/2017.
 */
public class OrdersActivity extends AppCompatActivity implements OrdersActivityView {

    private OrdersPresenter presenter;
    private ProgressDialog progress;

    OrdersBaseAdapter adapter;

    ListView list;
    EditText et_order_search;
    Button btn_clear;

    SharedManagerUtil session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        presenter = new OrdersPresenter(this);
        // Session Manager
        session = new SharedManagerUtil(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Queries");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        list=(ListView) findViewById(R.id.list_view);
        et_order_search = (EditText) findViewById(R.id.activity_orders_et_order_search);
        btn_clear = (Button) findViewById(R.id.activity_orders_btn_clear);

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getOrdersService(UrlUtil.GET_ORDERS_URL+"?userId="+session.getUserID());

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_order_search.setText("");
            }
        });

    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }


    public void startGetOrders() {
        progress.dismiss();

        adapter=new OrdersBaseAdapter(this, OrdersData.ordersList);
        list.setAdapter(adapter);
        et_order_search.addTextChangedListener(new TextWatcher() {

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
                String text = et_order_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });

    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void deleteOrder(String ordId){

        String userId = session.getUserID();

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.deleteOrderService(UrlUtil.DELETE_ORDER_URL, userId, ordId);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void errorOrderDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void successOrderDeleteInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
       // Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        new ActivityUtil(this).startOrdersActivity();
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
