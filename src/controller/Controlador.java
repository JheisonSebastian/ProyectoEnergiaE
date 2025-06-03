package controller;

import model.Cliente;
import model.Registrador;
import model.Consumo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Controlador {
    private List<Cliente> clientes;

    public Controlador() {
        clientes = new ArrayList<>();
    }

    // 1. Crear cliente (alias registrarCliente)
    public boolean crearCliente(Cliente cliente) {
        return registrarCliente(cliente);
    }

    public boolean registrarCliente(Cliente cliente) {
        if (buscarCliente(cliente.getId()) != null) {
            return false;
        }
        clientes.add(cliente);
        return true;
    }

    // 2. Editar cliente
    public boolean editarCliente(String id, String nuevoTipoId, String nuevoCorreo, String nuevaDireccion) {
        Cliente cliente = buscarCliente(id);
        if (cliente != null) {
            cliente.setTipoId(nuevoTipoId);
            cliente.setCorreo(nuevoCorreo);
            cliente.setDireccion(nuevaDireccion);
            return true;
        }
        return false;
    }

    // 3. Crear registrador (alias registrarRegistrador)
    public boolean crearRegistrador(String idCliente, Registrador registrador) {
        return registrarRegistrador(idCliente, registrador);
    }

    public boolean registrarRegistrador(String idCliente, Registrador registrador) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null && cliente.getRegistradorPorId(registrador.getId()) == null) {
            cliente.getRegistradores().add(registrador);
            return true;
        }
        return false;
    }

    // 4. Editar registrador
    public boolean editarRegistrador(String idCliente, String idRegistrador, String nuevaDireccion, String nuevaCiudad) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            Registrador r = cliente.getRegistradorPorId(idRegistrador);
            if (r != null) {
                r.setDireccion(nuevaDireccion);
                r.setCiudad(nuevaCiudad);
                return true;
            }
        }
        return false;
    }

    // 5. Cargar consumos automáticos para todos los clientes
    public void cargarConsumosAutomaticos(int mes, int anio) {
        for (Cliente cliente : clientes) {
            cargarConsumoCliente(cliente.getId(), mes, anio);
        }
    }

    // 6. Cargar consumos automáticos para un cliente
    public boolean cargarConsumoCliente(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            Random random = new Random();
            for (Registrador r : cliente.getRegistradores()) {
                r.getConsumos().removeIf(c -> c.getFechaHora().getMonthValue() == mes && c.getFechaHora().getYear() == anio);
                int diasMes = LocalDateTime.of(anio, mes, 1, 0, 0).toLocalDate().lengthOfMonth();
                for (int dia = 1; dia <= diasMes; dia++) {
                    for (int hora = 0; hora < 24; hora++) {
                        double kw = generarConsumoAleatorio(hora, random);
                        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, 0);
                        r.agregarConsumo(new Consumo(fechaHora, kw));
                    }
                }
            }
            return true;
        }
        return false;
    }

    // 7. Cambiar consumo específico (alias cambiarConsumosManuales)
    public boolean cambiarConsumosManuales(String idCliente, String idRegistrador, LocalDateTime fechaHora, double nuevoConsumo) {
        return cambiarConsumo(idCliente, idRegistrador, fechaHora, nuevoConsumo);
    }

    public boolean cambiarConsumo(String idCliente, String idRegistrador, LocalDateTime fechaHora, double nuevoConsumo) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            Registrador r = cliente.getRegistradorPorId(idRegistrador);
            if (r != null) {
                for (Consumo c : r.getConsumos()) {
                    if (c.getFechaHora().equals(fechaHora)) {
                        c.setKwHora(nuevoConsumo);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 8. Buscar cliente
    public Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    // 9. Obtener consumo mínimo del mes
    public Double obtenerConsumoMinimo(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.obtenerConsumoMinimoMes(mes, anio);
        }
        return null;
    }

    // 10. Obtener consumo máximo del mes
    public Double obtenerConsumoMaximo(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.obtenerConsumoMaximoMes(mes, anio);
        }
        return null;
    }

    // 11. Obtener consumo total del mes
    public Double obtenerConsumoTotal(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.obtenerConsumoTotalMes(mes, anio);
        }
        return null;
    }

    // 12. Obtener promedio de consumo del mes
    public Double obtenerPromedioConsumo(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.obtenerPromedioConsumoMes(mes, anio);
        }
        return null;
    }

    // 13. Generar factura PDF
    public boolean generarFacturaPDF(String idCliente, int mes, int anio) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente == null) return false;

        String nombreArchivo = "factura_" + idCliente + "_" + mes + "_" + anio + ".pdf";
        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            documento.add(new Paragraph("Factura de Consumo Energético"));
            documento.add(new Paragraph("Cliente: " + cliente.getId()));
            documento.add(new Paragraph("Mes/Año: " + mes + "/" + anio));
            documento.add(new Paragraph(" "));
//
            for (Registrador r : cliente.getRegistradores()) {
                double totalKw = r.calcularConsumoTotal(mes, anio);
                documento.add(new Paragraph("Registrador: " + r.getId()));
                documento.add(new Paragraph("Dirección: " + r.getDireccion()));
                documento.add(new Paragraph("Ciudad: " + r.getCiudad()));
                documento.add(new Paragraph("Consumo Total: " + String.format("%.2f", totalKw) + " kWh"));
                documento.add(new Paragraph(" "));
            }

            double total = cliente.obtenerConsumoTotalMes(mes, anio);
            documento.add(new Paragraph("Consumo Total del Cliente: " + String.format("%.2f", total) + " kWh"));
            documento.close();
            return true;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para generación aleatoria de consumo
    private double generarConsumoAleatorio(int hora, Random random) {
        if (hora >= 0 && hora <= 6) {
            return 100 + random.nextDouble() * 200;
        } else if (hora >= 7 && hora <= 17) {
            return 301 + random.nextDouble() * 299;
        } else {
            return 601 + random.nextDouble() * 398;
        }
    }

    // Métodos adicionales requeridos por la Vista
    public List<Cliente> obtenerClientes() {
        return clientes;
    }

    public List<Registrador> obtenerRegistradores(String idCliente) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            return cliente.getRegistradores();
        }
        return new ArrayList<>();
    }

    public List<Consumo> obtenerConsumosRegistrador(String idCliente, String idRegistrador) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            Registrador r = cliente.getRegistradorPorId(idRegistrador);
            if (r != null) {
                return r.getConsumos();
            }
        }
        return new ArrayList<>();
    }
}
