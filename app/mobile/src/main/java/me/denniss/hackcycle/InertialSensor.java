package me.denniss.hackcycle;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by dennis on 8/15/15.
 */
public class InertialSensor implements SensorEventListener{


    private SensorManager sensorManager;
    private Sensor accel;
    private Sensor magn;
    private Sensor gyro;



    private float[] accel_data;
    private float[] gyro_data;
    private float[] magn_data;

    public InertialSensor(Activity a){
        sensorManager = (SensorManager)a.getSystemService(Context.SENSOR_SERVICE);

        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magn = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }


    private int n = 0;

    @Override
    public final void onSensorChanged(SensorEvent event) {

        n++;

        //if(n % 100 == 0)
        //    Log.i("gesture", "got some events");

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel_data = event.values;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magn_data = event.values;
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
            gyro_data = event.values;


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }



    public void resume(){
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        //sensorManager.registerListener(this, magn, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void pause(){
        sensorManager.unregisterListener(this);
    }

}