package com.example.android.tutorify;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog loading;

    private ArrayList<DataObject> data = new ArrayList<DataObject>();

    String subjectQuery;
    String classQuery;
    String locationQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new RecyclerViewAdapter(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);

        if (checkInternetConnection()){
            getData();
            //The adapter for this condition is set in showJson method
        }
        else{
            mAdapter = new RecyclerViewAdapter(data);
            mRecyclerView.setAdapter(mAdapter);

            String message = "No Internet Connection!";
            displayNetworkSnackbar(MainActivity.this,message);

        }
        //getData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (checkInternetConnection()) { //Search will be clickable only if the internet is working else not

            if (id == R.id.search_button) {

                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);


            }
        }

        return super.onOptionsItemSelected(item);
    }


    //This is where the JSON response is received by making a call to the server
    private void getData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = Config.DATA_URL + "1"; //+ id;

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
                        //Log.e(TAG,error.getMessage().toString());
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //This is where the JSON response is Parsed
    private void showJSON(String response) {

        data.clear();

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray result = jsonObj.getJSONArray(Config.JSON_ARRAY);
            for(int i=0;i<result.length();i++) {
                JSONObject jsonObject = result.getJSONObject(i);
                DataObject dataObject = new DataObject();

                dataObject.setFIRST_NAME(jsonObject.getString(Config.FIRST_NAME));
                dataObject.setLAST_NAME(jsonObject.optString(Config.LAST_NAME));
                dataObject.setIMAGE(Config.IMAGE_URL + jsonObject.getString(Config.KEY_IMAGE));
                dataObject.setLOCATION(jsonObject.getString(Config.KEY_LOCATION));
                dataObject.setCONTACT_ONE(jsonObject.getString(Config.CONTACT_ONE));
                dataObject.setCONTACT_TWO(jsonObject.getString(Config.CONTACT_TWO));
                dataObject.setEDUCATION(jsonObject.getString(Config.KEY_EDUCATION));
                dataObject.setMEDIUM(jsonObject.getString(Config.KEY_MEDIUM));
                dataObject.setDESCRIPTION(jsonObject.getString(Config.KEY_DESCRIPTION));
                dataObject.setCLASS_FROM(jsonObject.getString(Config.CLASS_FROM));
                dataObject.setCLASS_UPTO(jsonObject.getString(Config.CLASS_UPTO));

                data.add(i,dataObject);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(data.size()==0){

            String message = "No matching results found!";
            displaySnackbar(MainActivity.this,message);

        }

        mAdapter = new RecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(intent.getStringExtra("Search").equals("Research"))
        {
            subjectQuery = intent.getStringExtra("Subject");
            classQuery = intent.getStringExtra("Class");
            locationQuery = intent.getStringExtra("Location");
            locationQuery = locationQuery.replaceAll(" ", "%20");

            getSearchData();
        }
    }

    //This is where the JSON response is received by making a call to the server
    private void getSearchData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

        String url = Config.GET_SEARCH_RESULTS_URL + "subject=" + subjectQuery + "&class=" + classQuery + "&location=" + locationQuery;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Log.d(TAG, "Response received :"+response);
                System.out.println(TAG + "response is : " + response);
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e(TAG,error.getMessage().toString());
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //This method displays the Snackbar
    public void displaySnackbar(Activity activity, String message) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        }).show();
    }

    //This method displays the Snackbar
    public void displayNetworkSnackbar(Activity activity, String message) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).show();

    }

    //This method checks if there is an internet connection or not!
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
