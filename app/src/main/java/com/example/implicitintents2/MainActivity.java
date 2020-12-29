package com.example.implicitintents2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = "com.example.implicitintents2.MainActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText editTextWebsite;
    private EditText editTextLocation;
    private EditText editTextMessage;
    private EditText editTextPhone;
    private Button buttonWebsite;
    private Button buttonLocation;
    private Button buttonMessage;
    private Button buttonCall;
    private Button buttonCamera;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWebsite = findViewById(R.id.editText_website);
        editTextLocation = findViewById(R.id.editText_location);
        editTextMessage = findViewById(R.id.editText_message);
        editTextPhone = findViewById(R.id.editText_dial);
        buttonWebsite = findViewById(R.id.button_website);
        buttonLocation = findViewById(R.id.button_location);
        buttonMessage = findViewById(R.id.button_message);
        buttonCall = findViewById(R.id.button_dial);
        buttonCamera = findViewById(R.id.button_camera);
        imageView = findViewById(R.id.image_view);

        buttonWebsite.setOnClickListener(v -> onClickWebsiteButton());
        buttonLocation.setOnClickListener(v -> onClickLocationButton());
        buttonMessage.setOnClickListener(v -> onClickMessageButton());
        buttonCall.setOnClickListener(v -> onclickCallButton());
        buttonCamera.setOnClickListener(v -> onClickCameraButton());

    }

    public void onClickWebsiteButton() {
        Uri website = Uri.parse(editTextWebsite.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, website);
        startIntentActivity(intent);
    }

    public void onClickLocationButton() {
        Uri location = Uri.parse("geo:0,0?q=" + editTextLocation.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        startIntentActivity(intent);
    }

    public void onClickMessageButton() {
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setChooserTitle("Send this message with: ")
                .setText(editTextMessage.getText().toString())
                .startChooser();
    }

    public void onclickCallButton() {
        Uri phoneNumber = Uri.parse("tel:" + editTextPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_DIAL, phoneNumber);
        startIntentActivity(intent);
    }

    public void onClickCameraButton() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Log.d(LOG_TAG, "No camera activity found to handle this intent!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap)extras.get("data");
            imageView.setImageBitmap(image);
        }
    }

    private void startIntentActivity(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can not handle this intent!");
        }
    }
}