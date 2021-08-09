package com.danp.compareaudio.ControllerAudio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.danp.compareaudio.R;
import com.danp.compareaudio.controller.Camera2Service;

import androidx.appcompat.app.AppCompatActivity;

public class VocalAlerta extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer miSonido;
    private Button bReturn;
    private Button bRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerta);
        stopService(new Intent(getBaseContext(), VocalServicio.class));
        initialize();

        /*miSonido = MediaPlayer.create(this,R.raw.alarm);
        miSonido.setOnCompletionListener(this);
        miSonido.start();*/

        Intent siguiente= new Intent(VocalAlerta.this, Camera2Service.class);
        startService(siguiente);

    }
    private void initialize() {
        bReturn = (Button) findViewById(R.id.quitButton);
        bRestart = (Button) findViewById(R.id.restartButton);
        bReturn.setOnClickListener(this);
        bRestart.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.restartButton :
                miSonido.release();
                Intent i = new Intent(getBaseContext(),VocalServicio.class);
                startService(i);
                finish();
                break;
            case R.id.quitButton:
                Intent siguiente= new Intent(VocalAlerta.this, Camera2Service.class);
                stopService(siguiente);
                //miSonido.release();
                finish();
                break;
        }
    }
    @Override
    public void onCompletion(MediaPlayer arg0) {
        arg0.start();
    }

}