package com.project.ridobiko.RESPONSES;

import com.project.ridobiko.INCLUDES.Bikes;

import java.util.List;

public class BikeResponse {
    private String Message  ;
    private List<Bikes> BikeDetails;

    public BikeResponse(String Message, List<Bikes> BikeDetails) {
        this.BikeDetails = BikeDetails;
        this.Message = Message;
    }

    public String getMessage() {
        return Message;
    }

    public List<Bikes> getBikes() {
        return BikeDetails;
    }
}
