package com.lnsel.ambujaneotiasales.views.Dashboard.activities.ExpenseAddScreen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import com.lnsel.ambujaneotiasales.utils.UrlUtil;
import com.lnsel.ambujaneotiasales.R;
import com.lnsel.ambujaneotiasales.presenters.ExpenseAddPresenter;
import com.lnsel.ambujaneotiasales.utils.ActivityUtil;
import com.lnsel.ambujaneotiasales.utils.SharedManagerUtil;


/**
 * Created by apps2 on 5/5/2017.
 */
public class ExpenseAddActivity extends AppCompatActivity implements ExpenseAddActivityView {

    private static final float maxHeight = 1280.0f;
    private static final float maxWidth = 1280.0f;

    private ExpenseAddPresenter presenter;
    SharedManagerUtil session;

    EditText et_expense_title, et_expense_amount, et_expense_description;
    TextInputLayout til_expense_title, til_expense_amount, til_expense_description;
    ImageButton ib_expense_image;
    Button btn_cancel, btn_submit;
    ImageView iv_expense_image;

    final int TAKE_PICTURE = 1;
    final int ACTIVITY_SELECT_IMAGE = 2;

    private static final int REQUEST_READ_STORAGE = 113;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 114;
    boolean hasPermissionRead;
    boolean hasPermissionCamera;

    Bitmap photo_bitmap;
    String image="";

