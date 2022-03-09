package mg.x261.svg_import;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class World {

    private final String[] africa;
    private final String[] asia;
    private final String[] europe;
    private final String[] northAmerica;
    private final String[] oceania;
    private final String[] southAmerica;
    private String[] continents = {"Africa", "Asia", "Australia", "Europe", "North America", "South America"};

    World() {
        this.africa = new String[]{
                "flag_algeria",
                "flag_angola",
                "flag_benin",
                "flag_botswana",
                "flag_burkina_faso",
                "flag_burundi",
                "flag_cabo_verde",
                "flag_cameroon",
                "flag_central_african_republic",
                "flag_comoros",
                "flag_cote_d_ivoire",
                "flag_democratic_republic_of_the_congo",
                "flag_djibouti",
                "flag_egypt",
                "flag_equatorial_guinea",
                "flag_eritrea",
                "flag_eswatini",
                "flag_ethiopia",
                "flag_gabon",
                "flag_gambia",
                "flag_ghana",
                "flag_guinea",
                "flag_guinea_bissau",
                "flag_kenya",
                "flag_lesotho",
                "flag_liberia",
                "flag_libya",
                "flag_madagascar",
                "flag_malawi",
                "flag_mali",
                "flag_mauritania",
                "flag_mauritius",
                "flag_morocco",
                "flag_mozambique",
                "flag_namibia",
                "flag_niger",
                "flag_nigeria",
                "flag_rwanda",
                "flag_sao_tome_and_principe",
                "flag_senegal",
                "flag_seychelles",
                "flag_sierra_leone",
                "flag_somalia",
                "flag_south_africa",
                "flag_south_sudan",
                "flag_tanzania",
                "flag_togo",
                "flag_tunisia",
                "flag_uganda",
                "flag_zambia",
                "flag_zimbabwe"};

        this.asia = new String[]{"flag_afghanistan",
                "flag_azerbaijan",
                "flag_bahrain",
                "flag_bangladesh",
                "flag_bhutan",
                "flag_brunei",
                "flag_cambodia",
                "flag_china",
                "flag_india",
                "flag_indonesia",
                "flag_iran",
                "flag_iraq",
                "flag_japan",
                "flag_jordan",
                "flag_kazakhstan",
                "flag_kuwait",
                "flag_kyrgyzstan",
                "flag_laos",
                "flag_lebanon",
                "flag_malaysia",
                "flag_maldives",
                "flag_mongolia",
                "flag_nepal",
                "flag_north_korea",
                "flag_oman",
                "flag_pakistan",
                "flag_palestine",
                "flag_philippines",
                "flag_qatar",
                "flag_saudi_arabia",
                "flag_singapore",
                "flag_south_korea",
                "flag_sri_lanka",
                "flag_syria",
                "flag_thailand",
                "flag_timor_leste",
                "flag_turkmenistan",
                "flag_united_arab_emirates",
                "flag_uzbekistan",
                "flag_vietnam",
                "flag_yemen"};
        this.europe = new String[]{"flag_albania",
                "flag_andorra",
                "flag_armenia",
                "flag_austria",
                "flag_belgium",
                "flag_bosnia_and_herzegovina",
                "flag_bulgaria",
                "flag_croatia",
                "flag_cyprus",
                "flag_czechia",
                "flag_denmark",
                "flag_finland",
                "flag_france",
                "flag_georgia",
                "flag_germany",
                "flag_greece",
                "flag_hungary",
                "flag_iceland",
                "flag_ireland",
                "flag_italy",
                "flag_latvia",
                "flag_liechtenstein",
                "flag_lithuania",
                "flag_luxembourg",
                "flag_malta",
                "flag_moldova",
                "flag_montenegro",
                "flag_netherlands",
                "flag_north_macedonia",
                "flag_norway",
                "flag_poland",
                "flag_portugal",
                "flag_romania",
                "flag_russia",
                "flag_san_marino",
                "flag_serbia",
                "flag_slovakia",
                "flag_slovenia",
                "flag_spain",
                "flag_sweden",
                "flag_switzerland",
                "flag_turkey",
                "flag_ukraine",
                "flag_united_kingdom",
                "flag_vatican_city"};
        this.northAmerica = new String[]{"flag_antigua_and_barbuda",
                "flag_bahamas",
                "flag_barbados",
                "flag_belize",
                "flag_canada",
                "flag_costa_rica",
                "flag_cuba",
                "flag_dominica",
                "flag_dominican_republic",
                "flag_el_salvador",
                "flag_grenada",
                "flag_guatemala",
                "flag_haiti",
                "flag_honduras",
                "flag_jamaica",
                "flag_mexico",
                "flag_nicaragua",
                "flag_saint_kitts_and_nevis",
                "flag_saint_lucia",
                "flag_saint_vincent_and_the_grenadines",
                "flag_trinidad_and_tobago",
                "flag_united_states_of_america"};
        this.oceania = new String[]{"flag_australia",
                "flag_fiji",
                "flag_kiribati",
                "flag_marshall_islands",
                "flag_micronesia",
                "flag_nauru",
                "flag_new_zealand",
                "flag_palau",
                "flag_papua_new_guinea",
                "flag_samoa",
                "flag_solomon_islands",
                "flag_tonga",
                "flag_tuvalu",
                "flag_vanuatu"};
        this.southAmerica = new String[]{"flag_argentina",
                "flag_bolivia",
                "flag_brazil",
                "flag_chile",
                "flag_colombia",
                "flag_guyana",
                "flag_paraguay",
                "flag_peru",
                "flag_suriname",
                "flag_uruguay",
                "flag_venezuela"};
    }

    public String[] getContinents() {
        return continents;
    }

    public String[] getCountries(String continent) {
        switch (continent) {
            case "Africa":
                return getAfricanFlags();
            case "Asia":
                return getAsianFlags();
            case "Australia":
                return getOceaniaFlags();
            case "Europe":
                return getEuropeanFlags();
            case "North America":
                return getNorthAmericanFlags();
            case "South America":
                return getSouthAmericanFlags();
        }
        return null;
    }

    public int getContinentsSize() {
        return continents.length;
    }


    public String[] getAfricanFlags() {
        return africa;
    }

    public String[] getAsianFlags() {
        return asia;
    }

    public String[] getEuropeanFlags() {
        return europe;
    }

    public String[] getSouthAmericanFlags() {
        return southAmerica;
    }

    public String[] getNorthAmericanFlags() {
        return northAmerica;
    }

    public String[] getOceaniaFlags() {
        return oceania;
    }

    /* number of countries given a continent */
    public int getAfricanCountriesCount() {
        return africa.length;
    }

    public int getAsianCountriesCount() {
        return asia.length;
    }

    public int getEuropeanCountriesCount() {
        return europe.length;
    }

    public int getSouthAmericanCountriesCount() {
        return southAmerica.length;
    }

    public int getNorthAmericanCountriesCount() {
        return northAmerica.length;
    }

    public int getOceaniaCountriesCount() {
        return oceania.length;
    }

    // Get the continent flag at a given index
    public String getAfricanFlagAtIndex(int index) {
        return africa[index];
    }

    // Get continent at index
    public String getContinentAtIndex(int index) {
        return continents[index];
    }

    // Country names
    public String[] countryNames(String[] continentFlagIds) {
        List<String> countries = new ArrayList<String>();
        for (int i = 0; i < continentFlagIds.length - 1; i++) {
            countries.add(toCountryName(getAfricanFlagAtIndex(i)));
        }
        return countries.toArray(new String[0]);
    }

    public String toCountryName(String resourceFlag) {
        String name = resourceFlag.replace("flag_", "");
        return capitalize(name.replace("_", " "));
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}
