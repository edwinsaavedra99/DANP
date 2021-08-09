package com.danp.compareaudio.ControllerAudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danp.compareaudio.R;
import com.musicg.fingerprint.FingerprintManager;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.fingerprint.FingerprintSimilarityComputer;
import com.musicg.wave.Wave;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

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

        start.setOnClickListener(view -> recorderThread.start());
        stop.setOnClickListener(view -> {
            recorderThread.stopRecording();
            ShareSound.SpecificAudio = recorderThread.getFrameBytes();
            System.out.println(ShareSound.SpecificAudio);


            /*byte[] firstFingerPrint = new FingerprintManager().extractFingerprint(new Wave("White Wedding.wav"));
            byte[] secondFingerPrint = new FingerprintManager().extractFingerprint(new Wave("Poison.wav"));
            // Compare fingerprints
            FingerprintSimilarity fingerprintSimilarity = new FingerprintSimilarityComputer(firstFingerPrint, secondFingerPrint).getFingerprintsSimilarity();
            System.out.println("Similarity score = " + fingerprintSimilarity.getScore());*/

        });

        radioStart.setOnClickListener(view -> System.out.println(recorderThread.getGrabadorAudio().getAudioSource()));

        MediaPlayer mediaPlayer = new MediaPlayer();

        /*try {
            new Converter().convert("White Wedding.mp3", "White Wedding.wav");
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        try {
            new Converter().convert("Poison.mp3", "Poison.wav");
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }*/


        // mediaPlayer.
    }




}