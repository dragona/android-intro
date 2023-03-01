package mg.x261.activitydemo;

public class Report {

    private String mName;
    private String mId;
    private String mSize;
    private String mStatus;

    public Report(String name, String id, String size, String status) {
        mName = name;
        mId = id;
        mSize = size;
        mStatus = status;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public String getSize() {
        return mSize;
    }

    public String getStatus() {
        return mStatus;
    }
}
