package com.example.mycalorietracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Register extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText;
    String firstName;
    String surname;
    String email;
    String username;
    String password;
    java.sql.Date birthDate;
    double height;
    double weight;
    String passwordhash;
    int uid;
    String dob;
    Date signupDate;
    Integer postCode;
    String address;
    char gender;
    int levelOfActivity;
    Double stepsPerMile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        List<String> list = new ArrayList<String>();
        list.add("3162");
        list.add("3152");
        list.add("3142");
        list.add("3132");
        list.add("3122");
        list.add("3112");
        final Spinner postcodes = (Spinner) findViewById(R.id.postcode_spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postcodes.setAdapter(spinnerAdapter );

        List<String> levelOfActivity = new ArrayList<String>();
        levelOfActivity.add("1");
        levelOfActivity.add("2");
        levelOfActivity.add("3");
        levelOfActivity.add("4");
        levelOfActivity.add("5");
        final Spinner levelActivities = (Spinner) findViewById(R.id.levelOfActivity_spinner);
        final ArrayAdapter<String> spinnerAdapters = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item,levelOfActivity);
        spinnerAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelActivities.setAdapter(spinnerAdapters);

    }
    public void addDob(View view){
        eText=(EditText) findViewById(R.id.dob);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });

    }



    public void registrationValues(View view){
        EditText edfirstName = (EditText)findViewById(R.id.firstName);
        if (edfirstName.getText().toString().isEmpty()){
            edfirstName.setError("First Name can not be empty!");
        }
        firstName = edfirstName.getText().toString();

        EditText edSurname = (EditText)findViewById(R.id.surName);
        if (edSurname.getText().toString().isEmpty()){
            edfirstName.setError("Surname can not be empty!");
        }
        surname = edSurname.getText().toString();

        EditText edemail = (EditText)findViewById(R.id.email);
        if (edemail.getText().toString().isEmpty()){
            edemail.setError("Surname can not be empty!");
        }
        email = edemail.getText().toString();

        EditText edusername = (EditText)findViewById(R.id.username);
        if (edusername.getText().toString().isEmpty()){
            edemail.setError("Email can not be empty!");
        }
        username = edusername.getText().toString();

        EditText edpassword = (EditText)findViewById(R.id.password);
        if (edpassword.getText().toString().isEmpty()){
            edpassword.setError("password can not be empty!");
        }
        password = edpassword.getText().toString();


        EditText edAddress = (EditText)findViewById(R.id.address);
        if(edAddress.getText().toString().isEmpty()){
            edAddress.setError("Address can not be empty");
        }
        address=edAddress.getText().toString();

        EditText stepsPerMileed = (EditText)findViewById(R.id.stepsPerMile);
        if(stepsPerMileed.getText().toString().isEmpty()){
            edAddress.setError("Steps per mile can not be empty");
        }
        stepsPerMile=Double.parseDouble(stepsPerMileed.getText().toString());

        if(eText.getText().toString().isEmpty()){
            eText.setError("Date of birth can not be empty");
        }
        dob=eText.getText().toString();

        EditText edheight = (EditText)findViewById(R.id.height);
        if(edheight.getText().toString().isEmpty()){
            edheight.setError("Height can not be empty");
        }
        height = Double.parseDouble(edheight.getText().toString());

        EditText edweight = (EditText)findViewById(R.id.weight);
        if(edweight.getText().toString().isEmpty()){
            edweight.setError("Weight can not be empty");
        }
        weight = Double.parseDouble(edweight.getText().toString());

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioSex);
        int sexButtonId = rg.getCheckedRadioButtonId();
        RadioButton sexRadioButton = (RadioButton) findViewById(sexButtonId);
        String sexRadioButtonText = sexRadioButton.getText().toString();
        gender = sexRadioButtonText.charAt(0);

        Spinner postCodeSpinner = (Spinner) findViewById(R.id.postcode_spinner);
        String postCodeStr = postCodeSpinner.getSelectedItem().toString();
        postCode = Integer.parseInt(postCodeStr);

        Spinner levelOfActivitySpinner = (Spinner) findViewById(R.id.levelOfActivity_spinner);
        levelOfActivity = Integer.parseInt(levelOfActivitySpinner.getSelectedItem().toString());

        MainActivity mainActivity = new MainActivity();
        passwordhash=mainActivity.hashCreator(password);

        CheckIfExist checkIfExist = new CheckIfExist();
        checkIfExist.execute(username);

    }

    private class CheckIfExist extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            return RestClient.checkUsername(username);
        }

        @Override
        protected void onPostExecute(String match) {
                if ((match.equals("[]"))) {
                        GetUserId getUserId = new GetUserId();
                        getUserId.execute();

                } else {
                    EditText editText = (EditText) findViewById(R.id.username);
                    editText.setError("Username already exists");
                }
        }
    }

    private class GetUserId extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... strings) {
            return RestClient.countRows();
        }

        @Override
        protected void onPostExecute(String result) {
             uid = Integer.parseInt(result)+1;
             beforeSendingToCredential();
        }
    }
    public void beforeSendingToCredential(){
        Credential credential = new Credential();
        credential.setUserid(uid);
        credential.setPasswordhash(passwordhash);
        credential.setUsername(username);
        //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd ");
        signupDate = new Date(System.currentTimeMillis());
        credential.setSignupdate(signupDate);
        AddToCred addToCred = new AddToCred();
        addToCred.execute(credential);
    }

    class AddToCred extends AsyncTask<Credential,Void,String>{

        @Override
        protected String doInBackground(Credential... credentials) {
            RestClient.createCredential(credentials[0]);
            return "Credential Added";
        }

        protected void onPostExecute(String result){
            beforeSendingToUsers();

        }
    }

    public void beforeSendingToUsers(){
        Credential credential = new Credential();
        credential.setUserid(uid);
        Users user = new Users();
        user.setCredential(credential);
        user.setAddress(address);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date = sdf.parse(dob);
            user.setDob(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setEmail(email);
        user.setGender(gender);
        user.setHeight(height);
        user.setLevelofactivity(levelOfActivity);
        user.setName(firstName);
        user.setStepspermile(stepsPerMile);
        user.setWeight(weight);
        user.setSurname(surname);
        user.setUserid(uid);
        user.setPostcode(postCode);
        AddToUsers addToUsers = new AddToUsers();
        addToUsers.execute(user);

    }

    class AddToUsers extends AsyncTask<Users,Void,String>{

        @Override
        protected String doInBackground(Users... users) {
            RestClient.createUser(users[0]);
            return "User Added";
        }

        protected void onPostExecute(String result){
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);        }
    }

}
