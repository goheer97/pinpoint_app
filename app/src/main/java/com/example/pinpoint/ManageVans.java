package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ManageVans extends AppCompatActivity implements VanAdapter.OnItemClickListener {

    public static String Van_URL = "http://192.168.100.26:3000/van";

    RecyclerView customerList;
    Van[] vans;
    TextView count;
    Button btn_create;

    VolleySingleton volleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vans);
        customerList = findViewById(R.id.recycler_view);
        btn_create = findViewById(R.id.btn_create);
        customerList.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.numberActions);


        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(view -> {
            Intent intent = new Intent(ManageVans.this, CreateVan.class);
            startActivity(intent);
        });

        make_cards();




    }

    private void make_cards() {

        final ProgressDialog progressDialog = new ProgressDialog(ManageVans.this,
                R.style.MyDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest req = new StringRequest(Request.Method.GET, Van_URL,
                response -> {
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    vans = gson.fromJson(response, Van[].class);
                    showVans(vans);

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

                Toast.makeText(ManageVans.this, "Check internet!", Toast.LENGTH_LONG).show();

            }
        });

//        VolleySingleton.getInstance(this).addToRequestQueue(req);
        volleySingleton.addToRequestQueue(req);


    }









    private void showVans(Van[] vans) {

        count.setText(vans.length + "");
        VanAdapter vanAdapter = new VanAdapter(this, vans);
        customerList.setAdapter(vanAdapter);
        vanAdapter.setOnItemClickListener(ManageVans.this);


    }

    @Override
    public void onItemClick(int position) {
        Van van = vans[position];



        Intent intent = new Intent(ManageVans.this, VanDetails.class);
        intent.putExtra("position", position);
        intent.putExtra("list", (Serializable) van);
        startActivity(intent);

        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Customers.this, Home.class);
//        startActivity(intent);
//        finish();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//    }
}
