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


public class CreateCustomer extends AppCompatActivity {


    @BindView(R.id.btn_create)
    Button btn_create;

    @BindView(R.id.custName )
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
        setContentView(R.layout.activity_create_customer);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(custName.getText().toString().trim()!= "" && custContact.getText().toString().trim() != "" && custEmail.getText().toString().trim() != "" && custPassword.getText().toString().trim() != ""){
                    final ProgressDialog progressDialog = new ProgressDialog(CreateCustomer.this,
                            R.style.MyDialogTheme);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    StringRequest req = new StringRequest(Request.Method.POST, ManageCustomers.Customer_URL,
                            response -> {
                                progressDialog.dismiss();
                                Toast.makeText(CreateCustomer.this, "Parent Created!", Toast.LENGTH_LONG).show();
                                custName.setText("");
                                custEmail.setText("");
                                custContact.setText("");
                                custPassword.setText("");
                            }, error -> {
                        Log.d("Error: ", error.getMessage());

                        progressDialog.dismiss();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            if (error.networkResponse.statusCode == 400) {
                                Toast.makeText(CreateCustomer.this, "Update Failed!", Toast.LENGTH_LONG).show();
                            } else {

                            }
                        } else {

                            Toast.makeText(CreateCustomer.this, "Check internet!", Toast.LENGTH_LONG).show();

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
                }else{
                    Toast.makeText(CreateCustomer.this, "Kindly fill all blanks!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
