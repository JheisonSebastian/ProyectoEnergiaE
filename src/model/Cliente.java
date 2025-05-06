package model;

import java.util.ArrayList;

public class Cliente {
    private String nombre;
    private String id;
    private String correo;
    private String direccion;
    private ArrayList<Registrador> registradores;

    public Cliente(String nombre, String id, String correo, String direccion) {
        this.nombre = nombre;
        this.id = id;
        this.correo = correo;
        this.direccion = direccion;
        this.registradores = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<Registrador> getRegistradores() {
        return registradores;
    }

    public boolean agregarRegistrador(Registrador r) {
        for (Registrador reg : registradores) {
            if (reg.getId().equals(r.getId())) {
                return false;
            }
        }
        registradores.add(r);
        return true;
    }

    public Registrador getRegistrador(String idRegistrador) {
        for (Registrador r : registradores) {
            if (r.getId().equals(idRegistrador)) {
                return r;
            }
        }
        return null;
    }
}
