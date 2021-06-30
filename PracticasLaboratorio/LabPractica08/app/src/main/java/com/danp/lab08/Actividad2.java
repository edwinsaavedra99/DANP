package com.danp.lab08;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Actividad2 extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private TextView accelerometer_value;
    private float[] accelerationValues,gravity = {0,0,0},linear_acceleration = {0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);
        setup();
    }
    /**
     * Refer var and get system service sensor manager
     */
    @SuppressLint("SetTextI18n")
    public void setup(){
        accelerometer_value = findViewById(R.id.value_sensor_acelerometer);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mAccelerometer!=null){
            accelerometer_value.setText("I have Accelerometer");
        }else{
            accelerometer_value.setText("I don't have Accelerometer");
        }
    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * Check Sensors Accelerometer and Magnetic Field
     */
    @Override
    public final void onSensorChanged(SensorEvent event) {
        String txt = "";
        final float alpha = (float) 0.8;
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER: {

                    gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                    linear_acceleration[0] = event.values[0] - gravity[0];
                    linear_acceleration[1] = event.values[1] - gravity[1];
                    linear_acceleration[2] = event.values[2] - gravity[2];

                    // |AT| = sqrt (X**2 + Y**2 + Z**2)

                    txt += "\n Aceleracion Total Lineal: " +
                            (Math.sqrt(linear_acceleration[0]*linear_acceleration[0]  +
                            linear_acceleration[1]*linear_acceleration[1] +
                            linear_acceleration[2]*linear_acceleration[2]));
                    txt += "\n Aceleracion Total: " +
                            (Math.sqrt(event.values[0]*event.values[0]
                                    + event.values[1]*event.values[1] +
                                    event.values[2]*event.values[2]));

                    accelerometer_value.setText(txt);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}