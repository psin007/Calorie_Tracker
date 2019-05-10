package com.example.mycalorietracker;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Register extends AppCompatActivity {
    DatePickerDialog picker;
    EditText eText;
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
//        eText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
//            }
//        });

    }

    public void registerationValues(View view){
        EditText edfirstName = (EditText)findViewById(R.id.firstName);
        final String firstName = edfirstName.getText().toString();
        int flag = 0;
        if (firstName.trim().length()==0){
            edfirstName.setError("First Name can not be empty!");
            flag = 1;
        }
        EditText edSurname = (EditText)findViewById(R.id.surName);
        final String surname = edSurname.getText().toString();
        if (surname.trim().length()==0){
            edfirstName.setError("Surname can not be empty!");
            flag = 1;
        }
        EditText edemail = (EditText)findViewById(R.id.email);
        final String email = edSurname.getText().toString();
        if (email.trim().length()==0){
            edemail.setError("Surname can not be empty!");
            flag = 1;
        }
        EditText edusername = (EditText)findViewById(R.id.username);
        if (edusername.getText().length()==0){
            edemail.setError("Email can not be empty!");
            flag = 1;
        }
        final String username = edusername.getText().toString();

        EditText edpassword = (EditText)findViewById(R.id.password);
        if (edpassword.getText().length()==0){
            edpassword.setError("password can not be empty!");
            flag = 1;
        }
        final String password = edpassword.getText().toString();

        EditText dateOfBirth = (EditText)findViewById(R.id.dob);
        SimpleDateFormat sdf = new SimpleDateFormat("dd=mm-yyyy");
        try{
            java.util.Date utilDate = sdf.parse(dateOfBirth.getText().toString());
            java.sql.Date birthDate = new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText edheight = (EditText)findViewById(R.id.height);
        if(edheight.getText().length() == 0){
            edheight.setError("Height can not be empty");
            flag = 1;
        }
        final double height = Double.parseDouble(edheight.getText().toString());

        EditText edweight = (EditText)findViewById(R.id.weight);
        if(edweight.getText().length() == 0){
            edheight.setError("Height can not be empty");
            flag = 1;
        }
        final double weight = Double.parseDouble(edheight.getText().toString());

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioSex);
        int sexButtonId = rg.getCheckedRadioButtonId();
        RadioButton sexradioButton = (RadioButton)findViewById(sexButtonId);
        char sexRadioButtonText = sexradioButton.getText().toString().charAt(0);

        Spinner postcodeSpinner = (Spinner)findViewById(R.id.postcode_spinner);
        String postcode = postcodeSpinner.getSelectedItem().toString();

        Spinner levelOfActivitySpinner = (Spinner)findViewById(R.id.levelOfActivity_spinner);
        int levelOfActivity = Integer.parseInt(levelOfActivitySpinner.getSelectedItem().toString());

        //addInCredentials();
        //To do: call addincredentials with paramereters; password needs to be hashed; current date needs to be known and
        //check if user already exists ir not
        //then add method in RestClient
    }
    public void addInCredentials(String username,Integer userid,String password){

    }

    public class Credential{
        private String username;
        private Integer userid;
        private String passwordhash;
        private Date signupdate;

        public Credential() {
        }

        public Credential(String username,Integer userid,String passwordhash,Date signupdate) {
            this.username = username;
            this.userid = userid;
            this.passwordhash = passwordhash;
            this.signupdate = signupdate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getPasswordhash() {
            return passwordhash;
        }

        public void setPasswordhash(String passwordhash) {
            this.passwordhash = passwordhash;
        }

        public Date getSignupdate() {
            return signupdate;
        }

        public void setSignupdate(Date signupdate) {
            this.signupdate = signupdate;
        }
    }


}
