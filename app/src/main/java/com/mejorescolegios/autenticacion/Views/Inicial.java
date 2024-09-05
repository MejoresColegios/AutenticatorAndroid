package com.mejorescolegios.autenticacion.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mejorescolegios.autenticacion.R;

public class Inicial extends AppCompatActivity {

    private ImageView email;
    private TextView tvEmail;
    private TextView signupRedirectText;
    private ImageButton googleSignInButton;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        email = findViewById(R.id.imageView2);
        tvEmail = findViewById(R.id.textView4);
        signupRedirectText = findViewById(R.id.signUpRedirectText);
        googleSignInButton = findViewById(R.id.ibSignInWithGoogle);

        // al pulsar en la imagen email redirigimos a la actividad activity_login
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicial.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        /*// al pulsar en el texto tvEmail redirigimos a la actividad activity_login
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicial.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
        // Abrir para registrarse en la actividad SingUpActivity
        signupRedirectText.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) {
            startActivity(new Intent(Inicial.this, SignUpActivity.class));
        }
        });

        // Configurar Google SignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        googleSignInButton.setOnClickListener(v -> signInWithGoogle());


    }

    private void signInWithGoogle() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Manejo de error
                Log.w("Google SignIn", getString(R.string.google_sign_in_failed), e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Ingreso exitoso, actualizar la UI con la información del usuario
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // Si el inicio de sesión falla, muestra un mensaje al usuario
                        Log.w("Google SignIn", getString(R.string.signinwithcredential_failure), task.getException());
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // El usuario ha iniciado sesión correctamente, puedes redirigirlo a la siguiente actividad
            Intent intent = new Intent(Inicial.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Mostrar alguna notificación de que el inicio de sesión falló
            Log.w("Google SignIn", getString(R.string.user_not_logged_in));
            Toast.makeText(this, getString(R.string.google_sign_in_failed), Toast.LENGTH_SHORT).show();
        }
    }








}