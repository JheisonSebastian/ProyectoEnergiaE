package controller;

import model.*;

import java.util.*;

public class Controlador {
    private ArrayList<Cliente> clientes;

    public Controlador() {
        clientes = new ArrayList<>();
    }

    // 1. Crear cliente
    public void crearCliente(String id, String tipoId, String correo, String direccion) {
        Cliente cliente = new Cliente(id, tipoId, correo, direccion);
        clientes.add(cliente);
    }

    // 2. Editar cliente (excepto ID único)
    public boolean editarCliente(String id, String nuevoTipoId, String nuevoCorreo, String nuevaDireccion) {
        Cliente c = buscarCliente(id);
        if (c != null) {
            c.setTipoIdentificacion(nuevoTipoId);
            c.setCorreo(nuevoCorreo);
            c.setDireccion(nuevaDireccion);
            return true;
        }
        return false;
    }

    // 3. Crear registrador para un cliente
    public boolean crearRegistrador(String idCliente, String idReg, String direccion, String ciudad) {
        Cliente cliente = buscarCliente(idCliente);
        if (cliente != null) {
            Registrador r = new Registrador(idReg, direccion, ciudad);
            cliente.agregarRegistrador(r);
            return true;
        }
        return false;
    }

    // 4. Editar registrador
    public boolean editarRegistrador(String idCliente, String idReg, String nuevaDir, String nuevaCiudad) {
        Registrador r = buscarRegistrador(idCliente, idReg);
        if (r != null) {
            r.setDireccion(nuevaDir);
            r.setCiudad(nuevaCiudad);
            return true;
        }
        return false;
    }

    // 5. Cargar consumos de TODOS los clientes (de forma automática)
    public void cargarConsumosTodos(String mesAnio, int diasDelMes) {
        Random rand = new Random();
        for (Cliente c : clientes) {
            for (Registrador r : c.getRegistradores()) {
                Consumo consumo = new Consumo(diasDelMes);
                for (int d = 0; d < diasDelMes; d++) {
                    for (int h = 0; h < 24; h++) {
                        consumo.setConsumo(d, h, generarConsumoAleatorioPorHora(h, rand));
                    }
                }
                r.agregarConsumo(mesAnio, consumo);
            }
        }
    }

    // 6. Cargar consumo automático para un solo cliente
    public boolean cargarConsumoCliente(String idCliente, String mesAnio, int diasDelMes) {
        Cliente c = buscarCliente(idCliente);
        if (c != null) {
            Random rand = new Random();
            for (Registrador r : c.getRegistradores()) {
                Consumo consumo = new Consumo(diasDelMes);
                for (int d = 0; d < diasDelMes; d++) {
                    for (int h = 0; h < 24; h++) {
                        consumo.setConsumo(d, h, generarConsumoAleatorioPorHora(h, rand));
                    }
                }
                r.agregarConsumo(mesAnio, consumo);
            }
            return true;
        }
        return false;
    }

    // 7. Cambiar consumo puntual (cliente, registrador, mes, día, hora)
    public boolean cambiarConsumoHora(String idCliente, String idReg, String mesAnio, int dia, int hora, double nuevoConsumo) {
        Registrador r = buscarRegistrador(idCliente, idReg);
        if (r != null && r.getConsumosMensuales().containsKey(mesAnio)) {
            r.getConsumosMensuales().get(mesAnio).setConsumo(dia, hora, nuevoConsumo);
            return true;
        }
        return false;
    }

    // ========= MÉTODOS AUXILIARES =============

    private Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getIdUnico().equals(id)) return c;
        }
        return null;
    }

    private Registrador buscarRegistrador(String idCliente, String idReg) {
        Cliente c = buscarCliente(idCliente);
        if (c != null) {
            for (Registrador r : c.getRegistradores()) {
                if (r.getId().equals(idReg)) return r;
            }
        }
        return null;
    }

    private double generarConsumoAleatorioPorHora(int hora, Random rand) {
        if (hora >= 0 && hora <= 6) {
            return 100 + rand.nextDouble() * 200; // 100-300
        } else if (hora >= 7 && hora <= 17) {
            return 300 + rand.nextDouble() * 300; // 300-600
        } else {
            return 600 + rand.nextDouble() * 400; // 600-1000
        }
    }
}

