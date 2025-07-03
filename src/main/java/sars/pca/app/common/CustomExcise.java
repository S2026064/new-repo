package sars.pca.app.common;

/**
 *
 * @author S2026987
 */
public enum CustomExcise {
    CUSTOM("Custom"), 
    EXCISE("Excise");
    
    private final String name;
    
    CustomExcise(final String name){
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
