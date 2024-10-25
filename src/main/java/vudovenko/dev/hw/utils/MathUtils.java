package vudovenko.dev.hw.utils;

public class MathUtils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException("Число знаков после запятой не может быть отрицательным");
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
