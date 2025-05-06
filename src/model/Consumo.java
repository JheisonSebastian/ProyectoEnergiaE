package model;

public class Consumo {
    // Matriz de días (filas) x horas (columnas), cada valor es consumo en kWh
    private double[][] datos;

    public Consumo(int diasDelMes) {
        datos = new double[diasDelMes][24]; // 24 horas por día
    }

    public void setConsumo(int dia, int hora, double valor) {
        datos[dia][hora] = valor;
    }

    public double getConsumo(int dia, int hora) {
        return datos[dia][hora];
    }

    public double[][] getDatos() {
        return datos;
    }
}
