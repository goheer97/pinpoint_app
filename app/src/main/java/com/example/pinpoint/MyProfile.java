package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfile extends AppCompatActivity {

    @BindView(R.id.name)
    EditText Name;
    @BindView(R.id.contact)
    EditText number;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.newPassword)
    EditText newPassword;
    @BindView(R.id.newPassword2)
    EditText newPassword2;
    @BindView(R.id.oldPassword)
    EditText oldPassword;
    @BindView(R.id.btn_changeUsername)
    Button btn_updateProfile;
    @BindView(R.id.btn_changePassword)
    Button btn_changePassword;
    @BindView(R.id.btn_logout)
    Button btn_logout;

    int cust_id;
    String cust_name, cust_number, cust_email, cust_password;

    String Profile_Url = "";
    VolleySingleton volleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance(this);

        Intent intent = getIntent();
        cust_id = intent.getIntExtra("customer_id", 1);
        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutClicked();
            }
        });
        loadProfile();


    }


    private void loadProfile() {

        final ProgressDialog progressDialog = new ProgressDialog(MyProfile.this,
                R.style.MyDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest req = new StringRequest(Request.Method.GET, ManageCustomers.Customer_URL +"/"+cust_id,
                response -> {
                    Log.i("LoadProfile", "[" + response + "]");
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Customer[] customers = gson.fromJson(response, Customer[].class);

                    if(customers.length != 0) {
                            cust_id = customers[0].getCustomerId();
                            cust_name = customers[0].getCustomerName();
                            cust_number = customers[0].getCustomerNumber();
                            cust_email = customers[0].getCustomerEmail();
                            cust_password = customers[0].getCustomerPassword();

                            Name.setText(cust_name);
                            number.setText(cust_number);
                            username.setText(cust_email);

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

                Toast.makeText(MyProfile.this, "Check internet!", Toast.LENGTH_LONG).show();

            }
        });

//        VolleySingleton.getInstance(this).addToRequestQueue(req);
        volleySingleton.addToRequestQueue(req);


    }


//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Setting.this, Home.class);
//        startActivity(intent);
//        finish();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//    }


    public void logoutClicked() {
        Intent intent = new Intent(MyProfile.this, Login.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public boolean validatePassword() {
        boolean valid = true;

        String password1 = newPassword.getText().toString();
        String password2 = newPassword2.getText().toString();
        String password_old = oldPassword.getText().toString();

        if (password1.isEmpty()) {
            newPassword.setError("Please enter new password.");
            valid = false;
        } else {
            newPassword.setError(null);
        }

        if (password2.isEmpty()) {
            newPassword2.setError("Please re-enter new password.");
            valid = false;
        } else {
            newPassword2.setError(null);
        }

        if (password_old.isEmpty()) {
            oldPassword.setError("Please enter old password.");
            valid = false;
        } else {
            oldPassword.setError(null);
        }

        if (!password1.equals(password2)) {
            newPassword.setError("Password didn't match.");
            newPassword2.setError("Password didn't match.");
            valid = false;
        } else {
            newPassword.setError(null);
            newPassword2.setError(null);
        }

        return valid;
    }

    public void updatePassword() {
        String pass1 = oldPassword.getText().toString();
        String pass2 = newPassword.getText().toString();


        if (validatePassword()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ManageCustomers.Customer_URL,
                    response -> {
                        Log.i("ChangePass", "[" + response + "]");
                        oldPassword.setText("");
                        newPassword.setText("");
                        newPassword2.setText("");
                        Toast.makeText(MyProfile.this, "Password Updated.", Toast.LENGTH_LONG).show();


                    },
                    error -> {


                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("customer_name", cust_name);
                    params.put("customer_number", cust_number);
                    params.put("customer_email", cust_email);
                    params.put("customer_password", pass2);
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    public void updateProfile() {
        String name_ = Name.getText().toString().trim();
        String userid_ = username.getText().toString().trim();
        String contactNumber_ = number.getText().toString();


        if (validateProfileInfo(userid_, contactNumber_, name_)) {


                StringRequest stringRequest = new StringRequest(Request.Method.POST, ManageCustomers.Customer_URL,
                        response -> {
                            Log.i("usernameUpdate", "[" + response + "]");

                        },
                        error -> Toast.makeText(MyProfile.this, "Failed.", Toast.LENGTH_LONG).show()) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("customer_name", cust_name);
                        params.put("customer_number", cust_number);
                        params.put("customer_email", cust_email);
                        params.put("customer_password", cust_password);
                        return params;
                    }
                };
               Volley.newRequestQueue(this).add(stringRequest);

        }
    }

    private boolean validateProfileInfo(String userid_, String contactNumber_, String name_) {
        boolean valid = true;

        if (userid_.equals(cust_email) && contactNumber_.equals(cust_number)) {
            Toast.makeText(MyProfile.this, "No Changes Found!", Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (userid_.isEmpty()) {
            username.setError("Can't be empty.");
            valid = false;
        } else {
            username.setError(null);
        }
        if (userid_.contains(" ")) {
            username.setError("Username can't have spaces!");
        } else {
            username.setError(null);
        }
        if (contactNumber_.isEmpty()) {
            number.setError("Can't be empty.");
            valid = false;
        } else {
            number.setError(null);
        }
        if(name_.isEmpty()){
            Name.setError("Can't be Empty");
            valid = false;
        }else{
            Name.setError(null);
        }
        return valid;


    }
}
