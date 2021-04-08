package com.example.foodfinderapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *   Fragment for nearby businesses page
 */
public class NearbyBusinessesFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap m_googleMap;
    private MapView m_mapView;
    private View m_view;

    private ArrayList<Business> m_businesses;
    private HashMap<Marker, Business> m_businessHashMap = new HashMap<>();

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

        // Create a position for the map to start out at
        CameraPosition clemson = CameraPosition.builder()
                .target(new LatLng(34.6834, -82.8374))
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

}