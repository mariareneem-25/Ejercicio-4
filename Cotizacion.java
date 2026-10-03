public class Cotizacion {
    private Equipo equipo;
    private int dias;
    private double total;

    public Cotizacion(Equipo equipo, int dias) {
        this.equipo = equipo;
        this.dias = dias;
        this.total = equipo.calcularCosto(dias);
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getDias() {
        return dias;
    }

    public double getTotal() {
        return total;
    }

    public String resumir() {
        return "Equipo: " + equipo.describir() + "\n"
                + "Días: " + dias + "\n"
                + "Total: " + Dinero.formatear(total);
    }
}
