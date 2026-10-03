import java.util.Locale;

public class Dinero {

    public static String formatear(double monto) {
        return "Q" + String.format(Locale.US, "%,.2f", monto);
    }
}
