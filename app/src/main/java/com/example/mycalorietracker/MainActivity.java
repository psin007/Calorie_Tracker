package com.example.mycalorietracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private CheckBox checkbox;
    private android.widget.EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void register(View view) {
        TextView txtvw = (TextView) findViewById(R.id.register);

        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);

    }

    public void showPassword(View view) {
        checkbox = (CheckBox) findViewById(R.id.showpassword);
        edtPassword = (EditText) findViewById(R.id.edit_password);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void login(View view) {
        Button signin = (Button) findViewById(R.id.Login);
        EditText edttxt = (EditText) findViewById(R.id.edit_username);
        final String username1 = edttxt.getText().toString();
        EditText edit = (EditText) findViewById(R.id.edit_password);
        final String passsword1 = edit.getText().toString();
        final String passHash = hashCreator(passsword1);
        Log.e("passHash", passHash);

        checkcredential checkcre = new checkcredential();
        checkcre.execute(new String[]{username1, passHash});


    }

    public String hashCreator(String password) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private class checkcredential extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String passwordhash = params[1];
            return RestClient.checkusercredentials(username, passwordhash);
        }

        @Override
        protected void onPostExecute(String match) {
            try {
                if (!(match.equals("[]"))) {
                    JSONArray jsonarray = new JSONArray(match);
                    JSONObject jsonobject = jsonarray.getJSONObject(0);
                    //to do sharedpreferenaces here
                    SharedPreferences loggedinuser = getApplicationContext().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
                    String loggeduserName = jsonobject.getJSONObject("users").getString("name");
                    String loggedid = jsonobject.getString("userid");
                    String loggedheight = jsonobject.getJSONObject("users").getString("height");
                    String loggedweight = jsonobject.getJSONObject("users").getString("weight");
                    String loggedgender = jsonobject.getJSONObject("users").getString("gender");
                    String loggedaddress = jsonobject.getJSONObject("users").getString("address");
                    String loggedpostcode = jsonobject.getJSONObject("users").getString("postcode");
                    String loggedlevelofactivity = jsonobject.getJSONObject("users").getString("levelofactivity");
                    String loggedstepsPerMile = jsonobject.getJSONObject("users").getString("stepspermile");
                    SharedPreferences.Editor loggedUsered = loggedinuser.edit();
                    loggedUsered.putString("username",loggeduserName);
                    loggedUsered.putString("userid",loggedid);
                    loggedUsered.putString("height",loggedheight);
                    loggedUsered.putString("weight",loggedweight);
                    loggedUsered.putString("gender",loggedgender);
                    loggedUsered.putString("address",loggedaddress);
                    loggedUsered.putString("postcode",loggedpostcode);
                    loggedUsered.putString("levelofactivity",loggedlevelofactivity);
                    loggedUsered.putString("stepspermile",loggedstepsPerMile);
                    loggedUsered.apply();
                    Intent intent = new Intent(MainActivity.this, NavDrawer.class);
                    startActivity(intent);
                } else {
                    EditText editText = (EditText) findViewById(R.id.edit_username);
                    editText.setError("Either username or password is wrong!");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
