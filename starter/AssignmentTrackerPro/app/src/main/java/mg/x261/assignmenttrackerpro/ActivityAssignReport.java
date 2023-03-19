package mg.x261.assignmenttrackerpro;

import androidx.annotation.NonNull;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The ActivityAssignReport class represents the activity that displays a list of reports and allows filtering
 * based on report status. It also allows the user to refresh the report list by pulling down on the screen.
 */
public class ActivityAssignReport extends AppCompatActivity {
    private Spinner mSpinner;
    private RecyclerView mRecyclerView;
    private ReportAdapter mReportAdapter;

    private RequestQueue mRequestQueue;
    private static final int MY_PERMISSIONS_REQUEST_NETWORK_STATE = 123;

    private ProgressBar mProgressBar;
    ProgressBar mProgressBarAssignment;
    SwipeRefreshLayout swipeRefreshLayout;

    String selectedAssignmentId = "001";
    private static List<Report> mReportList = new ArrayList<>();
    private List<Report> mFilteredReportList;

    private ApiManager mApiManager;

    private List<Assignment> assignmentList = new ArrayList<>();
    private AssignmentAdapter assignmentAdapter;

    RecyclerView assignmentRecyclerView;

    private boolean assignmentDataLoaded = false;

    RadioGroup optionsRadioGroup;

    BottomNavigationView bottomNavigationView;
    int currentLayout = 0; // 0: reportLayout, 1: assignmentLayout, 2: agoraLayout




