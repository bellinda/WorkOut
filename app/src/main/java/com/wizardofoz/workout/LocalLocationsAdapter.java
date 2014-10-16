package com.wizardofoz.workout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wizardofoz.workout.local.Location;

import java.util.ArrayList;

public class LocalLocationsAdapter extends ArrayAdapter<Location> {
    Context context;
    ArrayList<Location> data;
    int layoutResourceId;

    public LocalLocationsAdapter(Context context, int resource, ArrayList<Location> locations) {
        super(context, resource);
        this.context = context;
        this.data = locations;
        this.layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        if (data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LocationHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new LocationHolder();
            holder.img = (ImageView)row.findViewById(R.id.locationImg);
            holder.name = (TextView)row.findViewById(R.id.txtName);
            holder.description = (TextView)row.findViewById(R.id.txtDescription);


        } else {
            holder = (LocationHolder)row.getTag();
        }

        row.setTag(holder);

        Location location = data.get(position);
        Bitmap image = null;
        image = BitmapFactory.decodeByteArray(location.getImage(), 0, location.getImage().length);

        if (image != null){
            holder.img.setImageBitmap(image);
        }

        holder.name.setText(location.getName());
        holder.description.setText(location.getDescription());

        return  row;
    }

    static class LocationHolder{
        ImageView img;
        TextView name;
        TextView description;
    }
}
