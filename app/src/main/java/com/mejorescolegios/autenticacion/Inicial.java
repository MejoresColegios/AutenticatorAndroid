package com.mejorescolegios.autenticacion;

import static com.mejorescolegios.autenticacion.R.id.signUpRedirectText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Inicial extends AppCompatActivity {

    private ImageView email;
    private TextView tvEmail;
    private TextView signupRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        email = findViewById(R.id.imageView2);
        tvEmail = findViewById(R.id.textView4);
        signupRedirectText = findViewById(R.id.signUpRedirectText);

        // al pulsar en la imagen email redirigimos a la actividad activity_login
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicial.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // al pulsar en el texto tvEmail redirigimos a la actividad activity_login
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicial.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) {
            startActivity(new Intent(Inicial.this, SignUpActivity.class));
        }
        });
    }
}