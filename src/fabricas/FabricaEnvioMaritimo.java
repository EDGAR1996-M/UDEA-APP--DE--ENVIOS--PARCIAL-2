package fabricas;

import modelos.Envio;
import modelos.EnvioMaritimo;

import java.math.BigDecimal;

public class FabricaEnvioMaritimo extends FabricaEnvio {
    @Override
    public String getTipo() {
        return "Marítimo";
    }

    @Override
    public Envio crearEnvio(
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    ) {
        return new EnvioMaritimo(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }
}
