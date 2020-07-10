package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickupTime extends AppCompatActivity {

    @BindView(R.id.trip_id)
    TextView trip_id;
    @BindView(R.id.pickup_time)
    TextView pickup_time;
    @BindView(R.id.dropoff_time)
    TextView dropoff_time;
    VolleySingleton volleySingleton;

    String Pickup_URL = "http://192.168.100.26:3000/childPickup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_time);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        final ProgressDialog progressDialog = new ProgressDialog(PickupTime.this,
                R.style.MyDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest req = new StringRequest(Request.Method.GET, Pickup_URL,
                response -> {
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Pickup[] pickup = gson.fromJson(response, Pickup[].class);

                    if(pickup.length != 0){
                        if(pickup[0].getTripId() == null){

                        }else{
                            trip_id.setText(pickup[0].getTripId()+"");
                        }

                        pickup_time.setText(pickup[0].getChildPickupTime());
                        dropoff_time.setText(pickup[0].getChildDropOffTime());
                    }

                }, error -> {
            Log.d("Error: ", error.getMessage());

            progressDialog.dismiss();
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null) {
                if (error.networkResponse.statusCode == 204) {
//                    Toast.makeText(Customers.this, "No Data Assigned!", Toast.LENGTH_LONG).show();
                } else {

                }
            } else {

                Toast.makeText(PickupTime.this, "Check internet!", Toast.LENGTH_LONG).show();

            }
        });

//        VolleySingleton.getInstance(this).addToRequestQueue(req);
        volleySingleton.addToRequestQueue(req);
    }
}
