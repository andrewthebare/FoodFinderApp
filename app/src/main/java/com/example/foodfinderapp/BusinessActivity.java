package com.example.foodfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *   The activity for a business page
 */
public class BusinessActivity extends AppCompatActivity {

    Activity m_activity = this;

    Database m_database;
    Business m_business;
    User currentUser;

    TextView m_businessName;
    TextView m_businessDescription;
    TextView m_pointDisplay;
    Button m_setIfSavedBtn;
    Button m_getPointsButton;

    EditText codeEnter;

    RecyclerView m_recyclerView;
    BusinessActivity.Adapter m_adapter;

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

            // Create the recycler view
            m_recyclerView = findViewById(R.id.rewardsRV);
            m_recyclerView.setLayoutManager(new LinearLayoutManager(this));

            redrawPage();

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
            Database.getInstance().getCurrentUser().removeFavorite(m_business);
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
        final PopupWindow popupWindow = new PopupWindow(popupView, 1000, 600, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        Button enterCode = (Button) popupView.findViewById(R.id.getPointsGO);
        enterCode.setOnClickListener(this::onCodeEnterClick);

        codeEnter =  (EditText) popupView.findViewById(R.id.editTextNumber);
    }

    public void onCodeEnterClick(View view){

        if (!codeEnter.getText().toString().equals(""))  {
            int code = Integer.parseInt(codeEnter.getText().toString());

            if(Database.getInstance().codeFound(code,m_business.getIndex())){
                Database.getInstance().incrementPoints(m_business.getIndex(),code);
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

    // Redraws the page
    private void redrawPage () {
        ArrayList<Reward> rewards = Database.getInstance().getBusiness(m_business.getIndex()).getRewards();

        // Refresh adapter
        m_adapter = new BusinessActivity.Adapter(rewards);
        m_recyclerView.setAdapter(m_adapter);
    }

    /**
     *   The holder for a list item
     */
    private class Holder extends RecyclerView.ViewHolder {

        TextView m_rewardName;
        TextView m_rewardDescription;
        TextView m_rewardPoints;
        ProgressBar m_rewardProgress;
        Button m_rewardBtn;

        // Constructor
        public Holder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.reward_list_item, parent, false));

            m_rewardName = itemView.findViewById(R.id.rewardNameTV);
            m_rewardDescription = itemView.findViewById(R.id.rewardDescriptionTV);
            m_rewardPoints = itemView.findViewById(R.id.rewardPointsTV);
            m_rewardProgress = itemView.findViewById(R.id.rewardPB);
            m_rewardBtn = itemView.findViewById(R.id.rewardClaimBtn);
        }

        // Sets the properties of the views
        public void bind (Reward reward) {
            m_rewardName.setText(reward.getName());
            m_rewardDescription.setText(reward.getDescription());
            m_rewardPoints.setText(Integer.toString(reward.getPointsNeededToClaim()));

            m_rewardProgress.setMax(reward.getPointsNeededToClaim());
            m_rewardProgress.setProgress(Database.getInstance().getCurrentUser().getPoints(m_business.getIndex()));

            if (currentUser.getPoints(m_business.getIndex()) >= reward.getPointsNeededToClaim()) {
                m_rewardBtn.setEnabled(true);
            } else {
                m_rewardBtn.setEnabled(false);
            }
        }

    }

    /**
     *   The adapter for the list
     */
    private class Adapter extends RecyclerView.Adapter<BusinessActivity.Holder> {

        private ArrayList<Reward> m_rewards;

        // Constructor
        public Adapter (ArrayList<Reward> rewards) {
            m_rewards = rewards;
        }

        // Called when a new item is added to the list
        @Override
        public BusinessActivity.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(m_activity);
            return new BusinessActivity.Holder(layoutInflater, parent);
        }

        // Called to bind view holder
        @Override
        public void onBindViewHolder(BusinessActivity.Holder holder, int position) {
            holder.bind(m_rewards.get(position));

            // Setup click listener
            holder.itemView.findViewById(R.id.rewardClaimBtn).setOnClickListener(
                    new View.OnClickListener() {

                        // Called when button in list is clicked on
                        @Override
                        public void onClick (View view) {
                            currentUser.decrementPoints(m_business.getIndex(), m_rewards.get(position).getPointsNeededToClaim());
                            redrawPage();
                            m_pointDisplay.setText(Integer.toString(Database.getInstance().getCurrentUser().getPoints(m_business.getIndex())));
                        }
                    });
        }

        // Getter for the number of items in the list
        @Override
        public int getItemCount() {
            return m_rewards.size();
        }

    }

}