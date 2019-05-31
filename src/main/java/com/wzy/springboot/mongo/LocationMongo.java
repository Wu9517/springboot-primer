package com.wzy.springboot.mongo;

/**
 * @author wzy
 */
public class LocationMongo {
    private String place;
    private String year;

    public LocationMongo(String place, String year) {
        this.place = place;
        this.year = year;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
