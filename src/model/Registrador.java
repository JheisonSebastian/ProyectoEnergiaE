package model;

import java.util.HashMap;
import java.util.Map;

public class Registrador {
    private String id;
    private String direccion;
    private String ciudad;
    private Map<String, double[][]> consumosPorMes; // clave: "mm-yyyy", valor: matriz [31][24]

    public Registrador(String id, String direccion, String ciudad) {
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumosPorMes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void registrarConsumo(String mesAnio, int dia, int hora, double consumo) {
        double[][] matriz = consumosPorMes.get(mesAnio);
        if (matriz == null) {
            matriz = new double[31][24];
            consumosPorMes.put(mesAnio, matriz);
        }
        matriz[dia - 1][hora] = consumo;
    }

    public double[][] getConsumos(String mesAnio) {
        return consumosPorMes.getOrDefault(mesAnio, new double[31][24]);
    }

    public double consumoTotal(String mesAnio) {
        double[][] matriz = getConsumos(mesAnio);
        double total = 0;
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 24; j++) {
                total += matriz[i][j];
            }
        }
        return total;
    }
}
