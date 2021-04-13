package com.example.foodfinderapp;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static int numberOfUsers;

    private int id;
    private String name;
    private ArrayList<Business> favoriteBusList;
    private ArrayList<Integer> favoriteBusIndexList;
    private HashMap<Integer, Integer> pointsCount;

    public User(String name){
        numberOfUsers++;
        this.name = name;

        //weak and breaks if you delete users, but we won't worry about that
        id = numberOfUsers;

        favoriteBusIndexList = new ArrayList<>();
        favoriteBusList = new ArrayList<>();
        pointsCount = new HashMap<>();
    }

    public void addBusiness(Business b){
        pointsCount.putIfAbsent(b.getIndex(), 0);
        System.out.println("New Val set! "+pointsCount.get(b.getIndex()));
    }

    public void addFavorite(Business b){
        favoriteBusList.add(b);
        favoriteBusIndexList.add(b.getIndex());
    }

    public boolean isFavorite(int index){
        return favoriteBusIndexList.contains(index);
    }

    public ArrayList<Business> getFavoriteList(){
        return favoriteBusList;
    }

    public boolean busListContains(Business b){
        System.out.println("B: "+ b.getIndex());
        System.out.println("D: "+ pointsCount.containsKey(b.getIndex()));
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


    //Could create a new businessIndex and add to it if index doesn't already exist, IDK
    public void incrementPoints(int businessIndex, int pointAmount){
        System.out.print("Point Count: ");


        System.out.println(pointsCount);
        pointsCount.put(businessIndex, pointAmount+pointsCount.get(businessIndex));
    }
}
