package com.wizardofoz.workout.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wizardofoz.workout.R;

/**
 * Created by W510 on 17.10.2014 г..
 */
public class MusicFragment extends Fragment {
    public MusicFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_music, container, false);

        return rootView;
    }
}
