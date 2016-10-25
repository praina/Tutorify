package com.example.android.tutorify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;

    TextView nameTV;
    TextView locationTV;
    TextView contactOneTV;
    TextView contactTwoTV;
    TextView educationTV;
    TextView mediumTV;
    TextView classesTV;
    TextView descriptionTV;

    DataObject dataObject;
    DataObject myDataObject;
    String change;
    String JsonString;

    String firstName;
    String lastName;
    String location;
    String image;
    String contactOne;
    String contactTwo;
    String education;
    String medium;
    String classFrom;
    String classUpto;
    String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String temp;

        imageView = (ImageView) findViewById(R.id.displayImageDetail);

        nameTV = (TextView) findViewById(R.id.displayNameDetail);
        locationTV = (TextView) findViewById(R.id.displayLocationDetail);
        contactOneTV = (TextView) findViewById(R.id.displayContactOneDetail);
        contactTwoTV = (TextView) findViewById(R.id.displayContactTwoDetail);
        educationTV = (TextView) findViewById(R.id.displayEducationDetail);
        mediumTV = (TextView) findViewById(R.id.displayMediumDetail);
        classesTV = (TextView) findViewById(R.id.displayClassesDetail);
        descriptionTV = (TextView) findViewById(R.id.displayDescriptionDetail);

        dataObject = new DataObject();
        change = new Gson().toJson(dataObject);
        JsonString = getIntent().getExtras().getString("object",change);
        myDataObject = new Gson().fromJson(JsonString,DataObject.class);

        firstName = myDataObject.getFIRST_NAME();
        lastName = myDataObject.getLAST_NAME();
        location = myDataObject.getLOCATION();
        image = myDataObject.getIMAGE();
        contactOne = myDataObject.getCONTACT_ONE();
        contactTwo = myDataObject.getCONTACT_TWO();
        education = myDataObject.getEDUCATION();
        medium = myDataObject.getMEDIUM();
        classFrom = myDataObject.getCLASS_FROM();
        classUpto = myDataObject.getCLASS_UPTO();
        description = myDataObject.getDESCRIPTION();

        Picasso.with(this).load(image).into(imageView);

        temp = firstName + " " + lastName;
        nameTV.setText(temp);
        locationTV.setText(location);
        contactOneTV.setText(contactOne);
        contactTwoTV.setText(contactTwo);
        educationTV.setText(education);
        mediumTV.setText(medium);
        temp = classFrom + " to " + classUpto;
        classesTV.setText(temp);
        descriptionTV.setText(description);

    }

}
