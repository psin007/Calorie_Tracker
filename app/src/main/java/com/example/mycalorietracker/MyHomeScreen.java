package com.example.mycalorietracker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MyHomeScreen  extends Fragment {
    View vDisplayUnit;
    private TextView tvwelcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayUnit = inflater.inflate(R.layout.myhomescreen, container, false);
        welcomeMessage();
        return vDisplayUnit;
    }

    public void welcomeMessage(){
        tvwelcome = (TextView)vDisplayUnit.findViewById(R.id.tv_welcomeMsg);
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        String uname = loggedinuser.getString("username",null);
        //int ulevelofactivity = Integer.parseInt(loggedinuser.getString("levelofactivity",null));//just checking
        tvwelcome.setText("Welcome "+uname);
    }

}
