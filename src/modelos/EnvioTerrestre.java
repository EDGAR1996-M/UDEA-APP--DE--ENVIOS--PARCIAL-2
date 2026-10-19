package modelos;

import java.math.BigDecimal;

public class EnvioTerrestre extends Envio {
    private static final BigDecimal TARIFA_POR_KILOMETRO = new BigDecimal("1500");
    private static final BigDecimal RECARGO_POR_KILOGRAMO = new BigDecimal("5000");

    public EnvioTerrestre(String cliente, String codigo, BigDecimal pesoKilogramos, BigDecimal distanciaKilometros) {
        super(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }

    @Override
    public String getTipo() {
        return "Terrestre";
    }

    @Override
    public BigDecimal calcularTarifa() {
        return calcularTarifa(TARIFA_POR_KILOMETRO, RECARGO_POR_KILOGRAMO);
    }
}
