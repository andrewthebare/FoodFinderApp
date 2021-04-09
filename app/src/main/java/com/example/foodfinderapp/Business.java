package com.example.foodfinderapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *   Stores information about a single business
 */
public class Business implements Serializable {

    private static int businessNum = 0;

    private int m_index;
    private String m_name;
    private String m_description;
    private float m_lat;
    private float m_lng;
    private ArrayList<Reward> rewards = new ArrayList<>();
    private boolean m_isSaved = false;

    // Constructor
    public Business (String name, String description, float lat, float lng) {
        // Index updated statically
        m_index = businessNum;
        businessNum++;

        m_name = name;
        m_description = description;
        m_lat = lat;
        m_lng = lng;
    }

    // Getter for index of business
    public int getIndex () {
        return m_index;
    }

    // Getter for name of business
    public String getName () {
        return m_name;
    }

    // Getter for description of business
    public String getDescription () {
        return m_description;
    }

    // Getter for latitude of business
    public float getLat () {
        return m_lat;
    }

    // Getter for longitude of business
    public float getLng () {
        return m_lng;
    }

    // Getter to see if business is saved
    public boolean getIfSaved () {
        return m_isSaved;
    }

    // Setter for saving business
    public void setIfSaved (boolean isSaved) {
        m_isSaved = isSaved;
    }

    // Adds a reward to a business
    public void addReward (Reward reward) {
        rewards.add(reward);
    }

}