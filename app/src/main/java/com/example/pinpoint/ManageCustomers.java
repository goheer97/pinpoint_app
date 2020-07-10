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

public class ManageCustomers extends AppCompatActivity implements CustomerAdapter.OnItemClickListener {

    public static String Customer_URL = "http://192.168.100.26:3000/customer";

    RecyclerView customerList;
    Customer[] customers;
    TextView count;
    Button btn_create;

    VolleySingleton volleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customers);
        customerList = findViewById(R.id.recycler_view);
        btn_create = findViewById(R.id.btn_create);
        customerList.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.numberActions);


        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(view -> {
            Intent intent = new Intent(ManageCustomers.this, CreateCustomer.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        make_cards();




    }

    private void make_cards() {

        final ProgressDialog progressDialog = new ProgressDialog(ManageCustomers.this,
                R.style.MyDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest req = new StringRequest(Request.Method.GET, Customer_URL,
                response -> {
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    customers = gson.fromJson(response, Customer[].class);
                    showCustomers(customers);

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

                Toast.makeText(ManageCustomers.this, "Check internet!", Toast.LENGTH_LONG).show();

            }
        });

//        VolleySingleton.getInstance(this).addToRequestQueue(req);
        volleySingleton.addToRequestQueue(req);


    }









    private void showCustomers(Customer[] customers) {

            count.setText(customers.length + "");
            CustomerAdapter customerAdapter = new CustomerAdapter(this, customers);
            customerList.setAdapter(customerAdapter);
            customerAdapter.setOnItemClickListener(ManageCustomers.this);


    }

    @Override
    public void onItemClick(int position) {
        Customer customer = customers[position];



        Intent intent = new Intent(ManageCustomers.this, CustomerDetails.class);
        intent.putExtra("position", position);
        intent.putExtra("list", (Serializable) customer);
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
