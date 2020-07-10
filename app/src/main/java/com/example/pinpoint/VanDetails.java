package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class VanDetails extends AppCompatActivity {

    Van van;

    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.vanID)
    TextView vanID;
    @BindView(R.id.shipmentDate)
    EditText shipmentDate;
    @BindView(R.id.regNo)
    EditText regNo;
    @BindView(R.id.model)
    EditText model;


    VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_details);

        Intent intent = this.getIntent();
        van = (Van) intent.getSerializableExtra("list");

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(VanDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.DELETE, ManageVans.Van_URL +"/"+van.getVanId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(VanDetails.this, "Van Deleted!", Toast.LENGTH_LONG).show();
                            onBackPressed();
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

                        Toast.makeText(VanDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                });
                volleySingleton.addToRequestQueue(req);

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(VanDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.PUT, ManageVans.Van_URL +"/"+van.getVanId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(VanDetails.this, "Van Updated!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }, error -> {
                    Log.d("Error: ", error.getMessage());

                    progressDialog.dismiss();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        if (error.networkResponse.statusCode == 400) {
                            Toast.makeText(VanDetails.this, "Update Failed!", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    } else {

                        Toast.makeText(VanDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("shipment_date", shipmentDate.getText().toString().trim());
                        params.put("van_reg_no", regNo.getText().toString().trim());
                        params.put("van_model", model.getText().toString().trim());
                        return params;
                    }
                };
                volleySingleton.addToRequestQueue(req);
            }
        });



        vanID.setText(van.getVanId()+"");
        regNo.setText(van.getVanRegNo());
        shipmentDate.setText(van.getShipmentDate());
        model.setText(van.getVanModel());


    }
}
