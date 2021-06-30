package com.danp.lab08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button acti,acti2,acti3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acti = findViewById(R.id.button);
        acti2 = findViewById(R.id.button2);
        acti3 = findViewById(R.id.button3);
        acti.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Actividad1.class);
            startActivity(intent);
        });
        acti2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Actividad2.class);
            startActivity(intent);
        });
        acti3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Actividad3.class);
            startActivity(intent);
        });
    }
}