public class EquipoSonido extends Equipo {
    private static final double recargoPorKilovatioDia = 100;

    private double potenciaKilovatios;

    public EquipoSonido(String codigo, String marca, String modelo, double tarifaDiaria, double potenciaKilovatios) {
        super(codigo, marca, modelo, tarifaDiaria);
        if (!Double.isFinite(potenciaKilovatios) || potenciaKilovatios <= 0) {
            throw new IllegalArgumentException("La potencia debe ser mayor que cero.");
        }
        this.potenciaKilovatios = potenciaKilovatios;
    }

    @Override
    protected double calcularRecargo(int dias) {
        return recargoPorKilovatioDia * potenciaKilovatios * dias;
    }

    @Override
    public String getCategoria() {
        return "Equipo de sonido";
    }

    @Override
    protected String obtenerDetalle() {
        return "Potencia: " + potenciaKilovatios + " kW";
    }
}
