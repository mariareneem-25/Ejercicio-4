import java.util.ArrayList;

public class Inventario {
    private ArrayList<Equipo> equipos;
    private double ingresos;

    public Inventario() {
        equipos = new ArrayList<>();
        ingresos = 0;
    }

    public void registrar(Equipo equipo) {
        if (existe(equipo.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un equipo con el código " + equipo.getCodigo() + ".");
        }
        equipos.add(equipo);
    }

    public boolean existe(String codigo) {
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }

    private Equipo buscar(String codigo) {
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return equipo;
            }
        }
        throw new IllegalArgumentException("No existe un equipo con el código " + codigo.trim() + ".");
    }

    public Cotizacion cotizar(String codigo, int dias) {
        return new Cotizacion(buscar(codigo), dias);
    }

    public Cotizacion cotizarAlquiler(String codigo, int dias) {
        Cotizacion cotizacion = cotizar(codigo, dias);
        if (!cotizacion.getEquipo().estaDisponible()) {
            throw new IllegalStateException("El equipo " + cotizacion.getEquipo().getCodigo()
                    + " está alquilado y no puede alquilarse hasta que sea devuelto.");
        }
        return cotizacion;
    }

    public void confirmarAlquiler(Cotizacion cotizacion) {
        cotizacion.getEquipo().marcarAlquilado();
        ingresos += cotizacion.getTotal();
    }

    public void devolver(String codigo) {
        buscar(codigo).marcarDisponible();
    }

    public String mostrarInventario() {
        if (equipos.isEmpty()) {
            return "No hay equipos registrados.";
        }
        String lista = "===== INVENTARIO =====";
        for (Equipo equipo : equipos) {
            lista += "\n" + equipo.describir();
        }
        return lista;
    }

    private int contar(String categoria, boolean disponible) {
        int cantidad = 0;
        for (Equipo equipo : equipos) {
            if (equipo.getCategoria().equals(categoria) && equipo.estaDisponible() == disponible) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public String generarReporte() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Equipo equipo : equipos) {
            if (!categorias.contains(equipo.getCategoria())) {
                categorias.add(equipo.getCategoria());
            }
        }
        int totalDisponibles = 0;
        int totalAlquilados = 0;
        String detalle = "";
        for (String categoria : categorias) {
            int disponibles = contar(categoria, true);
            int alquilados = contar(categoria, false);
            totalDisponibles += disponibles;
            totalAlquilados += alquilados;
            detalle += categoria + ": " + (disponibles + alquilados) + " registrados, "
                    + disponibles + " disponibles, " + alquilados + " alquilados\n";
        }
        return "===== REPORTE GENERAL =====\n"
                + "Equipos registrados: " + equipos.size() + "\n"
                + "Equipos disponibles: " + totalDisponibles + "\n"
                + "Equipos alquilados: " + totalAlquilados + "\n"
                + detalle
                + "Ingresos acumulados: " + Dinero.formatear(ingresos);
    }

    public double getIngresos() {
        return ingresos;
    }
}
