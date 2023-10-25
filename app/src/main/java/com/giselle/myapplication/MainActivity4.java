//este es el login
package com.giselle.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity4 extends AppCompatActivity {

    private EditText txtEmail, txtPass;
    private Button btnLogin, btnRegister;
    private FirebaseAuth mAuth;
    private boolean isAccountDisabled = false; // Variable booleana que simula la inhabilitación de la cuenta

    private CheckBox cbxRecordar;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        sp = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        cbxRecordar = findViewById(R.id.cbxRecordar);

        txtEmail.setText(sp.getString("intEmail", ""));
        txtPass.setText(sp.getString("intPassword", ""));
        cbxRecordar.setChecked(sp.getBoolean("intRemember", false));

        mAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya ha iniciado sesión
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            irMain();
        } else {
            Toast.makeText(MainActivity4.this, "Se te ha enviado un correo de verificacion", Toast.LENGTH_SHORT).show();
        }


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irRegistro();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
                recordar();
            }
        });
    }

    private void iniciarSesion() {
        String correo = txtEmail.getText().toString().trim();
        String contraseña = txtPass.getText().toString();

        if (correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(MainActivity4.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    if (user.isEmailVerified()) {
                                        if (!isAccountDisabled) {
                                            // Inicio de sesión exitoso y correo verificado, y la cuenta no está inhabilitada
                                            Toast.makeText(MainActivity4.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                            irMain();
                                        } else {
                                            // Cuenta inhabilitada, mostrar mensaje apropiado
                                            Toast.makeText(MainActivity4.this, "Cuenta inhabilitada. Contacta al soporte.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Inicio de sesión exitoso, pero el correo no está verificado
                                        Toast.makeText(MainActivity4.this, "Por favor, verifica tu correo electrónico", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // No se pudo obtener el usuario actual
                                    Toast.makeText(MainActivity4.this, "Error en el inicio de sesión: No se pudo obtener el usuario actual", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Error en el inicio de sesión
                                Toast.makeText(MainActivity4.this, "Error en el inicio de sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void irMain() {
        Intent intent = new Intent(MainActivity4.this, NavigationDrawer.class);
        startActivity(intent);
    }

    private void irRegistro() {
        Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
        startActivity(intent);
    }

    private void recordar() {
        String email, password;
        boolean remember;

        email = txtEmail.getText().toString();
        password = txtPass.getText().toString();
        remember = cbxRecordar.isChecked();

        // Almacenar información en SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("intEmail", email);
        editor.putString("intPassword", password);
        editor.putBoolean("intRemember", remember);
        editor.apply();
    }
}
