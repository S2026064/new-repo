package sars.pca.app.common;

/**
 *
 * @author S2026015
 */
public enum Salutation {
    CLIENT("Client"),
    SIR_MADAM("Sir/Madam"),
    TRADER_AGENT("Trader/Agent");
    
    private final String name;
    
    Salutation(final String name){
        this.name = name;
}
    @Override
    public String toString(){
        return this.name;
    }
}
