package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum YearlyLoss {
    LESS_THAN_10000("Less than R 10 000"),
    BETWEEN_10000_AND_50000("Between R 10 000 – R 50 000"),
    BETWEEN_50001_AND_100000("Between R 50 001 – R 100 000"),
    BETWEEN_100001_AND_500000("Between R 100 001 – R 500 000"),
    BETWEEN_500001_AND_5000000("Between R 500 001 – R 5 000 000"),
    MORE_THAN_5000000("More than R 5 000 000");

    private final String name;

    YearlyLoss(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
