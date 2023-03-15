package mg.x261.assignmenttrackerpro;

import androidx.annotation.NonNull;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.volley.toolbox.JsonObjectRequest;


public class ActivityAssignReport extends AppCompatActivity {
    private Spinner mSpinner;
    private RecyclerView mRecyclerView;
    private ReportAdapter mReportAdapter;

    private RequestQueue mRequestQueue;
    private static final int MY_PERMISSIONS_REQUEST_NETWORK_STATE = 123;

    private ProgressBar mProgressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    String selectedAssignmentId = "001"; // Default value
    private static List<Report> mReportList = new ArrayList<>(); // Define mReportList variable here
    private List<Report> mFilteredReportList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_assignment);
        setTitle("Assign Report");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.reportRecyclerView);

        RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);

        mFilteredReportList = new ArrayList<>();// initialize empty list
        optionsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.show_all:
                        // handle selection of "All" radio button
                        mFilteredReportList = new ArrayList<>(mReportList); // create a new instance of the list
                        break;
                    case R.id.show_processing:
                        // handle selection of "Option 1" radio button
                        mFilteredReportList = filterReportsByStatus("Processing");
                        break;
                    case R.id.show_pass:
                        // handle selection of "Option 2" radio button
                        mFilteredReportList = filterReportsByStatus("Pass");
                        break;
                    case R.id.show_failed:
                        // handle selection of "Option 3" radio button
                        mFilteredReportList = filterReportsByStatus("Failed");
                        break;
                    default:
                        mFilteredReportList = new ArrayList<>(mReportList); // create a new instance of the list
                        break;
                }
                mReportAdapter.setReportList(mFilteredReportList);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do something when the user pulls to refresh
                loadRecyclerViewData(selectedAssignmentId);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        // Check if the app has permission to access network state
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


    private void checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            // Make API request
            makeApiRequest();
        } else {
            // Display error message
            Snackbar.make(findViewById(android.R.id.content), "No internet connection. Please check your network settings and try again.", Snackbar.LENGTH_LONG).show();
            // Show network failure layout
            findViewById(R.id.main_content).setVisibility(View.GONE);
            findViewById(R.id.network_failure).setVisibility(View.VISIBLE);
        }
    }


    private void makeApiRequest() {
        // Show main content layout
        findViewById(R.id.main_content).setVisibility(View.VISIBLE);
        findViewById(R.id.network_failure).setVisibility(View.GONE);
        Log.d("TAG", "request initiated");
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        loadSpinnerData();
        loadRecyclerViewData(selectedAssignmentId);

    }

    private void loadRecyclerViewData(String selectedAssignmentId) {

        mProgressBar.setVisibility(View.VISIBLE);
        // Initialize RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReportAdapter = new ReportAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mReportAdapter);

        String apiUrl = "https://studio.mg/submission2023/api-report.php?apikey=89821d232c6a62c57c369a9c8372fbc52bd9e206233748fb4032f86d28c2e86d&q=assign_" + selectedAssignmentId;
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
                            findViewById(R.id.main_content).setVisibility(View.VISIBLE);
                            findViewById(R.id.network_failure).setVisibility(View.GONE);
                        } catch (JSONException e) {
                            mProgressBar.setVisibility(View.GONE);
                            Log.e("JSON", "Error parsing JSON data", e);
                            Snackbar.make(findViewById(android.R.id.content), "There was an error loading the data. Please try again later.", Snackbar.LENGTH_LONG).show();
                            // Show network failure layout
                            findViewById(R.id.main_content).setVisibility(View.GONE);
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
                        findViewById(R.id.main_content).setVisibility(View.GONE);
                        findViewById(R.id.network_failure).setVisibility(View.VISIBLE);

                    }
                });
        reportRequest.setShouldCache(false);
        mRequestQueue.add(reportRequest);


        setLastUpdateDate(); // Move this outside of updateRecyclerView method
    }


    private void loadSpinnerData() {
        mSpinner = findViewById(R.id.sourceSelectionSpinner);
        List<String> options = new ArrayList<>();
        options.add("Loading...");
        mProgressBar.setVisibility(View.VISIBLE);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mRequestQueue = Volley.newRequestQueue(this);

        String apiUrl = "https://studio.mg/submission2023/api-assignment.php";
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
                        findViewById(R.id.main_content).setVisibility(View.GONE);
                        findViewById(R.id.network_failure).setVisibility(View.VISIBLE);
                    }
                });
        // Disable caching
        assignmentRequest.setShouldCache(false);
        mRequestQueue.add(assignmentRequest);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProgressBar.setVisibility(View.VISIBLE);

                if (position > 0) {
                    String selectedOption = (String) parent.getItemAtPosition(position);
                    selectedAssignmentId = selectedOption.substring(selectedOption.indexOf("Assign ") + 7, selectedOption.indexOf(" - "));
                }

                loadRecyclerViewData(selectedAssignmentId);

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

    private class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
//        private List<Report> mReportList;

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

            public void bind(Report report) {
                mNameTextView.setText(report.getName());
                mIdTextView.setText(report.getId());
                mSizeTextView.setText("Size: " + report.getSize());
                mStatusTextView.setText("Status: " + report.getStatus());
            }
        }


        public void updateData(List<Report> reportList) {
            mReportList = reportList;
            notifyDataSetChanged();
        }

        public void setReportList(List<Report> reportList) {
            mFilteredReportList = reportList;
            notifyDataSetChanged();
        }


    }

    private List<Report> parseJson(String jsonString) {
        List<Report> reportList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                String size = jsonObject.getString("size");
                String status = jsonObject.getString("status");
                Report report = new Report(name, id, size, status);
                reportList.add(report);
            }
        } catch (JSONException e) {
            Log.e("JSON", "Error parsing JSON data", e);
        }
        return reportList;
    }


    private void updateRecyclerView(String jsonString) {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);


        List<Report> reportList = parseJson(jsonString);
        mReportAdapter.updateData(reportList);
        setLastUpdateDate();
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private String removeLeadingAndTrailingNewLines(String str) {
        return str == null || str.isEmpty() ? str : str.replaceAll("(^\\n+|\\n+$)", "");
    }

    private void setLastUpdateDate() {
        TextView lastUpdateTextView = findViewById(R.id.lastUpdateTextView);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        lastUpdateTextView.setText("Last update: " + currentDateTimeString);
    }


}




