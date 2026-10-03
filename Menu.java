import java.util.Scanner;

public class Menu {
    private Inventario inventario;
    private Scanner scanner;

    public Menu() {
        inventario = new Inventario();
        scanner = new Scanner(System.in);
        cargarDatosIniciales();
    }

    public void iniciar() {
        int opcion = 0;
        while (opcion != 7) {
            mostrarOpciones();
            opcion = leerOpcion();
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Programa finalizado.");
    }

    private void cargarDatosIniciales() {
        inventario.registrar(new Proyector("P001", "Epson", "X49", 150, 3600, true));
        inventario.registrar(new Proyector("P002", "BenQ", "MS560", 120, 4000, false));
        inventario.registrar(new CamaraVideo("C001", "Sony", "ZV-E10", 200, 2160));
        inventario.registrar(new CamaraVideo("C002", "Canon", "M50", 180, 1080));
        inventario.registrar(new EquipoSonido("S001", "Yamaha", "Stagepas", 200, 1.5));
        inventario.registrar(new EquipoSonido("S002", "JBL", "Eon", 150, 0.8));
    }

    private void mostrarOpciones() {
        System.out.println();
        System.out.println("===== EnEscena =====");
        System.out.println("1. Registrar equipo");
        System.out.println("2. Consultar inventario");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Alquilar equipo");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Reporte general");
        System.out.println("7. Salir");
        System.out.println("Seleccione una opción:");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                registrarEquipo();
                break;
            case 2:
                consultarInventario();
                break;
            case 3:
                cotizar();
                break;
            case 4:
                alquilar();
                break;
            case 5:
                devolver();
                break;
            case 6:
                mostrarReporte();
                break;
            case 7:
                break;
            default:
                System.out.println("Opción inválida. Ingrese un número del 1 al 7.");
        }
    }

    private void registrarEquipo() {
        System.out.println("Categorías: 1. Proyector  2. Cámara de video  3. Equipo de sonido");
        int categoria = leerEnteroPositivo("Seleccione la categoría:");
        if (categoria > 3) {
            System.out.println("Categoría inválida.");
            return;
        }
        String codigo = leerTextoNoVacio("Código de inventario:");
        if (inventario.existe(codigo)) {
            System.out.println("Ya existe un equipo con el código " + codigo + ". No se registró nada.");
            return;
        }
        String marca = leerTextoNoVacio("Marca:");
        String modelo = leerTextoNoVacio("Modelo:");
        double tarifaDiaria = leerDecimalPositivo("Tarifa diaria en quetzales:");
        Equipo equipo;
        if (categoria == 1) {
            int luminosidad = leerEnteroPositivo("Luminosidad en lúmenes:");
            boolean inalambrico = leerSiNo("¿Tiene conectividad inalámbrica? (S/N):");
            equipo = new Proyector(codigo, marca, modelo, tarifaDiaria, luminosidad, inalambrico);
        } else if (categoria == 2) {
            int resolucion = leerEnteroPositivo("Resolución máxima en píxeles verticales:");
            equipo = new CamaraVideo(codigo, marca, modelo, tarifaDiaria, resolucion);
        } else {
            double potencia = leerDecimalPositivo("Potencia nominal en kilovatios:");
            equipo = new EquipoSonido(codigo, marca, modelo, tarifaDiaria, potencia);
        }
        inventario.registrar(equipo);
        System.out.println("Equipo registrado correctamente:");
        System.out.println(equipo.describir());
    }

    private void consultarInventario() {
        System.out.println(inventario.mostrarInventario());
    }

    private void cotizar() {
        String codigo = leerTextoNoVacio("Código de inventario:");
        int dias = leerEnteroPositivo("Cantidad de días:");
        Cotizacion cotizacion = inventario.cotizar(codigo, dias);
        System.out.println("--- Cotización (no genera ningún cobro) ---");
        System.out.println(cotizacion.resumir());
    }

    private void alquilar() {
        String codigo = leerTextoNoVacio("Código de inventario:");
        int dias = leerEnteroPositivo("Cantidad de días:");
        Cotizacion cotizacion = inventario.cotizarAlquiler(codigo, dias);
        System.out.println("--- Resumen del alquiler ---");
        System.out.println(cotizacion.resumir());
        if (leerSiNo("¿Confirma el alquiler? (S/N):")) {
            inventario.confirmarAlquiler(cotizacion);
            System.out.println("Alquiler confirmado. Total cobrado: " + Dinero.formatear(cotizacion.getTotal()));
        } else {
            System.out.println("Alquiler cancelado. No se realizó ningún cambio.");
        }
    }

    private void devolver() {
        String codigo = leerTextoNoVacio("Código de inventario:");
        inventario.devolver(codigo);
        System.out.println("Devolución registrada. El equipo " + codigo.toUpperCase() + " está disponible.");
    }

    private void mostrarReporte() {
        System.out.println(inventario.generarReporte());
    }

    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El valor no puede estar vacío.");
        }
    }

    private int leerEnteroPositivo(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) {
                    return valor;
                }
                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Formato incorrecto. Ingrese un número entero.");
            }
        }
    }

    private double leerDecimalPositivo(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (Double.isFinite(valor) && valor > 0) {
                    return valor;
                }
                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Formato incorrecto. Ingrese un número, por ejemplo 12.5.");
            }
        }
    }

    private boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (respuesta.equals("S") || respuesta.equals("SI") || respuesta.equals("SÍ")) {
                return true;
            }
            if (respuesta.equals("N") || respuesta.equals("NO")) {
                return false;
            }
            System.out.println("Respuesta inválida. Ingrese S o N.");
        }
    }
}
