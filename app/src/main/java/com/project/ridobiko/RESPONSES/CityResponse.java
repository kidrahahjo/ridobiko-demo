package com.project.ridobiko.RESPONSES;

import com.project.ridobiko.INCLUDES.City;

import java.util.List;

public class CityResponse {
    private String Message;

    private List<com.project.ridobiko.INCLUDES.City> City;

    public CityResponse(String Message, List<City> city) {
        this.Message = Message;
        this.City = city;
    }

    public String getMessage() {
        return Message;
    }

    public List<City> cityNames() {
        return City;
    }
}
