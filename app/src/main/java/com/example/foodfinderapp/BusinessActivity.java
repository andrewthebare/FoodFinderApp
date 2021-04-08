package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 *   The activity for a business page
 */
public class BusinessActivity extends AppCompatActivity {

    TextView businessName;
    TextView businessDescription;

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        businessName = (TextView) findViewById(R.id.businessNameTV);
        businessDescription = (TextView) findViewById(R.id.businessDescriptionTV);

        // Check if business object was successfully passed
        if (getIntent().getExtras() != null) {
            Business business = (Business) getIntent().getSerializableExtra("business");

            // Set values on page
            businessName.setText(business.getName());
            businessDescription.setText(business.getDescription());
        }
    }

}