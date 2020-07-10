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

public class DriverDetails extends AppCompatActivity {

    Driver driver;

    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.custID)
    TextView custID;
    @BindView(R.id.name)
    EditText custName;
    @BindView(R.id.cnic)
    EditText cnic;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.address)
    EditText address;


    VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        Intent intent = this.getIntent();
        driver = (Driver) intent.getSerializableExtra("list");

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(DriverDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.DELETE, ManageDrivers.Driver_URL +"/"+driver.getDriverId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(DriverDetails.this, "Driver Deleted!", Toast.LENGTH_LONG).show();
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

                        Toast.makeText(DriverDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                });
                volleySingleton.addToRequestQueue(req);

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(DriverDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.PUT, ManageDrivers.Driver_URL +"/"+driver.getDriverId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(DriverDetails.this, "Driver Updated!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }, error -> {
                    Log.d("Error: ", error.getMessage());

                    progressDialog.dismiss();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        if (error.networkResponse.statusCode == 400) {
                            Toast.makeText(DriverDetails.this, "Update Failed!", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    } else {

                        Toast.makeText(DriverDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("driver_name", custName.getText().toString().trim());
                        params.put("driver_contact_no", contact.getText().toString().trim());
                        params.put("driver_cnic", cnic.getText().toString().trim());
                        params.put("driver_address", address.getText().toString().trim());
                        return params;
                    }
                };
                volleySingleton.addToRequestQueue(req);
            }
        });


        custID.setText(driver.getDriverId()+"");
        custName.setText(driver.getDriverName());
        cnic.setText(driver.getDriverCnic());
        address.setText(driver.getDriverAddress());
        contact.setText(driver.getDriverContactNo());



    }
}
