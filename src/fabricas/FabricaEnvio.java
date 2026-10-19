package fabricas;

import modelos.Envio;

import java.math.BigDecimal;

public abstract class FabricaEnvio {
    public abstract String getTipo();

    public abstract Envio crearEnvio(
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    );
}
