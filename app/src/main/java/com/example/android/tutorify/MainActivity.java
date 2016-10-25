package com.example.android.tutorify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

    private EditText editTextId;
    private Button buttonGet;
    private TextView textViewResult;
    private ImageView imageView;

    private ProgressDialog loading;

    private ArrayList data = new ArrayList<DataObject>();

    String getClass;
    String getSubject;
    String getLocation;


    String FIRST_NAME = "";
    String LAST_NAME = "";
    String KEY_IMAGE = "";
    String KEY_LOCATION = "";
    String CONTACT_ONE = "";
    String CONTACT_TWO = "";
    String KEY_EDUCATION = "";
    String KEY_MEDIUM = "";
    String KEY_DESCRIPTION = "";
    String CLASS_FROM = "";
    String CLASS_UPTO = "";

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

        getData();

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

        if (id == R.id.search_button) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.search_dialog_box);
            dialog.show();

            final EditText enterClassEditText = (EditText) dialog.findViewById(R.id.enterClassEditText);
            final EditText enterSubjectEditText = (EditText) dialog.findViewById(R.id.enterSubjectEditText);
            final EditText enterLocationEditText = (EditText) dialog.findViewById(R.id.enterLocationEditText);
            Button dialogInputButton = (Button) dialog.findViewById(R.id.submitDialogInputButton);

            dialogInputButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getClass = enterClassEditText.getText().toString();
                    getSubject = enterSubjectEditText.getText().toString();
                    getLocation = enterLocationEditText.getText().toString();

                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(), getClass + " " + getSubject + " " + getLocation, Toast.LENGTH_LONG).show();

                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    //This is where the JSON response is received by making a call to the server
    private void getData() {
//        String id = editTextId.getText().toString().trim();
//        if (id.equals("")) {
//            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
//            return;
//        }
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
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

//                Toast.makeText(getApplicationContext(), "Name:\t" + dataObject.getCONTACT_ONE() + " " + dataObject.getCONTACT_TWO() +
//                        "\nLocation:\t" + dataObject.getLOCATION() + "\nEducation:\t" + dataObject.getEDUCATION(), Toast.LENGTH_LONG).show();

                data.add(i,dataObject);

                /*
                FIRST_NAME = jsonObject.getString(Config.FIRST_NAME);
                LAST_NAME = jsonObject.getString(Config.LAST_NAME);
                KEY_IMAGE = jsonObject.getString(Config.KEY_IMAGE);
                KEY_LOCATION = jsonObject.getString(Config.KEY_LOCATION);
                CONTACT_ONE = jsonObject.getString(Config.CONTACT_ONE);
                CONTACT_TWO = jsonObject.getString(Config.CONTACT_TWO);
                KEY_EDUCATION = jsonObject.getString(Config.KEY_EDUCATION);
                KEY_MEDIUM = jsonObject.getString(Config.KEY_MEDIUM);
                KEY_DESCRIPTION = jsonObject.getString(Config.KEY_DESCRIPTION);
                CLASS_FROM = jsonObject.getString(Config.CLASS_FROM);
                CLASS_UPTO = jsonObject.getString(Config.CLASS_UPTO);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Toast.makeText(getApplicationContext(), "Name:\t" + FIRST_NAME + " " + LAST_NAME + "\nLocation:\t" + KEY_LOCATION + "\nEducation:\t" + KEY_EDUCATION, Toast.LENGTH_LONG).show();

        /*textViewResult.setText("Name:\t" + FIRST_NAME + " " + LAST_NAME + "\nLocation:\t" + KEY_LOCATION + "\nEducation:\t" + KEY_EDUCATION);
        Picasso.with(this).load(Config.IMAGE_URL + KEY_IMAGE).into(imageView);*/

        mAdapter = new RecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

    }


//    @Override
//    public void onClick(View v) {
//        getData();
//    }
}
