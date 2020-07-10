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

import butterknife.ButterKnife;

public class ManageStudents extends AppCompatActivity implements StudentAdapter.OnItemClickListener {

    public static String Student_URL = "http://192.168.100.26:3000/customerChild";

    RecyclerView customerList;
    Student[] students;
    TextView count;
    Button btn_create;

    VolleySingleton volleySingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        customerList = findViewById(R.id.recycler_view);
        btn_create = findViewById(R.id.btn_create);
        customerList.setLayoutManager(new LinearLayoutManager(this));
        count = findViewById(R.id.numberActions);

        ButterKnife.bind(this);

        volleySingleton = VolleySingleton.getInstance(this);

        btn_create.setOnClickListener(view -> {
            Intent intent = new Intent(ManageStudents.this, CreateStudent.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        make_cards();




    }

    private void make_cards() {

        final ProgressDialog progressDialog = new ProgressDialog(ManageStudents.this,
                R.style.MyDialogTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest req = new StringRequest(Request.Method.GET, Student_URL,
                response -> {
                    progressDialog.dismiss();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    students = gson.fromJson(response, Student[].class);
                    showStudents(students);

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

                Toast.makeText(ManageStudents.this, "Check internet!", Toast.LENGTH_LONG).show();

            }
        });

//        VolleySingleton.getInstance(this).addToRequestQueue(req);
        volleySingleton.addToRequestQueue(req);


    }









    private void showStudents(Student[] customers) {

        count.setText(customers.length + "");
        StudentAdapter studentAdapter = new StudentAdapter(this, students);
        customerList.setAdapter(studentAdapter);
        studentAdapter.setOnItemClickListener(ManageStudents.this);


    }

    @Override
    public void onItemClick(int position) {
        Student student = students[position];



        Intent intent = new Intent(ManageStudents.this, StudentDetails.class);
        intent.putExtra("position", position);
        intent.putExtra("list", (Serializable) student);
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
