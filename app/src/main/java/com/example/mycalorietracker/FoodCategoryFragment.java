package com.example.mycalorietracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



public class FoodCategoryFragment extends Fragment {

    Spinner foodSpinner;
    EditText editQuanity;
    Food[] foods;
    int foodId = -1;
    int cId=-1;
    Button sendButton;
    Consumption consumption;
    TextView textViewAdd;
    EditText editFood;
    Button searchBtn;
    View viewCategory;
    TextView googleResult;
    ImageView googleImage;
    Button addDbBTn;
    TextView nutrientTv;
    Spinner categorySpinner;
    Food food;
    public FoodCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewCategory = inflater.inflate(R.layout.fragment_food_category, container, false);
        editQuanity = (EditText)(viewCategory.findViewById(R.id.quanityConsumed_editText));
        editFood = (EditText)(viewCategory.findViewById(R.id.ed_food_add));
        textViewAdd = (TextView)viewCategory.findViewById(R.id.food_add_tv);
        nutrientTv = (TextView)viewCategory.findViewById(R.id.tv_nutrient_result_tv);
        googleResult = (TextView)viewCategory.findViewById(R.id.tv_search_food);
        googleImage=(ImageView)viewCategory.findViewById(R.id.iv_search_image);

        searchBtn=(Button)viewCategory.findViewById(R.id.searchToAdd);
        addDbBTn=(Button)viewCategory.findViewById(R.id.add_food_db_btn);

        List<String> list = new ArrayList<String>();
        list.add("Spread");
        list.add("Meat");
        list.add("Sauce");
        list.add("Bread");
        list.add("Regional food");
        list.add("Regional Bread");
        list.add("Seafood");
        list.add("Vegetable");
        list.add("Appetizer");
        list.add("Sides");
        list.add("Cereal");
        list.add("Other");


