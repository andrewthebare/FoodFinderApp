package com.example.foodfinderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

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
        changePicBTN.setOnClickListener(this::takePhotoClick);
        //TODO find better default pic
        if(currentUser.profilePic == null){
            //default
            System.out.println("No profile pic!");
        }else{
            profilePic.setImageBitmap(currentUser.profilePic);
        }

        switchUser.setOnClickListener(this::onSwitchUserClick);
    }

    public void onSwitchUserClick(View view){
        Database.getInstance().setCurrentUser(Integer.parseInt(editUserNum.getText().toString()));
    }

    // From Zybooks
    public void takePhotoClick(View view) {

        // Start the activity that can handle the implicit intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            // Show preview
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePic.setImageBitmap(imageBitmap);
            Database.getInstance().getCurrentUser().setPicture(imageBitmap);
        }
    }
}