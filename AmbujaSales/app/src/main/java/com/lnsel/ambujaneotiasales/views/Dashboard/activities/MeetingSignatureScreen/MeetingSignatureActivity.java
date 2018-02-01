package com.lnsel.ambujaneotiasales.views.Dashboard.activities.MeetingSignatureScreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.helpers.CustomAdapter.MeetingsAdapter.MeetingsData;
import com.lnsel.ambujaneotiasales.helpers.Service.GPSService;
import com.lnsel.ambujaneotiasales.presenters.MeetingSignaturePresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;

/**
 * Created by apps2 on 5/8/2017.
 */
public class MeetingSignatureActivity extends AppCompatActivity implements MeetingSignatureActivityView {

    SharedManagerUtil session;

    LinearLayout mContent;
    signature mSignature;
    Button mClear, submitsign, mCancel;
    View mView;
    File mypath;
    private Bitmap signature_bitmap;
    String signature ="";

    private ProgressDialog progress;
    boolean gpsEnabled;

    private MeetingSignaturePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_signature);

        // Session Manager
        session = new SharedManagerUtil(this);

        presenter = new MeetingSignaturePresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Meeting Signature");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeetingDetailsActivity();
            }
        });



        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        mClear = (Button) findViewById(R.id.fragment_visits_tab_visited_btn_clear);
        submitsign = (Button) findViewById(R.id.fragment_visits_tab_visited_btn_submit);
        submitsign.setEnabled(false);
        mCancel = (Button) findViewById(R.id.fragment_visits_tab_visited_btn_cancel);
        mView = mContent;


        mClear.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                submitsign.setEnabled(false);
            }
        });

        submitsign.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Saved");
                mView.setDrawingCacheEnabled(true);
                mSignature.save(mView);
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v("log_tag", "Panel Canceled");
                startMeetingDetailsActivity();

            }
        });
    }

    public void errorInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void successInfo(String msg){
        if(progress != null){
            progress.dismiss();
        }
        MDToast.makeText(this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        startMeetingDetailsActivity();
    }

    @Override
    public void onBackPressed() {
        startMeetingDetailsActivity();
    }

    public void startMeetingDetailsActivity() {
        new ActivityUtil(this).startMeetingDetailsActivity();
    }


    /*Signature part............................................................................................*/
    public class signature extends View
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if(signature_bitmap == null)
            {
                signature_bitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(signature_bitmap);
            try{

                v.draw(canvas);
                signature = getStringSign(signature_bitmap);


                if(isNetworkAvailable()){
                    LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    gpsEnabled = service
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);

                    if(!gpsEnabled){
                        buildAlertMessageNoGps();
                    }else {

                        progress = new ProgressDialog(MeetingSignatureActivity.this);
                        progress.setMessage("loading...");
                        progress.show();
                        progress.setCanceledOnTouchOutside(false);

                        final String userId = session.getUserID();
                        final String mtnId = MeetingsData.get_current_mtnId;

                        String mtnSignatureImageName = "signature00"+session.getUserID()+"00"+MeetingsData.get_current_mtnId;

                        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
                        String mtnSignatureTime = stf.format(new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        final String mtnSignatureDate = sdf.format(new Date());

                        GPSService mGPSService = new GPSService(MeetingSignatureActivity.this);
                        mGPSService.getLocation();

                        double latitude = mGPSService.getLatitude();
                        double longitude = mGPSService.getLongitude();

                        String mtnSignatureLat = Double.toString(latitude);
                        String mtnSignatureLong = Double.toString(longitude);

                        if(mtnSignatureLat.equals("0.0")){
                            progress.dismiss();
                            Toast.makeText(MeetingSignatureActivity.this, "GPS not Responding, Please check your GPS", Toast.LENGTH_LONG).show();
                        }else{
                            presenter.updateMeetingSignatureService(UrlUtil.MEETING_SIGNATURE_UPDATE_URL, userId, mtnId, mtnSignatureImageName, mtnSignatureDate, mtnSignatureTime, mtnSignatureLat, mtnSignatureLong, signature);
                        }



                    }


                }else{
                    Toast.makeText(MeetingSignatureActivity.this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch(Exception e)
            {
                Log.v("log_tag_exception", e.toString());
            }


        }

        public void clear()
        {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float eventX = event.getX();
            float eventY = event.getY();
            submitsign.setEnabled(true);

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++)
                    {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string){
        }

        private void expandDirtyRect(float historicalX, float historicalY)
        {
            if (historicalX < dirtyRect.left)
            {
                dirtyRect.left = historicalX;
            }
            else if (historicalX > dirtyRect.right)
            {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top)
            {
                dirtyRect.top = historicalY;
            }
            else if (historicalY > dirtyRect.bottom)
            {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY)
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    public String getStringSign(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
