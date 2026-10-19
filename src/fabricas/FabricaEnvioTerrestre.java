package fabricas;

import modelos.Envio;
import modelos.EnvioTerrestre;

import java.math.BigDecimal;

public class FabricaEnvioTerrestre extends FabricaEnvio {
    @Override
    public String getTipo() {
        return "Terrestre";
    }

    @Override
    public Envio crearEnvio(
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    ) {
        return new EnvioTerrestre(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }
}
