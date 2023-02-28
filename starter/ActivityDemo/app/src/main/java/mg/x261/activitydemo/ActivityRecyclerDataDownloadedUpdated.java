package mg.x261.activitydemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ActivityRecyclerDataDownloadedUpdated extends AppCompatActivity {
    private int currentPage = 1; // Current page of data to download
    protected static final int ITEMS_PER_PAGE = 20; // Number of items to show per page

    ArrayList<DataObject> dataObjectsGlobal = new ArrayList<>();

    private boolean isDownloading = false;

    private int totalNumberOfPages = -1; // Total number of pages to download, initialized to an invalid value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view_data_downloaded);

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view_for_downloaded_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Download the first page of data
        downloadData(recyclerView, currentPage, true, dataObjectsGlobal);

        // Download more data when the bottom of the RecyclerView is reached
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    // Check if there is more data available to download
                    int lastVisibleItemPosition = firstVisibleItemPosition + visibleItemCount;
                    if (lastVisibleItemPosition >= totalItemCount && !isDownloading && currentPage <= totalNumberOfPages) {
                        Log.d("Recycler Data Downloaded", "Initiating download");
                        isDownloading = true;
                        downloadData(recyclerView, currentPage + 1, true, dataObjectsGlobal);
                    }
                }
            }
        });

    }

    private void downloadData(RecyclerView recyclerView, int page, boolean appendData, ArrayList<DataObject> dataObjectsGlobal) {
        Log.d("Recycler Data Downloaded", "All data downloaded - current page is " + page + " and total pages are " + totalNumberOfPages);

        if (totalNumberOfPages > 0 && page > totalNumberOfPages) {
            isDownloading = false;
            return;
        }

        String url = "https://studio.mg/api-country/index.php?page=" + page;
        Log.d("Recycler Data Downloaded", "Downloading data for page " + page);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject pagination = response.getJSONObject("pagination");
                            totalNumberOfPages = pagination.getInt("total_pages");

                            // Convert the JSON response to an array of objects
                            ArrayList<DataObject> dataObjects = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                String country = object.getString("country");
                                String capital = object.getString("capital");
                                dataObjects.add(new DataObject(country, capital));
//                            Log.d("Recycler Data Downloaded", "New data size:" + dataObjects.size() + "\n New data:" + dataObjects);
                            }

                            if (appendData) {
                                // Append the new data to the existing data
                                dataObjectsGlobal.addAll(dataObjects);
                                if (recyclerView.getAdapter() == null) {
                                    recyclerView.setAdapter(new MyAdapter(dataObjectsGlobal));
                                } else {
                                    recyclerView.getAdapter().notifyItemRangeInserted(dataObjectsGlobal.size() - dataObjects.size(), dataObjects.size());
                                }
                            } else {
                                // Set the new data to the RecyclerView adapter
                                recyclerView.setAdapter(new MyAdapter(dataObjects));
                            }

                            // Check if there is more data available to download
                            if (dataObjects.size() == ITEMS_PER_PAGE && page <= totalNumberOfPages) {
                                currentPage++;
                            }

                            isDownloading = false;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG", "error with the json data");
                        }
                        // Delay dismissing the ProgressDialog for at least 2 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 1000);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error downloading data", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }


    // RecyclerView adapter
    public class MyAdapter extends RecyclerView.Adapter<ActivityRecyclerDataDownloadedUpdated.MyAdapter.MyViewHolder> {
        private ArrayList<ActivityRecyclerDataDownloadedUpdated.DataObject> dataObjects;

        public MyAdapter(ArrayList<DataObject> dataObjects) {
            this.dataObjects = dataObjects;
        }

        public ArrayList<DataObject> getDataObjects() {
            return dataObjects;
        }


        @NonNull
        @Override
        public ActivityRecyclerDataDownloadedUpdated.MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_view_design_two, parent, false);
            return new ActivityRecyclerDataDownloadedUpdated.MyAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityRecyclerDataDownloadedUpdated.MyAdapter.MyViewHolder holder, int position) {
            ActivityRecyclerDataDownloadedUpdated.DataObject dataObject = dataObjects.get(position);
            holder.textViewUp.setText(dataObject.getCountry());
            holder.textViewDown.setText(dataObject.getCapital());
        }

        @Override
        public int getItemCount() {
            return dataObjects.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewUp;
            public TextView textViewDown;

            public MyViewHolder(View itemView) {
                super(itemView);
                textViewUp = itemView.findViewById(R.id.item_recycler_textview_up);
                textViewDown = itemView.findViewById(R.id.item_recycler_textview_down);

                // Set click listener on the item view
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getBindingAdapterPosition();
                        ActivityRecyclerDataDownloadedUpdated.DataObject dataObject = dataObjects.get(position);
                        Toast.makeText(itemView.getContext(), "Clicked on " + dataObject.getCountry(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // Data object class
    protected class DataObject {
        private String country;
        private String capital;

        public DataObject(String country, String capital) {
            this.country = country;
            this.capital = capital;
        }

        public String getCountry() {
            return country;
        }

        public String getCapital() {
            return capital;
        }
    }


}

