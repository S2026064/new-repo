package sars.pca.app.common;

/**
 *
 * @author S2026064
 */
public enum SectionRule {

    SECTION_4_7("Section 4 (7)"),
    RULE("Rule 101A.01A(2)(a)");

    private final String name;

    SectionRule(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