    private ProgressDialog progress;
    boolean gpsEnabled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_add);

        // Session Manager
        session = new SharedManagerUtil(this);


        presenter = new ExpenseAddPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Expense");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExpensesActivity();
            }
        });

        et_expense_title = (EditText) findViewById(R.id.activity_expense_add_et_title);
        et_expense_amount = (EditText) findViewById(R.id.activity_expense_add_et_amount);
        et_expense_description = (EditText) findViewById(R.id.activity_expense_add_et_description);

        btn_cancel = (Button) findViewById(R.id.activity_expense_add_btn_cancel);
        btn_submit = (Button) findViewById(R.id.activity_expense_add_btn_submit);
        ib_expense_image = (ImageButton) findViewById(R.id.activity_expense_add_ib_add_image);
        iv_expense_image = (ImageView) findViewById(R.id.activity_expense_add_iv_image);

        til_expense_title = (TextInputLayout) findViewById(R.id.activity_expense_add_til_title);
        til_expense_amount = (TextInputLayout) findViewById(R.id.acitvity_expense_add_til_amount);
        til_expense_description = (TextInputLayout) findViewById(R.id.activity_expense_add_til_description);

        et_expense_title.addTextChangedListener(new MyTextWatcher(et_expense_title));
        et_expense_amount.addTextChangedListener(new MyTextWatcher(et_expense_amount));
        et_expense_description.addTextChangedListener(new MyTextWatcher(et_expense_description));

        hasPermissionRead = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionRead) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_STORAGE);
        }

        hasPermissionCamera = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionCamera) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }


        ib_expense_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hasPermissionRead = (ContextCompat.checkSelfPermission(ExpenseAddActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionRead) {
                    ActivityCompat.requestPermissions(ExpenseAddActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_STORAGE);
                }

                hasPermissionCamera = (ContextCompat.checkSelfPermission(ExpenseAddActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionCamera) {
                    ActivityCompat.requestPermissions(ExpenseAddActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            CAMERA_PERMISSION_REQUEST_CODE);
                }
                if (hasPermissionCamera&&hasPermissionRead) {
                    selectImage();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExpensesActivity();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateExpenseTitle()) {
                    return;
                }
                if (!validateExpenseAmount()) {
                    return;
                }
                if (!validateExpenseDescription()) {
                    return;
                }

               if(photo_bitmap!=null) {
                    image = getStringImage(photo_bitmap);
                    if(isNetworkAvailable()){
                        submitPicture();
                    }else {
                        Toast.makeText(ExpenseAddActivity.this, "Please check Internet Connection", Toast.LENGTH_LONG);
                    }

                }else {
                    noPictureDialog();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startExpensesActivity();
    }

    public void submitPicture(){

        if(isNetworkAvailable()){

            LocationManager service = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            gpsEnabled = service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!gpsEnabled){
                buildAlertMessageNoGps();
            }else{

                progress = new ProgressDialog(this);
                progress.setMessage("loading...");
                progress.show();
                progress.setCanceledOnTouchOutside(false);

                final String userId = session.getUserID();
                final String userParentPath = session.getUserParentPath();
                String expImageStatus;
                String expImageAvailable;

                String expIsMeetingAssociated = "no";
                String expMeetingId = "0";

                String expTitle = et_expense_title.getText().toString();
                String expAmount = et_expense_amount.getText().toString();
                String expDescription = et_expense_description.getText().toString();
                UUID uniqueKey = UUID.randomUUID();
                String mtnPictureImageName = uniqueKey.toString();

                if(photo_bitmap!=null) {
                    image = getStringImage(photo_bitmap);
                    expImageStatus = "true";
                    expImageAvailable = "yes";
                    presenter.addExpenseService(UrlUtil.ADD_EXPENSE_URL, userId, userParentPath, mtnPictureImageName, expTitle, expAmount, expDescription, expImageStatus, expImageAvailable, image, expIsMeetingAssociated, expMeetingId);
                }else{
                    image = "NA";
                    expImageStatus = "false";
                    expImageAvailable = "no";
                    presenter.addExpenseService(UrlUtil.ADD_EXPENSE_URL, userId, userParentPath, mtnPictureImageName, expTitle, expAmount, expDescription, expImageStatus, expImageAvailable, image, expIsMeetingAssociated, expMeetingId);
                }

            }


        }else{
            Toast.makeText(this,"Internet Connection not Available", Toast.LENGTH_LONG).show();
        }



    }

    public String getStringImage(Bitmap bmp){

        int actualHeight = bmp.getHeight();
        int actualWidth = bmp.getWidth();

        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }

        int inSampleSize = calculateInSampleSize(bmp, actualWidth, actualHeight);

        actualHeight=actualHeight/inSampleSize;
        actualWidth=actualWidth/inSampleSize;
        bmp=Bitmap.createScaledBitmap(bmp, actualWidth,actualHeight , true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static int calculateInSampleSize(Bitmap bmp, int reqWidth, int reqHeight) {
        final int height = bmp.getHeight();
        final int width = bmp.getWidth();
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private void noPictureDialog(){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("Do you want to submit expense without Image")
                .setTitle("Expense")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        submitPicture();

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

    public void startExpensesActivity() {
        new ActivityUtil(this).startExpensesActivity();
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if(options[which].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                }
                else if(options[which].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
                else if(options[which].equals("Cancel"))
                {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    public void onActivityResult(int requestcode,int resultcode,Intent intent)
    {
        super.onActivityResult(requestcode, resultcode, intent);
        if(resultcode==this.RESULT_OK)
        {
            if(requestcode==TAKE_PICTURE)
            {
                photo_bitmap = (Bitmap)intent.getExtras().get("data");
                Drawable drawable=new BitmapDrawable(photo_bitmap);
                iv_expense_image.setBackgroundDrawable(drawable);
            }
            else if(requestcode==ACTIVITY_SELECT_IMAGE)
            {
                Uri selectedImage = intent.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                photo_bitmap = (BitmapFactory.decodeFile(picturePath));
                Drawable drawable=new BitmapDrawable(photo_bitmap);
                iv_expense_image.setBackgroundDrawable(drawable);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(this, "The app was not allowed to read to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }

            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(cameraIntent, TAKE_PICTURE);
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(this, "The app was not allowed to take photo from camera. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
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
        startExpensesActivity();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
                case R.id.activity_expense_add_et_title:
                    validateExpenseTitle();
                    break;
                case R.id.activity_expense_add_et_amount:
                    validateExpenseAmount();
                    break;
                case R.id.activity_expense_add_et_description:
                    validateExpenseDescription();
                    break;
            }
        }
    }


    private boolean validateExpenseTitle() {
        if (et_expense_title.getText().toString().trim().isEmpty()) {
            til_expense_title.setError("expense title can not be blank");
            requestFocus(et_expense_title);
            return false;
        } else {
            til_expense_title.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateExpenseAmount() {
        if (et_expense_amount.getText().toString().trim().isEmpty()) {
            til_expense_amount.setError("expense amount can not be blank");
            requestFocus(et_expense_amount);
            return false;
        } else {
            til_expense_amount.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateExpenseDescription() {
        if (et_expense_description.getText().toString().trim().isEmpty()) {
            til_expense_description.setError("expense description can not be blank");
            requestFocus(et_expense_description);
            return false;
        } else {
            til_expense_description.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
