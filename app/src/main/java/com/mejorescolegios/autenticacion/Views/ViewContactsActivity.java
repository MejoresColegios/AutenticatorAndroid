package com.mejorescolegios.autenticacion.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mejorescolegios.autenticacion.Model.Contacto;
import com.mejorescolegios.autenticacion.R;

import java.util.ArrayList;
import java.util.List;

public class ViewContactsActivity extends AppCompatActivity {

    private TextView tvUserList;
    private Button btnBack;
    private String uidUser;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference contactosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        // Inicializar Firebase Database y Auth
        database = FirebaseDatabase.getInstance("https://autenticacion-ffa84-default-rtdb.europe-west1.firebasedatabase.app");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            uidUser = user.getUid();

            // Inicializar referencias
            contactosRef = database.getReference("contactos");
            tvUserList = findViewById(R.id.tvUserList); // Asegúrate de que este ID existe en tu XML

            // Leer base de datos filtrando por uidUser
            contactosRef.orderByChild("uidUser").equalTo(uidUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Contacto> contactosList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        if (contacto != null) {
                            contactosList.add(contacto);
                        }
                    }
                    if (!contactosList.isEmpty()) {
                        displayContactos(contactosList);
                    } else {
                        Toast.makeText(ViewContactsActivity.this, "No se encontraron contactos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ViewContactsActivity.this, "Error al recuperar datos", Toast.LENGTH_SHORT).show();
                }
            });

            // Botón volver
            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> finish());

        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayContactos(List<Contacto> contactosList) {
        StringBuilder sb = new StringBuilder();
        for (Contacto contacto : contactosList) {
            sb.append("Name: ").append(contacto.getNombre()).append("\n");
            sb.append("Email: ").append(contacto.getCorreo()).append("\n");
            sb.append("Phone: ").append(contacto.getTelefono()).append("\n\n");
        }
        tvUserList.setText(sb.toString());
    }
}
