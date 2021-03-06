package com.danp.compareaudio.ControllerAudio;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import com.danp.compareaudio.R;
import com.danp.compareaudio.controller.Camera2Service;
import com.danp.compareaudio.view.MainActivity;

public class VocalServicio extends Service implements DetectarSonidoListener {
    private DetectorThread detectorThread;
    private RecorderThread recorderThread;
    private static final int NOTIFICATION_Id = 001;
    public static final int DETECT_NONE = 0;
    public static final int DETECT_WHISTLE = 1;
    public static int selectedDetection = DETECT_NONE;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        initNotification();
        startDetection();
        return START_STICKY;
    }
    public void initNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"0")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Detección de silbidos")
                        .setContentText("La detección de silbidos está activada.");
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this,0,
                resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NOTIFICATION_Id, mBuilder.build());
    }
    public void startDetection(){
        selectedDetection = DETECT_WHISTLE;
        recorderThread = new RecorderThread();
        recorderThread.start();
        detectorThread = new DetectorThread(recorderThread);
        detectorThread.setDetectarSonidoListener(this);
        detectorThread.start();
        Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (recorderThread != null) {
            recorderThread.stopRecording();
            recorderThread = null;
        }
        if (detectorThread != null) {
            detectorThread.stopDetection();
            detectorThread = null;
        }
        selectedDetection = DETECT_NONE;
        Toast.makeText(this, "Servicio detenido", Toast.LENGTH_LONG).show();
        stopNotification();
    }
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onWhistleDetected() {
        Intent intent = new Intent(this, VocalAlerta.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.stopSelf();
    }
    public void stopNotification(){
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.cancel(NOTIFICATION_Id);
    }
}