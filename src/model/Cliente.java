package model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
    private String idUnico;
    private String tipoIdentificacion;
    private String correo;
    private String direccion;
    private List<Registrador> registradores;
    private String tipoId;

    public Cliente(String idUnico, String tipoIdentificacion, String correo, String direccion) {
        this.idUnico = idUnico;
        this.tipoIdentificacion = tipoIdentificacion;
        this.correo = correo;
        this.direccion = direccion;
        this.registradores = new ArrayList<>();
    }

    // Getters y setters
    public String getIdUnico() {
        return idUnico;
    }

    // Este método es requerido por Controlador.java
    public String getId() {
        return idUnico;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Registrador> getRegistradores() {
        return registradores;
    }

    public void agregarRegistrador(Registrador r) {
        this.registradores.add(r);
    }

    // Método: consumo total del cliente en un mes
    public double calcularConsumoTotal(int mes, int anio) {
        return registradores.stream()
                .mapToDouble(r -> r.calcularConsumoTotal(mes, anio))
                .sum();
    }

    // Método requerido por Controlador.java
    public double obtenerConsumoTotalMes(int mes, int anio) {
        return calcularConsumoTotal(mes, anio);
    }

    // Método requerido por Controlador.java
    public double obtenerConsumoMinimoMes(int mes, int anio) {
        return obtenerConsumoMinimo(mes, anio);
    }

    // Método requerido por Controlador.java
    public double obtenerConsumoMaximoMes(int mes, int anio) {
        return obtenerConsumoMaximo(mes, anio);
    }

    // Método requerido por Controlador.java
    public double obtenerPromedioConsumoMes(int mes, int anio) {
        YearMonth ym = YearMonth.of(anio, mes);
        int dias = ym.lengthOfMonth();
        double total = calcularConsumoTotal(mes, anio);
        return dias > 0 ? total / (dias * 24.0) : 0.0; // asumiendo 24h por día
    }

    // Método: consumo por franja horaria del cliente
    public Map<Integer, Double> calcularConsumoPorFranjas(int mes, int anio) {
        Map<Integer, Double> franjasTotales = new HashMap<>();
        franjasTotales.put(1, 0.0);
        franjasTotales.put(2, 0.0);
        franjasTotales.put(3, 0.0);

        for (Registrador r : registradores) {
            Map<Integer, Double> franjas = r.calcularConsumoPorFranja(mes, anio);
            franjasTotales.put(1, franjasTotales.get(1) + franjas.get(1));
            franjasTotales.put(2, franjasTotales.get(2) + franjas.get(2));
            franjasTotales.put(3, franjasTotales.get(3) + franjas.get(3));
        }

        return franjasTotales;
    }

    // Método: consumo por día del cliente
    public Map<LocalDate, Double> calcularConsumoPorDia(int mes, int anio) {
        Map<LocalDate, Double> consumoPorDia = new HashMap<>();

        for (Registrador r : registradores) {
            Map<LocalDate, Double> parciales = r.calcularConsumoPorDia(mes, anio);
            for (Map.Entry<LocalDate, Double> entry : parciales.entrySet()) {
                LocalDate fecha = entry.getKey();
                double kw = entry.getValue();
                consumoPorDia.put(fecha, consumoPorDia.getOrDefault(fecha, 0.0) + kw);
            }
        }

        return consumoPorDia;
    }

    // Método: consumo mínimo y máximo del cliente en un mes
    public double obtenerConsumoMinimo(int mes, int anio) {
        return registradores.stream()
                .mapToDouble(r -> r.obtenerConsumoMinimo(mes, anio))
                .min().orElse(0.0);
    }

    public double obtenerConsumoMaximo(int mes, int anio) {
        return registradores.stream()
                .mapToDouble(r -> r.obtenerConsumoMaximo(mes, anio))
                .max().orElse(0.0);
    }

    // Método: valor total a pagar en la factura
    public double calcularValorFactura(int mes, int anio) {
        return registradores.stream()
                .mapToDouble(r -> r.calcularValorFactura(mes, anio))
                .sum();
    }

    // Obtener registrador por ID
    public Registrador getRegistradorPorId(String idRegistrador) {
        for (Registrador r : registradores) {
            if (r.getId().equals(idRegistrador)) {
                return r;
            }
        }
        return null;    
    }
 public String getTipoId() {
    return tipoId;
}

public void setTipoId(String tipoId) {
    this.tipoId = tipoId;
}
       
}
