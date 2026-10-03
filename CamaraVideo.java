public class CamaraVideo extends Equipo {
    private static final int resolucionSinRecargo = 1080;
    private static final double recargoAltaResolucion = 75;

    private int resolucion;

    public CamaraVideo(String codigo, String marca, String modelo, double tarifaDiaria, int resolucion) {
        super(codigo, marca, modelo, tarifaDiaria);
        if (resolucion <= 0) {
            throw new IllegalArgumentException("La resolución debe ser mayor que cero.");
        }
        this.resolucion = resolucion;
    }

    @Override
    protected double calcularRecargo(int dias) {
        if (resolucion > resolucionSinRecargo) {
            return recargoAltaResolucion;
        }
        return 0;
    }

    @Override
    public String getCategoria() {
        return "Cámara de video";
    }

    @Override
    protected String obtenerDetalle() {
        return "Resolución máxima: " + resolucion + "p";
    }
}
