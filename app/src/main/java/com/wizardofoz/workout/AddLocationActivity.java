package com.wizardofoz.workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.query.definition.FileField;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;


public class AddLocationActivity extends ActionBarActivity implements View.OnClickListener{

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String MEDIA_MOUNTED = "mounted";

    Context context;
    TextView name;
    EditText description;
    Bitmap photo;
    Button getLocationImage;
    Button addLocation;

    Activity activity = this;
    EverliveApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        this.initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        app = Everlive.getEverlive();

        context = this;
        name = (TextView)this.findViewById(R.id.addName);
        description = (EditText)this.findViewById(R.id.addDescription);
        getLocationImage = (Button)this.findViewById(R.id.btnTakePicture);
        addLocation = (Button)this.findViewById(R.id.btnAddLocation);

        getLocationImage.setOnClickListener(this);
        addLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getLocationImage.getId()){
            this.takePicture();
        } else {
            this.submitLocation();
        }
    }

    private void submitLocation() {
        final WorkoutLocation newLocation = new WorkoutLocation();
        final String locName = name.getText().toString();
        final String locDescription = description.getText().toString();
        if (locName.equals("")){
            Toast.makeText(context, "Invalid name!", Toast.LENGTH_LONG).show();
            return;
        }

        if (locDescription.equals("")){
            Toast.makeText(context, "Invalid description!", Toast.LENGTH_LONG).show();
            return;
        }

        if (photo == null){
            Toast.makeText(context, "Invalid photo!", Toast.LENGTH_LONG).show();
            return;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        final byte[] bitmapData = bos.toByteArray();
        final ByteArrayInputStream bs = new ByteArrayInputStream(bitmapData);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadFile(app, locName, "image/jpeg", bs);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UUID id = UUID.nameUUIDFromBytes(locName.getBytes());
                        newLocation.setName(locName);
                        newLocation.setDescription(locDescription);
                        newLocation.setPicture(id);
                        app.workWith().data(WorkoutLocation.class).create(newLocation).executeAsync(new RequestResultCallbackAction<ArrayList<WorkoutLocation>>() {
                            @Override
                            public void invoke(RequestResult<ArrayList<WorkoutLocation>> requestResult) {
                                if (requestResult.getSuccess()) {
                                    navigateToLocations();
                                } else {
                                    Toast.makeText(context, "Couldn't load the locations!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void UploadFile(EverliveApp app, String fileName, String contentType, InputStream inputStream) {
        FileField fileField = new FileField(fileName, contentType, inputStream);
        app.workWith().files().upload(fileField).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Bundle extras = data.getExtras();
                photo = (Bitmap)extras.get("data");
                Toast.makeText(context, "Picture taken!", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                Toast.makeText(context, "Unable to take picture, please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navigateToLocations() {
        Intent intent = new Intent(AddLocationActivity.this, LocationsActivity.class);
        startActivity(intent);
    }

    private void takePicture() {
        //TODO: Take the picture, load it to the image file, upload to Everlive.
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        this.startActivityForResult(camera, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
}
