package com.splitit.splitit;

import java.util.ArrayList;

/**
 * Created by ciyengar on 2/20/16.
 */
public class Person {
    private String firstName, lastName, id;
    private ArrayList<Trip> trips;

    public Person(String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.trips = new ArrayList<Trip>();
    }

    public Person(String id) {
        this.id = id;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getID() { return id; }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> al) {
        this.trips = al;
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public String[] getTripNames() {
        String[] tripNames = new String[trips.size()];
        int i = 0;
        for (Trip t : trips) {
            tripNames[i] = t.getTripName();
            i++;
        }
        return tripNames;
    }

}
