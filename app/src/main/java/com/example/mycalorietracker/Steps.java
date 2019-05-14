package com.example.mycalorietracker;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Steps  extends Fragment {
    View vSteps;
    private int positionSelected;
    Button updateButton;
    Button addButton;
    Button btnForce;
    StepsRoomDatabase db =null;
    EditText editText = null;
    List<HashMap<String, String>> stepsAndTime;
    SimpleAdapter myListAdapter;
    ListView stepsList;
    String[] colHEAD = new String[] {"Time","Steps"};
    int[] dataCell = new int[] {R.id.timeAdded,R.id.no_Steps};
    String timeSelectedInList="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vSteps = inflater.inflate(R.layout.steps, container, false);

        updateButton = (Button)vSteps.findViewById(R.id.button_update);
        addButton = (Button)vSteps.findViewById(R.id.btn_add);
        btnForce = (Button)vSteps.findViewById(R.id.button_force);
        stepsList = (ListView)vSteps.findViewById(R.id.list_view_steps);
        stepsAndTime = new ArrayList<HashMap<String, String>>();
        myListAdapter = new SimpleAdapter(vSteps.getContext(),stepsAndTime,R.layout.list_view,colHEAD,dataCell);
        stepsList.setAdapter(myListAdapter);
        db = Room.databaseBuilder(getActivity(),StepsRoomDatabase.class, "StepsRoomDatabase").fallbackToDestructiveMigration().build();
        final Button addButton = (Button)vSteps.findViewById(R.id.btn_add);
        editText = (EditText)vSteps.findViewById(R.id.stepsEntered);
        Button forceButton = (Button)vSteps.findViewById(R.id.button_force);
        //when button to add step is clicked
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                InsertSteps insertSteps = new InsertSteps();
                insertSteps.execute();
            }
        });
        ReadDatabase readDatabase = new ReadDatabase();
        readDatabase.execute();
        stepsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object eachStep = stepsList.getItemAtPosition(position);
                HashMap<String,String>temp = (HashMap<String,String>)eachStep;
                String timeEntered = temp.get("Time");
                String stepsEntered = temp.get("Steps");
                timeSelectedInList=timeEntered;
                updateButton.setVisibility(Button.VISIBLE);
                addButton.setVisibility(Button.GONE);
                btnForce.setVisibility(Button.GONE);
                editText.setText(stepsEntered);
                positionSelected=position;
                timeSelectedInList=timeEntered;
            }
        });
        //when update button is clicked
        updateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                UpdateSteps updateSteps = new UpdateSteps();
                updateSteps.execute();
            }
        });
        //when user presses force button
//        btnForce.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//               deleteFromLocalDb();
//            }
//        });



        return vSteps;
    }
    private class InsertSteps extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String numSteps="0";
            if(!(editText.getText().toString().isEmpty())){
                numSteps = editText.getText().toString();
            }
            //getting current time in String format
            String date = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
            StepsRoom stepsEx = new StepsRoom(date,Integer.parseInt(numSteps));
            db.StepsRoomDao().insert(stepsEx);
            return(numSteps+" on "+date);
        }
        @Override
        protected void onPostExecute(String details) {
            Toast.makeText(vSteps.getContext(),details+" added",Toast.LENGTH_SHORT).show();
            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.execute();


        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            stepsAndTime.clear();
            List<StepsRoom> steps = db.StepsRoomDao().getAll();
            if (!(steps.isEmpty() || steps == null) ){
                for(StepsRoom temp:steps){
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("Time",temp.getTimeStamp());
                    map.put("Steps",String.valueOf(temp.getStepsTaken()));
                    stepsAndTime.add(map);
                }
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void v) {
            myListAdapter = new SimpleAdapter(vSteps.getContext(),stepsAndTime,R.layout.list_view,colHEAD,dataCell);
            stepsList.setAdapter(myListAdapter);
        }
    }

    private class UpdateSteps extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            StepsRoom step = null;
            editText = (EditText) vSteps.findViewById(R.id.stepsEntered);
            if (!(editText.getText().toString().equals(""))) {
                int newSteps = Integer.parseInt(editText.getText().toString());
                int id = db.StepsRoomDao().findId(timeSelectedInList);
                step = db.StepsRoomDao().findByID(id);
                step.setStepsTaken(newSteps);
                db.StepsRoomDao().updateSteps(step);
                return String.valueOf(newSteps);
            }
            else{
                return "";
            }

        }
        @Override
        protected void onPostExecute(String step) {
            Toast.makeText(vSteps.getContext(),"updated to " + step,Toast.LENGTH_SHORT).show();
            ReadDatabase readDatabase = new ReadDatabase();
            readDatabase.execute();
            updateButton.setVisibility(Button.GONE);
            addButton.setVisibility(Button.VISIBLE);
            btnForce.setVisibility(Button.VISIBLE);
        }
    }


}


