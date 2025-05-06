package model;

import java.util.HashMap;

public class Registrador {
    private String id;
    private String direccion;
    private String ciudad;

    // Clave: "2025-05" -> Consumo
    private HashMap<String, Consumo> consumosMensuales;

    public Registrador(String id, String direccion, String ciudad) {
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumosMensuales = new HashMap<>();
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public HashMap<String, Consumo> getConsumosMensuales() { return consumosMensuales; }

    public void agregarConsumo(String mesAnio, Consumo consumo) {
        consumosMensuales.put(mesAnio, consumo);
    }
}
