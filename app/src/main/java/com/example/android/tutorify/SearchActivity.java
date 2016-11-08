package com.example.android.tutorify;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Prateek Raina on 26-10-2016.
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private ProgressDialog loading;

    Button searchButton;

    ArrayList<String> uniqueLocations = new ArrayList<String>();
    ArrayList<String> uniqueSubjects = new ArrayList<String>();
    ArrayList<String> uniqueClasses = new ArrayList<String>();

    AppCompatSpinner locationSpinner;
    AppCompatSpinner classesSpinner;
    AppCompatSpinner subjectSpinner;

    ArrayAdapter<String> locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // If your minSdkVersion is 11 or higher use:
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        uniqueLocations = new ArrayList<String>();
//        uniqueSubjects = new ArrayList<String>();
//        uniqueClasses = new ArrayList<String>();

        searchButton = (Button) findViewById(R.id.submitDialogInputButton);

        locationSpinner = (AppCompatSpinner) findViewById(R.id.selectLocationSpinner);
        classesSpinner = (AppCompatSpinner) findViewById(R.id.selectClassSpinner);
        subjectSpinner = (AppCompatSpinner) findViewById(R.id.selectSubjectSpinner);

        uniqueSubjects.add("All Subjects");
        uniqueSubjects.add("Science");
        uniqueSubjects.add("Social Science");
        uniqueSubjects.add("English");
        uniqueSubjects.add("Sanskrit");
        uniqueSubjects.add("Computer Science");
        uniqueSubjects.add("Mathematics");
        uniqueSubjects.add("Economics");
        uniqueSubjects.add("Physics");
        uniqueSubjects.add("Chemistry");
        uniqueSubjects.add("Biology");
        uniqueSubjects.add("Business Studies");
        uniqueSubjects.add("Accountancy");

        Collections.sort(uniqueSubjects, String.CASE_INSENSITIVE_ORDER);

        uniqueClasses.add(0,"1");
        uniqueClasses.add(1,"2");
        uniqueClasses.add(2,"3");
        uniqueClasses.add(3,"4");
        uniqueClasses.add(4,"5");
        uniqueClasses.add(5,"6");
        uniqueClasses.add(6,"7");
        uniqueClasses.add(7,"8");
        uniqueClasses.add(8,"9");
        uniqueClasses.add(9,"10");
        uniqueClasses.add(10,"11");
        uniqueClasses.add(11,"12");


        /*locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                if (item!=null) {
                    Toast.makeText(SearchActivity.this, item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SearchActivity.this, "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });*/


        if(checkInternetConnection()) {

            getFilterData();

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    intent.putExtra("Search", "Research");
                    intent.putExtra("Subject", subjectSpinner.getSelectedItem().toString());
                    intent.putExtra("Class", classesSpinner.getSelectedItem().toString());
                    intent.putExtra("Location", locationSpinner.getSelectedItem().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
        else{
            String message = "No Internet Connection!";
            displayNetworkSnackbar(SearchActivity.this,message);
        }

        locationAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, uniqueLocations);
        locationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, uniqueClasses);
        classesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        classesSpinner.setAdapter(classesAdapter);

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, uniqueSubjects);
        subjectAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //This is where the JSON response is received by making a call to the server
    private void getFilterData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = Config.GET_LOCATIONS_DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d(TAG, "Response received :"+response);
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(SearchActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        loading.hide();
                        loading.dismiss();
                        String message = "Unable to connect to the server!";
                        displayNetworkSnackbar(SearchActivity.this,message);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //This is where the JSON response is Parsed
    private void showJSON(String response) {


        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray result = jsonObj.getJSONArray(Config.JSON_ARRAY);
            System.out.println("THIS IS THE LENGTH OF RESULT : " + result.length());
            for(int i=0;i<result.length();i++) {
                JSONObject jsonObject = result.getJSONObject(i);

                uniqueLocations.add(i,jsonObject.getString(Config.KEY_LOCATION));

            }
            Collections.sort(uniqueLocations, String.CASE_INSENSITIVE_ORDER);
            locationAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //This method checks if there is an internet connection or not!
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //This method displays the Snackbar
    public void displayNetworkSnackbar(Activity activity, String message) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).show();

    }

}
