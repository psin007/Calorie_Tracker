package com.example.mycalorietracker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostToServer {
    int countReportRows;
    double calBurnedALevel;
    double calBurnedPerStep;
    double calConsumed;
    Report reportnew = new Report();
    int flag=4;
    int totalSteps;
    Date reportDate;
    double uDailyGoal;
    StepsRoomDatabase db =null;

    public void addInReport(Context context){
        db = Room.databaseBuilder(context,StepsRoomDatabase.class, "StepsRoomDatabase").fallbackToDestructiveMigration().build();
        //userId,date,totalCalConsumed,totalCalBurned,totalSteps,setGoal,reportId
        //userid from sharedPrefernce-done
        //date currentdate-done
        //totalCalConsumed- consumptionfacde,mathodname-findCalConsumed - webservices
        //totalCalBurned -users facade-steps*caloriesBurnedPerStep+calBurnedAtRest -(calBuredA/Level+steps*calBurnedPerStep)webservices
        //setGoal - sharedpreference-done
        //reportId- count+1 - webservices-done
        GetAllSteps getAllSteps = new GetAllSteps();
        getAllSteps.execute();

        SharedPreferences loggedinuser = context.getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        int uid = Integer.parseInt(loggedinuser.getString("userid","0"));
        uDailyGoal = Integer.parseInt(loggedinuser.getString("goalValue","0"));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd ");
        reportDate = new Date(System.currentTimeMillis());
        Credential credential = new Credential();
        credential.setUserid(uid);
        reportnew.setUserid(credential);

        CountRows countRows = new CountRows();
        countRows.execute();
        CalBurnedLevelBMR calBurned = new CalBurnedLevelBMR();
        calBurned.execute(String.valueOf(uid));
        calBurnedPerStepUser calBurnedPerStep = new calBurnedPerStepUser();
        calBurnedPerStep.execute(String.valueOf(uid));
        findCalConsumed findCalConsumedd = new findCalConsumed();
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        findCalConsumedd.execute(String.valueOf(uid),today);
    }

    public void afterAsync(){
        reportnew.setReportid(countReportRows+1);
        reportnew.setTotalcaloriesconsumed(calConsumed);
        reportnew.setTotalcaloriesburned(totalSteps*calBurnedPerStep);
        reportnew.setReportdate(reportDate);
        reportnew.setTotalsteps(totalSteps);
        reportnew.setSetgoal(uDailyGoal);
        WriteToServer writeToServer = new WriteToServer();
        writeToServer.execute(reportnew);
        DeleteDatabase deleteDatabase = new DeleteDatabase();
        deleteDatabase.execute();
    }

    class CountRows extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... strings) {
            return ReportServer.countRows();
        }

        protected void onPostExecute(String result){
            countReportRows = Integer.parseInt(result);
            flag--;
            if(flag==0){
                afterAsync();
            }
        }
    }
    //calBurnedAToLevel - REST server
    class CalBurnedLevelBMR extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.calBurnLevelRest(strings[0]);
            //    public static String calBurnLevelRest(String userid) {
        }

        protected void onPostExecute(String result){
            calBurnedALevel = Double.parseDouble(result);
            flag--;
            if(flag==0){
                afterAsync();
            }
        }
    }
//
    class calBurnedPerStepUser extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.calBurnedPerStepRest(strings[0]);
            //    public static String calBurnedPerStepRest(String uid) {
        }

        protected void onPostExecute(String result) {
            calBurnedPerStep = Double.parseDouble(result);
            flag--;
            if (flag == 0) {
                afterAsync();
            }
        }
    }
    //findCalConsumed-Consumption facade
    class findCalConsumed extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.findCalConsumed(strings[0],strings[1]);
            //    public static String findCalConsumed(String uid,String cdate) {
        }

        protected void onPostExecute(String result) {
            calConsumed = Double.parseDouble(result);
            flag--;
            if (flag == 0) {
                afterAsync();
            }
        }
    }

    class WriteToServer extends AsyncTask<Report,Void,String>{

        @Override
        protected String doInBackground(Report... reports) {
           ReportServer.createReport(reports[0]);
           return "Report Added";
            //    public static String calBurnLevelRest(String userid) {
        }

        protected void onPostExecute(String result){
            Log.d("donePooja",result);
        }
    }

    class GetAllSteps extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            int totalStepsTaken=0;
            List<StepsRoom> stepsRoomList =db.StepsRoomDao().getAll();
            if(!(stepsRoomList.isEmpty() || stepsRoomList==null)){
                for(StepsRoom stepsRoom:stepsRoomList){
                    totalStepsTaken += stepsRoom.getStepsTaken();
                }

            }
            return(String.valueOf(totalStepsTaken));
        }
        @Override
        protected void onPostExecute(String steps) {
            totalSteps = Integer.parseInt(steps);
        }

    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            db.StepsRoomDao().deleteAll();
            return null;
        }
        protected void onPostExecute(Void param) {
        }
    }
}





