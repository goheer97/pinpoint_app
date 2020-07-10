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

public class CustomerDetails extends AppCompatActivity {

    Customer customer;

    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.custID)
    TextView custID;
    @BindView(R.id.name)
    EditText custName;
    @BindView(R.id.email)
    EditText custEmail;
    @BindView(R.id.password)
    EditText custPassword;
    @BindView(R.id.contact)
    EditText custContact;


    VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent intent = this.getIntent();
        customer = (Customer) intent.getSerializableExtra("list");

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(CustomerDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.DELETE, ManageCustomers.Customer_URL +"/"+customer.getCustomerId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(CustomerDetails.this, "Parent Deleted!", Toast.LENGTH_LONG).show();
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

                        Toast.makeText(CustomerDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                });
                volleySingleton.addToRequestQueue(req);

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(CustomerDetails.this,
                        R.style.MyDialogTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);

                StringRequest req = new StringRequest(Request.Method.PUT, ManageCustomers.Customer_URL +"/"+customer.getCustomerId(),
                        response -> {
                            progressDialog.dismiss();
                            Toast.makeText(CustomerDetails.this, "Parent Updated!", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }, error -> {
                    Log.d("Error: ", error.getMessage());

                    progressDialog.dismiss();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        if (error.networkResponse.statusCode == 400) {
                            Toast.makeText(CustomerDetails.this, "Update Failed!", Toast.LENGTH_LONG).show();
                        } else {

                        }
                    } else {

                        Toast.makeText(CustomerDetails.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_name", custName.getText().toString().trim());
                        params.put("customer_number", custContact.getText().toString().trim());
                        params.put("customer_email", custEmail.getText().toString().trim());
                        params.put("customer_password", custPassword.getText().toString().trim());
                        return params;
                    }
                };
                volleySingleton.addToRequestQueue(req);
            }
        });

        Log.d("Customer:", customer.toString());

        custID.setText(customer.getCustomerId()+"");
        custName.setText(customer.getCustomerName());
        custContact.setText(customer.getCustomerNumber());
        custEmail.setText(customer.getCustomerEmail());
        custPassword.setText(customer.getCustomerPassword());



    }
}
