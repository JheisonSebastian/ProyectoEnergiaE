package model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Registrador {
    private String idRegistrador;
    private String direccion;
    private String ciudad;
    private List<Consumo> consumos;

    public Registrador(String idRegistrador, String direccion, String ciudad) {
        this.idRegistrador = idRegistrador;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumos = new ArrayList<>();
    }

    // 
    public String getId() {
        return idRegistrador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void agregarConsumo(Consumo c) {
        this.consumos.add(c);
    }

    public List<Consumo> obtenerConsumosDeMes(int mes, int anio) {
        return consumos.stream()
                .filter(c -> c.getFechaHora().getMonthValue() == mes && c.getFechaHora().getYear() == anio)
                .collect(Collectors.toList());
    }

    public double calcularConsumoTotal(int mes, int anio) {
        return obtenerConsumosDeMes(mes, anio).stream()
                .mapToDouble(Consumo::getKwHora)
                .sum();
    }

    public Map<Integer, Double> calcularConsumoPorFranja(int mes, int anio) {
        Map<Integer, Double> franjas = new HashMap<>();
        franjas.put(1, 0.0);
        franjas.put(2, 0.0);
        franjas.put(3, 0.0);

        for (Consumo c : obtenerConsumosDeMes(mes, anio)) {
            int hora = c.getFechaHora().getHour();
            double kw = c.getKwHora();

            if (hora >= 0 && hora <= 6 && kw >= 100 && kw <= 300) {
                franjas.put(1, franjas.get(1) + kw);
            } else if (hora >= 7 && hora <= 17 && kw > 300 && kw <= 600) {
                franjas.put(2, franjas.get(2) + kw);
            } else if (hora >= 18 && hora <= 23 && kw > 600 && kw < 1000) {
                franjas.put(3, franjas.get(3) + kw);
            }
        }
        return franjas;
    }

    public Map<LocalDate, Double> calcularConsumoPorDia(int mes, int anio) {
        Map<LocalDate, Double> consumoPorDia = new HashMap<>();
        for (Consumo c : obtenerConsumosDeMes(mes, anio)) {
            LocalDate fecha = c.getFechaHora().toLocalDate();
            consumoPorDia.put(fecha, consumoPorDia.getOrDefault(fecha, 0.0) + c.getKwHora());
        }
        return consumoPorDia;
    }

    public double obtenerConsumoMinimo(int mes, int anio) {
        return obtenerConsumosDeMes(mes, anio).stream()
                .mapToDouble(Consumo::getKwHora)
                .min().orElse(0.0);
    }

    public double obtenerConsumoMaximo(int mes, int anio) {
        return obtenerConsumosDeMes(mes, anio).stream()
                .mapToDouble(Consumo::getKwHora)
                .max().orElse(0.0);
    }

    public double calcularValorFactura(int mes, int anio) {
        Map<Integer, Double> franjas = calcularConsumoPorFranja(mes, anio);
        return franjas.get(1) * 200 + franjas.get(2) * 300 + franjas.get(3) * 500;
    }
}
 