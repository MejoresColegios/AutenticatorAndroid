package com.mejorescolegios.autenticacion.Controller;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mejorescolegios.autenticacion.Model.Contacto;
import com.mejorescolegios.autenticacion.R;
import com.mejorescolegios.autenticacion.Views.ViewContactsActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Contacto> contactos;
    private Contacto selectedContact;

    public ContactAdapter(Context context, List<Contacto> contactos) {
        this.context = context;
        this.contactos = contactos;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitemcontact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contacto contacto = contactos.get(position);
        holder.tvNombre.setText(context.getString(R.string.FullName) + ": " + contacto.getNombre());
        holder.tvCorreo.setText(context.getString(R.string.email) + ": " + contacto.getCorreo());
        holder.tvTelefono.setText(context.getString(R.string.Phone) + ": " + contacto.getTelefono());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public Contacto getSelectedContact() {
        return selectedContact;
    }


    // Clase interna para el ViewHolder
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCorreo, tvTelefono;

        // Constructor
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvName);
            tvCorreo = itemView.findViewById(R.id.tvEmail);
            tvTelefono = itemView.findViewById(R.id.tvPhone);


            // Muestro el menú contextual
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    MenuInflater inflater = new MenuInflater(context);
                    inflater.inflate(R.menu.context_menu, menu);
                    selectedContact = contactos.get(getAdapterPosition());
                }
            });
        }
    }

}