    /**
     * Called when the activity is created. Initializes the views and listeners and checks network status.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_assignment);
        setTitle("Assign Report");

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        setBottomNavigationEnabled(bottomNavigationView, false); // wait for the report recyclerView to be populated first

        LinearLayout reportLayout = findViewById(R.id.main_report_layout);
        RelativeLayout assignmentLayout = findViewById(R.id.assignment_layout);
        FrameLayout agoraLayout = findViewById(R.id.agora_layout);

        // Initialize views
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.reportRecyclerView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);

        assignmentRecyclerView = findViewById(R.id.recyclerViewAssignments);
        assignmentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mProgressBarAssignment = findViewById(R.id.progressBarAssignmentLoading);
        mProgressBarAssignment.setVisibility(View.GONE);



        // Set the report layout as the default
        reportLayout.setVisibility(View.VISIBLE);
        assignmentLayout.setVisibility(View.GONE);
        agoraLayout.setVisibility(View.GONE);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_assignment_tracker:
                    reportLayout.setVisibility(View.VISIBLE);
                    assignmentLayout.setVisibility(View.GONE);
                    agoraLayout.setVisibility(View.GONE);
                    currentLayout = 0;
                    break;
                case R.id.navigation_assignment_details:
                    reportLayout.setVisibility(View.GONE);
                    assignmentLayout.setVisibility(View.VISIBLE);
                    agoraLayout.setVisibility(View.GONE);
                    currentLayout = 1;

                    showAssignmentLayout();
                    break;
                case R.id.navigation_agora:
                    reportLayout.setVisibility(View.GONE);
                    assignmentLayout.setVisibility(View.GONE);
                    agoraLayout.setVisibility(View.VISIBLE);
                    currentLayout = 2;
                    break;
                default:
                    reportLayout.setVisibility(View.VISIBLE);
                    assignmentLayout.setVisibility(View.GONE);
                    agoraLayout.setVisibility(View.GONE);
                    currentLayout = 0;
                    break;
            }
            return true;
        });

        // Initialize objects
        mApiManager = new ApiManager();
        mFilteredReportList = new ArrayList<>();

        // Set up radio button listeners
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.show_all:
                        // Handle selection of "All" radio button
                        mFilteredReportList = new ArrayList<>(mReportList); // Create a new instance of the list
                        break;
                    case R.id.show_processing:
                        mFilteredReportList = filterReportsByStatus("Processing");
                        break;
                    case R.id.show_pass:
                        mFilteredReportList = filterReportsByStatus("Pass");
                        break;
                    case R.id.show_failed:
                        mFilteredReportList = filterReportsByStatus("Failed");
                        break;
                    default:
                        mFilteredReportList = new ArrayList<>(mReportList); // Create a new instance of the list
                        break;
                }
                mReportAdapter.setReportList(mFilteredReportList);
            }
        });

        // Set up SwipeRefreshLayout listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setBottomNavigationEnabled(bottomNavigationView, false);
                // Load new data when user refreshes
                loadRecyclerViewData(selectedAssignmentId, new DataLoadCallback() {
                    @Override
                    public void onDataLoaded() {
                        swipeRefreshLayout.setRefreshing(false);
                        setBottomNavigationEnabled(bottomNavigationView, true);
                    }
                });
            }
        });


        // Agora

        final TextInputEditText etAskQuestion = findViewById(R.id.et_ask_question);

        etAskQuestion.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isKeyboardVisible(etAskQuestion.getRootView())) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        // Check for network state permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    MY_PERMISSIONS_REQUEST_NETWORK_STATE);
        } else {
            // Permission is granted, check network status
            checkNetworkStatus();
        }
    }


    private boolean isKeyboardVisible(View rootView) {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        int screenHeight = rootView.getRootView().getHeight();
        int keypadHeight = screenHeight - r.bottom;

        return keypadHeight > screenHeight * 0.15; // 15% of the screen height
    }



    /**
     * Enable or disable click events for each menu item in the BottomNavigationView.
     *
     * @param bottomNavigationView The BottomNavigationView instance whose menu items need to be enabled or disabled.
     * @param enabled              A boolean value indicating whether the menu items should be enabled or disabled.
     *                             Pass 'true' to enable the menu items and 'false' to disable them.
     */
    private void setBottomNavigationEnabled(BottomNavigationView bottomNavigationView, boolean enabled) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(enabled);
        }
    }



    private void showAssignmentLayout() {


        assignmentAdapter = new AssignmentAdapter(assignmentList);
        assignmentRecyclerView.setAdapter(assignmentAdapter);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentDataLoaded = false; // Set the flag to false when the Activity is created

        Log.d("TAG", "showAssignmentLayout() called");



        if (!assignmentDataLoaded) {
            if (!new NetworkHelper().isNetworkAvailable(this)) {
                findViewById(R.id.assignment_layout).setVisibility(View.GONE);
                findViewById(R.id.network_failure).setVisibility(View.VISIBLE);
                setBottomNavigationEnabled(bottomNavigationView, false);
                return;
            } else {
                findViewById(R.id.assignment_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.network_failure).setVisibility(View.GONE);
                setBottomNavigationEnabled(bottomNavigationView, true);
            }

            mProgressBarAssignment.setVisibility(View.VISIBLE);
            // Clear the assignmentList before downloading new data
            assignmentList.clear();

            String url = mApiManager.getAssignmentFilesApiUrl();
            JsonArrayRequest assignmentRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    response -> {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Assignment>>(){}.getType();
                        assignmentList.addAll(gson.fromJson(response.toString(), listType));
                        Log.d("TAG", "assignmentList size: " + assignmentList.size());
                        assignmentAdapter.notifyDataSetChanged();
                        Log.d("TAG", "notifyDataSetChanged() called");
                        assignmentDataLoaded = true; // Set the flag to true after loading data
                        mProgressBarAssignment.setVisibility(View.GONE);
                    },
                    error -> {
                        Toast.makeText(this, "Error downloading assignments", Toast.LENGTH_SHORT).show();
                        mProgressBarAssignment.setVisibility(View.GONE);
                    }

            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Cache-Control", "no-cache");
                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(assignmentRequest);
            assignmentDataLoaded = true; // Set the flag to true after loading data
            mProgressBarAssignment.setVisibility(View.GONE);
        }





    }


    private void resetRadioToAll() {
        optionsRadioGroup.check(R.id.show_all);
    }




    /**
     * The filterReportsByStatus method filters a list of reports based on their status.
     *
     * @param status A string representing the status to filter the report list by.
     * @return A List of Report objects with the specified status.
     */
    private List<Report> filterReportsByStatus(String status) {
        Log.d("Filter", "Filtering reports by status: " + status);
        List<Report> filteredReports = new ArrayList<>();
        for (Report report : mReportList) {
            // Log to see the report
            String reportStatus = report.getStatus().trim();
            Log.d("Filter", "Report: " + report.toString() + " - " + reportStatus + " - status is " + status);

            if (reportStatus != null && reportStatus.equals(status.trim())) {
                Log.d("Filter", "Adding report with status " + status + ": " + report.toString());
                filteredReports.add(report);
            }
        }
        return filteredReports;
    }

    /**
     * The checkNetworkStatus method checks whether the device is connected to the internet and displays an error message
     * if it is not.
     */
    /**
     * This method checks the network status and initiates an API request to load data into the views.
     * If the device is connected to the internet, the appropriate view is shown and the API request is made.
     * If the device is not connected to the internet, an error message is displayed and the network failure layout is shown.
     */
    private void checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            // Make API request
            // 0: reportLayout, 1: assignmentLayout, 2: agoraLayout
            View viewToShow;
            switch (currentLayout) {
                case 0:
                    viewToShow = findViewById(R.id.main_report_layout);
                    break;
                case 1:
                    viewToShow = findViewById(R.id.assignment_layout);
                    break;
                case 2:
                    viewToShow = findViewById(R.id.agora_layout);
                    break;
                default:
                    viewToShow = findViewById(R.id.main_report_layout);
                    break;
            }
            makeApiRequest(viewToShow);

        } else {
            // Display error message
            Snackbar.make(findViewById(android.R.id.content), "No internet connection. Please check your network settings and try again.", Snackbar.LENGTH_LONG).show();
            // Show network failure layout
            findViewById(R.id.main_report_layout).setVisibility(View.GONE);
            findViewById(R.id.network_failure).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method initiates an API request to load data into the views based on the input view.
     * If the input view is the main report layout, the main content layout is shown and the data is loaded into the views.
     * If the input view is the assignment layout, the assignment layout is shown.
     *
     * @param viewToShow the view to show after the API request is made
     */
    private void makeApiRequest(View viewToShow) {
        // Show main content layout
        if (viewToShow == findViewById(R.id.main_report_layout)){
            findViewById(R.id.main_report_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.network_failure).setVisibility(View.GONE);
            Log.d("TAG", "request initiated");
            mProgressBar = findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.GONE);
            setBottomNavigationEnabled(bottomNavigationView, false);
            loadSpinnerData();
            loadRecyclerViewData(selectedAssignmentId, new DataLoadCallback() {
                @Override
                public void onDataLoaded() {
                    setBottomNavigationEnabled(bottomNavigationView, true);
                }
            });
        } else if (findViewById(R.id.assignment_layout) == viewToShow) {
            // Show assignment layout
            showAssignmentLayout();
        }
    }

    /**
     * The loadRecyclerViewData method loads the report data into the RecyclerView.
     *
     * @param selectedAssignmentId A string representing the ID of the selected assignment.
     */
    private void loadRecyclerViewData(String selectedAssignmentId, DataLoadCallback callback) {

        mProgressBar.setVisibility(View.VISIBLE);
        resetRadioToAll();
        // Initialize RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReportAdapter = new ReportAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mReportAdapter);
        String apiUrl = mApiManager.getReportApiUrl(selectedAssignmentId);
        Log.d("TAG", apiUrl);
        JsonObjectRequest reportRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mProgressBar.setVisibility(View.GONE);
                            JSONArray reports = response.getJSONArray("reports");
                            List<Report> reportList = parseJson(reports.toString());
                            mReportList = reportList; // Update the mReportList with the fetched data
                            mReportAdapter.setReportList(reportList);
                            setLastUpdateDate();
                            // Show main content layout
                            findViewById(R.id.main_report_layout).setVisibility(View.VISIBLE);
                            findViewById(R.id.network_failure).setVisibility(View.GONE);
                            // Call the callback's onDataLoaded method after populating the RecyclerView
                            if (callback != null) {
                                callback.onDataLoaded();
                            }
                        } catch (JSONException e) {
                            mProgressBar.setVisibility(View.GONE);
                            Log.e("JSON", "Error parsing JSON data", e);
                            Snackbar.make(findViewById(android.R.id.content), "There was an error loading the data. Please try again later.", Snackbar.LENGTH_LONG).show();
                            // Show network failure layout
                            findViewById(R.id.main_report_layout).setVisibility(View.GONE);
                            findViewById(R.id.network_failure).setVisibility(View.VISIBLE);

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error retrieving report data", error);
                        Snackbar.make(findViewById(android.R.id.content), "There was an error retrieving the data. Please try again later.", Snackbar.LENGTH_LONG).show();
                        mProgressBar.setVisibility(View.GONE);
                        // Show network failure layout
                        findViewById(R.id.main_report_layout).setVisibility(View.GONE);
                        findViewById(R.id.network_failure).setVisibility(View.VISIBLE);

                    }
                });
        reportRequest.setShouldCache(false);
        mRequestQueue.add(reportRequest);

        // Update the last update date
        setLastUpdateDate();
    }


    /**
     * The loadSpinnerData method loads the assignment data into the spinner.
     */
    private void loadSpinnerData() {
        mSpinner = findViewById(R.id.sourceSelectionSpinner);
        List<String> options = new ArrayList<>();
        options.add("Loading...");
        mProgressBar.setVisibility(View.VISIBLE);

        resetRadioToAll();

        // Initialize spinner with loading message
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mRequestQueue = Volley.newRequestQueue(this);
        String apiUrl = mApiManager.getAssignmentApiUrl();

        // Make request to get assignments
        JsonObjectRequest assignmentRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray assignments = response.getJSONArray("assignments");
                            List<String> options = new ArrayList<>();
                            for (int i = 0; i < assignments.length(); i++) {
                                JSONObject assignment = assignments.getJSONObject(i);
                                options.add("Assign " + assignment.getString("id") + " - " + assignment.getString("name"));
                            }
                            adapter.clear();
                            adapter.addAll(options);
                            adapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Log.e("JSON", "Error parsing JSON data", e);
                            mProgressBar.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        Log.e("Volley", "Error retrieving assignment options", error);
                        Snackbar.make(findViewById(android.R.id.content), "Error retrieving assignment options. Please try again later.", Snackbar.LENGTH_LONG).show();
                        // Show network failure layout
                        findViewById(R.id.main_report_layout).setVisibility(View.GONE);
                        findViewById(R.id.network_failure).setVisibility(View.VISIBLE);
                    }
                });
        // Disable caching
        assignmentRequest.setShouldCache(false);
        mRequestQueue.add(assignmentRequest);

        // Set listener for spinner item selection
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProgressBar.setVisibility(View.VISIBLE);
                setBottomNavigationEnabled(bottomNavigationView, false);

                if (position > 0) {
                    String selectedOption = (String) parent.getItemAtPosition(position);
                    selectedAssignmentId = selectedOption.substring(selectedOption.indexOf("Assign ") + 7, selectedOption.indexOf(" - "));
                }

                loadRecyclerViewData(selectedAssignmentId, new DataLoadCallback() {
                    @Override
                    public void onDataLoaded() {
                        setBottomNavigationEnabled(bottomNavigationView, true);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_NETWORK_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, check network status
                checkNetworkStatus();
            } else {
                // Permission denied, show error message
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Opens the network settings of the device to allow the user to check the internet connection.
     *
     * @param view The view that triggered the method call.
     */
    public void btnNetworkSettings(View view) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }


    public void btnRefresh(View view) {
        // Internet should be back now, try loading the data again
        checkNetworkStatus();
    }

    /**
     * The ReportAdapter class is a custom adapter that binds report data to a RecyclerView.
     */
    private class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
        private List<Report> mReportList;
        private List<Report> mFilteredReportList;

        /**
         * Constructor for ReportAdapter class.
         * @param reportList The list of Report objects to bind to the RecyclerView.
         */
        public ReportAdapter(List<Report> reportList) {
            mReportList = reportList;
            mFilteredReportList = reportList; // initialize the filtered report list
        }

        @NonNull
        @Override
        public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_report, parent, false);
            return new ReportViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mFilteredReportList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
            Report report = mFilteredReportList.get(position);
            holder.bind(report);
        }

        /**
         * The ReportViewHolder class represents a single item in the RecyclerView.
         */
        class ReportViewHolder extends RecyclerView.ViewHolder {
            private TextView mNameTextView;
            private TextView mIdTextView;
            private TextView mSizeTextView;
            private TextView mStatusTextView;

            public ReportViewHolder(@NonNull View itemView) {
                super(itemView);
                mNameTextView = itemView.findViewById(R.id.nameTextView);
                mIdTextView = itemView.findViewById(R.id.idTextView);
                mSizeTextView = itemView.findViewById(R.id.sizeTextView);
                mStatusTextView = itemView.findViewById(R.id.statusTextView);
            }

            /**
             * Bind Report object data to the view holder's TextViews.
             * @param report The Report object to bind.
             */
            public void bind(Report report) {
                mNameTextView.setText(report.getName());
                mIdTextView.setText(report.getId());
                mSizeTextView.setText("Size: " + report.getSize());
                mStatusTextView.setText("Status: " + report.getStatus());
            }
        }

        /**
         * Update the data in the adapter with a new list of Report objects.
         * @param reportList The new list of Report objects.
         */
        public void updateData(List<Report> reportList) {
            mReportList = reportList;
            notifyDataSetChanged();
        }

        /**
         * Set the list of Report objects to display in the RecyclerView.
         * @param reportList The list of Report objects to display.
         */
        public void setReportList(List<Report> reportList) {
            mFilteredReportList = reportList;
            notifyDataSetChanged();
        }
    }


    /**
     * Parse a JSON string and convert it to a list of Report objects.
     * @param jsonString The JSON string to parse.
     * @return A List of Report objects parsed from the JSON string.
     */
    private List<Report> parseJson(String jsonString) {
        List<Report> reportList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = removeLeadingAndTrailingNewLines(jsonObject.getString("name"));
                String id = removeLeadingAndTrailingNewLines(jsonObject.getString("id"));
                String size = removeLeadingAndTrailingNewLines(jsonObject.getString("size"));
                String status = removeLeadingAndTrailingNewLines(jsonObject.getString("status"));
                Report report = new Report(name, id, size, status);
                reportList.add(report);
            }
        } catch (JSONException e) {
            Log.e("JSON", "Error parsing JSON data", e);
        }
        return reportList;
    }


    /**
     * Updates the RecyclerView with the data parsed from a JSON string.
     * @param jsonString The JSON string to parse.
     */
    private void updateRecyclerView(String jsonString) {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        List<Report> reportList = parseJson(jsonString);
        mReportAdapter.updateData(reportList);
        setLastUpdateDate();
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Removes leading and trailing newlines from a string.
     * @param str The string to remove newlines from.
     * @return The string without leading or trailing newlines.
     */
    private String removeLeadingAndTrailingNewLines(String str) {
        return str == null || str.isEmpty() ? str : str.replaceAll("(^\\n+|\\n+$)", "");
    }

    /**
     * Sets the text of the last update TextView to the current date and time.
     */
    private void setLastUpdateDate() {
        TextView lastUpdateTextView = findViewById(R.id.lastUpdateTextView);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        lastUpdateTextView.setText("Last update: " + currentDateTimeString);
    }


}




