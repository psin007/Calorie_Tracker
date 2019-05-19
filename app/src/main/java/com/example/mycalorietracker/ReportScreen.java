package com.example.mycalorietracker;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportScreen extends Fragment {
    View vReport;
    EditText editPieDate;
    DatePickerDialog picker;
    Button submit;
    RadioButton chartButtonId;
    EditText ed_input_date_start_bar;
    float totalCaloriesBurned=-1;
    float totalCalConsumed=-1;
    float remainingCalories = -1;
    TextView tv_label_Date_start_bar;
    EditText ed_input_date_end_bar;
    TextView tv_label_Date_end_bar;
    TextView tv_label_Date_pie;
    Button btn_submit_bar;
    BarChart barChart;
    List<String>reporDates;
    List<Float>calConsumed;
    List<Float>calBurned;

    StepsRoomDatabase db = null;
    int uid=-1;
    private float[] yData;
    private String[] xData={"remainingCalPercent","totalCaloriesBurnedPercent","calConsumedPercent"};
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vReport = inflater.inflate(R.layout.report, container, false);
        tv_label_Date_start_bar =(TextView)vReport.findViewById(R.id.label_Date_start_bar);
        tv_label_Date_end_bar =(TextView)vReport.findViewById(R.id.label_Date_end_bar);
        ed_input_date_end_bar=(EditText)vReport.findViewById(R.id.input_date_end_bar);
        ed_input_date_start_bar=(EditText)vReport.findViewById(R.id.input_date_start_bar);
        tv_label_Date_pie = (TextView)vReport.findViewById(R.id.label_Date_pie);
        btn_submit_bar = (Button)vReport.findViewById(R.id.submit_bar);
        editPieDate=(EditText) vReport.findViewById(R.id.input_date_pie);
        pieChart = (PieChart) vReport.findViewById(R.id.idPieChart);
        barChart = (BarChart)vReport.findViewById(R.id.barchart);

        pieChart.setRotationEnabled(true);

        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Goal report");
        pieChart.setCenterTextSize(10);


        btn_submit_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pooja oclick","");
                reporDates = new ArrayList<>();
                calConsumed = new ArrayList<>();
                calBurned = new ArrayList<>();
                SharedPreferences loggedinuser = vReport.getContext().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
                int uid = Integer.parseInt(loggedinuser.getString("userid","0"));
                GetReportData getReportData = new GetReportData();
                getReportData.execute(String.valueOf(uid),ed_input_date_start_bar.getText().toString(),ed_input_date_end_bar.getText().toString());
            }
        });
        RadioGroup rg = (RadioGroup) vReport.findViewById(R.id.radio_charts);

            editPieDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 addDate(editPieDate);
                }
            });
        ed_input_date_end_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate(ed_input_date_end_bar);
            }
        });
        ed_input_date_start_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDate(ed_input_date_start_bar);
            }
        });
            submit = (Button) vReport.findViewById(R.id.submit_chart);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editPieDate.getText().toString().isEmpty()) {
                        editPieDate.setError("Enter a date");
                    } else {
                        getDataValues();
                    }

                }
            });
       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==R.id.radioPie){
                   tv_label_Date_start_bar.setVisibility(View.GONE);
                   tv_label_Date_end_bar.setVisibility(View.GONE);
                   ed_input_date_end_bar.setVisibility(View.GONE);
                   ed_input_date_start_bar.setVisibility(View.GONE);
                   tv_label_Date_pie.setVisibility(View.VISIBLE);
                   editPieDate.setVisibility(View.VISIBLE);
                   submit.setVisibility(View.VISIBLE);
                   btn_submit_bar.setVisibility(View.GONE);
                   pieChart.setVisibility(View.VISIBLE);
                   barChart.setVisibility(View.GONE);

               }
               else{
                   tv_label_Date_start_bar.setVisibility(View.VISIBLE);
                   tv_label_Date_end_bar.setVisibility(View.VISIBLE);
                   ed_input_date_end_bar.setVisibility(View.VISIBLE);
                   ed_input_date_start_bar.setVisibility(View.VISIBLE);
                   tv_label_Date_pie.setVisibility(View.GONE);
                   editPieDate.setVisibility(View.GONE);
                   submit.setVisibility(View.GONE);
                   btn_submit_bar.setVisibility(View.VISIBLE);
                   pieChart.setVisibility(View.GONE);
                   barChart.setVisibility(View.VISIBLE);
               }

           }
       });

        return vReport;
    }
    public void addDate(final EditText etext)
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
    // date picker dialog
    picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        editPieDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            etext.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    }   , year, month, day);
                    picker.show();}
    public void getDataValues(){
        //calConsumed
        //CalBurnedAtrest
        //TotalNumberOfSteps
        //Goal
        //Remaining Calories
        Log.d("pooja","pooja");
        SharedPreferences loggedinuser = getActivity().getSharedPreferences("Loggeduser", Context.MODE_PRIVATE);
        uid = Integer.parseInt(loggedinuser.getString("userid","0"));
        db = Room.databaseBuilder(getActivity(), StepsRoomDatabase.class, "StepsRoomDatabase").fallbackToDestructiveMigration().build();
        String dateSelected =editPieDate.getText().toString();
        GetAllValuesReport getAllValuesReport = new GetAllValuesReport();
        getAllValuesReport.execute(String.valueOf(uid),dateSelected);

    }


    class GetAllValuesReport extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.getCalBurnedReport(strings[0],strings[1]);

        }

        protected void onPostExecute(String result){
            Log.d("resyltPooja",result);
            try {
                if(!result.isEmpty()) {

                    JSONArray jsonarray = new JSONArray(result);
                    Log.d("resyltPooja", String.valueOf(jsonarray));
                        JSONObject jsonObject = jsonarray.getJSONObject(0);
                        totalCalConsumed = Float.parseFloat(jsonObject.getString("total calories consumed"));
                        totalCaloriesBurned = Float.parseFloat(jsonObject.getString("total calories burned"));
                        remainingCalories = Float.parseFloat(jsonObject.getString("remaining calories"));
                        calculateY();

                }
                else{
                    Toast.makeText(getActivity(),"No data to show",Toast.LENGTH_SHORT).show();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void calculateY(){

          yData=new float[]{totalCalConsumed,totalCaloriesBurned,remainingCalories};
        addDataSet();
    }
    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , xData[i]));
        }

//        for(int i = 1; i < xData.length; i++){
//            xEntrys.add(xData[i]);
//        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    class GetReportData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ReportServer.getReportData(strings[0],strings[1],strings[2]);
        }

        protected void onPostExecute(String result) {
            Log.d("pooja result",result);

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    calBurned.add(Float.parseFloat(jsonObject.getString("totalcaloriesburned")));
                    calConsumed.add(Float.parseFloat(jsonObject.getString("totalcaloriesconsumed")));
                    reporDates.add(jsonObject.getString("reportdate"));
                    showBar();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void showBar(){


        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);

        xl.setValueFormatter(new IndexAxisValueFormatter(reporDates) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try{
                    return String.valueOf((int) value);                }
                catch(Exception e){
                    return "";
                }


            }


        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

        });

        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        barChart.getAxisRight().setEnabled(false);

        //data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset



        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();


        for (int i = 0; i < calConsumed.size(); i++) {
            yVals1.add(new BarEntry(i, calConsumed.get(i)));
        }

        for (int i = 0; i < calBurned.size(); i++) {
            yVals2.add(new BarEntry(i, calBurned.get(i)));
        }


        BarDataSet set1, set2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)barChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set1 = new BarDataSet(yVals1, "Calories consumed");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Calories burned");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }
}


