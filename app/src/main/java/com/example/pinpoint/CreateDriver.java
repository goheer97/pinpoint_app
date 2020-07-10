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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateDriver extends AppCompatActivity {


    @BindView(R.id.btn_create)
    Button btn_create;

    @BindView(R.id.custName )
    EditText custName;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.cnic)
    EditText cnic;
    @BindView(R.id.address)
    EditText address;


    VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_driver);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(custName.getText().toString().trim()!= "" && cnic.getText().toString().trim() != "" && address.getText().toString().trim() != "" && contact.getText().toString().trim() != ""){
                    final ProgressDialog progressDialog = new ProgressDialog(CreateDriver.this,
                            R.style.MyDialogTheme);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    StringRequest req = new StringRequest(Request.Method.POST, ManageDrivers.Driver_URL,
                            response -> {
                                progressDialog.dismiss();
                                Toast.makeText(CreateDriver.this, "Driver Created!", Toast.LENGTH_LONG).show();

                                custName.setText("");
                                contact.setText("");
                                cnic.setText("");
                                address.setText("");

                            }, error -> {
                        Log.d("Error: ", error.getMessage());

                        progressDialog.dismiss();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            if (error.networkResponse.statusCode == 400) {
                                Toast.makeText(CreateDriver.this, "Update Failed!", Toast.LENGTH_LONG).show();

                            } else {

                            }
                        } else {

                            Toast.makeText(CreateDriver.this, "Check internet!", Toast.LENGTH_LONG).show();

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
                }else{
                    Toast.makeText(CreateDriver.this, "Kindly fill all blanks!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
