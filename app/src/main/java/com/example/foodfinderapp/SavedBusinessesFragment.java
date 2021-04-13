package com.example.foodfinderapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *   Fragment for saved businesses page
 */
public class SavedBusinessesFragment extends Fragment {

    TextView m_info;

    RecyclerView m_recyclerView;
    Adapter m_adapter;

    // Required empty public constructor
    public SavedBusinessesFragment() {

    }

    // Factory method to create new instance
    public static SavedBusinessesFragment newInstance() {
        SavedBusinessesFragment fragment = new SavedBusinessesFragment();
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
        View view = inflater.inflate(R.layout.fragment_saved_businesses, container, false);

        m_info = view.findViewById(R.id.infoTV);

        // Create the recycler view
        m_recyclerView = view.findViewById(R.id.savedBusinessesRV);
        m_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        redrawPage();

        return view;
    }

    // Called after view is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Called when fragment resumes
    @Override
    public void onResume() {
        super.onResume();

        // Redraw the page
        redrawPage();
    }

    // Redraws the page
    private void redrawPage () {
        ArrayList<Business> savedBusinesses = Database.getInstance().getCurrentUser().getFavoriteList();

        // Determine if to show info
        if (savedBusinesses.size() == 0) {
            m_info.setVisibility(View.VISIBLE);
        } else {
            m_info.setVisibility(View.INVISIBLE);
        }

        // Refresh adapter
        m_adapter = new Adapter(savedBusinesses);
        m_recyclerView.setAdapter(m_adapter);
    }

    /**
     *   The holder for a list item
     */
    private class Holder extends RecyclerView.ViewHolder {

        Business m_business;

        TextView m_businessName;
        TextView m_points;

        // Constructor
        public Holder (LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.saved_business_list_item, parent, false));

            m_businessName = itemView.findViewById(R.id.listItemBusinessNameTV);
            m_points = itemView.findViewById(R.id.listItemPointsTV);
        }

        // Sets the properties of the views
        public void bind (Business business) {
            m_business = business;
            m_businessName.setText(business.getName());
            m_points.setText(Integer.toString(business.getPoints()));
        }

    }

    /**
     *   The adapter for the list
     */
    private class Adapter extends RecyclerView.Adapter<Holder> {

        private ArrayList<Business> m_savedBusinesses;

        // Constructor
        public Adapter (ArrayList<Business> savedBusinesses) {
            m_savedBusinesses = savedBusinesses;
        }

        // Called when a new item is added to the list
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new Holder(layoutInflater, parent);
        }

        // Called to bind view holder
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bind(m_savedBusinesses.get(position));

            // Setup click listener
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {

                        // Called when item in list is clicked on
                        @Override
                        public void onClick (View view) {
                            // Start new activity that shows the business page of the list item that was clicked on
                            Intent intent = new Intent (getActivity(), BusinessActivity.class);
                            intent.putExtra("business", m_savedBusinesses.get(position));
                            startActivity(intent);
                        }
                    });
        }

        // Getter for the number of items in the list
        @Override
        public int getItemCount() {
            return m_savedBusinesses.size();
        }

    }

}