package model;

import java.util.ArrayList;

public class Cliente {
    private String idUnico;
    private String tipoIdentificacion;
    private String correo;
    private String direccion;
    private ArrayList<Registrador> registradores;

    public Cliente(String idUnico, String tipoIdentificacion, String correo, String direccion) {
        this.idUnico = idUnico;
        this.tipoIdentificacion = tipoIdentificacion;
        this.correo = correo;
        this.direccion = direccion;
        this.registradores = new ArrayList<>();
    }

    // Métodos Getters y Setters
    public String getIdUnico() { return idUnico; }
    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public ArrayList<Registrador> getRegistradores() { return registradores; }

    public void agregarRegistrador(Registrador r) {
        registradores.add(r);
    }
}
