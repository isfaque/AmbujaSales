package com.lnsel.ambujaneotiasales.views.Dashboard;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.NavigationDrawer.NavItem;
import com.lnsel.ambujaneotiasales.helpers.NavigationDrawer.NavListAdapter;
import com.lnsel.ambujaneotiasales.helpers.Service.GPSService;
import com.lnsel.ambujaneotiasales.helpers.Service.LocationService;
import com.lnsel.ambujaneotiasales.notification.NotificationConfig;
import com.lnsel.ambujaneotiasales.presenters.MainPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.AttendanceScreen.AttendanceActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.CustomersScreen.CustomersActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpensesScreen.ExpensesActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.LeaveRequestScreen.LeaveRequestActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingsScreen.MeetingsActivity;
import com.lnsel.ambujaneotiasales.views.Dashboard.activities.OrdersScreen.OrdersActivity;

/**
 * Created by apps2 on 4/20/2017.
 */
public class MainActivity extends AppCompatActivity implements MainActivityView {

    public static double aroundMeLat, aroundMeLong;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    AlertDialog alertbox;

    List<NavItem> listNavItems;
    List<Activity> listFragments;

    ActionBarDrawerToggle actionBarDrawerToggle;

    SharedManagerUtil session;

    private MainPresenter presenter;
    boolean gpsEnabled;

    private ProgressDialog progress;

    LinearLayout ll_btn_attendance, ll_btn_meetings, ll_btn_orders, ll_btn_expenses, ll_btn_leave_request, ll_btn_customers, ll_btn_performance, ll_btn_contacts, ll_btn_around_me;
    TextView tv_username, tv_user_designation;
    Button btn_start_trip, btn_stop_trip;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        // Session Manager
        session = new SharedManagerUtil(getApplicationContext());

        if(!(isMyServiceRunning())){
            startService(new Intent(MainActivity.this, LocationService.class));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);
        listNavItems = new ArrayList<NavItem>();
        listNavItems.add(new NavItem("Attendance", "check your attendance", R.drawable.attendance_icon));
        listNavItems.add(new NavItem("Meetings", "check your meetings", R.drawable.meeting_icon));
        listNavItems.add(new NavItem("Orders", "check your orders", R.drawable.orders_icon));
        listNavItems.add(new NavItem("Expenses", "manage your expenses", R.drawable.expense_icon));
        listNavItems.add(new NavItem("Leave Request", "send your leave request", R.drawable.leave_request_icon));
        listNavItems.add(new NavItem("Customers", "customers list", R.drawable.customer_icon));
        listNavItems.add(new NavItem("Performance", "see your performance", R.drawable.performance_icon));
        listNavItems.add(new NavItem("My Contacts", "manage your contacts", R.drawable.contacts_icon));
        listNavItems.add(new NavItem("Around Me", "check companies around current location", R.drawable.around_me_icon));

        ll_btn_attendance = (LinearLayout) findViewById(R.id.activity_main_ll_one);
        ll_btn_meetings = (LinearLayout) findViewById(R.id.activity_main_ll_two);
        ll_btn_orders = (LinearLayout) findViewById(R.id.activity_main_ll_three);
        ll_btn_expenses = (LinearLayout) findViewById(R.id.activity_main_ll_four);
        ll_btn_leave_request = (LinearLayout) findViewById(R.id.activity_main_ll_five);
        ll_btn_customers = (LinearLayout) findViewById(R.id.activity_main_ll_six);
        ll_btn_performance = (LinearLayout) findViewById(R.id.activity_main_ll_seven);
        ll_btn_contacts = (LinearLayout) findViewById(R.id.activity_main_ll_eight);
        ll_btn_around_me = (LinearLayout) findViewById(R.id.activity_main_ll_nine);

        btn_start_trip = (Button) findViewById(R.id.activity_main_btn_trip_start);
        btn_stop_trip = (Button) findViewById(R.id.activity_main_btn_trip_stop);

        tv_username = (TextView) findViewById(R.id.activity_main_tv_username);
        tv_user_designation= (TextView) findViewById(R.id.activity_main_tv_user_designation);

        tv_username.setText(session.getUserName().toString());
        tv_user_designation.setText(session.getUserDesignation().toString());

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.item_nav_list, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<Activity>();
        listFragments.add(new MainActivity());
        listFragments.add(new AttendanceActivity());
        listFragments.add(new MeetingsActivity());
        listFragments.add(new OrdersActivity());
        listFragments.add(new ExpensesActivity());
        listFragments.add(new LeaveRequestActivity());
        listFragments.add(new CustomersActivity());

        // set listener for navigation items:
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                lvNav.setItemChecked(position, true);

