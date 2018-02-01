package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsBaseAdapter;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsData;
import com.lnsel.ambujaneotiasales.presenters.MeetingsPresenter;
import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsSetterGetter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/3/2017.
 */
public class MeetingsActivity extends AppCompatActivity implements MeetingsActivityView {

    private MeetingsPresenter presenter;
    private ProgressDialog progress;

    MeetingsBaseAdapter adapter;

    ListView list;
    EditText et_meeting_search;
    Button btn_clear,activity_meetings_btn_search_date;
    FloatingActionButton btn_add_meeting;

    SharedManagerUtil session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        // Session Manager
        session = new SharedManagerUtil(this);

        list=(ListView) findViewById(R.id.list_view);
        et_meeting_search = (EditText) findViewById(R.id.activity_meetings_et_meeting_search);
        btn_clear = (Button) findViewById(R.id.activity_meetings_btn_clear);
        activity_meetings_btn_search_date=(Button)findViewById(R.id.activity_meetings_btn_search_date);
        btn_add_meeting = (FloatingActionButton) findViewById(R.id.activity_meetings_fab_add_meeting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Meetings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        presenter = new MeetingsPresenter(this);

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.getMeetingsService(UrlUtil.GET_MEETINGS_URL+"?userId="+session.getUserID());
        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_meeting_search.setText("");
            }
        });

        btn_add_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingAddActivity();
            }
        });


        // start dob date picker===================================================
        activity_meetings_btn_search_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(MeetingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        DecimalFormat mFormat= new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        selectedmonth = selectedmonth + 1;
                        String select_date =  selectedyear + "-" +  mFormat.format(Double.valueOf(selectedmonth)) + "-" +  mFormat.format(Double.valueOf(selectedday));
                        et_meeting_search.setText("" + select_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });
        // end dob date picker===================================================


    }

    public void startGetMeetings() {
        progress.dismiss();

        adapter=new MeetingsBaseAdapter(this, MeetingsData.meetingsList);
        list.setAdapter(adapter);

        et_meeting_search.addTextChangedListener(new TextWatcher() {

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
                String text = et_meeting_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, btn_clear);

            }
        });
/*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_LONG).show();
                Toast.makeText(MeetingsActivity.this, "You Clicked at "+ position, Toast.LENGTH_SHORT).show();


            }
        });*/


    }

    public void errorInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    public void startMeetingAddActivity() {
        new ActivityUtil(this).startMeetingAddActivity();
    }

    public void startMeetingDetailsActivity() {
        new ActivityUtil(this).startMeetingDetailsActivity();
    }


    public void completeMeeting(String mtnId){

        String userId = session.getUserID();
        String mtnCompleted = "yes";

        if(isNetworkAvailable()){
            progress = new ProgressDialog(this);
            progress.setMessage("loading...");
            progress.show();
            progress.setCanceledOnTouchOutside(false);
            presenter.updateMeetingCompletedService(UrlUtil.MEETING_COMPLETED_UPDATE_URL, userId, mtnId, mtnCompleted);

        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }

    }

    public void errorMeetingCompletedInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void successMeetingCompletedInfo(String msg){
        progress.dismiss();
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        new ActivityUtil(this).startMeetingsActivity();
    }



    public void addCalendarEvent(MeetingsSetterGetter meetings){

        String givenDateString = meetings.getMtnDate()+" "+meetings.getMtnTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long endTimeInMilliseconds=0;
        long startTimeInMilliseconds=0;
        try {
            //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date mDate = sdf.parse(givenDateString);

            // endTimeInMilliseconds = mDate.getTime();
            // startTimeInMilliseconds = endTimeInMilliseconds - 3000 * 60 * 60;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            endTimeInMilliseconds = calendar.getTimeInMillis();
            startTimeInMilliseconds = endTimeInMilliseconds - 3000 * 60 * 60;


            System.out.println(mDate.getYear()+" "+mDate.getMonth()+" "+mDate.getDay()+" "+mDate.getHours()+" ");
            System.out.println(givenDateString+" startTimeInMilliseconds :: " + startTimeInMilliseconds);
            System.out.println(givenDateString+" endTimeInMilliseconds :: " + endTimeInMilliseconds);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        String description="Customer Name : "+meetings.getMtnCustomerName();

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeInMilliseconds)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeInMilliseconds)
                .putExtra(CalendarContract.Events.TITLE, meetings.getMtnName())
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, meetings.getMtnCustomerAddress())
                //RRULE:FREQ=MINUTELY;INTERVAL=20;BYHOUR=9,10,11,12,13,14,15,16
                //.putExtra(CalendarContract.Events.RRULE, "FREQ=MINUTELY;INTERVAL=30;BYHOUR=12,13,14;UNTIL="+startTimeInMilliseconds)
                //RRULE:FREQ=DAILY;BYHOUR=9,10,11,12,13,14,15,16;BYMINUTE=0,20,40
                //.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY;BYHOUR=12,13,14;BYMINUTE=0,30;UNTIL="+startTimeInMilliseconds)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(intent);
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
