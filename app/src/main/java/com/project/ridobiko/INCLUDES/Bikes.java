package com.project.ridobiko.INCLUDES;

public class Bikes {
    private int BikeID,BikeRent,BikeDeposit;
    private String BikeName,BikePic;

    public Bikes(int bikeID, int bikeRent, int bikeDeposit, String bikePic, String bikeName) {
        BikeID = bikeID;
        BikeRent = bikeRent;
        BikeDeposit = bikeDeposit;
        BikePic = bikePic;
        BikeName = bikeName;
    }

    public int getBikeID() {
        return BikeID;
    }

    public String getBikePic() {
        return BikePic;
    }

    public String getBikeName() {
        return BikeName;
    }

    public int getBikeRent() {
        return BikeRent;
    }

    public int getBikeDeposit() {
        return BikeDeposit;
    }

}
