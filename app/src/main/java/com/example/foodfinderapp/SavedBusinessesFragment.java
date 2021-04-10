package com.example.foodfinderapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 *   Fragment for saved businesses page
 */
public class SavedBusinessesFragment extends Fragment {

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

        // Create the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.savedBusinessesRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create the adapter
        Adapter adapter = new Adapter(Database.getInstance().getSavedBusinesses());
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Called after view is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     *   The holder for a list item
     */
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        // Called when item is clicked
        @Override
        public void onClick(View view) {
            Log.d("%%%%%", Integer.toString(this.getLayoutPosition()));
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
        }

        // Getter for the number of items in the list
        @Override
        public int getItemCount() {
            return m_savedBusinesses.size();
        }

    }

}