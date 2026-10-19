package controladores;

import modelos.Envio;
import servicios.EnviosServicio;
import vistas.EnvioVista;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EnvioControlador {
    private final EnvioVista vista;
    private final EnviosServicio servicio;
    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));

    public EnvioControlador(EnvioVista vista, EnviosServicio servicio) {
        this.vista = vista;
        this.servicio = servicio;

        vista.setTiposEnvio(servicio.getTiposDisponibles());
        vista.setAgregarClick(evento -> agregarEnvio());
        vista.setRetirarClick(evento -> retirarEnvio());
        actualizarTabla();
    }

    private void agregarEnvio() {
        try {
            servicio.agregarEnvio(
                    vista.getTipoSeleccionado(),
                    vista.getCliente(),
                    vista.getCodigo(),
                    parsearDecimal(vista.getPeso()),
                    parsearDecimal(vista.getDistancia())
            );
            actualizarTabla();
            vista.mostrarMensaje("El envío se agregó correctamente.");
            vista.limpiarFormulario();
        } catch (NumberFormatException exception) {
            vista.mostrarError("El peso y la distancia deben ser números válidos. Use punto o coma decimal.");
        } catch (IllegalArgumentException exception) {
            vista.mostrarError(exception.getMessage());
        }
    }

    private void retirarEnvio() {
        String codigo = vista.getCodigo();
        if (codigo.isBlank()) {
            vista.mostrarError("Ingrese el código del envío que desea retirar.");
            return;
        }

        if (servicio.retirarEnvio(codigo)) {
            actualizarTabla();
            vista.mostrarMensaje("El envío se retiró correctamente.");
            vista.limpiarFormulario();
        } else {
            vista.mostrarError("No existe un envío con el código " + codigo.trim() + ".");
        }
    }

    private void actualizarTabla() {
        List<Envio> envios = servicio.listarEnvios();
        Object[][] filas = envios.stream()
                .map(envio -> new Object[]{
                        envio.getCodigo(),
                        envio.getCliente(),
                        envio.getTipo(),
                        envio.getPesoKilogramos().toPlainString(),
                        envio.getDistanciaKilometros().toPlainString(),
                        formatoMoneda.format(envio.calcularTarifa())
                })
                .toArray(Object[][]::new);
        vista.mostrarEnvios(filas);
    }

    private static BigDecimal parsearDecimal(String valor) {
        String numero = valor.trim();
        if (numero.contains(",") && numero.contains(".")) {
            throw new NumberFormatException("Use un solo separador decimal.");
        }
        return new BigDecimal(numero.replace(',', '.'));
    }
}
