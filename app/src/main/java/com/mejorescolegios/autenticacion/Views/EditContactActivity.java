package com.mejorescolegios.autenticacion.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mejorescolegios.autenticacion.Model.Contacto;
import com.mejorescolegios.autenticacion.R;

public class EditContactActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;
    private Button btnEditSave;
    private Contacto contacto;
    String uidUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // Inicializar las vistas
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        btnEditSave = findViewById(R.id.btnEditSave);

        // Inicializar FirebaseAuth y FirebaseDatabase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://autenticacion-ffa84-default-rtdb.europe-west1.firebasedatabase.app");

        // Obtener el contacto desde el Intent
        Intent intent = getIntent();
        if (intent != null) {
            contacto = intent.getParcelableExtra("contacto");
        }



        // Mostrar los datos del contacto en los campos de edición
        editName.setText(contacto.getNombre());
        editEmail.setText(contacto.getCorreo());
        editPhone.setText(contacto.getTelefono());

        // Guardar los cambios
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos de los campos
                String nombre = editName.getText().toString().trim();
                String correo = editEmail.getText().toString().trim();
                String telefono = editPhone.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(EditContactActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Actualizar los datos del contacto
                contacto.setNombre(nombre);
                contacto.setCorreo(correo);
                contacto.setTelefono(telefono);


                // Actualizar el contacto en la base de datos
                DatabaseReference reference = database.getReference("contactos").child(contacto.getId());
                reference.setValue(contacto);


                // Mostrar mensaje de éxito
                Toast.makeText(EditContactActivity.this, "Contacto actualizado", Toast.LENGTH_SHORT).show();

                // Volver a la actividad anterior
                finish();
            }
        });


    }


}
