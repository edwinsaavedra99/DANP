package com.danp.cameraoptions;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ejer02 extends AppCompatActivity {

    private ImageButton imageButton;
    private final int MY_PERMISSIONS_REQUEST_CAMARA = 0;
    private final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 3;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 2;
    private Bitmap currentBitmap;
    private int flag = 0;
    private int flag2 = 0;
    private int flag3 = 0;
    String ExternalStorageDirectory = Environment.getExternalStorageDirectory() + File.separator;
    String rutacarpeta = "Lab09Ejer2/";
    File directorioImagenes = new File(ExternalStorageDirectory + rutacarpeta);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer02);
        Button openCamera = findViewById(R.id.openCamera);
        openCamera.setOnClickListener(view -> showAddFileProjectDialog());
        askPermits();
        if (!directorioImagenes.exists())
            directorioImagenes.mkdirs();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Permisos Lectura ...", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE );
            }
        } else {
            recuperarFoto();
        }
    }

    private void askPermits(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            flag2 = 0;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Permisos Almacenamiento Externo ...", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE );
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            flag3 = 0;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Permisos Camara ...", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMARA );
            }
        }
    }

    private void showAddFileProjectDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Nueva Foto: ");
        dialog.setMessage("Pulse para activar la camara" );
        dialog.setCancelable(false);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View add_layout = inflater.inflate(R.layout.data_project,null);
        imageButton = add_layout.findViewById(R.id.addImageProject);
        imageButton.setOnClickListener(v -> {
            try {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Toast.makeText(this, "Permisos Camara ...", Toast.LENGTH_LONG).show();
                    } else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMARA );
                    }
                }else{
                    abrirCamara ();
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
        dialog.setView(add_layout);
        dialog.setPositiveButton("Guardar", (dialog1, which) ->{
            if(flag == 1){
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    } else {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE );
                    }
                }else{
                    saveImage(currentBitmap);
                }
                flag = 0;
            } else {
                Toast.makeText(this, "No has tomado una foto todavia", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setNegativeButton("Cancelar", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMARA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(flag3!=1)
                        abrirCamara();
                } else {
                    Toast.makeText(this, "Sin Permisos de Camara", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Genial", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Sin Permisos de Lectura", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(flag2!=1) {
                        if (flag == 1) {
                            saveImage(currentBitmap);
                            flag = 0;
                        } else {
                            Toast.makeText(this, "No has tomado una foto todavia", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Sin Permisos de Almacenamiento externo", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void abrirCamara (){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    public void recuperarFoto() {
        String[] archivos = directorioImagenes.list();
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, archivos);
        ListView lv1 = findViewById(R.id.list);
        lv1.setAdapter(adaptador1);
    }

    private String createName (){
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "IMG_" + timeStamp + ".jpg";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            flag = 0;
            try{
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    currentBitmap = (Bitmap) extras.get("data");
                    imageButton.setImageBitmap(currentBitmap);
                    flag = 1;
                }else if (resultCode == RESULT_CANCELED){
                    Toast.makeText(this, "NO HAY SELECCION ALGUNA", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this, "An error has occurred...", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private void saveImage(Bitmap tempBitmap){
        try {
            String nombre = createName();
            int bitmapWidth = 120;
            int bitmapHeight = 120;
            Bitmap bitmapout = Bitmap.createScaledBitmap(tempBitmap, bitmapWidth, bitmapHeight, false);
            bitmapout.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(ExternalStorageDirectory + rutacarpeta + nombre));
            File filefinal = new File(ExternalStorageDirectory + rutacarpeta + nombre);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "Titulo");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.ImageColumns.BUCKET_ID, filefinal.toString().toLowerCase(Locale.getDefault()).hashCode());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, filefinal.getName().toLowerCase(Locale.getDefault()));
            }
            values.put("_data", filefinal.getAbsolutePath());
            ContentResolver cr = getContentResolver();
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            recuperarFoto();
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}