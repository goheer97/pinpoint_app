package com.example.pinpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    String user = "Admin";
    int custID;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;

    VolleySingleton volleySingleton;
    private RadioGroup radioUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radioUser = findViewById(R.id.radioUser);
        ButterKnife.bind(this);
        volleySingleton =  VolleySingleton.getInstance(this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedRadioId = radioUser.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(selectedRadioId);
                user = (String) radioButton.getText();
                login();
            }
        });
        _emailText.setHint("Username");
        radioUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    if(checkedRadioButton.getText().equals("Van")){
                        _passwordText.setVisibility(View.GONE);
                        _emailText.setHint("Van ID");
                    }else if(checkedRadioButton.getText().equals("Parent")) {
                        _passwordText.setVisibility(View.VISIBLE);
                        _emailText.setHint("Username");
                    }else{
                        _passwordText.setVisibility(View.VISIBLE);
                        _emailText.setHint("Username");

                    }

                }
                _loginButton.setEnabled(true);
            }
        });


    }

        public void login() {
            Log.d(TAG, "Login");

            if (!validate()) {
                onLoginFailed();
                return;
            }

            _loginButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.AppTheme_Dark);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            // TODO: Implement your own authentication logic here.

            if(user.equals("Admin")){
                if(email.equals(("admin")) && password.equals("admin")){
                    user = "Admin";
                    onLoginSuccess("admin");
                }else{

                }
            }else if(user.equals("Parent")){
                StringRequest req = new StringRequest(Request.Method.PUT, "http://192.168.100.26:3000/user/login",
                        response -> {
                            progressDialog.dismiss();

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            Customer[] customers = gson.fromJson(response, Customer[].class);
                            if(customers.length == 0){

                            }else{
                                custID = customers[0].getCustomerId();
                                user = "Parent";
                                onLoginSuccess("parent");
                            }
//                            try {
//                                JSONArray jsonArray = new JSONArray(response);
//                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                custID = (int) jsonArray.getInt(0);
//                                user = "Parent";
//                                onLoginSuccess("parent");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }


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

                        Toast.makeText(Login.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_id", email);
                        params.put("customer_password", password);
                        return params;
                    }
                };

                volleySingleton.addToRequestQueue(req);
            }else{
                StringRequest req = new StringRequest(Request.Method.GET, ManageVans.Van_URL+"/"+_emailText.getText().toString(),
                        response -> {
                            progressDialog.dismiss();
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            Van[] vans = gson.fromJson(response, Van[].class);

                            if(vans.length == 0){
                                Toast.makeText(Login.this, "Invalid username or password!", Toast.LENGTH_LONG).show();
                            }else{
                                user = "Van";
                                onLoginSuccess("van");
                            }


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

                        Toast.makeText(Login.this, "Check internet!", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_id", email);
                        params.put("customer_password", password);
                        return params;
                    }
                };

                volleySingleton.addToRequestQueue(req);
            }
//            new android.os.Handler().postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            // On complete call either onLoginSuccess or onLoginFailed
//                            onLoginSuccess();
//                            // onLoginFailed();
//                            progressDialog.dismiss();
//                        }
//                    }, 3000);
        }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String userType) {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(Login.this, MainActivity.class);
        if(user.equals("Admin")){
            intent.putExtra("userType", "admin");
        }else if(user.equals("Van")){
            intent.putExtra("userType", "van");
            intent.putExtra("van_id", _emailText.getText().toString().trim());
        }else{
            intent.putExtra("userType", "parent");
            intent.putExtra("customer_id", custID);
            Log.d("CustID", custID+"");
        }
        startActivity(intent);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if(user.equals("Van")){
            if (email.isEmpty()) {
                _emailText.setError("enter a valid van id");
                valid = false;
            } else {
                _emailText.setError(null);
            }
        }else{
            if (email.isEmpty()) {
                _emailText.setError("enter a valid username");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }
        }

        return valid;
    }

}
