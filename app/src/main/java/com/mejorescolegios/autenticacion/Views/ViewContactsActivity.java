package com.mejorescolegios.autenticacion.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mejorescolegios.autenticacion.Controller.ContactAdapter;
import com.mejorescolegios.autenticacion.Model.Contacto;
import com.mejorescolegios.autenticacion.R;

import java.util.ArrayList;

public class ViewContactsActivity extends AppCompatActivity {


    // Array que contiene los datos para mostrar
    private ArrayList<Contacto> contactos = new ArrayList<>();
    private RecyclerView rvContacts;
    private ContactAdapter adapter;

    private Button btnBack;
    private String uidUser;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference contactosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        // creo el adaptador
        adapter = new ContactAdapter(this, contactos);
        // Vinculo el objeto Recycler View con el objeto correspondiente del layout
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setAdapter(adapter);
        // Asigno un LinearLayour vertical al RecyclerView para que se vean los datos en formato lista
        rvContacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        // Inicializar Firebase Database y Auth
        database = FirebaseDatabase.getInstance("https://autenticacion-ffa84-default-rtdb.europe-west1.firebasedatabase.app");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            uidUser = user.getUid();

            // Inicializar referencias
            contactosRef = database.getReference("contactos");


            // Leer base de datos filtrando por uidUser y meto los contactos en el array
            contactosRef.orderByChild("uidUser").equalTo(uidUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    contactos.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Contacto contacto = child.getValue(Contacto.class);
                        contacto.setId(child.getKey());
                        contactos.add(contacto);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ViewContactsActivity.this, "Error al leer la base de datos", Toast.LENGTH_SHORT).show();
                }
            });

            // Botón volver
            btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(v -> finish());

        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    // Funcionalidad al menú contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.cmEdit) {
            // Editar el contacto seleccionado en EditContactActivity
            Intent intent = new Intent(ViewContactsActivity.this, EditContactActivity.class);
            intent.putExtra("contacto", adapter.getSelectedContact());
            startActivity(intent);



        } else if (itemId == R.id.cmDelete) {
            // Eliminar el contacto seleccionado de la base de datos
            Contacto contacto = adapter.getSelectedContact();
            if (contacto != null && contacto.getId() != null) {
                contactosRef.child(contacto.getId()).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ViewContactsActivity.this, R.string.contacto_eliminado, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewContactsActivity.this, "Error al eliminar contacto", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(ViewContactsActivity.this, "Contacto no válido", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onContextItemSelected(item);
    }


}
