package modelos;

import java.math.BigDecimal;

public class EnvioAereo extends Envio {
    private static final BigDecimal TARIFA_POR_KILOMETRO = new BigDecimal("2000");
    private static final BigDecimal RECARGO_POR_KILOGRAMO = new BigDecimal("800");

    public EnvioAereo(String cliente, String codigo, BigDecimal pesoKilogramos, BigDecimal distanciaKilometros) {
        super(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }

    @Override
    public String getTipo() {
        return "Aéreo";
    }

    @Override
    public BigDecimal calcularTarifa() {
        return calcularTarifa(TARIFA_POR_KILOMETRO, RECARGO_POR_KILOGRAMO);
    }
}
