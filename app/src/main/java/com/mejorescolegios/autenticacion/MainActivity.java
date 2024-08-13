package com.mejorescolegios.autenticacion;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth auth;
    private TextView saludoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializa Firebase
        FirebaseApp.initializeApp(this);
        // Inicializa Firebase Authentication
        FirebaseAuth.getInstance();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        saludoTextView = findViewById(R.id.saludoTextView);
        FirebaseUser usuario = auth.getCurrentUser();
        if (usuario != null) {
            String nombreUsuario = usuario.getDisplayName() != null ? usuario.getDisplayName() : "Usuario";
            saludoTextView.setText("Hola, " + nombreUsuario);
        } else {
            // Manejar el caso en que el usuario no ha iniciado sesión
            saludoTextView.setText("Por favor, inicia sesión");
        }
    }
}