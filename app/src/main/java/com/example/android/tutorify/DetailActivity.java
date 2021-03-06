package com.example.android.tutorify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;

    TextView nameTV;
    TextView addressTV;
    TextView contactOneTV;
    TextView contactTwoTV;
    TextView educationTV;
    TextView mediumTV;
    TextView classesTV;
    TextView descriptionTV;
    TextView subjectsTV;

    DataObject dataObject;
    DataObject myDataObject;
    String change;
    String JsonString;

    String firstName;
    String lastName;
    String address;
    String image;
    String contactOne;
    String contactTwo;
    String education;
    String medium;
    String classFrom;
    String classUpto;
    String description;
    String subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String temp;

        imageView = (ImageView) findViewById(R.id.displayImageDetail);

        nameTV = (TextView) findViewById(R.id.displayNameDetail);
        addressTV = (TextView) findViewById(R.id.displayAddressDetail);
        contactOneTV = (TextView) findViewById(R.id.displayContactOneDetail);
        contactTwoTV = (TextView) findViewById(R.id.displayContactTwoDetail);
        educationTV = (TextView) findViewById(R.id.displayEducationDetail);
        mediumTV = (TextView) findViewById(R.id.displayMediumDetail);
        classesTV = (TextView) findViewById(R.id.displayClassesDetail);
        descriptionTV = (TextView) findViewById(R.id.displayDescriptionDetail);
        subjectsTV = (TextView) findViewById(R.id.displaySubjectsDetail);

        dataObject = new DataObject();
        change = new Gson().toJson(dataObject);
        JsonString = getIntent().getExtras().getString("object",change);
        myDataObject = new Gson().fromJson(JsonString,DataObject.class);

        firstName = myDataObject.getFIRST_NAME();
        lastName = myDataObject.getLAST_NAME();
        address = myDataObject.getADDRESS();
        image = myDataObject.getIMAGE();
        contactOne = myDataObject.getCONTACT_ONE();
        contactTwo = myDataObject.getCONTACT_TWO();
        education = myDataObject.getEDUCATION();
        medium = myDataObject.getMEDIUM();
        classFrom = myDataObject.getCLASS_FROM();
        classUpto = myDataObject.getCLASS_UPTO();
        description = myDataObject.getDESCRIPTION();
        subjects = myDataObject.getSUBJECTS();

        Picasso.with(this).load(image).into(imageView);

        temp = firstName + " " + lastName;
        nameTV.setText(temp);
        addressTV.setText(address);
        contactOneTV.setText(contactOne);
        contactTwoTV.setText(contactTwo);
        educationTV.setText(education);
        mediumTV.setText(medium);
        temp = classFrom + " to " + classUpto;
        classesTV.setText(temp);
        descriptionTV.setText(description);
        subjectsTV.setText(subjects);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.search_button:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
