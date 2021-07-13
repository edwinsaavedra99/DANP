package com.danp.cameraoptions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button goEj01, goEj02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goEj01 = findViewById(R.id.goEj01);
        goEj01.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Ejer01.class);
            startActivity(intent);
        });
        goEj02 = findViewById(R.id.goEj02);
        goEj02.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Ejer02.class);
            startActivity(intent);
        });
    }

}