package com.example.foodfinderapp;

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
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearbyBusinessesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyBusinessesFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap m_googleMap;
    MapView m_mapView;
    View m_view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NearbyBusinessesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearbyBusinessesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearbyBusinessesFragment newInstance(String param1, String param2) {
        NearbyBusinessesFragment fragment = new NearbyBusinessesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        m_view = inflater.inflate(R.layout.fragment_nearby_businesses, container, false);
        return m_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_mapView = (MapView) m_view.findViewById(R.id.map);
        if (m_mapView != null) {
            m_mapView.onCreate(null);
            m_mapView.onResume();
            m_mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        m_googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(34.6834, -82.8374))
                .title("Clemson"));

        CameraPosition clemson = CameraPosition.builder()
                .target(new LatLng(34.6834, -82.8374))
                .zoom(16)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(clemson));
    }
}