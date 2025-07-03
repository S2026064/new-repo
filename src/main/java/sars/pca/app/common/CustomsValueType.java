package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum CustomsValueType {
    PURPOSE_CODE("Purpose Code"),
    CLEARING_AGENT("Clearing Agent"),
    PORT_OF_ENTRY("Port of Entry");
    
    private final String name;
    
    CustomsValueType(final String name){
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
