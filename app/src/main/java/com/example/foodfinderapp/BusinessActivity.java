package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 *   The activity for a business page
 */
public class BusinessActivity extends AppCompatActivity {

    Database m_database;
    Business m_business;
    User currentUser;

    TextView m_businessName;
    TextView m_businessDescription;
    TextView m_pointDisplay;
    Button m_setIfSavedBtn;
    Button m_getPointsButton;

    // Called when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        m_businessName = (TextView) findViewById(R.id.businessNameTV);
        m_businessDescription = (TextView) findViewById(R.id.businessDescriptionTV);
        m_pointDisplay = (TextView) findViewById(R.id.pointsTVDisplay);
        m_setIfSavedBtn = (Button) findViewById(R.id.setIfSavedBtn);


        //popup code entry
        m_getPointsButton = (Button) findViewById(R.id.getPointsBtn);
        m_getPointsButton.setOnClickListener(this::onEnterPointsButtonClick);

        // Check if business object was successfully passed
        if (getIntent().getExtras() != null) {
            m_database = Database.getInstance();
            m_business = (Business) getIntent().getSerializableExtra("business");
            currentUser = Database.getInstance().getCurrentUser();

            // Set values on page
            m_businessName.setText(m_business.getName());
            m_businessDescription.setText(m_business.getDescription());
            if(!currentUser.busListContains(m_business)) {
                Database.getInstance().getCurrentUser().addBusiness(m_business);
                currentUser = Database.getInstance().getCurrentUser();
            }
            m_pointDisplay.setText(Integer.toString(currentUser.getPoints(m_business.getIndex())));
            updateSetIfSavedBtn();
        }

    }

    // Called when setIfSavedBtn is clicked
    public void setIfSavedBtnClicked (android.view.View view) {
        // Update database
//        m_database.setIfBusinessSaved(m_business, !m_database.getBusiness(m_business.getIndex()).getIfSaved());
        if(!currentUser.isFavorite(m_business.getIndex())){
            Database.getInstance().getCurrentUser().addBusiness(m_business);
            Database.getInstance().getCurrentUser().addFavorite(m_business);
        }else{
            //remove favorite
        }

        // Update setIfSavedBtn
        updateSetIfSavedBtn();

        // Show toast indicating change
        if (!Database.getInstance().getCurrentUser().isFavorite(m_business.getIndex())) {
            Toast.makeText(this, "Business Removed from Saved", Toast.LENGTH_SHORT).show();
        } else {
//            Database.getInstance().getCurrentUser().addFavorite(m_business);
            Toast.makeText(this, "Business Added to Saved", Toast.LENGTH_SHORT).show();
        }
    }

    // Changes the look of the setIfSavedBtn depending if the business is saved or not
    private void updateSetIfSavedBtn () {
        if (!Database.getInstance().getCurrentUser().isFavorite(m_business.getIndex())) {
            m_setIfSavedBtn.setText("+");
        } else {
            m_setIfSavedBtn.setText("x");
        }
    }

    public void onEnterPointsButtonClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.get_points_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, 400, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button enterCode = (Button) popupView.findViewById(R.id.getPointsGO);
        enterCode.setOnClickListener(this::onCodeEnterClick);

    }

    public void onCodeEnterClick(View view){
        System.out.println("IM HERE");
        //TODO shoot a request to database
        //TODO wait for response

        boolean goodResponse = true;
        if (goodResponse){
            Database.getInstance().getCurrentUser().incrementPoints(m_business.getIndex(),10);
            Toast.makeText(this, "Points added to your profile!", Toast.LENGTH_SHORT).show();

            //TODO refreshes page, not sure if that's great practice tho
            //Actually causes an error that doesn't crash the app soooooo idk
            finish();
            this.startActivity(getIntent());

        }else{
            //Tell user they entered a wrong number
            Toast.makeText(this, "Code Entered not Found!", Toast.LENGTH_SHORT).show();
        }

    }

}