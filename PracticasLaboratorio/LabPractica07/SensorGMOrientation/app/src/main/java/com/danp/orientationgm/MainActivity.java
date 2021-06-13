package com.danp.orientationgm;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mGravity;
    private Sensor mMagnetic;
    private float[] gravityValues;
    private float[] magneticValues;
    private TextView gravity_value,magnetic_value,orientation_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }
    public void setup(){
        gravity_value = findViewById(R.id.value_sensor_gravity);
        magnetic_value = findViewById(R.id.value_sensor_magnometer);
        orientation_value = findViewById(R.id.value_orientation);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if(mMagnetic!=null){
            magnetic_value.setText("I have Magnetic");
        }else{
            magnetic_value.setText("I don't have Magnetic");
        }
        if(mGravity!=null){
            gravity_value.setText("I have Gravity");
        }else{
            gravity_value.setText("I don't have Gravity");
        }
    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        String txt = "";
        float[] rotationMatrix;
        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_GRAVITY: {
                    txt += "\n x: " + event.values[0];
                    txt += "\n y: " + event.values[1];
                    txt += "\n z: " + event.values[2];
                    gravity_value.setText(txt);
                    gravityValues = event.values.clone();
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
                    magnetic_value.setText(txt);
                    magneticValues = event.values.clone();
                    rotationMatrix = generateRotationMatrix();
                    if (rotationMatrix != null){
                        determineOrientation(rotationMatrix);
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    private float[] generateRotationMatrix() {
        float[] rotationMatrix = null;
        if (gravityValues != null && magneticValues != null){
            rotationMatrix = new float[16];
            boolean rotationMatrixGenerated;
            rotationMatrixGenerated =  SensorManager.getRotationMatrix(rotationMatrix,null,gravityValues,magneticValues);
            if (!rotationMatrixGenerated){
                rotationMatrix = null;
            }
        }
        return rotationMatrix;
    }
    private void determineOrientation(float[] rotationMatrix) {
        float[] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);
        double azimuth = Math.toDegrees(orientationValues[0]);
        double pitch = Math.toDegrees(orientationValues[1]);
        double roll = Math.toDegrees(orientationValues[2]);
        String txt = "";
        if (Math.abs(pitch) <= 20){
            if(roll<=0) {
                txt += "\n Result: " + onHorizontal2();
            }else{
                txt += "\n Result: " + onHorizaontal1();
            }
        }else{
            if(pitch <= 0){
                txt += "\n Result: " + onVertical1();
            }else {
                txt += "\n Result: " + onVertical2();
            }
        }
        orientation_value.setText(txt);
    }
    private String onVertical1(){
        return "Vertical 1";
    }
    private String onVertical2(){
        return "Vertical 2";
    }
    private String onHorizaontal1(){
        return "Horizontal 1";
    }
    private String onHorizontal2(){
        return "Horizontal 2";
    }
}