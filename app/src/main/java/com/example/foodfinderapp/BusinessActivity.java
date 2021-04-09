package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 *   The activity for a business page
 */
public class BusinessActivity extends AppCompatActivity {

    Database database;
    Business business;

    TextView businessName;
    TextView businessDescription;
    Button setIfSavedBtn;

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        businessName = (TextView) findViewById(R.id.businessNameTV);
        businessDescription = (TextView) findViewById(R.id.businessDescriptionTV);
        setIfSavedBtn = (Button) findViewById(R.id.setIfSavedBtn);

        // Check if business object was successfully passed
        if (getIntent().getExtras() != null) {
            database = Database.getInstance();
            business = (Business) getIntent().getSerializableExtra("business");

            // Set values on page
            businessName.setText(business.getName());
            businessDescription.setText(business.getDescription());
            updateSetIfSavedBtn();
        }
    }

    // Called when setIfSavedBtn is clicked
    public void setIfSavedBtnClicked (android.view.View view) {
        // Update database
        database.setIfBusinessSaved(business, !database.getBusiness(business.getIndex()).getIfSaved());

        // Update setIfSavedBtn
        updateSetIfSavedBtn();

        // Show toast indicating change
        if (database.getBusiness(business.getIndex()).getIfSaved() == false) {
            Toast.makeText(this, "Business Removed from Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Business Added to Saved", Toast.LENGTH_SHORT).show();
        }
    }

    // Changes the look of the setIfSavedBtn depending if the business is saved or not
    private void updateSetIfSavedBtn () {
        if (database.getBusiness(business.getIndex()).getIfSaved() == false) {
            setIfSavedBtn.setText("+");
        } else {
            setIfSavedBtn.setText("x");
        }
    }

}