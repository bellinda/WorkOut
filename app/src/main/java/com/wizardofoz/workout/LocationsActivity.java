package com.wizardofoz.workout;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.telerik.everlive.sdk.core.EverliveApp;
import com.telerik.everlive.sdk.core.result.RequestResult;
import com.telerik.everlive.sdk.core.result.RequestResultCallbackAction;
import com.wizardofoz.workout.local.Location;
import com.wizardofoz.workout.local.LocationSQLiteHelper;
import com.wizardofoz.workout.local.LocationsDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LocationsActivity extends Activity {

    private EverliveApp app;
    private ListView locationsList;
    private ArrayList<WorkoutLocation> mLocations;
    private ArrayList<Location> mLocalLocations;
    private Context context;
    private WorkoutLocationsAdapter mAdapter;
    private LocalLocationsAdapter mLocalAdapter;

    private LocationsDataSource mDataSource;

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);


        initialize();
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
        context = this;
        mDataSource = new LocationsDataSource(context);
        boolean hasNetworkConnection = isOnline();
        if (!hasNetworkConnection){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mDataSource.open();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    mLocalLocations = mDataSource.getAllLocations();
                    mLocalAdapter = new LocalLocationsAdapter(context, R.layout.locations_listview_item, mLocalLocations);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Locations loaded!", Toast.LENGTH_LONG).show();

                            locationsList = (ListView) findViewById(R.id.listView);

                            locationsList.setAdapter(mLocalAdapter);
                        }
                    });

                    mDataSource.close();
                }
            }).start();

        } else {
            app = Everlive.getEverlive();
            mLocations = null;
            app.workWith().data(WorkoutLocation.class)
                    .get()
                    .executeAsync(new RequestResultCallbackAction<ArrayList<WorkoutLocation>>() {
                        @Override
                        public void invoke(RequestResult<ArrayList<WorkoutLocation>> requestResult) {
                            if (requestResult.getSuccess()) {
                                mLocations = requestResult.getValue();
                                mAdapter = new WorkoutLocationsAdapter(LocationsActivity.this, R.layout.locations_listview_item, mLocations);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Locations loaded!", Toast.LENGTH_LONG).show();

                                        locationsList = (ListView)findViewById(R.id.listView);

                                        locationsList.setAdapter(mAdapter);
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Couldn't load the locations!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
