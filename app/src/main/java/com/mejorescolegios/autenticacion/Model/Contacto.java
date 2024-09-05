package com.mejorescolegios.autenticacion.Model;

public class Contacto {
    private int id;
    private String uidUser;
    private String nombre;
    private String correo;
    private String telefono;

    // Constructor
    public Contacto(int id, String idUser) {
        this.id = id;
        this.uidUser = idUser;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }
}
