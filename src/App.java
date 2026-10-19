import controladores.EnvioControlador;
import fabricas.FabricaEnvio;
import fabricas.FabricaEnvioAereo;
import fabricas.FabricaEnvioMaritimo;
import fabricas.FabricaEnvioTerrestre;
import servicios.EnviosServicio;
import servicios.ServicioEnvios;
import vistas.EnvioVista;

import javax.swing.SwingUtilities;
import java.util.List;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<FabricaEnvio> fabricas = List.of(
                    new FabricaEnvioTerrestre(),
                    new FabricaEnvioAereo(),
                    new FabricaEnvioMaritimo()
            );

            EnviosServicio servicio = new ServicioEnvios(fabricas);
            EnvioVista vista = new EnvioVista();
            new EnvioControlador(vista, servicio);
            vista.setVisible(true);
        });
    }
}
