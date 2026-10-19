package modelos;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Envio {
    private final String cliente;
    private final String codigo;
    private final BigDecimal pesoKilogramos;
    private final BigDecimal distanciaKilometros;

    protected Envio(String cliente, String codigo, BigDecimal pesoKilogramos, BigDecimal distanciaKilometros) {
        this.cliente = validarTexto(cliente, "El nombre del cliente");
        this.codigo = validarTexto(codigo, "El código");
        this.pesoKilogramos = validarPositivo(pesoKilogramos, "El peso");
        this.distanciaKilometros = validarPositivo(distanciaKilometros, "La distancia");
    }

    public String getCliente() {
        return cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getPesoKilogramos() {
        return pesoKilogramos;
    }

    public BigDecimal getDistanciaKilometros() {
        return distanciaKilometros;
    }

    public abstract String getTipo();

    public abstract BigDecimal calcularTarifa();

    protected BigDecimal calcularTarifa(BigDecimal tarifaPorKilometro, BigDecimal recargoPorKilogramo) {
        return distanciaKilometros.multiply(tarifaPorKilometro)
                .add(pesoKilogramos.multiply(recargoPorKilogramo));
    }

    private static String validarTexto(String valor, String nombreCampo) {
        Objects.requireNonNull(valor, nombreCampo + " no puede ser nulo.");
        String texto = valor.trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " es obligatorio.");
        }
        return texto;
    }

    private static BigDecimal validarPositivo(BigDecimal valor, String nombreCampo) {
        Objects.requireNonNull(valor, nombreCampo + " no puede ser nulo.");
        if (valor.signum() <= 0) {
            throw new IllegalArgumentException(nombreCampo + " debe ser mayor que cero.");
        }
        return valor;
    }
}
