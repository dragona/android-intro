package mg.x261.svg_import;

public class RowItem {
    private final String flagImageId;
    private final String countryName;


    public RowItem(String countryName, String imageId) {
        this.flagImageId = imageId;
        this.countryName = countryName;

    }

    public String getFlagImageId() {
        return flagImageId;
    }

    public String getCountryName() {
        return countryName;
    }
}
