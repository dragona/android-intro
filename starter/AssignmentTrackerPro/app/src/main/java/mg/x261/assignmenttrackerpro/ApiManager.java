package mg.x261.assignmenttrackerpro;
/**
 * A class that manages the API URLs used in the app.
 */
public class ApiManager {
    private static final String API_KEY = "89821d232c6a62c57c369a9c8372fbc52bd9e206233748fb4032f86d28c2e86d";
    private static final String BASE_URL = "https://studio.mg/submission2023/";
    private static final String REPORT_API_URL = BASE_URL + "api-report.php?apikey=" + API_KEY;
    private static final String ASSIGNMENT_API_URL = BASE_URL + "api-assignment.php";

    /**
     * Gets the URL for the report API, filtered by a specific assignment ID.
     * @param assignmentId The ID of the assignment to filter the report API by.
     * @return The URL for the report API with the specified assignment ID filter.
     */
    public String getReportApiUrl(String assignmentId) {
        return REPORT_API_URL + "&q=assign_" + assignmentId;
    }

    /**
     * Gets the URL for the assignment API.
     * @return The URL for the assignment API.
     */
    public String getAssignmentApiUrl() {
        return ASSIGNMENT_API_URL;
    }
}
