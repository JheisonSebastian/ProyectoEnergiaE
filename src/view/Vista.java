package view;

import controller.Controlador;

import java.util.*;

public class Vista {
    private Scanner sc;
    private Controlador controlador;

    public Vista() {
        sc = new Scanner(System.in);
        controlador = new Controlador();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> editarCliente();
                case 3 -> crearRegistrador();
                case 4 -> editarRegistrador();
                case 5 -> cargarConsumosTodos();
                case 6 -> cargarConsumoCliente();
                case 7 -> cambiarConsumoHora();
                case 8 -> generarFactura();
                case 9 -> mostrarConsumoMinimo();
                case 10 -> mostrarConsumoMaximo();
                case 11 -> mostrarConsumoPorFranjas();
                case 12 -> mostrarConsumoPorDias();
                case 13 -> calcularValorFactura();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("""
                \n--- MENÚ PRINCIPAL ---
                1. Crear cliente
                2. Editar cliente
                3. Crear registrador
                4. Editar registrador
                5. Cargar consumos automáticamente (todos los clientes)
                6. Cargar consumo automáticamente (cliente)
                7. Cambiar consumo puntual
                8. Generar factura (simulada)
                9. Mostrar consumo mínimo
                10. Mostrar consumo máximo
                11. Mostrar consumo por franjas
                12. Mostrar consumo por días
                13. Calcular valor total de factura
                0. Salir
                -------------------------
                Ingrese una opción:
                """);
    }

    private void crearCliente() {
        System.out.print("ID único del cliente: ");
        String id = sc.nextLine();
        System.out.print("Tipo de identificación: ");
        String tipo = sc.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Dirección física: ");
        String direccion = sc.nextLine();
        controlador.crearCliente(id, tipo, correo, direccion);
        System.out.println("Cliente creado exitosamente.");
    }

    private void editarCliente() {
        System.out.print("ID del cliente a editar: ");
        String id = sc.nextLine();
        System.out.print("Nuevo tipo de identificación: ");
        String tipo = sc.nextLine();
        System.out.print("Nuevo correo: ");
        String correo = sc.nextLine();
        System.out.print("Nueva dirección: ");
        String dir = sc.nextLine();
        if (controlador.editarCliente(id, tipo, correo, dir)) {
            System.out.println("Cliente editado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void crearRegistrador() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = sc.nextLine();
        System.out.print("Dirección del registrador: ");
        String direccion = sc.nextLine();
        System.out.print("Ciudad del registrador: ");
        String ciudad = sc.nextLine();
        if (controlador.crearRegistrador(idCliente, idReg, direccion, ciudad)) {
            System.out.println("Registrador creado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void editarRegistrador() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = sc.nextLine();
        System.out.print("Nueva dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Nueva ciudad: ");
        String ciudad = sc.nextLine();
        if (controlador.editarRegistrador(idCliente, idReg, direccion, ciudad)) {
            System.out.println("Registrador editado correctamente.");
        } else {
            System.out.println("Registrador o cliente no encontrado.");
        }
    }

    private void cargarConsumosTodos() {
        System.out.print("Mes y año (ej. 05-2025): ");
        String mesAnio = sc.nextLine();
        System.out.print("Cantidad de días del mes: ");
        int dias = Integer.parseInt(sc.nextLine());
        controlador.cargarConsumosTodos(mesAnio, dias);
        System.out.println("Consumos cargados correctamente.");
    }

    private void cargarConsumoCliente() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 05-2025): ");
        String mesAnio = sc.nextLine();
        System.out.print("Cantidad de días del mes: ");
        int dias = Integer.parseInt(sc.nextLine());
        if (controlador.cargarConsumoCliente(id, mesAnio, dias)) {
            System.out.println("Consumo cargado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void cambiarConsumoHora() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = sc.nextLine();
        System.out.print("Mes y año (ej. 05-2025): ");
        String mesAnio = sc.nextLine();
        System.out.print("Día (0 basado): ");
        int dia = Integer.parseInt(sc.nextLine());
        System.out.print("Hora (0-23): ");
        int hora = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo consumo (kWh): ");
        double nuevoConsumo = Double.parseDouble(sc.nextLine());
        if (controlador.cambiarConsumoHora(idCliente, idReg, mesAnio, dia, hora, nuevoConsumo)) {
            System.out.println("Consumo actualizado correctamente.");
        } else {
            System.out.println("Error: cliente o registrador no encontrado.");
        }
    }

    private void generarFactura() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 05-2025): ");
        String mes = sc.nextLine();
        if (!controlador.generarFactura(id, mes)) {
            System.out.println("Error al generar factura.");
        }
    }

    private void mostrarConsumoMinimo() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año: ");
        String mes = sc.nextLine();
        double min = controlador.consumoMinimo(id, mes);
        System.out.println("Consumo mínimo: " + min + " kWh");
    }

    private void mostrarConsumoMaximo() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año: ");
        String mes = sc.nextLine();
        double max = controlador.consumoMaximo(id, mes);
        System.out.println("Consumo máximo: " + max + " kWh");
    }

    private void mostrarConsumoPorFranjas() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año: ");
        String mes = sc.nextLine();
        Map<String, Double> franjas = controlador.consumoPorFranjas(id, mes);
        franjas.forEach((franja, valor) -> System.out.println(franja + ": " + valor + " kWh"));
    }

    private void mostrarConsumoPorDias() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año: ");
        String mes = sc.nextLine();
        double[] dias = controlador.consumoPorDias(id, mes);
        for (int i = 0; i < dias.length; i++) {
            System.out.printf("Día %d: %.2f kWh\n", i + 1, dias[i]);
        }
    }

    private void calcularValorFactura() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año: ");
        String mes = sc.nextLine();
        double total = controlador.calcularFacturaCliente(id, mes);
        System.out.println("Total a pagar: " + total + " COP");
    }
}
