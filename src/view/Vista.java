package view;

import controller.Controlador;
import model.Cliente;
import model.Registrador;
import model.Consumo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Vista {
    private Controlador controlador;
    private Scanner scanner;

    public Vista(Controlador controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Crear cliente");
            System.out.println("2. Editar cliente");
            System.out.println("3. Crear registrador");
            System.out.println("4. Editar registrador");
            System.out.println("5. Cargar consumos automáticos (TODOS)");
            System.out.println("6. Cargar consumos automáticos (UN cliente)");
            System.out.println("7. Cambiar consumo manual");
            System.out.println("8. Mostrar consumo mínimo");
            System.out.println("9. Mostrar consumo máximo");
            System.out.println("10. Mostrar consumo total");
            System.out.println("11. Mostrar promedio de consumo");
            System.out.println("12. Generar factura PDF");
            System.out.println("13. Listar clientes y registradores");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // limpiar buffer

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> editarCliente();
                case 3 -> crearRegistrador();
                case 4 -> editarRegistrador();
                case 5 -> cargarConsumosAutomaticosTodos();
                case 6 -> cargarConsumosAutomaticosCliente();
                case 7 -> cambiarConsumoManual();
                case 8 -> mostrarConsumoMinimo();
                case 9 -> mostrarConsumoMaximo();
                case 10 -> mostrarConsumoTotal();
                case 11 -> mostrarPromedioConsumo();
                case 12 -> generarFacturaPDF();
                case 13 -> listarClientesYRegistradores();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearCliente() {
        System.out.print("ID del cliente: ");
        String id = scanner.nextLine();
        System.out.print("Tipo de ID: ");
        String tipoId = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        Cliente cliente = new Cliente(id, tipoId, correo, direccion);
        if (controlador.crearCliente(cliente)) {
            System.out.println("Cliente registrado con éxito.");
        } else {
            System.out.println("Ya existe un cliente con ese ID.");
        }
    }

    private void editarCliente() {
        System.out.print("ID del cliente: ");
        String id = scanner.nextLine();
        System.out.print("Nuevo tipo de ID: ");
        String tipoId = scanner.nextLine();
        System.out.print("Nuevo correo: ");
        String correo = scanner.nextLine();
        System.out.print("Nueva dirección: ");
        String direccion = scanner.nextLine();

        if (controlador.editarCliente(id, tipoId, correo, direccion)) {
            System.out.println("Cliente editado con éxito.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void crearRegistrador() {
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = scanner.nextLine();
        System.out.print("Dirección del registrador: ");
        String direccion = scanner.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();

        Registrador r = new Registrador(idReg, direccion, ciudad);
        if (controlador.crearRegistrador(idCliente, r)) {
            System.out.println("Registrador agregado.");
        } else {
            System.out.println("Error al agregar registrador.");
        }
    }

    private void editarRegistrador() {
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = scanner.nextLine();
        System.out.print("Nueva dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Nueva ciudad: ");
        String ciudad = scanner.nextLine();

        if (controlador.editarRegistrador(idCliente, idReg, direccion, ciudad)) {
            System.out.println("Registrador editado con éxito.");
        } else {
            System.out.println("No se encontró el registrador.");
        }
    }

    private void cargarConsumosAutomaticosTodos() {
        int[] fecha = pedirMesYAnio();
        controlador.cargarConsumosAutomaticos(fecha[0], fecha[1]);
        System.out.println("Consumos cargados para todos los clientes.");
    }

    private void cargarConsumosAutomaticosCliente() {
        System.out.print("ID del cliente: ");
        String id = scanner.nextLine();
        int[] fecha = pedirMesYAnio();
        if (controlador.cargarConsumoCliente(id, fecha[0], fecha[1])) {
            System.out.println("Consumos cargados.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void cambiarConsumoManual() {
        System.out.print("ID del cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("ID del registrador: ");
        String idReg = scanner.nextLine();
        System.out.print("Año: ");
        int anio = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        System.out.print("Hora (0-23): ");
        int hora = scanner.nextInt();
        System.out.print("Nuevo consumo (kWh): ");
        double nuevo = scanner.nextDouble();
        scanner.nextLine();

        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, 0);
        if (controlador.cambiarConsumosManuales(idCliente, idReg, fechaHora, nuevo)) {
            System.out.println("Consumo actualizado.");
        } else {
            System.out.println("No se pudo cambiar el consumo.");
        }
    }

    private void mostrarConsumoMinimo() {
        String id = pedirCliente();
        int[] fecha = pedirMesYAnio();
        Double valor = controlador.obtenerConsumoMinimo(id, fecha[0], fecha[1]);
        System.out.println("Consumo mínimo: " + (valor != null ? valor + " kWh" : "No hay datos"));
    }

    private void mostrarConsumoMaximo() {
        String id = pedirCliente();
        int[] fecha = pedirMesYAnio();
        Double valor = controlador.obtenerConsumoMaximo(id, fecha[0], fecha[1]);
        System.out.println("Consumo máximo: " + (valor != null ? valor + " kWh" : "No hay datos"));
    }

    private void mostrarConsumoTotal() {
        String id = pedirCliente();
        int[] fecha = pedirMesYAnio();
        Double valor = controlador.obtenerConsumoTotal(id, fecha[0], fecha[1]);
        System.out.println("Consumo total: " + (valor != null ? valor + " kWh" : "No hay datos"));
    }

    private void mostrarPromedioConsumo() {
        String id = pedirCliente();
        int[] fecha = pedirMesYAnio();
        Double valor = controlador.obtenerPromedioConsumo(id, fecha[0], fecha[1]);
        System.out.println("Promedio de consumo: " + (valor != null ? valor + " kWh" : "No hay datos"));
    }

    private void generarFacturaPDF() {
        String id = pedirCliente();
        int[] fecha = pedirMesYAnio();
        if (controlador.generarFacturaPDF(id, fecha[0], fecha[1])) {
            System.out.println("Factura generada.");
        } else {
            System.out.println("Error al generar factura.");
        }
    }

    private void listarClientesYRegistradores() {
        List<Cliente> clientes = controlador.obtenerClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println("Cliente: " + c.getId() + " - " + c.getCorreo());
            for (Registrador r : c.getRegistradores()) {
                System.out.println("  Registrador: " + r.getId() + " (" + r.getCiudad() + ")");
            }
        }
    }

    private String pedirCliente() {
        System.out.print("ID del cliente: ");
        return scanner.nextLine();
    }

    private int[] pedirMesYAnio() {
        System.out.print("Mes (1-12): ");
        int mes = scanner.nextInt();
        System.out.print("Año: ");
        int anio = scanner.nextInt();
        scanner.nextLine();
        return new int[]{mes, anio};
    }
}
