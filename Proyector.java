public class Proyector extends Equipo {
    private static final double recargoInalambricoPorDia = 50;

    private int luminosidad;
    private boolean conectividadInalambrica;

    public Proyector(String codigo, String marca, String modelo, double tarifaDiaria,
                     int luminosidad, boolean conectividadInalambrica) {
        super(codigo, marca, modelo, tarifaDiaria);
        if (luminosidad <= 0) {
            throw new IllegalArgumentException("La luminosidad debe ser mayor que cero.");
        }
        this.luminosidad = luminosidad;
        this.conectividadInalambrica = conectividadInalambrica;
    }

    @Override
    protected double calcularRecargo(int dias) {
        if (conectividadInalambrica) {
            return recargoInalambricoPorDia * dias;
        }
        return 0;
    }

    @Override
    public String getCategoria() {
        return "Proyector";
    }

    @Override
    protected String obtenerDetalle() {
        String inalambrico = conectividadInalambrica ? "Sí" : "No";
        return luminosidad + " lúmenes, inalámbrico: " + inalambrico;
    }
}
