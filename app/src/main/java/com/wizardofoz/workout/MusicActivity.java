package com.wizardofoz.workout;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by W510 on 17.10.2014 г..
 */
public class MusicActivity extends ActionBarActivity implements View.OnClickListener {
    private final Context context = this;

    Button btnPlayMusic;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //registerForContextMenu((ListView)findViewById(R.id.listView));
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        btnPlayMusic = (Button)this.findViewById(R.id.btn_music);

        btnPlayMusic.setOnClickListener(this);

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
        if(id == btnPlayMusic.getId()){
            if(mediaPlayer.isPlaying() == false) {
                mediaPlayer.start();
                btnPlayMusic.setText("Turn the music OFF");
                Toast.makeText(this, "Background music started", Toast.LENGTH_SHORT).show();
            } else {
                mediaPlayer.stop();
                btnPlayMusic.setText("Turn the music ON");
                Toast.makeText(this, "Background music stopped", Toast.LENGTH_SHORT).show();
                mediaPlayer = MediaPlayer.create(this, R.raw.song);
            }
        }
    }

    private void initializeActivity(){
        btnPlayMusic = (Button)this.findViewById(R.id.btn_music);

        btnPlayMusic.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
    }
}
