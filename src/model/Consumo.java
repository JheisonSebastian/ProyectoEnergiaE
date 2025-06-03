package model;

import java.time.LocalDateTime;

public class Consumo {
    private LocalDateTime fechaHora;
    private double kwHora;

    public Consumo(LocalDateTime fechaHora, double kwHora) {
        this.fechaHora = fechaHora;
        this.kwHora = kwHora;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getKwHora() {
        return kwHora;
    }

    public void setKwHora(double kwHora) {
        this.kwHora = kwHora;
    }
}
