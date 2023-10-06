package com.example.myapplication;

public class TripDBInput {
    public static final String TABLE_NAME = "Trip";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESTINATION = "destination";
    public static final String COL_DATE = "date";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONENUMBER = "phoneNumber";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_RISKASSESSMENT = "riskAssessment";

    public static final String CREATE_TABLE_SQLITE = "CREATE TABLE IF NOT EXISTS " +
            "Trip (id INTEGER PRIMARY KEY, name TEXT NOT NULL, destination TEXT NOT NULL, " +
            "date TEXT NOT NULL, email TEXT NOT NULL, phoneNumber TEXT NOT NULL," +
            " description TEXT , riskAssessment TEXT)";


    public static final String DELETE_TABLE_SQLITE = "DROP TABLE IF EXISTS Trip";
}
