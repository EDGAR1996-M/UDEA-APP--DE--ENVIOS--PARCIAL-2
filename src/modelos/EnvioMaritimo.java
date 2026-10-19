package modelos;

import java.math.BigDecimal;

public class EnvioMaritimo extends Envio {
    private static final BigDecimal TARIFA_POR_KILOMETRO = new BigDecimal("4000");
    private static final BigDecimal RECARGO_POR_KILOGRAMO = new BigDecimal("1000");

    public EnvioMaritimo(String cliente, String codigo, BigDecimal pesoKilogramos, BigDecimal distanciaKilometros) {
        super(cliente, codigo, pesoKilogramos, distanciaKilometros);
    }

    @Override
    public String getTipo() {
        return "Marítimo";
    }

    @Override
    public BigDecimal calcularTarifa() {
        return calcularTarifa(TARIFA_POR_KILOMETRO, RECARGO_POR_KILOGRAMO);
    }
}
