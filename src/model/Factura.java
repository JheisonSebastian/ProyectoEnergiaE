package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Factura {
    private String idCliente;
    private String nombreCliente;
    private String mesAnio;
    private double consumoTotal;
    private double tarifaPorKwh = 200; // Este apartado se puede configurar

    public Factura(String idCliente, String nombreCliente, String mesAnio, double consumoTotal) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.mesAnio = mesAnio;
        this.consumoTotal = consumoTotal;
    }

    public double calcularTotalFactura() {
        return consumoTotal * tarifaPorKwh;
    }

    public boolean generarPDF(String nombreArchivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            document.add(new Paragraph("=== FACTURA DE ENERGÍA ELÉCTRICA ===\n"));
            document.add(new Paragraph("Cliente: " + nombreCliente));
            document.add(new Paragraph("ID Cliente: " + idCliente));
            document.add(new Paragraph("Periodo: " + mesAnio));
            document.add(new Paragraph("Consumo total: " + String.format("%.2f", consumoTotal) + " kWh"));
            document.add(new Paragraph("Tarifa: " + tarifaPorKwh + " COP/kWh"));
            document.add(new Paragraph("Total a pagar: " + String.format("%.2f", calcularTotalFactura()) + " COP"));

            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Getters y setters 
    public String getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getMesAnio() {
        return mesAnio;
    }

    public double getConsumoTotal() {
        return consumoTotal;
    }

    public double getTarifaPorKwh() {
        return tarifaPorKwh;
    }

    public void setTarifaPorKwh(double tarifaPorKwh) {
        this.tarifaPorKwh = tarifaPorKwh;
    }
}
