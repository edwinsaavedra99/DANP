package com.danp.compareaudio.ControllerAudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danp.compareaudio.R;

public class RecoderActivity extends AppCompatActivity {

    private RecorderThread recorderThread;
    private Button start,stop,radioStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoder);

        recorderThread = new RecorderThread();

        start = findViewById(R.id.startRecording);
        stop = findViewById(R.id.stopRecording);
        radioStart = findViewById(R.id.radioStart);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recorderThread.start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // recorderThread.stopRecording();

                ShareSound.SpecificAudio = recorderThread.getFrameBytes();
                System.out.println(ShareSound.SpecificAudio);
            }
        });
        radioStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(recorderThread.getGrabadorAudio().getAudioSource());
            }
        });

        MediaPlayer mediaPlayer = new MediaPlayer();
        // mediaPlayer.
    }




}