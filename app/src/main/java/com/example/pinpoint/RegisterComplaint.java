package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterComplaint extends AppCompatActivity {

    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.btn_register)
    Button btn_register;

    String customer_id;
    VolleySingleton volleySingleton;

    String Complaint_URL = "http://192.168.100.26:3000/complaint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        volleySingleton = VolleySingleton.getInstance(this);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        customer_id = intent.getStringExtra("customer_id");
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(RegisterComplaint.this,
                        R.style.AppTheme_Dark);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                StringRequest req = new StringRequest(Request.Method.POST, Complaint_URL,
                        response -> {
                            progressDialog.dismiss();

                            Toast.makeText(RegisterComplaint.this, "Complaint Registered!", Toast.LENGTH_LONG).show();


                        }, error -> {
                    Log.d("Error: ", error.getMessage());

                    progressDialog.dismiss();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        if (error.networkResponse.statusCode == 204) {
//                              Toast.makeText(Customers.this, "No Data Assigned!", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    } else {

                        Toast.makeText(RegisterComplaint.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_id", customer_id);
                        params.put("complaint_type", type.getText().toString().trim());
                        params.put("complaint_desc", description.getText().toString().trim());
                        params.put("trip_id", "1");
//                        params.put("complaint_type", type.getText().toString().trim());

                        return params;
                    }
                };

                volleySingleton.addToRequestQueue(req);
            }
        });

    }
}
