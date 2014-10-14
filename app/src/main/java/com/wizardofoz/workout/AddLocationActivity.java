package com.wizardofoz.workout;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddLocationActivity extends ActionBarActivity implements View.OnClickListener{

    Context context;
    TextView name;
    EditText description;
    Image locationImage;
    Button getLocationImage;
    Button addLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        this.initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_location, menu);
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
    }

    private void takePicture() {
    }
}
