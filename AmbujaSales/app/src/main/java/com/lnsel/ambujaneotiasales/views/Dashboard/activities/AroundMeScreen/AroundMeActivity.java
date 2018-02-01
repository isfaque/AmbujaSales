package com.lnsel.ambujaneotiasales.views.Dashboard.activities.AroundMeScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.lnsel.ambujaneotiasales.views.Dashboard.MainActivity;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.NearByLocation.GooglePlacesReadTask;
import com.lnsel.ambujaneotiasales.helpers.NearByLocation.PlacesDisplayTask;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;

/**
 * Created by apps2 on 5/19/2017.
 */
public class AroundMeActivity extends AppCompatActivity implements AroundMeActivityView, OnMapReadyCallback {

    private static final String GOOGLE_API_KEY = "AIzaSyBypOtpZWlq-WQ4Q-W_lss58WQeCfNRwdI";
    // GoogleMap googleMap;
    //double latitude = 22.5867;
    public static double latitude = MainActivity.aroundMeLat;
    //double longitude = 88.4171;
    public static double longitude = MainActivity.aroundMeLong;
    private int PROXIMITY_RADIUS = 5000;


    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);

        PlacesDisplayTask.myMAPLocationName = "My Location";
        PlacesDisplayTask.myMAPAddressName = "";
        PlacesDisplayTask.greenLat = latitude;
        PlacesDisplayTask.greenLong = longitude;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Around Me");

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(this, "Google Play Service Not Available", Toast.LENGTH_LONG).show();
        }


        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        shownearcompany();

    }

    public void shownearcompany(){
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=company");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
        Object[] toPass = new Object[2];
        toPass[0] = mMap;
        toPass[1] = googlePlacesUrl.toString();
        googlePlacesReadTask.execute(toPass);
    }

    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }
}
