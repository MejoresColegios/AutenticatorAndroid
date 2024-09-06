package com.mejorescolegios.autenticacion.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mejorescolegios.autenticacion.Model.Contacto;
import com.mejorescolegios.autenticacion.R;

public class InsertContactActivity extends AppCompatActivity {

    TextView insertContact;
    EditText name;
    EditText email;
    EditText phone;
    Button buttonSave;
    Button buttonCancel;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_contact);

        insertContact = findViewById(R.id.tvTitle);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        phone = findViewById(R.id.etPhone);
        buttonSave = findViewById(R.id.btnSave);
        buttonCancel = findViewById(R.id.btnCancel);

        // Inicializar FirebaseAuth y FirebaseDatabase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://autenticacion-ffa84-default-rtdb.europe-west1.firebasedatabase.app");

        // Botón de guardar
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de los campos
                String nombre = name.getText().toString().trim();
                String correo = email.getText().toString().trim();
                String telefono = phone.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(InsertContactActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser usuario = auth.getCurrentUser();
                if (usuario == null) {
                    Toast.makeText(InsertContactActivity.this, "No estás autenticado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear un nuevo contacto
                Contacto contacto = new Contacto(usuario.getUid(), nombre, correo, telefono);

                // Guardar el contacto en la base de datos
                DatabaseReference contactosRef = database.getReference("contactos");
                contactosRef.push().setValue(contacto).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Mostrar mensaje de éxito
                        Toast.makeText(InsertContactActivity.this, "Contacto guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        // Mostrar mensaje de error
                        Toast.makeText(InsertContactActivity.this, "Error al guardar contacto", Toast.LENGTH_SHORT).show();
                    }
                    // Volver a la pantalla anterior
                    finish();
                });
            }
        });

        // Botón de cancelar
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a la pantalla anterior
                finish();
            }
        });
    }
}
