package com.example.foodfinderapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *   Stores information about a single business
 */
public class Business implements Serializable {

    private static int s_businessNum = 0;

    private int m_index;
    private String m_name;
    private String m_description;
    private float m_lat;
    private float m_lng;
    private ArrayList<Reward> m_rewards = new ArrayList<>();
    private boolean m_isSaved = false;
    private int m_points = 0;

    private HashMap<Integer, Integer> receiptCodes = new HashMap<>();

    // Constructor
    public Business (String name, String description, float lat, float lng) {
        // Index updated statically
        m_index = s_businessNum;
        s_businessNum++;

        m_name = name;
        m_description = description;
        m_lat = lat;
        m_lng = lng;
    }

    public void addReceipt(int code, int points){
        receiptCodes.putIfAbsent(code, points);
    }

    public void removeReceipt(int code){
        receiptCodes.remove(code);
    }

    public int getPoints(int code){
        return receiptCodes.get(code);
    }

    public boolean containsReceipt(int r){
        return receiptCodes.containsKey(r);
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

    // Getter for number of points
    public int getPoints () {
        return m_points;
    }

    // Setter for saving business
    public void setIfSaved (boolean isSaved) {
        m_isSaved = isSaved;
    }

    // Setter for number of points
    public void setPoints (int points) {
        m_points = points;
    }

    // Adds a reward to a business
    public void addReward (Reward reward) {
        m_rewards.add(reward);
    }

}