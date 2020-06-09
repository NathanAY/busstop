package com.example.domain;

import java.time.LocalTime;

public class Service {

    private final String company;
    private final LocalTime departure;
    private final LocalTime arrival;

    public Service(String company, LocalTime departure, LocalTime arrival) {
        this.company = company;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getCompany() {
        return company;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }
}
