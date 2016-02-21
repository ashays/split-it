package com.splitit.splitit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    private String id;
    private HashMap<Person, Integer> charges;

    public Trip(String tripName, String tripID) {
        this.tripName = tripName;
        this.id = tripID;
    }

    public void addPerson(Person person) {
        if (people.contains(person)) {
            return;
        }
        people.add(person);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setCharge(Person person, int charge) {
        charges.put(person, (Integer)charge);
    }

    /*public String getCharge(Person person, int position) {
        
    }*/

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
