import controller.Controlador;
import view.Vista;

public class App {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Vista vista = new Vista(controlador);
        vista.mostrarMenu();
    }
}
