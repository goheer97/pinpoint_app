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


public class CreateVan extends AppCompatActivity {


    @BindView(R.id.btn_create)
    Button btn_create;

    @BindView(R.id.regNo )
    EditText regNo;
    @BindView(R.id.model)
    EditText model;
    @BindView(R.id.shipmentDate)
    EditText shipmentDate;

    VolleySingleton volleySingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_van);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(regNo.getText().toString().trim()!= "" && model.getText().toString().trim() != "" && shipmentDate.getText().toString().trim() != ""){
                    final ProgressDialog progressDialog = new ProgressDialog(CreateVan.this,
                            R.style.MyDialogTheme);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    StringRequest req = new StringRequest(Request.Method.POST, ManageVans.Van_URL,
                            response -> {
                                progressDialog.dismiss();
                                Toast.makeText(CreateVan.this, "Van Created!", Toast.LENGTH_LONG).show();Intent intent = new Intent(CreateVan.this, ManageVans.class);
                                regNo.setText("");
                                model.setText("");
                                shipmentDate.setText("");
                                }, error -> {
                        Log.d("Error: ", error.getMessage());

                        progressDialog.dismiss();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            if (error.networkResponse.statusCode == 400) {
                                Toast.makeText(CreateVan.this, "Update Failed!", Toast.LENGTH_LONG).show();
                            } else {

                            }
                        } else {

                            Toast.makeText(CreateVan.this, "Check internet!", Toast.LENGTH_LONG).show();

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
                }else{
                    Toast.makeText(CreateVan.this, "Kindly fill all blanks!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
