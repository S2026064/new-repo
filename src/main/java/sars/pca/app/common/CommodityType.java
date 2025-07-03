package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum CommodityType {
    IMPORT("Import"), 
    EXPORT("Export"), 
    WAREHOUSE("Warehouse"), 
    TRANSIT("Transit");
    
    private final String name;
    
    CommodityType(final String name){
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
