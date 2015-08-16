package me.denniss.hackcycle;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by dennis on 8/15/15.
 */
public class LocationSensor implements LocationListener {

    public static Location lastlocation;

    public LocationSensor(Activity ctx){
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
    }



    @Override
    public void onLocationChanged(Location location) {

        Log.i("location", "" + location.getLatitude());
        lastlocation = location;
        Socket.inst.send_data(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