                if(position == 0){
                    drawerLayout.closeDrawer(drawerPane);
                    startAttendanceActivity();
                }else if(position == 1){
                    drawerLayout.closeDrawer(drawerPane);
                    startMeetingsActivity();
                }else if(position == 2){
                    drawerLayout.closeDrawer(drawerPane);
                    startOrdersActivity();
                }else if(position == 3) {
                    drawerLayout.closeDrawer(drawerPane);
                    startExpensesActivity();
                }else if(position == 4){
                    drawerLayout.closeDrawer(drawerPane);
                    startLeaveRequestActivity();
                }else if(position == 5){
                    drawerLayout.closeDrawer(drawerPane);
                    startCustomersActivity();
                }else if(position == 6){
                    drawerLayout.closeDrawer(drawerPane);
                    startPerformanceActivity();
                }else if(position == 7){
                    drawerLayout.closeDrawer(drawerPane);
                }else{
                    drawerLayout.closeDrawer(drawerPane);
                    startAroundMeActivity();
                }


            }
        });

        // create listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

        };

        ll_btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAttendanceActivity();
            }
        });

        ll_btn_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingsActivity();
            }
        });

        ll_btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrdersActivity();
            }
        });

        ll_btn_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExpensesActivity();
            }
        });


        ll_btn_leave_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLeaveRequestActivity();
            }
        });

        ll_btn_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCustomersActivity();
            }
        });

        ll_btn_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPerformanceActivity();
            }
        });

        ll_btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactsActivity();
            }
        });

        ll_btn_around_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAroundMeActivity();
            }
        });

        if(session.isTripStart()){
            btn_start_trip.setVisibility(View.GONE);
            btn_stop_trip.setVisibility(View.VISIBLE);
        }else{
            btn_stop_trip.setVisibility(View.GONE);
            btn_start_trip.setVisibility(View.VISIBLE);
        }

        btn_start_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()){

                    LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    gpsEnabled = service
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if(!gpsEnabled){
                        buildAlertMessageNoGps();
                    }else{
                        GPSService mGPSService = new GPSService(MainActivity.this);
                        mGPSService.getLocation();

                        progress = new ProgressDialog(MainActivity.this);
                        progress.setMessage("loading...");
                        progress.show();
                        //progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);

                        String userId = session.getUserID();
                        String trpLoginRecordId = session.getUserLoginRecordId();

                        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
                        String trpStartTime = stf.format(new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String trpStartDate = sdf.format(new Date());

                        double latitude = mGPSService.getLatitude();
                        double longitude = mGPSService.getLongitude();

                        String trpStartLat = Double.toString(latitude);
                        String trpStartLong = Double.toString(longitude);

                        if(trpStartLat.equals("0.0")) {
                            progress.dismiss();
                            Toast.makeText(MainActivity.this, "GPS not Responding, check your GPS", Toast.LENGTH_LONG).show();
                        }else{
                            presenter.updateStarTripService(UrlUtil.UPDATE_START_TRIP, userId, trpLoginRecordId, trpStartDate, trpStartTime, trpStartLat, trpStartLong);
                        }
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_stop_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isNetworkAvailable()){

                    LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    gpsEnabled = service
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if(!gpsEnabled){
                        buildAlertMessageNoGps();
                    }else {

                        progress = new ProgressDialog(MainActivity.this);
                        progress.setMessage("loading...");
                        progress.show();
                        //progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);

                        String userId = session.getUserID();
                        String trpId = session.getTripId();

                        String trpLoginRecordId = session.getUserLoginRecordId();

                        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
                        String trpEndTime = stf.format(new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String trpEndDate = sdf.format(new Date());

                        GPSService mGPSService = new GPSService(MainActivity.this);
                        mGPSService.getLocation();

                        double latitude = mGPSService.getLatitude();
                        double longitude = mGPSService.getLongitude();

                        String trpEndLat = Double.toString(latitude);
                        String trpEndLong = Double.toString(longitude);

                        if(trpEndLat.equals("0.0")) {
                            progress.dismiss();
                            Toast.makeText(MainActivity.this, "GPS not Responding, check your GPS", Toast.LENGTH_LONG).show();
                        }else{
                            presenter.updateStopTripService(UrlUtil.UPDATE_STOP_TRIP, userId, trpId, trpLoginRecordId, trpEndDate, trpEndTime, trpEndLat, trpEndLong);
                        }



                    }





                }else{
                    Toast.makeText(MainActivity.this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
                }

            }
        });




        // push notification register
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(NotificationConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(NotificationConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(NotificationConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                }
            }
        };

        displayFirebaseRegId();



    }

    /** function for options menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);

        return true;

    }

    /** function for item selected in options menu **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        else
            switch (item.getItemId()) {
                // action with ID action_refresh was selected
                case R.id.menu_item_share:
                   // Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
                    logoutDialog();
                    break;
                case R.id.change_password:
                    startChangePasswordActivity();
                    break;
                case R.id.open_reports:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app.lnsel.in/ambujaneotiasales/login"));
                    startActivity(browserIntent);
                    break;

                default:
                    break;
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void startLoginActivity(){
        new ActivityUtil(this).startLoginActivity();
    }

    public void startMainActivity(){
        new ActivityUtil(this).startMainActivity();
    }

    public void startAttendanceActivity(){
        new ActivityUtil(this).startAttendanceActivity();
    }

    public void startMeetingsActivity(){
        new ActivityUtil(this).startMeetingsActivity();
    }

    public void startOrdersActivity(){
        new ActivityUtil(this).startOrdersActivity();
    }

    public void startExpensesActivity(){
        new ActivityUtil(this).startExpensesActivity();
    }

    public void startLeaveRequestActivity(){
        new ActivityUtil(this).startLeaveRequestActivity();
    }

    public void startCustomersActivity(){
        new ActivityUtil(this).startCustomersActivity();
    }

    public void startChangePasswordActivity(){
        new ActivityUtil(this).startChangePasswordActivity();
    }

    public void startPerformanceActivity(){
        new ActivityUtil(this).startPerformanceSelectionActivity();
    }

    public void startContactsActivity(){
        new ActivityUtil(this).startContactsActivity();
    }

    public void startAroundMeActivity(){
        if(isNetworkAvailable()){
            final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            gpsEnabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gpsEnabled) {
                buildAlertMessageNoGps();
            }else{
                GPSService mGPSService = new GPSService(this);
                mGPSService.getLocation();

                double latitude = mGPSService.getLatitude();
                double longitude = mGPSService.getLongitude();

                aroundMeLat = latitude;
                aroundMeLong = longitude;

                String loginLat = Double.toString(latitude);
                String loginLong = Double.toString(longitude);

                if(loginLat.equals("0.0")){
                    Toast.makeText(this, "GPS not Responding, check your GPS", Toast.LENGTH_LONG).show();
                }else{
                    new ActivityUtil(this).startAroundMeActivity();
                }
            }
        }else{
            Toast.makeText(this, "Internet Connection not Available", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    private void logoutDialog(){

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout from app")
                .setTitle("Logout")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        if(isNetworkAvailable()){

                            final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                            gpsEnabled = service
                                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
                            if (!gpsEnabled) {
                                buildAlertMessageNoGps();
                            }else{

                                if(session.isTripStart()){
                                    Toast.makeText(MainActivity.this, "First Stop your Trip", Toast.LENGTH_SHORT).show();
                                }else{
                                    logoutApp();
                                }

                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Internet Connection not Available", Toast.LENGTH_SHORT).show();
                        }

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

    private void logoutApp(){

        progress = new ProgressDialog(this);
        progress.setMessage("loading...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        String userId = session.getUserID();
        String lgnrId = session.getUserLoginRecordId();

        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
        String lgnrLogoutTime = stf.format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lgnrLogoutDate = sdf.format(new Date());

        GPSService mGPSService = new GPSService(this);
        mGPSService.getLocation();

        double latitude = mGPSService.getLatitude();
        double longitude = mGPSService.getLongitude();

        String lgnrLogoutLat = Double.toString(latitude);
        String lgnrLogoutLong = Double.toString(longitude);

        if(lgnrLogoutLat.equals("0.0")){
            progress.dismiss();
            Toast.makeText(this, "GPS not Responding, check your GPS", Toast.LENGTH_LONG).show();
        }else {
            presenter.updateLogoutService(UrlUtil.UPDATE_LOGOUT_RECORD, userId, lgnrId, lgnrLogoutDate, lgnrLogoutTime, lgnrLogoutLat, lgnrLogoutLong);
        }


    }

    public void errorInfo(String msg){
        progress.dismiss();
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void successInfo(String msg){
        progress.dismiss();
        session.logoutUser();
        startLoginActivity();

    }


    public void updateStarTripSession(String trpId){
        session.createTripSession(trpId);
        progress.dismiss();
        btn_start_trip.setVisibility(View.GONE);
        btn_stop_trip.setVisibility(View.VISIBLE);
    }

    public void updateStopTripSession(){
        session.stopTripSession();
        progress.dismiss();
        btn_stop_trip.setVisibility(View.GONE);
        btn_start_trip.setVisibility(View.VISIBLE);
    }


    private void buildAlertMessageNoGps() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Activate GPS to use use location services")
                .setTitle("Location not available, Open GPS")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (LocationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        // update token if it changed...........................
        SharedPreferences pref = getApplicationContext().getSharedPreferences(NotificationConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", "");
        Log.e("TAG", "Firebase reg id  : " + regId);
        if (!TextUtils.isEmpty(regId)) {
            System.out.println("Firebase Reg Id: " + regId);
        } else {
            System.out.println("Fire-base Reg Id is not received yet!" );
        }

        if(isNetworkAvailable() && session.isLoggedIn()) {
            if(!session.getKeyUserPushToken().equalsIgnoreCase(regId)) {
                presenter.sendPushTokenToServer(UrlUtil.TOKEN_UPDATE_URL, session.getUserID(), regId);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        //NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void updatePushToken(String userPushToken){
        session.updatePushToken(userPushToken);
    }
}
