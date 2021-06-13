package com.danp.orientationam;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Implement an application that allows to identify the orientation of the device.
 * Use the following "Accelerometer and Magnetometer" sensors. The application
 * should display device orientation values on screen
 * in degrees (for each axis).
 *
 * @autor Edwin Saavedra
 * @version 01.00
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetic;
    private TextView accelerometer_value,magnetic_value,orientation_value;
    private float[] accelerationValues;
    private float[] magneticValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }
    /**
     * Refer var and get system service sensor manager
     */
    @SuppressLint("SetTextI18n")
    public void setup(){
        accelerometer_value = findViewById(R.id.value_sensor_acelerometer);
        magnetic_value = findViewById(R.id.value_sensor_magnometer);
        orientation_value = findViewById(R.id.value_orientation);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mAccelerometer!=null){
            accelerometer_value.setText("I have Accelerometer");
        }else{
            accelerometer_value.setText("I don't have Accelerometer");
        }
        if(mMagnetic!=null){
            magnetic_value.setText("I have Magnetic");
        }else{
            magnetic_value.setText("I don't have Magnetic");
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
        float[] rotationMatrix;
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER: {
                    txt += "\n x: " + event.values[0] + " m/s";
                    txt += "\n y: " + event.values[1] + " m/s";
                    txt += "\n z: " + event.values[2] + " m/s";
                    accelerationValues = event.values.clone();
                    accelerometer_value.setText(txt);
                    rotationMatrix = generateRotationMatrix();
                    if (rotationMatrix != null){
                        determineOrientation(rotationMatrix);
                    }
                    break;
                }
                case Sensor.TYPE_MAGNETIC_FIELD: {
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];
                    magneticValues = event.values.clone();
                    rotationMatrix = generateRotationMatrix();
                    if (rotationMatrix != null){
                        determineOrientation(rotationMatrix);
                    }
                    magnetic_value.setText(txt);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    /**
     * Generate Rotation Matrix, I use sensor manger with getRotationMatrix
     */
    private float[] generateRotationMatrix() {
        float[] rotationMatrix = null;
        if (accelerationValues != null && magneticValues != null){
            rotationMatrix = new float[16];
            boolean rotationMatrixGenerated;
            rotationMatrixGenerated =  SensorManager.getRotationMatrix(rotationMatrix,null,accelerationValues,magneticValues);
            if (!rotationMatrixGenerated){
                rotationMatrix = null;
            }
        }
        return rotationMatrix;
    }

    /**
     * Calculate Orientation with degrees, I work azimuth, roll and pitch
     */
    private void determineOrientation(float[] rotationMatrix) {
        float[] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);
        double azimuth = Math.toDegrees(orientationValues[0]);
        double pitch = Math.toDegrees(orientationValues[1]);
        double roll = Math.toDegrees(orientationValues[2]);
        String txt = "";
        txt += "\n pitch: " + azimuth;
        txt += "\n roll: " + pitch;
        txt += "\n azimuth: " + roll;
        orientation_value.setText(txt);
    }
}