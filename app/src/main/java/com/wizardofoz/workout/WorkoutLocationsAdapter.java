package com.wizardofoz.workout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class WorkoutLocationsAdapter extends ArrayAdapter<WorkoutLocation> {
    Context context;
    ArrayList<WorkoutLocation> data;
    int layoutResourceId;

    public WorkoutLocationsAdapter(Context context, int resource, ArrayList<WorkoutLocation> locations) {
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
        WorkoutLocationHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WorkoutLocationHolder();
            holder.img = (ImageView)row.findViewById(R.id.locationImg);
            holder.name = (TextView)row.findViewById(R.id.txtName);
            holder.description = (TextView)row.findViewById(R.id.txtDescription);


        } else {
            holder = (WorkoutLocationHolder)row.getTag();
        }

        row.setTag(holder);

        WorkoutLocation location = data.get(position);
        URL url = null;
        Bitmap image = null;
        try {
            if (location.getPicture() != null){
                url = new URL(Everlive.getEverlive().workWith().files().download(location.getPicture()).getDownloadPath());
                image = new GetImageAsync().execute(url).get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (image != null){
            holder.img.setImageBitmap(image);
        }

        holder.name.setText(location.getName());
        holder.description.setText(location.getDescription());

        return  row;
    }

    static class WorkoutLocationHolder{
        ImageView img;
        TextView name;
        TextView description;
    }

    class GetImageAsync extends AsyncTask<URL, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = null;
            Bitmap image = null;
            try {
                url = params[0];
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return image;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
