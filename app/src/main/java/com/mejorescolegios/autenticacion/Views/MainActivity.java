package com.mejorescolegios.autenticacion.Views;

import static com.mejorescolegios.autenticacion.R.string.sesi_n_cerrada;

import android.annotation.SuppressLint;
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
import com.mejorescolegios.autenticacion.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth auth;
    private TextView saludoTextView;
    private Button signOut;
    private Button btnViewContacts;

    @SuppressLint("MissingInflatedId")
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
            String nombreUsuario = usuario.getDisplayName() != null ? usuario.getDisplayName() : getString(R.string.usuario);
            String uidUsuario = usuario.getUid();

            saludoTextView.setText(getString(R.string.hola) + nombreUsuario + ". \n" +
                    getString(R.string.el_id_de_tu_usuario_es) + uidUsuario);
        } else {
            saludoTextView.setText(R.string.txtSigIn);
        }

        // Configurar el botón para ver contactos
        btnViewContacts = findViewById(R.id.btnViewContacts);
        btnViewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, ViewContacts.class));
            }
        });

        // Configurar el botón de cerrar sesión
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(MainActivity.this, sesi_n_cerrada, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Inicial.class));
                finish();
            }
        });
    }
}
