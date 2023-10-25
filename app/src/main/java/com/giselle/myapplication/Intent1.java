package com.giselle.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Intent1 extends AppCompatActivity {

    EditText txtIntentNombre, txtIntentEdad, txtIntentColor;
    CheckBox ckbIntentRemember;
    Button btnIntentSend;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);

        sp = this.getSharedPreferences("UserPreference", Context.MODE_PRIVATE);

        //Inicializacion
        txtIntentNombre = findViewById(R.id.txtIntentNombre);
        txtIntentEdad = findViewById(R.id.txtIntentEdad);
        txtIntentColor = findViewById(R.id.txtIntentColor);
        ckbIntentRemember = findViewById(R.id.ckbIntentRemember);
        btnIntentSend = findViewById(R.id.btnIntentSend);

        //
        txtIntentNombre.setText(sp.getString("IntentName", ""));
        txtIntentEdad.setText("" + sp.getInt("IntentAge", 0));
        txtIntentColor.setText(sp.getString("IntentColor", "#000000"));
        ckbIntentRemember.setChecked(sp.getBoolean("IntentRembember", true));


        // Obtener los valores de la intención
        btnIntentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInfo();
            }
        });
    }

    private void sendInfo(){
        // Variables locales
        String name, color;
        int age;
        boolean remember;

        // Obtener contenido de componentes
        name = txtIntentNombre.getText().toString();
        color = txtIntentColor.getText().toString();
        age = Integer.parseInt(txtIntentEdad.getText().toString());
        remember = ckbIntentRemember.isChecked();

        //Almacenar informacion de SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        if (ckbIntentRemember.isChecked()){
            editor.putString("IntentName", name);
            editor.putInt("IntentAge", age);
            editor.putString("IntentColor", color);
            editor.putBoolean("IntentRemember", remember);
            editor.apply();

        }else{
          editor.clear();
          editor.apply();
        }

        // Enviar información a otra pantalla
        Intent intent = new Intent(Intent1.this, Intent2.class);
        intent.putExtra("IntentName", name);
        intent.putExtra("IntentAge", age);
        intent.putExtra("IntentColor", color);
        intent.putExtra("IntentRemember", remember);
        startActivity(intent);
    }
}
