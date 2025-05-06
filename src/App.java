import controller.Controlador;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Controlador controlador = new Controlador();

        int opcion;
        do {
            System.out.println("\n=== MERCADO DE ENERGÍA ELÉCTRICA ===");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Agregar registrador a cliente");
            System.out.println("3. Registrar consumo eléctrico");
            System.out.println("4. Consultar consumo por mes");
            System.out.println("5. Calcular consumo total");
            System.out.println("6. Generar factura en PDF");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del cliente: ");
                    String nombre = sc.nextLine();
                    System.out.print("ID del cliente: ");
                    String id = sc.nextLine();
                    System.out.print("Correo del cliente: ");
                    String correo = sc.nextLine();
                    System.out.print("Dirección del cliente: ");
                    String direccion = sc.nextLine();
                    if (controlador.registrarCliente(nombre, id, correo, direccion)) {
                        System.out.println("Cliente registrado con éxito.");
                    } else {
                        System.out.println("Ya existe un cliente con ese ID.");
                    }
                    break;

                case 2:
                    System.out.print("ID del cliente: ");
                    String idCliente = sc.nextLine();
                    System.out.print("ID del registrador: ");
                    String idRegistrador = sc.nextLine();
                    System.out.print("Dirección del registrador: ");
                    String dirRegistrador = sc.nextLine();
                    System.out.print("Ciudad del registrador: ");
                    String ciudad = sc.nextLine();
                    if (controlador.agregarRegistrador(idCliente, idRegistrador, dirRegistrador, ciudad)) {
                        System.out.println("Registrador agregado con éxito.");
                    } else {
                        System.out.println("No se pudo agregar el registrador. Verifica el ID del cliente o si ya existe el registrador.");
                    }
                    break;

                case 3:
                    System.out.print("ID del cliente: ");
                    idCliente = sc.nextLine();
                    System.out.print("ID del registrador: ");
                    idRegistrador = sc.nextLine();
                    System.out.print("Mes y año (MM-YYYY): ");
                    String mesAnio = sc.nextLine();
                    System.out.print("Día (1-31): ");
                    int dia = sc.nextInt();
                    System.out.print("Hora (0-23): ");
                    int hora = sc.nextInt();
                    System.out.print("Consumo (kWh): ");
                    double consumo = sc.nextDouble();
                    sc.nextLine(); // limpiar buffer

                    if (controlador.registrarConsumo(idCliente, idRegistrador, mesAnio, dia, hora, consumo)) {
                        System.out.println("Consumo registrado.");
                    } else {
                        System.out.println("Error al registrar consumo.");
                    }
                    break;

                case 4:
                    System.out.print("ID del cliente: ");
                    idCliente = sc.nextLine();
                    System.out.print("ID del registrador: ");
                    idRegistrador = sc.nextLine();
                    System.out.print("Mes y año (MM-YYYY): ");
                    mesAnio = sc.nextLine();
                    double[][] consumos = controlador.obtenerConsumo(idCliente, idRegistrador, mesAnio);
                    System.out.println("Consumo por día y hora:");
                    for (int i = 0; i < 31; i++) {
                        for (int j = 0; j < 24; j++) {
                            if (consumos[i][j] > 0) {
                                System.out.printf("Día %d, Hora %d: %.2f kWh%n", i + 1, j, consumos[i][j]);
                            }
                        }
                    }
                    break;

                case 5:
                    System.out.print("ID del cliente: ");
                    idCliente = sc.nextLine();
                    System.out.print("ID del registrador: ");
                    idRegistrador = sc.nextLine();
                    System.out.print("Mes y año (MM-YYYY): ");
                    mesAnio = sc.nextLine();
                    double total = controlador.calcularConsumoTotal(idCliente, idRegistrador, mesAnio);
                    System.out.printf("Consumo total en %s: %.2f kWh%n", mesAnio, total);
                    break;

                case 6:
                    System.out.print("ID del cliente: ");
                    idCliente = sc.nextLine();
                    System.out.print("ID del registrador: ");
                    idRegistrador = sc.nextLine();
                    System.out.print("Mes y año (MM-YYYY): ");
                    mesAnio = sc.nextLine();
                    System.out.print("Nombre del archivo PDF (ej: factura.pdf): ");
                    String archivo = sc.nextLine();
                    if (controlador.generarFacturaPDF(idCliente, idRegistrador, mesAnio, archivo)) {
                        System.out.println("Factura generada correctamente.");
                    } else {
                        System.out.println("Error al generar la factura.");
                    }
                    break;

                case 7:
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 7);
    }
}