// 8. Generar factura del cliente en un mes (simulada por ahora, PDF se implementará después)
public boolean generarFactura(String idCliente, String mesAnio) {
    Cliente c = buscarCliente(idCliente);
    if (c == null) return false;

    double total = calcularFacturaCliente(idCliente, mesAnio);
    System.out.println("----- FACTURA -----");
    System.out.println("Cliente: " + idCliente);
    System.out.println("Mes: " + mesAnio);
    System.out.println("Valor total a pagar: " + total + " COP");
    System.out.println("-------------------");

    // Implementar generación de PDF con iText o similar si se desea

    return true;
}

// 9. Consumo mínimo de un cliente en un mes
public double consumoMinimo(String idCliente, String mesAnio) {
    double min = Double.MAX_VALUE;
    Cliente c = buscarCliente(idCliente);
    if (c != null) {
        for (Registrador r : c.getRegistradores()) {
            Consumo cons = r.getConsumosMensuales().get(mesAnio);
            if (cons != null) {
                for (double[] dia : cons.getDatos()) {
                    for (double hora : dia) {
                        if (hora < min) min = hora;
                    }
                }
            }
        }
    }
    return min;
}

// 10. Consumo máximo de un cliente en un mes
public double consumoMaximo(String idCliente, String mesAnio) {
    double max = Double.MIN_VALUE;
    Cliente c = buscarCliente(idCliente);
    if (c != null) {
        for (Registrador r : c.getRegistradores()) {
            Consumo cons = r.getConsumosMensuales().get(mesAnio);
            if (cons != null) {
                for (double[] dia : cons.getDatos()) {
                    for (double hora : dia) {
                        if (hora > max) max = hora;
                    }
                }
            }
        }
    }
    return max;
}

// 11. Consumo por franjas (retorna mapa con total por franja)
public Map<String, Double> consumoPorFranjas(String idCliente, String mesAnio) {
    Map<String, Double> franjas = new HashMap<>();
    franjas.put("Franja 1", 0.0);
    franjas.put("Franja 2", 0.0);
    franjas.put("Franja 3", 0.0);

    Cliente c = buscarCliente(idCliente);
    if (c != null) {
        for (Registrador r : c.getRegistradores()) {
            Consumo cons = r.getConsumosMensuales().get(mesAnio);
            if (cons != null) {
                for (double[] dia : cons.getDatos()) {
                    for (int h = 0; h < 24; h++) {
                        double valor = dia[h];
                        if (h >= 0 && h <= 6) {
                            franjas.put("Franja 1", franjas.get("Franja 1") + valor);
                        } else if (h >= 7 && h <= 17) {
                            franjas.put("Franja 2", franjas.get("Franja 2") + valor);
                        } else {
                            franjas.put("Franja 3", franjas.get("Franja 3") + valor);
                        }
                    }
                }
            }
        }
    }
    return franjas;
}

// 12. Consumo por días
public double[] consumoPorDias(String idCliente, String mesAnio) {
    Cliente c = buscarCliente(idCliente);
    if (c == null || c.getRegistradores().isEmpty()) return new double[0];

    int diasDelMes = 0;
    for (Registrador r : c.getRegistradores()) {
        Consumo cons = r.getConsumosMensuales().get(mesAnio);
        if (cons != null) {
            diasDelMes = cons.getDatos().length;
            break;
        }
    }

    double[] sumaPorDia = new double[diasDelMes];
    for (Registrador r : c.getRegistradores()) {
        Consumo cons = r.getConsumosMensuales().get(mesAnio);
        if (cons != null) {
            for (int d = 0; d < diasDelMes; d++) {
                for (int h = 0; h < 24; h++) {
                    sumaPorDia[d] += cons.getConsumo(d, h);
                }
            }
        }
    }
    return sumaPorDia;
}

// 13. Calcular valor total a pagar de un cliente en un mes
public double calcularFacturaCliente(String idCliente, String mesAnio) {
    double total = 0;
    Cliente c = buscarCliente(idCliente);
    if (c != null) {
        for (Registrador r : c.getRegistradores()) {
            Consumo cons = r.getConsumosMensuales().get(mesAnio);
            if (cons != null) {
                for (int d = 0; d < cons.getDatos().length; d++) {
                    for (int h = 0; h < 24; h++) {
                        double consumo = cons.getConsumo(d, h);
                        total += calcularCostoHora(h, consumo);
                    }
                }
            }
        }
    }
    return total;
}

// Cálculo del costo según franja horaria
private double calcularCostoHora(int hora, double consumo) {
    if (hora >= 0 && hora <= 6) {
        return consumo * 200;
    } else if (hora >= 7 && hora <= 17) {
        return consumo * 300;
    } else {
        return consumo * 500;
    }
}
