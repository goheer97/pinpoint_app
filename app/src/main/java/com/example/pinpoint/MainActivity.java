package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String user;
    ScrollView adminDisplay, vanDisplay, parentDisplay;
    TextView userName;
    int customer_id;
    int van_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();
        user = intent.getStringExtra("userType");

        adminDisplay = findViewById(R.id.AdminDisplay);
        vanDisplay = findViewById(R.id.VanDriverDisplay);
        parentDisplay = findViewById(R.id.ParentDisplay);
        userName = findViewById(R.id.user);

        if(user.equals("admin")){
            adminDisplay.setVisibility(View.VISIBLE);
            userName.setText("Admin");
        }else if(user.equals("van")){
            vanDisplay.setVisibility(View.VISIBLE);
            userName.setText("Van");
            van_id = intent.getIntExtra("van_id", 1);
        }else{
            parentDisplay.setVisibility(View.VISIBLE);
            userName.setText("Parent");
            customer_id = intent.getIntExtra("customer_id", 1);

        }

    }


    public void ManageCustomers(View view) {
        Intent intent =  new Intent(MainActivity.this, ManageCustomers.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void ManageDrivers(View view) {
        Intent intent =  new Intent(MainActivity.this, ManageDrivers.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void ManageTimetable(View view) {
        Intent intent =  new Intent(MainActivity.this, ManageTimetable.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void ManageStudetns(View view) {
        Intent intent =  new Intent(MainActivity.this, ManageStudents.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void ManageVans(View view) {
        Intent intent =  new Intent(MainActivity.this, ManageVans.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void Pickup_Time(View view) {
        Intent intent =  new Intent(MainActivity.this, PickupTime.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void TrackSpeed(View view) {
        Intent intent =  new Intent(MainActivity.this, TrackSpeed.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void TrackLocation(View view) {


        Intent mapIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:<" + 24.952968  + ">,<" + 67.107928 + ">?q=<" + 24.952968  + ">,<" + 67.107928 + ">"));
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }



    public void DontPickup(View view) {
        Toast.makeText(MainActivity.this, "Noted!", Toast.LENGTH_LONG).show();

    }

    public void MyProfile(View view) {
        Intent intent =  new Intent(MainActivity.this, MyProfile.class);
        intent.putExtra("customer_id", customer_id);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void RegisterComplaint(View view) {
        Intent intent =  new Intent(MainActivity.this, RegisterComplaint.class);
        intent.putExtra("customer_id", customer_id+"");
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void ShowVanDetails(View view) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Van van = gson.fromJson(" {\n" +
                "        \"van_id\": 1,\n" +
                "        \"shipment_date\": \"2020-07-04T19:00:00.000Z\",\n" +
                "        \"van_reg_no\": \"AGR-928\",\n" +
                "        \"van_model\": \"Wegnar\",\n" +
                "        \"driver_id\": null\n" +
                "    }", Van.class);

        Intent intent = new Intent(MainActivity.this, VanDetails.class);
        intent.putExtra("list", (Serializable) van);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void start_trip(View view) {
        double latitude = 24.952968;
        double longitude = 67.107928;
        String uri_str = String.format(Locale.ENGLISH, "google.navigation:q=%f, %f", latitude, longitude);
        Uri uri = Uri.parse(uri_str);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void EndTrip(View view) {
        Toast.makeText(MainActivity.this, "Trip Ended!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}
