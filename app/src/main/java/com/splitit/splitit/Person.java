package com.splitit.splitit;

/**
 * Created by ciyengar on 2/20/16.
 */
public class Person {
    private String firstName, lastName, id;
    private ArrayList<Trip> trips;

    public Person(firstName, lastName, id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }
}
