package com.example.foodfinderapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *   Fragment for nearby businesses page
 */
public class NearbyBusinessesFragment extends Fragment implements OnMapReadyCallback {

    private final int REQUEST_LOCATION_PERMISSIONS = 195;

    private GoogleMap m_googleMap;
    private MapView m_mapView;
    private View m_view;

    private static double m_latitude = 34.6834;
    private static double m_longitude = -82.8374;

    private ArrayList<Business> m_businesses;
    private HashMap<Marker, Business> m_businessHashMap = new HashMap<>();

    TextView info;

    // Required empty public constructor
    public NearbyBusinessesFragment() {

    }

    // Factory method to create new instance
    public static NearbyBusinessesFragment newInstance() {
        NearbyBusinessesFragment fragment = new NearbyBusinessesFragment();
        return fragment;
    }

    // Called when fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //info = m_mapView.findViewById(R.id.nearbyInfoTV);

        // Get businesses in database
        m_businesses = Database.getInstance().getBusinesses();

    }

    // Called when view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_nearby_businesses, container, false);
        return m_view;
    }

    // Called after view is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Update users location
        if (hasLocationPermission()) {
            findLocation();
        } else {
            System.out.println("Please enable location");
        }

        // Create google map
        m_mapView = (MapView) m_view.findViewById(R.id.map);
        if (m_mapView != null) {
            m_mapView.onCreate(null);
            m_mapView.onResume();
            m_mapView.getMapAsync(this);
        }

    }

    // Called when google map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        // Set map type
        m_googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add businesses in the database as markers on the map
        for (int i = 0; i < m_businesses.size(); i++) {
            // Create marker
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(m_businesses.get(i).getLat(), m_businesses.get(i).getLng()))
                    .title(m_businesses.get(i).getName()));

            // Use hashmap to store which marker goes with which business
            m_businessHashMap.put(marker, m_businesses.get(i));
        }

        // Clemson: 34.6834, -82.8374

        // Update users location
        if (hasLocationPermission()) {
            findLocation();
        }

        // Create a position for the map to start out at
        CameraPosition clemson = CameraPosition.builder()
                .target(new LatLng(m_latitude, m_longitude))
                .zoom(16)
                .build();

        // Move the camera to that position
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(clemson));

        // Listener for when marker is clicked on the map
        m_googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    // Called when marker is clicked on the map
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        // Start new activity that shows the business page of the marker that was clicked on
                        Intent intent = new Intent (getActivity(), BusinessActivity.class);
                        intent.putExtra("business", m_businessHashMap.get(marker));
                        startActivity(intent);
                        return false;
                    }
                });
    }

    // Check if user has allowed app to use location : From zybooks
    private boolean hasLocationPermission() {
        // Request fine location permission if not already granted
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity() ,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION_PERMISSIONS);

            return false;
        }

        return true;
    }

    // Returns location of user : From zybooks
    @SuppressLint("MissingPermission")
    private void findLocation() {
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        NearbyBusinessesFragment.m_latitude = location.getLatitude();
                        NearbyBusinessesFragment.m_longitude = location.getLongitude();
                    }
                });
    }

    // Determines if user has given permission to use location: From zybooks
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Find the location when permission is granted
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                findLocation();
            }
        }
    }

}