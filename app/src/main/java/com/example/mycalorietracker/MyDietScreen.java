package com.example.mycalorietracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyDietScreen  extends Fragment {
    View vEnterUnit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vEnterUnit = inflater.inflate(R.layout.mydietscreen, container, false);

        return vEnterUnit;
    }

}