        categorySpinner = (Spinner) viewCategory.findViewById(R.id.category_spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (getActivity(),android.R.layout.simple_spinner_item,list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter );

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("foodPooja",parent.getItemAtPosition(position).toString());
                GetFoodFromCategory getFoodFromCategory = new GetFoodFromCategory();
                getFoodFromCategory.execute(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        foodSpinner = (Spinner) viewCategory.findViewById(R.id.food_spinner);

        sendButton=(Button)viewCategory.findViewById(R.id.submit_quanity);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editQuanity.getText().toString().isEmpty()){
                    editQuanity.setError("Enter quantity");
                }
                else if(editQuanity.getText().toString().charAt(0)=='.'){
                    editQuanity.setError("Quanity cannot start from decimal");

                }
                else{
                    consumption = new Consumption();
                    SharedPreferences loggedinuser = viewCategory.getContext().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
                    int uid = Integer.parseInt(loggedinuser.getString("userid","0"));
                    Date cDate = new Date(System.currentTimeMillis());
                    if(foodId < 0){
                        editQuanity.setError("select some food");
                        return;
                    }
                    double cQuanity = Double.parseDouble(editQuanity.getText().toString());
                    consumption.setConsumptiondate(cDate);
                    Food food = new Food();
                    food.setFoodid(foodId);
                    consumption.setFoodid(food);
                    consumption.setQuantity(cQuanity);
                    Credential credential = new Credential();
                    credential.setUserid(uid);
                    consumption.setUserid(credential);
                    CountConsumptionRows countConsumptionRows= new CountConsumptionRows();
                    countConsumptionRows.execute();
                }
            }
        });
        textViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFood.setVisibility(View.VISIBLE);
                searchBtn.setVisibility(View.VISIBLE);
                googleResult.setVisibility(View.VISIBLE);
                googleImage.setVisibility(View.VISIBLE);
                nutrientTv.setVisibility(View.VISIBLE);
                editFood.requestFocus();


            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(editFood.getText().toString().isEmpty())) {
                    googleResult.setText("");
                    nutrientTv.setText("");
                    String foodToSearch = editFood.getText().toString();
                    SearchGoogleSnippet searchSnippet = new SearchGoogleSnippet();
                    searchSnippet.execute(foodToSearch);
                    DownloadImage dimage = new DownloadImage(googleImage);
                    dimage.execute(foodToSearch);
                    new GetNDBNumber().execute(foodToSearch);

                }
                else{
                    editFood.setError("Enter food to search");
                }

            }
        });
        addDbBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountFoodRows countFoodRows = new CountFoodRows();
                countFoodRows.execute();
            }
        });
        return viewCategory;

    }


    class CountConsumptionRows extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... strings) {

            return RestClient.countConsumptionRows();
        }

        protected void onPostExecute(String result){
            cId = (Integer.parseInt(result)+1);
            consumption.setConsumptionid(cId);
            WriteToConsumption writeToConsumption = new WriteToConsumption();
            writeToConsumption.execute(consumption);

        }
    }


    class WriteToConsumption extends AsyncTask<Consumption,Void,String>{

        @Override
        protected String doInBackground(Consumption... consumptions) {
            ReportServer.createConsumption(consumptions[0]);
            return "Consumption added in database";
            //    public static String calBurnLevelRest(String userid) {
        }

        protected void onPostExecute(String result){
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
        }
    }

    //get food from category selected
    class GetFoodFromCategory extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return RestClient.getFoodByCategory(params[0].toLowerCase());

        }

        protected void onPostExecute(String result){
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
                foods = new Food[jsonarray.length()];
                List<String>foodsList = new ArrayList<>();
                for(int i =0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    foods[i]= new Food(jsonobject.getInt("foodid"),jsonobject.getString("name"),
                            jsonobject.getString("category"),jsonobject.getDouble("calorieamount"),jsonobject.getString("servingunit"),
                            jsonobject.getDouble("servingamount"),jsonobject.getDouble("fat"));
                    foodsList.add(foods[i].getName());
                }
                final ArrayAdapter<String> foodAdapter = new ArrayAdapter<String> (getActivity(),android.R.layout.simple_spinner_item,foodsList);
                foodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                foodSpinner.setAdapter(foodAdapter);
                foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        editQuanity.setHint(foods[position].getServingamount()+" "+foods[position].getServingunit());
                        foodId=(foods[position].getFoodid());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
           // Log.d("donePooja",result);
        }
    }

    private class SearchGoogleSnippet extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.searchResult(params[0],new String[]{"num"},new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {

            googleResult.setText(SearchGoogleAPI.getFormattedSnippet(result));
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImage(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getActivity(), "Please wait, it may take sometime...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... params) {
            String result = SearchGoogleAPI.searchResult(params[0], new String[]{"num", "searchType"}, new String[]{"1", "image"});
            String imageLink = SearchGoogleAPI.getImageLink(result);
            Bitmap image = null;
            try {
                InputStream in = new java.net.URL(imageLink).openStream();
                image = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {

            imageView.setImageBitmap(result);
        }
    }

    class GetNDBNumber extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NutritionAPI.getNDBNO(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonobject = new JSONObject(result);

                JSONArray jsonArray= jsonobject.getJSONObject("list").getJSONArray("item");
                String nbdno=jsonArray.getJSONObject(0).getString("ndbno");
                GetNutrientData getNutrientData = new GetNutrientData();
                getNutrientData.execute(nbdno);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    class GetNutrientData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return NutritionAPI.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            paresResult(result);
            addDbBTn.setVisibility(View.VISIBLE);
            Log.d("poojaJSon food nutrient",result);
        }
    }

    private void paresResult(String result) {
        double fat;
        double calories;
        String servingUnit;
        double servingAmount;
        String category;
        String foodName;
        String fatFact="";
        String calFact="";
        try {
            food = new Food();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
            for(int i = 0;i<jsonArray.length();i++){
                String nutrientId = jsonArray.getJSONObject(i).getString("nutrient_id");
                if(nutrientId.equals("204")){
                    fat = Double.parseDouble(jsonArray.getJSONObject(i) .getString("value"));
                    JSONArray measuresArray = jsonArray.getJSONObject(i).getJSONArray("measures");
                    servingAmount = measuresArray.getJSONObject(0).getDouble("qty");
                    servingUnit = measuresArray.getJSONObject(0).getString("label");
                    food.setFat(fat);
                    food.setServingamount(servingAmount);
                    food.setServingunit(servingUnit);
                    fatFact = "\nFat - "+fat;

                }
                if(nutrientId.equals("208")){
                    calories = Double.parseDouble(jsonArray.getJSONObject(i) .getString("value"));
                    food.setCalorieamount(calories);
                    calFact = "\nCalories -"+calories;
                }
            }
            String NutrientFact = "Nutrient facts:"+fatFact+calFact;

            category = categorySpinner.getSelectedItem().toString();
            food.setCategory(category.toLowerCase());
            foodName =  editFood.getText().toString();
            food.setName(foodName);
            //set nutrientTv values
            nutrientTv.setText(NutrientFact);

        }

        catch (JSONException e) {
            e.printStackTrace();
        }


    }
    class CountFoodRows extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... strings) {

            return RestClient.countFoodRows();
        }

        protected void onPostExecute(String result){
            int fId = (Integer.parseInt(result)+1);
            food.setFoodid(fId);
            WriteToFood writeToFood = new WriteToFood();
            writeToFood.execute(food);

        }
    }
    class WriteToFood extends AsyncTask<Food,Void,String>{
        String category;
        @Override
        protected String doInBackground(Food... foods) {
            category=foods[0].getCategory();
            ReportServer.createFood(foods[0]);
            return "Food added in database";
            //    public static String calBurnLevelRest(String userid) {
        }

        protected void onPostExecute(String result){
            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            //103+104 line number
            GetFoodFromCategory getFoodFromCategory = new GetFoodFromCategory();
            getFoodFromCategory.execute(category);
        }
    }

}
