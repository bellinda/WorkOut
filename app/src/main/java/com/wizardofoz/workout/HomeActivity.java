package com.wizardofoz.workout;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    private final Context context = this;
    Button btnShowLocations;
    Button btnAddLocation;
    Button btnCheckPlaces;
    Button btnPlayMusic;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.initializeActivity();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnShowLocations.getId()){
            Intent intent = new Intent(HomeActivity.this, LocationsActivity.class);
            startActivity(intent);
        } else if(id == btnAddLocation.getId()){
            Intent intent = new Intent(HomeActivity.this, AddLocationActivity.class);
            startActivity(intent);
        } else if(id == btnCheckPlaces.getId()){
            Intent intent = new Intent(HomeActivity.this, CheckForNearPlacesActivity.class);
            startActivity(intent);
        } else if(id == btnPlayMusic.getId()){
            if(mediaPlayer.isPlaying() == false) {
                mediaPlayer.start();
                btnPlayMusic.setText("Turn the music OFF");
                Toast.makeText(this, "Background music started", Toast.LENGTH_SHORT).show();
            } else {
                mediaPlayer.stop();
                btnPlayMusic.setText("Turn the music ON");
                Toast.makeText(this, "Background music stopped", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeActivity(){
        btnShowLocations = (Button)this.findViewById(R.id.btn_locations);
        btnAddLocation = (Button)this.findViewById(R.id.btn_addLcn);
        btnCheckPlaces = (Button)this.findViewById(R.id.btn_checkPlc);
        btnPlayMusic = (Button)this.findViewById(R.id.btn_music);

        btnShowLocations.setOnClickListener(this);
        btnAddLocation.setOnClickListener(this);
        btnCheckPlaces.setOnClickListener(this);
        btnPlayMusic.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
    }
}
