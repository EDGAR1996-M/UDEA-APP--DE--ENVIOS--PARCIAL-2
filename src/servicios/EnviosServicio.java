package servicios;

import modelos.Envio;

import java.math.BigDecimal;
import java.util.List;

public interface EnviosServicio {
    List<String> getTiposDisponibles();

    Envio agregarEnvio(
            String tipo,
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    );

    boolean retirarEnvio(String codigo);

    List<Envio> listarEnvios();
}
