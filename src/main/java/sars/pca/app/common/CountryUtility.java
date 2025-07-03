package sars.pca.app.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author S2026987
 */
public class CountryUtility {

    public static List<String> getDisplayCountryNames() {
        String[] locales = Locale.getISOCountries();
        // Locale.
        List<String> countryNames = new ArrayList<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryNames.add(obj.getDisplayCountry());
        }
        return countryNames;
    }

    public static Map<String, String> getCoutryCodes() {
        Map<String, String> countryCodes = new HashMap<>();
        String[] locales = Locale.getISOCountries();
        List<String> countryNames = new ArrayList<>();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryNames.add(obj.getDisplayCountry());
            countryCodes.put(obj.getDisplayCountry(), obj.getISO3Country());
        }
        return countryCodes;
    }

}
