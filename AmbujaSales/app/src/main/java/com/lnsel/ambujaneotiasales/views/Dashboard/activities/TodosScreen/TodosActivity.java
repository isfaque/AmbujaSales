package com.lnsel.ambujaneotiasales.views.Dashboard.activities.TodosScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;

/**
 * Created by apps2 on 5/3/2017.
 */
public class TodosActivity extends AppCompatActivity implements TodosActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Todos");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });


    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }
}
