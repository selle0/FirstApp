package com.giselle.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;

public class MainActivity3 extends AppCompatActivity {

    EditText txtNombre, txtEmail, txtPass;
    Button btnRegistrar;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        mAuth = FirebaseAuth.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().toString().trim();
        String correo = txtEmail.getText().toString().trim();
        String contrasena = txtPass.getText().toString();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(MainActivity3.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else if (contrasena.length() < 6) {
            Toast.makeText(MainActivity3.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity3.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                limpiarFormulario();

                                // Crear un Intent para abrir MainActivity5 y enviar el nombre del usuario
                                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                                intent.putExtra("nombre_usuario", nombre);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity3.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtPass.setText("");
    }
}



