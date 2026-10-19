package tests;

import fabricas.FabricaEnvio;
import fabricas.FabricaEnvioAereo;
import fabricas.FabricaEnvioMaritimo;
import fabricas.FabricaEnvioTerrestre;
import modelos.Envio;
import servicios.ServicioEnvios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServicioEnviosTest {
    public static void main(String[] args) {
        ServicioEnvios servicio = new ServicioEnvios(List.of(
                new FabricaEnvioTerrestre(),
                new FabricaEnvioAereo(),
                new FabricaEnvioMaritimo()
        ));

        Envio terrestre = servicio.agregarEnvio(
                "Terrestre", "Ana", "T-1", new BigDecimal("2"), new BigDecimal("3"));
        Envio aereo = servicio.agregarEnvio(
                "Aéreo", "Luis", "A-1", new BigDecimal("2"), new BigDecimal("3"));
        Envio maritimo = servicio.agregarEnvio(
                "Marítimo", "Eva", "M-1", new BigDecimal("2"), new BigDecimal("3"));

        comprobarTarifa(terrestre, "14500");
        comprobarTarifa(aereo, "7600");
        comprobarTarifa(maritimo, "14000");
        comprobar(servicio.listarEnvios().size() == 3, "Se deben listar los tres envíos agregados.");
        comprobar(!servicio.retirarEnvio("inexistente"), "Retirar un código inexistente debe reportar false.");
        comprobar(servicio.retirarEnvio("a-1"), "El retiro debe ignorar mayúsculas y minúsculas.");
        comprobar(servicio.listarEnvios().size() == 2, "El envío retirado no debe permanecer en la lista.");

        comprobarLanzaIllegalArgumentException(
                () -> servicio.agregarEnvio(
                        "Terrestre", "Otra persona", "t-1", BigDecimal.ONE, BigDecimal.ONE),
                "No se deben aceptar códigos duplicados.");
        comprobarLanzaIllegalArgumentException(
                () -> servicio.agregarEnvio(
                        "Terrestre", "Otra persona", "T-2", BigDecimal.ZERO, BigDecimal.ONE),
                "El peso debe ser mayor que cero.");
        comprobar(servicio.listarEnvios().size() == 2, "Una operación inválida no debe agregar un envío.");

        FabricaEnvio fabricaExpress = new FabricaEnvioExpress();
        List<FabricaEnvio> fabricasExtendidas = new ArrayList<>(List.of(
                new FabricaEnvioTerrestre(),
                new FabricaEnvioAereo(),
                new FabricaEnvioMaritimo()
        ));
        fabricasExtendidas.add(fabricaExpress);
        ServicioEnvios servicioExtendido = new ServicioEnvios(fabricasExtendidas);
        comprobar(servicioExtendido.getTiposDisponibles().contains("Express"),
                "El nuevo tipo debe aparecer sin cambios en el servicio.");
        Envio express = servicioExtendido.agregarEnvio(
                "Express", "Carlos", "E-1", new BigDecimal("2"), new BigDecimal("3"));
        comprobarTarifa(express, "23400");

        System.out.println("Pruebas correctas: tarifas, altas, códigos únicos, retiros y extensión Express.");
    }

    private static void comprobarTarifa(Envio envio, String tarifaEsperada) {
        comprobar(envio.calcularTarifa().compareTo(new BigDecimal(tarifaEsperada)) == 0,
                "Tarifa incorrecta para el tipo " + envio.getTipo() + ".");
    }

    private static void comprobarLanzaIllegalArgumentException(Runnable operacion, String mensaje) {
        try {
            operacion.run();
        } catch (IllegalArgumentException excepcion) {
            return;
        }
        throw new AssertionError(mensaje);
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static class EnvioExpress extends Envio {
        private EnvioExpress(
                String cliente,
                String codigo,
                BigDecimal pesoKilogramos,
                BigDecimal distanciaKilometros
        ) {
            super(cliente, codigo, pesoKilogramos, distanciaKilometros);
        }

        @Override
        public String getTipo() {
            return "Express";
        }

        @Override
        public BigDecimal calcularTarifa() {
            return calcularTarifa(new BigDecimal("7000"), new BigDecimal("1200"));
        }
    }

    private static class FabricaEnvioExpress extends FabricaEnvio {
        @Override
        public String getTipo() {
            return "Express";
        }

        @Override
        public Envio crearEnvio(
                String cliente,
                String codigo,
                BigDecimal pesoKilogramos,
                BigDecimal distanciaKilometros
        ) {
            return new EnvioExpress(cliente, codigo, pesoKilogramos, distanciaKilometros);
        }
    }
}
