package com.wizardofoz.workout;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;

import java.util.ArrayList;

/**
 * Created by W510 on 16.10.2014 г..
 */
public class CheckForNearPlacesActivity extends Activity {
    private EverliveApp app;
    private ListView locationsList;
    private ArrayList<WorkoutLocation> mLocations;
    private Context context;
    private WorkoutLocationsAdapter mAdapter;
    private NotificationManager notifManager;

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_for_places);

        initialize();
    }

    private void initialize() {
        GPSTracker gpsTracker = new GPSTracker(this);
        final double currentLat = gpsTracker.getLatitude();
        final double currentLong = gpsTracker.getLongitude();
        context = this;
        app = Everlive.getEverlive();
        mLocations = null;
        app.workWith().data(WorkoutLocation.class)
                .get()
                .executeAsync(new RequestResultCallbackAction<ArrayList<WorkoutLocation>>() {
                    @Override
                    public void invoke(RequestResult<ArrayList<WorkoutLocation>> requestResult) {
                        if (requestResult.getSuccess()) {
                            mLocations = requestResult.getValue();
                            ArrayList<WorkoutLocation> nearLocations = new ArrayList<WorkoutLocation>();
                            for(WorkoutLocation loc: mLocations){
                                if(loc.getLocation() != null && Math.floor(loc.getLocation().getLatitude()) == Math.floor(currentLat) && Math.floor(loc.getLocation().getLongitude()) == Math.floor(currentLong)){
                                    nearLocations.add(loc);
                                }
                            }
                            if(nearLocations.isEmpty()){
                                notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notify=new Notification(android.R.drawable.
                                        stat_notify_more,"Warning: No Connection!",System.currentTimeMillis());
                                PendingIntent pending=PendingIntent.getActivity(
                                        getApplicationContext(),0, new Intent(),0);
                                notify.setLatestEventInfo(getApplicationContext(),"No connection","You should turn on either your GPS or your WiFi",pending);
                                notifManager.notify(0, notify);
//                                Toast.makeText(context, "Couldn't load the locations!", Toast.LENGTH_LONG).show();
//                                Looper.prepare();
                            } else {
                                mAdapter = new WorkoutLocationsAdapter(CheckForNearPlacesActivity.this, R.layout.locations_listview_item, nearLocations);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Locations loaded!", Toast.LENGTH_LONG).show();

                                        locationsList = (ListView) findViewById(R.id.listView);

                                        locationsList.setAdapter(mAdapter);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(context, "Couldn't load the locations!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
}
