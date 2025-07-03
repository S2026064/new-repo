package sars.pca.app.common;

import java.util.List;

/**
 *
 * @author S2026080
 */
public class SummationUtility {

    public static <T extends Number> double sumArrayOfNumbers(T... values) {
        double sum = 0;
        for (T value : values) {
            sum = Double.sum(sum, value.doubleValue());
        }
        return sum;
    }

    public static <T extends Number> double multiplyArrayOfNumbers(T... values) {
        double sum = 1;
        for (T value : values) {
            sum *= value.doubleValue();
        }
        return sum;
    }

    public static <T extends Number> double sumListOfNumbers(List<T> list) {
        double sum = 0.0;
        for (T element : list) {
            sum = Double.sum(sum, element.doubleValue());
        }
        return sum;
    }
}
