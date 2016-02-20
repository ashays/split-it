package com.splitit.splitit;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ciyengar on 2/20/16.
 */
public class Trip {
    private String tripName;
    private Person creator;
    private ArrayList<Person> people;
    private Date startDate;
    private Date endDate;
    private boolean activeTrip;

    public Trip(String tripName) {
        this.tripName = tripName;
    }

    public void addPerson(Person person) {
        if (people.contains(person)) {
            return;
        }
        people.add(person);
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripName() {
        return this.tripName;
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public void removePerson(Person person) {
        if (people.contains(person)) {
            people.remove(person);
        }
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }
}
