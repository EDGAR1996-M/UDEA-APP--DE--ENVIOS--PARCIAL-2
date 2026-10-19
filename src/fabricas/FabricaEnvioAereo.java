package fabricas;

import modelos.Envio;
import modelos.EnvioAereo;

import java.math.BigDecimal;

public class FabricaEnvioAereo extends FabricaEnvio {
    @Override
    public String getTipo() {
        return "Aéreo";
    }

    @Override
    public Envio crearEnvio(
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    ) {
        return new EnvioAereo(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }
}
