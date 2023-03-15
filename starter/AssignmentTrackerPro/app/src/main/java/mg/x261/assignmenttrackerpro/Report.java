package mg.x261.assignmenttrackerpro;

/**
 * The Report class represents a single report object with a name, id, size, and status.
 */
public class Report {
    private String mName;
    private String mId;
    private String mSize;
    private String mStatus;

    /**
     * Creates a new Report object with the given parameters.
     *
     * @param name   the name of the report
     * @param id     the id of the report
     * @param size   the size of the report
     * @param status the status of the report
     */
    public Report(String name, String id, String size, String status) {
        mName = name;
        mId = id;
        mSize = size;
        mStatus = status;
    }

    /**
     * Returns the name of the report.
     *
     * @return the name of the report
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns the id of the report.
     *
     * @return the id of the report
     */
    public String getId() {
        return mId;
    }

    /**
     * Returns the size of the report.
     *
     * @return the size of the report
     */
    public String getSize() {
        return mSize;
    }

    /**
     * Returns the status of the report.
     *
     * @return the status of the report
     */
    public String getStatus() {
        return mStatus;
    }
}
