package com.mejorescolegios.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth auth;
    private TextView saludoTextView;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las vistas
        signOut = findViewById(R.id.btnSignOut);
        saludoTextView = findViewById(R.id.saludoTextView);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Obtener usuario actual
        FirebaseUser usuario = auth.getCurrentUser();
        if (usuario != null) {
            String nombreUsuario = usuario.getDisplayName() != null ? usuario.getDisplayName() : "Usuario";
            String uidUsuario = usuario.getUid();

            saludoTextView.setText("Hola, " + nombreUsuario + ". \nEl ID de tu usuario es: \n" + uidUsuario);
        } else {
            saludoTextView.setText("Por favor, inicia sesión");
        }

        // Configurar el botón de cerrar sesión
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Inicial.class));
                finish();
            }
        });
    }
}
