package mg.studio.listview_demo;

public class RowItem {
    private int flagImageId;
    private String countryName;


    public RowItem(int imageId, String title) {
        this.flagImageId = imageId;
        this.countryName = title;

    }

    public int getFlagImageId() {
        return flagImageId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setFlagImageId(int flagImageId) {
        this.flagImageId = flagImageId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
