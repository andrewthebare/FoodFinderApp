package com.example.foodfinderapp;

import java.util.ArrayList;

/**
 *   The database for the project implemented using the Singleton design pattern
 */
public class Database {

    private static Database s_database;

    // List of all businesses in the database
    private ArrayList<Business> m_businesses = new ArrayList<>();
    private ArrayList<User> m_users = new ArrayList<>();
    private User currentUser;

    // Getter for the singleton instance
    public static Database getInstance() {
        if (s_database == null) {
            s_database = new Database();
        }
        return s_database;
    }

    // Constructor ( Only called once )
    private Database () {
        // Add data to database
        Business b0 = new Business("Business 0", "This is business 0.", 34.6832f, -82.8373f);
        b0.addReward(new Reward("Reward 0", "This is reward 0.", 5));
        b0.addReward(new Reward("Reward 1", "This is reward 1.", 15));
        m_businesses.add(b0);

        Business b1 = new Business("Business 1", "This is business 1.", 34.6846f, -82.8373f);
        b1.addReward(new Reward("Reward 0", "This is reward 0.", 10));
        b1.addReward(new Reward("Reward 1", "This is reward 1.", 50));
        b1.addReward(new Reward("Reward 2", "This is reward 2.", 100));
        m_businesses.add(b1);

        Business b2 = new Business("Business 2", "This is business 2.", 34.6839f, -82.8354f);
        b2.addReward(new Reward("Reward 0", "This is reward 0.", 25));
        b2.addReward(new Reward("Reward 1", "This is reward 1.", 25));
        m_businesses.add(b2);

        User testUser = new User("Tom");
        m_users.add(testUser);
        testUser = new User("Jerry");
        m_users.add(testUser);
        currentUser = m_users.get(0);
    }

    // Getter for single business in database
    public Business getBusiness (int index) {
        return m_businesses.get(index);
    }

    // Getter for current user
    public User getCurrentUser(){
        System.out.println(currentUser.getFavoriteList().toString());
        return currentUser;
    }

    // Getter for the list of all businesses in the database
    public ArrayList<Business> getBusinesses () {
        return m_businesses;
    }

    // Getter for the list of saved businesses in the database
    public ArrayList<Business> getSavedBusinesses () {
        ArrayList<Business> savedBusinesses = new ArrayList<>();

        // Add saved businesses to new arraylist
        for (int i = 0; i < m_businesses.size(); i++) {
            if (m_businesses.get(i).getIfSaved() == true) {
                savedBusinesses.add(m_businesses.get(i));
            }
        }

        return savedBusinesses;
    }

    // Set if a business is saved or not
    public void setIfBusinessSaved (Business business, boolean isSaved) {
        m_businesses.get(business.getIndex()).setIfSaved(isSaved);
    }

    public void setCurrentUser(int index){
        currentUser = m_users.get(index);
    }

}