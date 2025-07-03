package sars.pca.app.common;

/**
 *
 * @author S2024726
 */
public enum MainIndustry {

    LIVE_ANIMALS("Live Animals"),
    VERGITABLE_PRODUCTS("Vegetable Products"),
    ANIMAL_VEGITABLE_FATS("Animal or Vegetable Fats"),
    PREPARED_FOODSTUFFS_BEVERAGES_SPIRITS_VENEGAR_TOBACCO("Prepared Foodstuffs; Beverages, Spirits, Vinegar, Tobacco"),
    MINERAL_PRODUCTS("Mineral Products"),
    CHEMICAL_ALLIED_INDUSTRIES("Chemical/Allied Industries"),
    PLASTICS_RUBBER("Plastics, Rubber"),
    RAW_HIDES_SKINS_LEATHER_FUR_ETC("Raw Hides Skins, Leather, Fur Etc"),
    WOOD_ARTICLES_OF_WOOD_CORK_STRAW("Wood Articles of Wood, Cork, Straw"),
    PULP_WOOD_PAPER_ETC("Pulp Wood, Paper, Etc"),
    TEXTILE_ARTICLES("Textiles & Articles"),
    HEAD_GEAR_FOOTWEAR_UMBRELLA_ETC("Head Gear Footwear Umbrellas, Etc"),
    PLASTER_CEMENT_ASBESTOS_ETC("Plaster, Cement Asbestos, Etc"),
    SEMI_PRECIOUS_STONES_METALS_IMITATION_JEWELERRY_ETC("Semi Precious Stones, Metals, Imitation Jewellery Etc"),
    BASE_METALS("Base Metals"),
    MICHINERY_ELECTRICAL_EQUIPMENT_ETC("Machinery, Electrical Equipment, Etc"),
    VEHICLES_AIRCRAFT_VESSELS_ETC("Vehicles, Aircraft, Vessels, Etc"),
    OPTICAL_PHOTOGRAPHIC_CINEMATOGRAPHY_APARATUS("Optical Photographic, Cinematographic Apparatus"),
    ARMS_AND_AMMUNITION("Arms and Ammunition"),
    MISCELLANEOUS("Miscellaneous"),
    WORKS_OF_ART_COLLECTORS_PIECES("Works of Art, Collectors Pieces"),
    ORIGINAL_EQUIPMENT_COMPONENTS("Original Equipment Components"),
    MISCELLANEOUS_CLASSIFICATION_PROVISIONS("Miscellaneous Classification Provisions");

    private final String name;

    MainIndustry(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
