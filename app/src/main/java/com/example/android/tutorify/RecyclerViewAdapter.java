package com.example.android.tutorify;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prateek Raina on 22-10-2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "RecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static ArrayList<DataObject> myObject;
    //private static MyClickListener myClickListener;

    public RecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        this.mDataset = myDataset;
        myObject = this.mDataset;
        System.out.println("This is myObject : " + myObject.get(0).getFIRST_NAME() + " " + myObject.get(0).getLAST_NAME() + "\n");
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{ //implements View.OnClickListener {
        TextView name;
        TextView location;
        TextView contactOne;
        TextView classes;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.displayName);
            image = (ImageView) itemView.findViewById(R.id.displayImage);
            location = (TextView) itemView.findViewById(R.id.displayLocation);
            contactOne = (TextView) itemView.findViewById(R.id.displayContactOne);
            classes = (TextView) itemView.findViewById(R.id.displayClass);

            Log.i(LOG_TAG, "Adding Listener");
            //itemView.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String jsonString = new Gson().toJson(myObject.get(position));
                    //Toast.makeText(v.getContext(), "This is JsonString : " + jsonString, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(),DetailActivity.class);
                    intent.putExtra("object",jsonString);
                    v.getContext().startActivity(intent);
                }
            });

        }

        /*@Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }*/
    }

    /*public void setOnItemClickListener(MyClickListener myClickListener) {
        RecyclerViewAdapter.myClickListener = myClickListener;
    }*/



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_card, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        Context context = holder.image.getContext();
        Picasso.with(context).load(mDataset.get(position).getIMAGE()).into(holder.image);
        String temp1 = mDataset.get(position).getFIRST_NAME();
        String temp2 = mDataset.get(position).getLAST_NAME();
        temp1 = "Name : " + temp1 + " " + temp2;
        holder.name.setText(temp1);
        temp1 = "Location : " + mDataset.get(position).getLOCATION();
        holder.location.setText(temp1);
        temp1 = "Contact : " + mDataset.get(position).getCONTACT_ONE();
        holder.contactOne.setText(temp1);
        temp1 =mDataset.get(position).getCLASS_FROM();
        temp2 =mDataset.get(position).getCLASS_UPTO();
        temp1 = "Teaching : " + temp1 + " to " + temp2;
        holder.classes.setText(temp1);
        System.out.println("On Bind View Holder : " + temp1);
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }



    /*public interface MyClickListener {
        void onItemClick(int position, View v);
    }*/
}