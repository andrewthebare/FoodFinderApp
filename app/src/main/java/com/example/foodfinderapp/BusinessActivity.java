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

    Database m_database;
    Business m_business;

    TextView m_businessName;
    TextView m_businessDescription;
    Button m_setIfSavedBtn;

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        m_businessName = (TextView) findViewById(R.id.businessNameTV);
        m_businessDescription = (TextView) findViewById(R.id.businessDescriptionTV);
        m_setIfSavedBtn = (Button) findViewById(R.id.setIfSavedBtn);

        // Check if business object was successfully passed
        if (getIntent().getExtras() != null) {
            m_database = Database.getInstance();
            m_business = (Business) getIntent().getSerializableExtra("business");

            // Set values on page
            m_businessName.setText(m_business.getName());
            m_businessDescription.setText(m_business.getDescription());
            updateSetIfSavedBtn();
        }
    }

    // Called when setIfSavedBtn is clicked
    public void setIfSavedBtnClicked (android.view.View view) {
        // Update database
        m_database.setIfBusinessSaved(m_business, !m_database.getBusiness(m_business.getIndex()).getIfSaved());

        // Update setIfSavedBtn
        updateSetIfSavedBtn();

        // Show toast indicating change
        if (m_database.getBusiness(m_business.getIndex()).getIfSaved() == false) {
            Toast.makeText(this, "Business Removed from Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Business Added to Saved", Toast.LENGTH_SHORT).show();
        }
    }

    // Changes the look of the setIfSavedBtn depending if the business is saved or not
    private void updateSetIfSavedBtn () {
        if (m_database.getBusiness(m_business.getIndex()).getIfSaved() == false) {
            m_setIfSavedBtn.setText("+");
        } else {
            m_setIfSavedBtn.setText("x");
        }
    }

}