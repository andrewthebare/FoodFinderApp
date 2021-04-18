/**
 *   Names: Matthew Held and Andrew Bare
 *   Emails: mjheld@clemson.edu
 *           abare@clemson.edu
 */

package com.example.foodfinderapp;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class User {
    private static int numberOfUsers = 0;

    private int id;
    public String name;
    public String userName;
    public Bitmap profilePic;
    private String pw;
    private ArrayList<Business> favoriteBusList;
    private ArrayList<Integer> favoriteBusIndexList;
    private HashMap<Integer, Integer> pointsCount;

    public User(String name, String uName){
        numberOfUsers++;
        this.name = name;
        pw = "password";
        userName = uName;
        profilePic = null;

        //weak and breaks if you delete users, but we won't worry about that
        id = numberOfUsers;

        favoriteBusIndexList = new ArrayList<>();
        favoriteBusList = new ArrayList<>();
        pointsCount = new HashMap<>();
    }
    public User(String name, String uName, String pw){
        numberOfUsers++;
        this.name = name;
        this.pw=pw;
        userName = uName;
        profilePic = null;

        //weak and breaks if you delete users, but we won't worry about that
        id = numberOfUsers;

        favoriteBusIndexList = new ArrayList<>();
        favoriteBusList = new ArrayList<>();
        pointsCount = new HashMap<>();
    }

    public void addBusiness(Business b){
        pointsCount.putIfAbsent(b.getIndex(), 0);
//        System.out.println("New Val set! "+pointsCount.get(b.getIndex()));
    }

    public void addFavorite(Business b){
        favoriteBusList.add(b);
        favoriteBusIndexList.add(b.getIndex());
    }

    public void removeFavorite(Business b){
        favoriteBusIndexList.remove(favoriteBusIndexList.indexOf(b.getIndex()));
        Predicate<Business> pr= (Business bus) ->(bus.getIndex() ==b.getIndex());
        favoriteBusList.removeIf(pr);

//        System.out.println("\tfavoriteBusIndexList");
//        System.out.println(favoriteBusIndexList);
//        System.out.println("\tfavoriteBusList");
//        System.out.println(favoriteBusList);
    }

    public boolean isFavorite(int index){
        return favoriteBusIndexList.contains(index);
    }

    public ArrayList<Business> getFavoriteList(){
        return favoriteBusList;
    }

    public boolean busListContains(Business b){
        return pointsCount.containsKey(b.getIndex());
    }

    /**
     * NOTE Assumes this entry exists in the list
     *
     * Returns the number of points for a business this user has
     * @param businessIndex
     * @return
     */
    public int getPoints(int businessIndex){
        return pointsCount.get(businessIndex);
    }

    public int getIndex(){
        return id;
    }

    public boolean checkPassword(String password){
        return pw.equals(password);
    }


    //Could create a new businessIndex and add to it if index doesn't already exist, IDK
    public void incrementPoints(int businessIndex, int pointAmount){
        pointsCount.put(businessIndex, pointAmount+pointsCount.get(businessIndex));
    }

    public void decrementPoints(int businessIndex, int pointAmount){
        pointsCount.put(businessIndex, pointsCount.get(businessIndex)-pointAmount);
    }

    public void setPicture(Bitmap pic){
        profilePic = pic;
    }
}
