package com.example.foodfinderapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

import androidx.core.content.res.ResourcesCompat;

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
        Business b0 = new Business("Spill The Beans", "Ice Cream Shop", 34.6836f, -82.8379f);
        b0.addReward(new Reward("10% Off Purchase", "", 50));
        b0.addReward(new Reward("Free Ice Cream", "Medium Ice Cream Cone", 100));
        b0.addReceipt(000, 10);
        b0.addReceipt(111, 100);
        m_businesses.add(b0);

        Business b1 = new Business("Tiger Sports Shop", "Sportswear Store", 34.6830f, -82.8372f);
        b1.addReward(new Reward("Free Small Car Decal", "", 100));
        b1.addReward(new Reward("15% Off Purchase", "", 250));
        b1.addReward(new Reward("Free T-Shirt", "", 500));
        b1.addReceipt(000, 50);
        b1.addReceipt(111, 50);
        m_businesses.add(b1);

        Business b2 = new Business("Insomnia Cookies", "Cookie Shop", 34.6836f, -82.8357f);
        b2.addReward(new Reward("Free Cookie", "Sugar Cookie", 25));
        b2.addReward(new Reward("Free Cookie", "Chocolate Chip", 25));
        b2.addReceipt(000, 5);
        b2.addReceipt(111, 25);
        m_businesses.add(b2);

        User testUser = new User("Tom", "tomthecat");
        m_users.add(testUser);
        testUser = new User("Jerry", "jerrythemouse");
        m_users.add(testUser);
        currentUser = m_users.get(0);
    }

    public boolean codeFound(int code, int busID){
        return getBusiness(busID).containsReceipt(code);
    }
    public void incrementPoints(int biz, int code){
        currentUser.incrementPoints(biz, getBusiness(biz).getPoints(code));
        getBusiness(biz).removeReceipt(code);
    }

    // Getter for single business in database
    public Business getBusiness (int index) {
        return m_businesses.get(index);
    }

    // Getter for current user
    public User getCurrentUser(){
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