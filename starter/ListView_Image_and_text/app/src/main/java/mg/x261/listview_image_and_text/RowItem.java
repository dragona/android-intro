package mg.x261.listview_image_and_text;

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
}
