public abstract class Equipo {
    private String codigo;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponible;

    public Equipo(String codigo, String marca, String modelo, double tarifaDiaria) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de inventario no puede estar vacío.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        if (!Double.isFinite(tarifaDiaria) || tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor que cero.");
        }
        this.codigo = codigo.trim();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public void marcarAlquilado() {
        if (!disponible) {
            throw new IllegalStateException("El equipo " + codigo + " ya está alquilado y no puede alquilarse hasta que sea devuelto.");
        }
        disponible = false;
    }

    public void marcarDisponible() {
        if (disponible) {
            throw new IllegalStateException("El equipo " + codigo + " ya está disponible, no hay una devolución pendiente.");
        }
        disponible = true;
    }

    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser un entero positivo.");
        }
        return tarifaDiaria * dias + calcularRecargo(dias);
    }

    protected abstract double calcularRecargo(int dias);

    public abstract String getCategoria();

    protected abstract String obtenerDetalle();

    public String describir() {
        String estado = disponible ? "Disponible" : "Alquilado";
        return codigo + " | " + getCategoria() + " | " + marca + " " + modelo + " | "
                + Dinero.formatear(tarifaDiaria) + " por día | " + obtenerDetalle() + " | " + estado;
    }
}
