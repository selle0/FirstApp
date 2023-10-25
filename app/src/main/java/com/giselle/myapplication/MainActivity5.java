package com.giselle.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import android.content.Intent;

public class MainActivity5 extends AppCompatActivity {

    EditText txtNewName;
    Button btnChangeNameAndSendVerification;
    TextView txtCurrentName;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        txtNewName = findViewById(R.id.txtNewName);
        btnChangeNameAndSendVerification = findViewById(R.id.btnChangeNameAndSendVerification);
        txtCurrentName = findViewById(R.id.txtCurrentName);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            String nombreUsuario = intent.getStringExtra("nombre_usuario");
            if (nombreUsuario != null) {
                txtCurrentName.setText("Nombre actual: " + nombreUsuario);
            }
        }

        btnChangeNameAndSendVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserNameAndSendVerification();
            }
        });
    }

    private void changeUserNameAndSendVerification() {
        String newName = txtNewName.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                txtCurrentName.setText("Nombre actual: " + newName);
                                Toast.makeText(MainActivity5.this, "Nombre actualizado exitosamente", Toast.LENGTH_SHORT).show();

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MainActivity5.this, "Se ha enviado un correo de verificación", Toast.LENGTH_SHORT).show();

                                                    // Redirigir a MainActivity4 después de enviar la verificación
                                                    Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                                                    startActivity(intent);

                                                } else {
                                                    Toast.makeText(MainActivity5.this, "Error al enviar el correo de verificación", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(MainActivity5.this, "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
