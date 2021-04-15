package com.example.foodfinderapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *   Fragment for profile page
 */
public class ProfileFragment extends Fragment {
    Button switchUser;
    EditText editUserNum;

    ImageView profilePic;
    Button changePicBTN;
    TextView nameDisplay;
    TextView usernameDisplay;

    // Required empty public constructor
    public ProfileFragment() {

    }

    // Factory method to create new instance
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    // Called when fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Called when view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // Called after view is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchUser = view.findViewById(R.id.switchUser);
        editUserNum = (EditText) view.findViewById(R.id.userSwitchNum);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        changePicBTN = (Button) view.findViewById(R.id.changePictureBTN);
        nameDisplay = (TextView) view.findViewById(R.id.profileNameTV);
        usernameDisplay = (TextView) view.findViewById(R.id.profileUsernameTV);

        //pull down the user and get the current values
        User currentUser = Database.getInstance().getCurrentUser();

        nameDisplay.setText(currentUser.name);
        usernameDisplay.setText('@'+ currentUser.userName);
        //TODO Logic to set default PP, otherwise load saved PP

        switchUser.setOnClickListener(this::onSwitchUserClick);
    }

    public void onSwitchUserClick(View view){
        Database.getInstance().setCurrentUser(Integer.parseInt(editUserNum.getText().toString()));
    }

}