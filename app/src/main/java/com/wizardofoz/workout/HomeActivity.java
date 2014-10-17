package com.wizardofoz.workout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wizardofoz.workout.model.NavDrawerItem;
import com.wizardofoz.workout.model.NavDrawerListAdapter;

import java.util.ArrayList;

public class HomeActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Locations
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Add location
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Near places
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Music
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }


    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        LayoutInflater inflater;
        View view;
        Intent i;
        switch (position) {
            case 0:
                //fragment = new HomeFragment();
//                inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.main_page, null);
//                i = new Intent(this, MainPageActivity.class);
//                //i.setClass(HomeActivity.this, LocationsActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                //startActivity(i);
//                view.getContext().startActivity(i);
                break;
            case 1:
//                fragment = new LocationsFragment();
//                Intent intent = new Intent(HomeActivity.this, LocationsActivity.class);
//                startActivity(intent);

                inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_package_layout, null);
                i = new Intent(this, LocationsActivity.class);
                //i.setClass(HomeActivity.this, LocationsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(i);
                view.getContext().startActivity(i);
                break;
            case 2:
                //fragment = new AddLocationFragment();
                inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_add_location, null);
                i = new Intent(this, AddLocationActivity.class);
                //i.setClass(HomeActivity.this, LocationsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(i);
                view.getContext().startActivity(i);
                break;
            case 3:
                //fragment = new CheckForNearPlacesFragment();
                inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_add_location, null);
                i = new Intent(this, CheckForNearPlacesActivity.class);
                //i.setClass(HomeActivity.this, LocationsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(i);
                view.getContext().startActivity(i);
                break;
            case 4:
                //fragment = new MusicFragment();
                inflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_music, null);
                i = new Intent(this, MusicActivity.class);
                //i.setClass(HomeActivity.this, LocationsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(i);
                view.getContext().startActivity(i);
                break;
            default:
                break;
        }

//        if (fragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).commit();
//
//            // update selected item and title, then close the drawer
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            setTitle(navMenuTitles[position]);
//            mDrawerLayout.closeDrawer(mDrawerList);
//        } else {
//            // error in creating fragment
//            Log.e("MainActivity", "Error in creating fragment");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

//public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

//    private final Context context = this;
//    Button btnShowLocations;
//    Button btnAddLocation;
//    Button btnCheckPlaces;
//    Button btnPlayMusic;
//    MediaPlayer mediaPlayer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        //registerForContextMenu((ListView)findViewById(R.id.listView));
//
//
//
//        this.initializeActivity();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == btnShowLocations.getId()){
//            Intent intent = new Intent(HomeActivity.this, LocationsActivity.class);
//            startActivity(intent);
//        } else if(id == btnAddLocation.getId()){
//            Intent intent = new Intent(HomeActivity.this, AddLocationActivity.class);
//            startActivity(intent);
//        } else if(id == btnCheckPlaces.getId()){
//            Intent intent = new Intent(HomeActivity.this, CheckForNearPlacesActivity.class);
//            startActivity(intent);
//        } else if(id == btnPlayMusic.getId()){
//            if(mediaPlayer.isPlaying() == false) {
//                mediaPlayer.start();
//                btnPlayMusic.setText("Turn the music OFF");
//                Toast.makeText(this, "Background music started", Toast.LENGTH_SHORT).show();
//            } else {
//                mediaPlayer.stop();
//                btnPlayMusic.setText("Turn the music ON");
//                Toast.makeText(this, "Background music stopped", Toast.LENGTH_SHORT).show();
//                mediaPlayer = MediaPlayer.create(this, R.raw.song);
//            }
//        }
//    }
//
//    private void initializeActivity(){
//        btnShowLocations = (Button)this.findViewById(R.id.btn_locations);
//        btnAddLocation = (Button)this.findViewById(R.id.btn_addLcn);
//        btnCheckPlaces = (Button)this.findViewById(R.id.btn_checkPlc);
//        btnPlayMusic = (Button)this.findViewById(R.id.btn_music);
//
//        btnShowLocations.setOnClickListener(this);
//        btnAddLocation.setOnClickListener(this);
//        btnCheckPlaces.setOnClickListener(this);
//        btnPlayMusic.setOnClickListener(this);
//
//        mediaPlayer = MediaPlayer.create(this, R.raw.song);
//    }
//}
