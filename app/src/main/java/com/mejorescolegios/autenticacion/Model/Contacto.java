package com.mejorescolegios.autenticacion.Model;

public class Contacto {
    private String uidUser;
    private String nombre;
    private String correo;
    private String telefono;

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
}
