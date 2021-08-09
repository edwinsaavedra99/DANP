package com.danp.compareaudio.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.danp.compareaudio.ControllerAudio.RecoderActivity;
import com.danp.compareaudio.R;
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
        start = findViewById(R.id.btnIniciar);
        stop = findViewById(R.id.btnDetener);
        start.setOnClickListener(v -> {
            startService(new Intent(getBaseContext(), VocalServicio.class));
        });
        stop.setOnClickListener(v -> {
            stopService(new Intent(getBaseContext(), VocalServicio.class));
        });

        gotoRecoder.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RecoderActivity.class);
            startActivity(intent);
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