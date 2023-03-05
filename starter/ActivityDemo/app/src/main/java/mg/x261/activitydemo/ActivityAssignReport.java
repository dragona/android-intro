package mg.x261.activitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityAssignReport extends AppCompatActivity {

    private Spinner mSpinner;
    private RecyclerView mRecyclerView;
    private ReportAdapter mReportAdapter;

    // TODO: Load the data from the server
    //     https://studio.mg/submission2023/api-report.php?apikey=89821d232c6a62c57c369a9c8372fbc52bd9e206233748fb4032f86d28c2e86d&q=assign_001

    String mJsonString1 = "{\"reports\":[{\"name\":\"XueXiang\",\"id\":\"20205911\",\"size\":\"0.2 Ko\",\"status\":\"Processing\\n\"},{\"name\":\"\\u6731\\u5f00\\u6e90\",\"id\":\"20204229\",\"size\":\"0.2 Ko\",\"status\":\"Processing\\n\"},{\"name\":\"\\u8c22\\u5b9d\\u6770\",\"id\":\"20201703\",\"size\":\"0.2 Ko\",\"status\":\"Processing\\n\"}]}";
    String mJsonString2 = "{\"reports\":[{\"name\":\"ZhuZiJun\",\"id\":\"20204051\",\"size\":\"13.1 Mo\",\"status\":\"Processing\\n\"},{\"name\":\"\\u4efb\\u799bAzil\",\"id\":\"20201697\",\"size\":\"13.2 Mo\",\"status\":\"Processing\\n\"},{\"name\":\"\\u6731\\u5f00\\u6e90\",\"id\":\"20204229\",\"size\":\"13 Mo\",\"status\":\"Processing\\n\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_assignment);
        // Set the title of the activity
        setTitle("Assign Report");

        // Initialize views
        mSpinner = findViewById(R.id.sourceSelectionSpinner);
        mRecyclerView = findViewById(R.id.reportRecyclerView);

        // Set up spinner options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.data_source_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);

        // Set default JSON string
        final String[] jsonString = {mJsonString1};

        // Set listener for spinner selection
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    jsonString[0] = mJsonString1;
                } else if (position == 1) {
                    jsonString[0] = mJsonString2;
                }
                updateRecyclerView(jsonString[0]);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Initialize RecyclerView
        mReportAdapter = new ReportAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mReportAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Parse initial JSON string and update RecyclerView
        List<Report> reportList = parseJson(jsonString[0]);
        mReportAdapter.setReportList(reportList);

    }

    private static class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
        private List<Report> mReportList;

        public ReportAdapter(List<Report> reportList) {
            mReportList = reportList;
        }

        @NonNull
        @Override
        public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_report, parent, false);
            return new ReportViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
            Report report = mReportList.get(position);
            holder.bind(report);
        }


        @Override
        public int getItemCount() {
            return mReportList.size();
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
            mReportList = reportList;
            notifyDataSetChanged();
        }

    }

    private List<Report> parseJson(String jsonString) {
        List<Report> reportList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("reports");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject reportObject = jsonArray.getJSONObject(i);
                String name = reportObject.getString("name");
                String id = reportObject.getString("id");
                String size = reportObject.getString("size");
                String status = removeLeadingAndTrailingNewLines(reportObject.getString("status"));
                reportList.add(new Report(name, id, size, status));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reportList;
    }


    private void updateRecyclerView(String jsonString) {
        List<Report> reportList = parseJson(jsonString);
        mReportAdapter.updateData(reportList);
        setLastUpdateDate();
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


