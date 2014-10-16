package com.wizardofoz.workout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
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


public class LocationsActivity extends Activity implements View.OnTouchListener {

    private EverliveApp app;
    private ListView locationsList;
    private ArrayList<WorkoutLocation> mLocations;
    private ArrayList<Location> mLocalLocations;
    private Context context;
    private WorkoutLocationsAdapter mAdapter;
    private LocalLocationsAdapter mLocalAdapter;
    //private RelativeLayout container;

    private LocationsDataSource mDataSource;

    private float oldDistance = 0f;
    //private float textSize = 12f;

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
//        container = (RelativeLayout)findViewById(R.id.container);
//        locationsList.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//        ZoomView zoomView = new ZoomView(LocationsActivity.this);
//        zoomView.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//        zoomView.addView(locationsList);
//        container.addView(zoomView);

        locationsList = (ListView)findViewById(R.id.listView);
        locationsList.setOnTouchListener(this);
        locationsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> p, View v,final int position, long id) {
                //final ListView locationslist = (ListView) findViewById(R.id.listView);
                final AlertDialog.Builder alert = new AlertDialog.Builder(LocationsActivity.this);
                //final int pos = position;

                alert.setTitle("Geolocation");
                //validation
                if(mLocations.get(position).getLocation() != null) {
                    alert.setMessage("Longitude: " + mLocations.get(position).getLocation().getLongitude() + "\n" + "Latitude: " + mLocations.get(position).getLocation().getLatitude());
                } else{
                    alert.setMessage("The GPS coordinates of the location are not available");
                }

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        alert.setCancelable(true);
                    }
                });
//                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int whichButton) {
//                        }
//                    });
                alert.show();
                return true;
            }
        });



        initialize();
    }

//    protected boolean onLongListItemClick(View v, int pos, long id) {
//        Log.i("Tapped", "onLongListItemClick id=" + id);
//        //Intent intent = new Intent(LocationsActivity.this, LocationInfoActivity.class);
//        return true;
//    }


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

    @Override
    public boolean onTouch(View v, MotionEvent event){
        if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE){
            if(event.getPointerCount() == 2){
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                float newDistance = FloatMath.sqrt(x*x + y*y);
                oldDistance = newDistance;
            }
        }
        return true;
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
