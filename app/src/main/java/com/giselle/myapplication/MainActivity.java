package com.giselle.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Declaración de variables
    private TextView holamundo;
    private Button btnExplicito;
    private Button btnImplicito;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("holamundo", "onCreate");

        // Inicialización de variables y enlace al componente
        holamundo = findViewById(R.id.txtHolaMundo);
        btnExplicito = findViewById(R.id.btnExplicito);
        btnImplicito = findViewById(R.id.btnImplicito);

        // Evento Click
        btnExplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Integración de Intent explícito para abrir Intent1
                Intent i = new Intent(MainActivity.this, Intent1.class);
                startActivity(i);
            }
        });


        // Evento Click
        btnImplicito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String webpage = "https://youtu.be/hVVkCn2MKfs?si=GkygVeDBgV6XOgCY";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(webpage));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("holamundo", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("holamundo", "onResume");
        holamundo.setText("Hola mundo desde la clase Java en el método onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("holamundo", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("holamundo", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("holamundo", "onDestroy");
    }
}
