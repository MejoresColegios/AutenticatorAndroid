package com.mejorescolegios.autenticacion.Views;

import static com.mejorescolegios.autenticacion.R.string.signup_successful;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mejorescolegios.autenticacion.R;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    // Declare any other necessary variables.
 private FirebaseAuth auth;
 private EditText signupName, signupEmail, signupPassword;
 private Button signupButton;
 private TextView loginRedirectText;

 @Override protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_sign_up);
     //Initialize the FirebaseAuth instance in the onCreate()
     auth = FirebaseAuth.getInstance();
     signupName = findViewById(R.id.signup_fullName);
     signupEmail = findViewById(R.id.signup_email);
     signupPassword = findViewById(R.id.signup_password);
     signupButton = findViewById(R.id.signup_button);
     signupButton.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View view) {
             String user = signupEmail.getText().toString().trim();
             String pass = signupPassword.getText().toString().trim();
             String name = signupName.getText().toString().trim();
             if (name.isEmpty()){ signupName.setError(getString(R.string.name_cannot_be_empty)); }
             if (user.isEmpty()){ signupEmail.setError(getString(R.string.email_cannot_be_empty)); }
             if(pass.isEmpty()){ signupPassword.setError(getString(R.string.password_cannot_be_empty));
             } else {
                 auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){ Toast.makeText(SignUpActivity.this, signup_successful, Toast.LENGTH_SHORT).show();
                         //Actualizar el usuario con el nombre
                            Objects.requireNonNull(auth.getCurrentUser()).updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                     } else {
                         Toast.makeText(SignUpActivity.this, getString(R.string.signup_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             });
             }
         }
     });

 }
}
