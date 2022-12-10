package com.example.secondgame.utils;

import android.content.Context;

import im.delight.android.location.SimpleLocation;

public class GPS {
    private static GPS myGps = null;


    private Context context;

    public static GPS getInstance() {
        return myGps;
    }

    private SimpleLocation location;

    private GPS(Context context) {
        this.context = context;
        location = new SimpleLocation(context);

    }

    public static void init(Context context) {
        if (myGps == null) {
            myGps = new GPS(context);
        }
    }


    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public void start() {
        location.beginUpdates();
    }

    public void stop() {
        location.endUpdates();
    }
}
