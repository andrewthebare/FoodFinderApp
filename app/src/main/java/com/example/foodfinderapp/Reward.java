/**
 *   Names: Matthew Held and Andrew Bare
 *   Emails: mjheld@clemson.edu
 *           abare@clemson.edu
 */

package com.example.foodfinderapp;

import java.io.Serializable;

/**
 *   Stores information about a single reward
 */
public class Reward implements Serializable {

    private String m_name;
    private String m_description;
    private int m_pointsNeededToClaim;

    // Constructor
    public Reward (String name, String description, int pointsNeededToClaim) {
        m_name = name;
        m_description = description;
        m_pointsNeededToClaim = pointsNeededToClaim;
    }

    // Getter for name of reward
    public String getName () {
        return m_name;
    }

    // Getter for description of reward
    public String getDescription () {
        return m_description;
    }

    // Getter for the points needed to claim the reward
    public int getPointsNeededToClaim () {
        return m_pointsNeededToClaim;
    }

}