package mg.x261.recyclerview;

public class ObjectItem {
    private String countryName;
    private int imageFlagId;
    private String continentName;

    // Constructor
    public ObjectItem(String countryName, int imageFlagId, String continentName) {
        this.countryName = countryName;
        this.imageFlagId = imageFlagId;
        this.continentName = continentName;
    }

    //Setting up the getter methods
    public String getCountryName() {
        return countryName;
    }

    public int getImageFlagId() {
        return imageFlagId;
    }

    public String getContinentName() {
        return continentName;
    }
}
