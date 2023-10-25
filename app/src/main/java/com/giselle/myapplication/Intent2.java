package com.giselle.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.CheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;
import android.widget.Toast; // Debes importar la clase Toast

public class Intent2 extends AppCompatActivity {

    TextView txtIntentName, txtIntentAge, txtIntentColor;
    CheckBox ckbIntentRemember;
    ConstraintLayout layoutIntentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);
        Intent intent = getIntent();

        // Inicialización de componentes
        txtIntentName = findViewById(R.id.txtIntentName);
        txtIntentAge = findViewById(R.id.txtIntentAge); // Actualiza este ID en tu XML
        txtIntentColor = findViewById(R.id.txtIntentColor);
        ckbIntentRemember = findViewById(R.id.ckbIntentRemember);
        layoutIntentColor = findViewById(R.id.layoutIntentColor); // Actualiza este ID en tu XML

        // Comprobar si los extras están presentes antes de usarlos
        if (intent != null) {
            txtIntentName.setText(intent.getStringExtra("IntentName"));
            String ageText = "No age found";
            int intentAge = intent.getIntExtra("IntentAge", 0);
            if (intentAge != 0) {
                ageText = "Age: " + intentAge;
            }
            txtIntentAge.setText(ageText); // Actualiza el ID en tu XML
            txtIntentColor.setText(intent.getStringExtra("IntentColor"));

            layoutIntentColor.setBackgroundColor(Color.parseColor(intent.getStringExtra("IntentColor")));
            ckbIntentRemember.setChecked(intent.getBooleanExtra("IntentRemember", false));

            if (intent.getStringExtra("IntentColor").contains("#")){
                try {
                    layoutIntentColor.setBackgroundColor((Color.parseColor((intent.getStringExtra("IntentColor")))));
                }catch(Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
        } else {
            Toast.makeText(this, "No se encontraron datos de la intención.", Toast.LENGTH_SHORT).show();
        }
    }


}
