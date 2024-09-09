package com.mejorescolegios.autenticacion.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Contacto implements Parcelable {
    private String uidUser;
    private String nombre;
    private String correo;
    private String telefono;
    private String id;

    // Constructor vacío
    public Contacto() {
    }

    // Constructor
    public Contacto(String uidUser, String nombre, String correo, String telefono) {
        this.uidUser = uidUser;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    // Getters y Setters

    protected Contacto(Parcel in) {
        uidUser = in.readString();
        nombre = in.readString();
        correo = in.readString();
        telefono = in.readString();
        id = in.readString();
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(uidUser);
        dest.writeString(nombre);
        dest.writeString(correo);
        dest.writeString(telefono);
        dest.writeString(id);
    }
}
