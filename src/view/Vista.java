import java.util.Map;
import java.util.Scanner;

public class Vista {

    private final Scanner sc;
    private final Controlador controlador;

    public Vista(Controlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Crear cliente");
            System.out.println("2. Mostrar clientes");
            System.out.println("3. Editar cliente");
            System.out.println("4. Crear registrador");
            System.out.println("5. Editar registrador");
            System.out.println("6. Cargar consumos a todos los clientes");
            System.out.println("7. Cargar consumo a cliente");
            System.out.println("8. Cambiar consumo de una hora específica");
            System.out.println("9. Generar factura");
            System.out.println("10. Mostrar consumo mínimo");
            System.out.println("11. Mostrar consumo máximo");
            System.out.println("12. Mostrar consumo por franjas");
            System.out.println("13. Mostrar consumo por días");
            System.out.println("14. Calcular valor total de la factura");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> mostrarClientes();
                case 3 -> editarCliente();
                case 4 -> crearRegistrador();
                case 5 -> editarRegistrador();
                case 6 -> cargarConsumosTodos();
                case 7 -> cargarConsumoCliente();
                case 8 -> cambiarConsumoHora();
                case 9 -> generarFactura();
                case 10 -> mostrarConsumoMinimo();
                case 11 -> mostrarConsumoMaximo();
                case 12 -> mostrarConsumoPorFranjas();
                case 13 -> mostrarConsumoPorDias();
                case 14 -> calcularValorFactura();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void crearCliente() {
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Tipo de identificación: ");
        String tipo = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        controlador.crearCliente(nombre, id, tipo, correo, direccion);
        System.out.println("Cliente creado exitosamente.");
    }

    private void mostrarClientes() {
        String listado = controlador.mostrarClientes();
        System.out.println("--- CLIENTES REGISTRADOS ---");
        System.out.println(listado);
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
            System.out.println("Cliente editado exitosamente.");
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
            System.out.println("Registrador agregado correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void editarRegistrador() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        System.out.print("ID del registrador a editar: ");
        String idReg = sc.nextLine();
        System.out.print("Nueva dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Nueva ciudad: ");
        String ciudad = sc.nextLine();
        if (controlador.editarRegistrador(idCliente, idReg, direccion, ciudad)) {
            System.out.println("Registrador editado correctamente.");
        } else {
            System.out.println("Registrador no encontrado.");
        }
    }

    private void cargarConsumosTodos() {
        System.out.print("Mes y año (ej. 2025-05): ");
        String mesAnio = sc.nextLine();
        System.out.print("Número de días del mes: ");
        int dias = Integer.parseInt(sc.nextLine());
        controlador.cargarConsumosTodos(mesAnio, dias);
        System.out.println("Consumos cargados para todos los clientes.");
    }

    private void cargarConsumoCliente() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mesAnio = sc.nextLine();
        System.out.print("Número de días del mes: ");
        int dias = Integer.parseInt(sc.nextLine());
        if (controlador.cargarConsumoCliente(id, mesAnio, dias)) {
            System.out.println("Consumos cargados para el cliente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void cambiarConsumoHora() {
        System.out.print("ID del cliente: ");
        String idCliente = sc.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mesAnio = sc.nextLine();
        System.out.print("Día (0-indexado): ");
        int dia = Integer.parseInt(sc.nextLine());
        System.out.print("Hora (0 a 23): ");
        int hora = Integer.parseInt(sc.nextLine());
        System.out.print("Nuevo valor de consumo (kWh): ");
        double consumo = Double.parseDouble(sc.nextLine());

        if (controlador.cambiarConsumoHora(idCliente, idReg, mesAnio, dia, hora, consumo)) {
            System.out.println("Consumo modificado correctamente.");
        } else {
            System.out.println("Error: cliente, registrador o mes no encontrado.");
        }
    }

    private void generarFactura() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mesAnio = sc.nextLine();
        if (!controlador.generarFactura(id, mesAnio)) {
            System.out.println("No se pudo generar la factura. Cliente no encontrado.");
        }
    }

    private void mostrarConsumoMinimo() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mes = sc.nextLine();
        double min = controlador.consumoMinimo(id, mes);
        if (min == Double.MAX_VALUE) {
            System.out.println("No hay datos disponibles.");
        } else {
            System.out.println("Consumo mínimo: " + min + " kWh");
        }
    }

    private void mostrarConsumoMaximo() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mes = sc.nextLine();
        double max = controlador.consumoMaximo(id, mes);
        if (max == Double.MIN_VALUE) {
            System.out.println("No hay datos disponibles.");
        } else {
            System.out.println("Consumo máximo: " + max + " kWh");
        }
    }

    private void mostrarConsumoPorFranjas() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mes = sc.nextLine();
        Map<String, Double> franjas = controlador.consumoPorFranjas(id, mes);
        System.out.println("--- Consumo por franjas ---");
        for (Map.Entry<String, Double> entry : franjas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " kWh");
        }
    }

    private void mostrarConsumoPorDias() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mes = sc.nextLine();
        double[] consumos = controlador.consumoPorDias(id, mes);
        if (consumos.length == 0) {
            System.out.println("No hay datos para mostrar.");
        } else {
            System.out.println("--- Consumo diario ---");
            for (int i = 0; i < consumos.length; i++) {
                System.out.println("Día " + (i + 1) + ": " + consumos[i] + " kWh");
            }
        }
    }

    private void calcularValorFactura() {
        System.out.print("ID del cliente: ");
        String id = sc.nextLine();
        System.out.print("Mes y año (ej. 2025-05): ");
        String mes = sc.nextLine();
        double total = controlador.calcularFacturaCliente(id, mes);
        System.out.println("Valor total a pagar: " + total + " COP");
    }
}
