package servicios;

import fabricas.FabricaEnvio;
import modelos.Envio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class ServicioEnvios implements EnviosServicio {
    private final List<FabricaEnvio> fabricas;
    private final List<Envio> envios = new ArrayList<>();

    public ServicioEnvios(List<FabricaEnvio> fabricas) {
        Objects.requireNonNull(fabricas, "La lista de fábricas no puede ser nula.");
        if (fabricas.isEmpty()) {
            throw new IllegalArgumentException("Debe registrarse al menos una fábrica de envíos.");
        }

        Set<String> tiposRegistrados = new HashSet<>();
        for (FabricaEnvio fabrica : fabricas) {
            Objects.requireNonNull(fabrica, "La lista no puede contener fábricas nulas.");
            if (!tiposRegistrados.add(normalizar(fabrica.getTipo()))) {
                throw new IllegalArgumentException("Hay más de una fábrica registrada para el mismo tipo.");
            }
        }
        this.fabricas = List.copyOf(fabricas);
    }

    @Override
    public List<String> getTiposDisponibles() {
        return fabricas.stream().map(FabricaEnvio::getTipo).toList();
    }

    @Override
    public Envio agregarEnvio(
            String tipo,
            String cliente,
            String codigo,
            BigDecimal pesoKilogramos,
            BigDecimal distanciaKilometros
    ) {
        String codigoNormalizado = normalizar(codigo);
        if (envios.stream().anyMatch(envio -> normalizar(envio.getCodigo()).equals(codigoNormalizado))) {
            throw new IllegalArgumentException("Ya existe un envío con el código " + codigo.trim() + ".");
        }

        FabricaEnvio fabrica = fabricas.stream()
                .filter(item -> normalizar(item.getTipo()).equals(normalizar(tipo)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El tipo de envío seleccionado no está disponible."));

        Envio envio = fabrica.crearEnvio(cliente, codigo, pesoKilogramos, distanciaKilometros);
        envios.add(envio);
        return envio;
    }

    @Override
    public boolean retirarEnvio(String codigo) {
        String codigoNormalizado = normalizar(codigo);
        return envios.removeIf(envio -> normalizar(envio.getCodigo()).equals(codigoNormalizado));
    }

    @Override
    public List<Envio> listarEnvios() {
        return Collections.unmodifiableList(new ArrayList<>(envios));
    }

    private static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim().toUpperCase(Locale.ROOT);
    }
}
