package com.example.mycalorietracker;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CalorieTrackerScreen  extends Fragment {
    View vCalorieTracker;
    TextView tv_goalset_value;
    TextView tv_total_steps_value;
    TextView tv_cal_burned_value;
    TextView tv_cal_consumed_value;
    int totalSteps = -1;
    int totalGoal = -1;
    double totalCalBurnedPerStep=-1;
    StepsRoomDatabase db = null;
    int uid=-1;
    double calBurnedPerStep=-1;
    double calBurnedALevel=-1;
    double totalBurnCalories=-1;
    double calConsumed=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vCalorieTracker = inflater.inflate(R.layout.calorietrackerscreen, container, false);
        tv_goalset_value = (TextView) vCalorieTracker.findViewById(R.id.tv_goalset_value);
        tv_total_steps_value = (TextView) vCalorieTracker.findViewById(R.id.tv_total_steps_value);
        tv_cal_burned_value = (TextView) vCalorieTracker.findViewById(R.id.tv_cal_burned_value);
        tv_cal_consumed_value = (TextView) vCalorieTracker.findViewById(R.id.tv_cal_consumed_value);
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        uid = Integer.parseInt(loggedinuser.getString("userid","0"));
        totalGoal = Integer.parseInt(loggedinuser.getString("goalValue", "0"));
        if (totalGoal >= 0) {
            tv_goalset_value.setText(String.valueOf(totalGoal));
        }
        db = Room.databaseBuilder(getActivity(), StepsRoomDatabase.class, "StepsRoomDatabase").fallbackToDestructiveMigration().build();
        GetAllSteps getAllSteps = new GetAllSteps();
        getAllSteps.execute();
        if (totalSteps >= 0) {
            tv_goalset_value.setText(String.valueOf(totalSteps));
        }
//        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
//        uid = Integer.parseInt(loggedinuser.getString("userid","0"));
//        totalGoal = Integer.parseInt(loggedinuser.getString("goalValue", "0"));
//        if (totalGoal >= 0) {
//            tv_goalset_value.setText(String.valueOf(totalGoal));
//        }


        return vCalorieTracker;
    }

    class GetAllSteps extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            int totalStepsTaken = 0;
            List<StepsRoom> stepsRoomList = db.StepsRoomDao().getAll();
            if (!(stepsRoomList.isEmpty() || stepsRoomList == null)) {
                for (StepsRoom stepsRoom : stepsRoomList) {
                    totalStepsTaken += stepsRoom.getStepsTaken();
                }

            }
            return (String.valueOf(totalStepsTaken));
        }

        @Override
        protected void onPostExecute(String steps) {
            int tSteps = Integer.parseInt(steps);
            setSteps(tSteps);
            CalBurnedPerStepUser calBurnedPerStepUser= new CalBurnedPerStepUser();
            calBurnedPerStepUser.execute(String.valueOf(uid));
        }

    }

    public void setSteps(int tSteps) {
        tv_total_steps_value.setText(String.valueOf(tSteps));
    }

    class CalBurnedPerStepUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.calBurnedPerStepRest(strings[0]);
        }

        protected void onPostExecute(String result) {
            Log.d("poojaCalperStep",result.toString());
            calBurnedPerStep = Double.parseDouble(result);
            CalBurnedLevelBMR calBurnedLevelBMR = new CalBurnedLevelBMR();
            calBurnedLevelBMR.execute(String.valueOf(uid));
        }
    }
    class CalBurnedLevelBMR extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.calBurnLevelRest(strings[0]);
        }

        protected void onPostExecute(String result){
             calBurnedALevel= Double.parseDouble(result);
            calTotalBurn();
            FindCalConsumed findCalConsumed = new FindCalConsumed();
            String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            findCalConsumed.execute(String.valueOf(uid),today);        }
    }

    private void calTotalBurn() {
        totalBurnCalories = (totalSteps*calBurnedPerStep)+calBurnedALevel;
        if(totalBurnCalories>=0){
            tv_cal_burned_value.setText(String.format("%.2f",totalBurnCalories));
        }
    }

    class FindCalConsumed extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.findCalConsumed(strings[0],strings[1]);
        }

        protected void onPostExecute(String result) {
            calConsumed = Double.parseDouble(result);
            if(calConsumed >=0){
                tv_cal_consumed_value.setText(String.format("%.2f",calConsumed));
            }
        }
    }
}




