package com.example.wanderertravelapp.Class;

import android.database.sqlite.SQLiteDatabase;


public class Location {
    private String name;
    private String Address;
    private String Descrip;
    private String ImgID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String addressLine;


    public Location() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String descrip) {
        Descrip = descrip;
    }

    public String getImgID() {
        return ImgID;
    }

    public void setImgID(String imgID) {
        ImgID = imgID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void addToDB(SQLiteDatabase db)
    {
        try
        {
            String query = "INSERT INTO Location (Name,Address,Descrip)  " +
                    "VALUES('"+this.name+"', '"+this.Address+"', '"+this.Descrip+"')";
            db.execSQL(query);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
