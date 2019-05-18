package com.example.mycalorietracker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyHomeScreen  extends Fragment {
    View vHomeScreen;
    private TextView tvwelcome;
    private TextView tvdate;
    private EditText editGoal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vHomeScreen = inflater.inflate(R.layout.myhomescreen, container, false);
        welcomeMessage();
        showgoal();
        //when user updates Goal
        Button clearButton=(Button)vHomeScreen.findViewById(R.id.edit_goal);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editGoal = (EditText)vHomeScreen.findViewById(R.id.edgoal);
                String goalUpdated = editGoal.getText().toString();
                SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
                SharedPreferences.Editor loggedUsered = loggedinuser.edit();
                loggedUsered.putString("goalValue",goalUpdated);
                loggedUsered.apply();
                Toast.makeText(vHomeScreen.getContext(),"Goal updated",Toast.LENGTH_SHORT).show();
                showgoal();
            }
        });
        return vHomeScreen;
    }

    //to welcome user on dashboard and show current date and time
    public void welcomeMessage(){
        tvwelcome = (TextView)vHomeScreen.findViewById(R.id.tv_welcomeMsg);
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        String uname = loggedinuser.getString("username",null);
        //int ulevelofactivity = Integer.parseInt(loggedinuser.getString("levelofactivity",null));//just checking
        tvwelcome.setText("Welcome "+uname);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        tvdate = (TextView)vHomeScreen.findViewById(R.id.tv_currentdateandtime);
        tvdate.setText("Current date and time: " +date);
    }
    //show goal to dashboard
    public void showgoal(){
        editGoal = (EditText)vHomeScreen.findViewById(R.id.edgoal);
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        String goalVal = loggedinuser.getString("goalValue","0");
        editGoal.setText(goalVal);
    }




}
