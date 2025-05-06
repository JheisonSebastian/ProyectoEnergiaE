package controller;

import model.Cliente;
import model.Registrador;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controlador {
    private Map<String, Cliente> clientes = new HashMap<>();

    // 1. Registrar cliente
    public boolean registrarCliente(String nombre, String id, String correo, String direccion) {
        if (clientes.containsKey(id)) return false;
        clientes.put(id, new Cliente(nombre, id, correo, direccion));
        return true;
    }

    // 2. Registrar registrador a un cliente
    public boolean registrarRegistrador(String idCliente, String idRegistrador, String direccion, String ciudad) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return false;

        Registrador nuevoRegistrador = new Registrador(idRegistrador, direccion, ciudad);
        return cliente.agregarRegistrador(nuevoRegistrador);
    }

    // 3. Registrar consumo horario
    public boolean registrarConsumo(String idCliente, String idRegistrador, String mesAnio, int dia, int hora, double consumo) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return false;

        Registrador registrador = cliente.getRegistrador(idRegistrador);
        if (registrador == null) return false;

        registrador.registrarConsumo(mesAnio, dia, hora, consumo);
        return true;
    }

    // 4. Mostrar consumos de un registrador por mes
    public double[][] obtenerConsumosRegistrador(String idCliente, String idRegistrador, String mesAnio) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return null;

        Registrador registrador = cliente.getRegistrador(idRegistrador);
        if (registrador == null) return null;

        return registrador.getConsumos(mesAnio);
    }

    // 5. Mostrar consumo total de un cliente por mes
    public double calcularFacturaCliente(String idCliente, String mesAnio) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return -1;

        double total = 0;
        for (Registrador r : cliente.getRegistradores()) {
            total += r.consumoTotal(mesAnio);
        }
        return total;
    }

    // 6. Buscar cliente por ID
    public Cliente buscarCliente(String idCliente) {
        return clientes.get(idCliente);
    }

    // 7. Obtener todos los clientes
    public ArrayList<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes.values());
    }

    // 8. Obtener registradores de un cliente
    public ArrayList<Registrador> obtenerRegistradores(String idCliente) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return null;
        return cliente.getRegistradores();
    }

    // 9. Generar factura en PDF
    public boolean generarFactura(String idCliente, String mesAnio) {
        Cliente cliente = clientes.get(idCliente);
        if (cliente == null) return false;

        double total = calcularFacturaCliente(idCliente, mesAnio);

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("Factura_" + idCliente + "_" + mesAnio + ".pdf"));
            documento.open();

            documento.add(new Paragraph("FACTURA DE CONSUMO ENERGÉTICO", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Cliente: " + cliente.getNombre()));
            documento.add(new Paragraph("ID: " + cliente.getId()));
            documento.add(new Paragraph("Correo: " + cliente.getCorreo()));
            documento.add(new Paragraph("Dirección: " + cliente.getDireccion()));
            documento.add(new Paragraph("Mes facturado: " + mesAnio));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("Detalle de consumo por registrador:"));

            for (Registrador reg : cliente.getRegistradores()) {
                double totalReg = reg.consumoTotal(mesAnio);
                documento.add(new Paragraph("- Registrador " + reg.getId() + " (" + reg.getDireccion() + ", " + reg.getCiudad() + "): " + totalReg + " kWh"));
            }

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Total a pagar: " + total + " COP", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            documento.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
