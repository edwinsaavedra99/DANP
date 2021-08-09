package com.danp.compareaudio.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danp.compareaudio.ControllerAudio.RecoderActivity;
import com.danp.compareaudio.R;
import com.danp.compareaudio.controller.Camera2Service;
import com.danp.compareaudio.ControllerAudio.VocalServicio;

public class MainActivity extends AppCompatActivity {
    private Button start,stop;
    private Button takePictureButton;
    private Button stopButton;
    private Button gotoRecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takePictureButton = (Button) findViewById(R.id.btn_takepicture);
        stopButton = (Button) findViewById(R.id.btn_stop);
        gotoRecoder = findViewById(R.id.gotoRecoder);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente= new Intent(MainActivity.this, Camera2Service.class);
                startService(siguiente);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente= new Intent(MainActivity.this, Camera2Service.class);
                stopService(siguiente);

            }
        });
        /*if (AppCompatActivity.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && AppCompatActivity.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            AppCompatActivity.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1000);
            AppCompatActivity.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1000);
        }*/
        start = (Button) findViewById(R.id.btnIniciar);
        stop = (Button) findViewById(R.id.btnDetener);
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), VocalServicio.class));
            }
        });
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(),VocalServicio.class));
            }
        });
        gotoRecoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecoderActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}