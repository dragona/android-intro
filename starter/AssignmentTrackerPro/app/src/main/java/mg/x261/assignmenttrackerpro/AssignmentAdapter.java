package mg.x261.assignmenttrackerpro;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> assignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView filenameTextView;
        public TextView downloadTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.assignment_image);
            filenameTextView = itemView.findViewById(R.id.assignment_title);
            downloadTextView = itemView.findViewById(R.id.assignment_download);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_list_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.filenameTextView.setText(assignment.getFilename());
        holder.downloadTextView.setText(assignment.getUrl());

        // Set the image resource based on the file extension
        String filename = assignment.getFilename();
        if (filename.endsWith(".pdf")) {
            holder.imageView.setImageResource(R.drawable.logo_pdf);
        } else if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
            holder.imageView.setImageResource(R.drawable.assignment);
        } else if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
            holder.imageView.setImageResource(R.drawable.assignment);
        } else {
            holder.imageView.setImageResource(R.drawable.assignment);
        }

        holder.itemView.setOnClickListener(view -> {
            // Start animation
            holder.itemView.animate().scaleX(0.8f).scaleY(0.8f).setDuration(200).withEndAction(() -> {
                holder.itemView.animate().scaleX(1f).scaleY(1f).setDuration(200);
            }).start();
            downloadFile(holder, assignments.get(position));
        });

    }


    private void openFile(ViewHolder holder, File file) {
        Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
        Uri fileUri = FileProvider.getUriForFile(holder.itemView.getContext(), holder.itemView.getContext().getPackageName() + ".provider", file);
        openFileIntent.setDataAndType(fileUri, "application/pdf"); // Later: Replace "application/pdf" with the appropriate MIME type if not PDF
        openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Log.d("OPEN_PATH", "Open Path: " + file.getAbsolutePath());
        holder.itemView.getContext().startActivity(Intent.createChooser(openFileIntent, "Open file with"));
    }

    private void startDownload(ViewHolder holder, Assignment assignment, DownloadManager downloadManager, DownloadManager.Request request, File file) {
        long downloadReference = downloadManager.enqueue(request);

        // Create and show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(holder.itemView.getContext());
        progressDialog.setMessage("Downloading " + assignment.getFilename());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        // Add a cancel button to the dialog
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the download
                downloadManager.remove(downloadReference); // Cancel the download
                progressDialog.dismiss();
                Toast.makeText(holder.itemView.getContext(), "Download cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        progressDialog.show();


        // Monitor download progress
        new Thread(() -> {
            boolean downloading = true;
            while (downloading) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadReference);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int bytesDownloaded = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytesTotal = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int progress = (int) ((bytesDownloaded * 100L) / bytesTotal);
                    progressDialog.setProgress(progress);
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))) {
                        downloading = false;
                        progressDialog.dismiss();
                        // Show a dialog telling the user where to find the downloaded file
                        holder.itemView.post(() -> {
                            holder.itemView.post(() -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Download Complete");
                                builder.setMessage("The file has been downloaded to your Downloads folder. Do you want to open it?");
                                builder.setPositiveButton("Open", (dialog, which) -> {
                                    // Open the downloaded file using an appropriate application
                                    Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
                                    Uri fileUri = FileProvider.getUriForFile(holder.itemView.getContext(), holder.itemView.getContext().getPackageName() + ".provider", file);
                                    openFileIntent.setDataAndType(fileUri, "application/pdf"); // Later: Replace "application/pdf" with the appropriate MIME type if not PDF
                                    openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Log.d("OPEN_PATH", "Open Path: " + file.getAbsolutePath());
                                    holder.itemView.getContext().startActivity(Intent.createChooser(openFileIntent, "Open file with"));
                                });
                                builder.setNegativeButton("Dismiss", null);
                                // Show the dialog and get a reference to its "Dismiss" button
                                AlertDialog alertDialog = builder.show();
                                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                                // Update the appearance of the "Dismiss" button
                                negativeButton.setTextColor(Color.BLACK);
                                negativeButton.setBackgroundColor(Color.WHITE);
                                // Update the appearance of the "Open" button
                                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                positiveButton.setBackgroundResource(R.color.white);
                                positiveButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.button_gray));
                                positiveButton.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                positiveButton.setAllCaps(true);
                                positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                positiveButton.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                                positiveButton.setPadding(32, 16, 32, 16);
                            });

                        });
                    }
                }
                cursor.close();
            }
        }).start();
    }
    private void downloadFile(ViewHolder holder, Assignment assignment) {
        // Get download URL
        String downloadUrl = assignment.getUrl();
        Log.d("DOWNLOAD SOURCE", "Download URL for file " + assignment.getFilename() + ": " + downloadUrl);


        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) holder.itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        // Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

        // Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);

        // Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle(assignment.getFilename());

        // Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading " + assignment.getFilename());

        // Set the local destination for the downloaded file to the Downloads folder
        File file = new File(holder.itemView.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), assignment.getFilename());
        request.setDestinationUri(Uri.fromFile(file));
        Log.d("DOWNLOAD_PATH", "Download Path: " + file.getAbsolutePath());

        /**
         * check if the file is already available before starting the download.
         * If the file is available, you can ask the user whether they want to open the existing
         * file or remove it and proceed with downloading a new copy.
          */
        if (file.exists()) {
            // Ask the user whether to open the existing file or remove and proceed with downloading
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setTitle("File Already Exists");
            builder.setMessage("The file " + assignment.getFilename() + " already exists. Do you want to open it or remove it and download a new copy?");

            builder.setNegativeButton("Remove and Download", (dialog, which) -> {
                // Remove the existing file and start the download
                if (file.delete()) {
                    if (new NetworkHelper().isNetworkAvailable(holder.itemView.getContext())) {
                        // Start the download
                        startDownload(holder, assignment, downloadManager, request, file);
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "No internet connection. Please check your network settings and try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Error removing existing file. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton("Open", (dialog, which) -> {
                // Open the existing file using an appropriate application
                openFile(holder, file);
            });
            builder.setNeutralButton("Cancel", null);

            // Create the AlertDialog
            AlertDialog alertDialog = builder.create();

            // Customize the buttons
            alertDialog.setOnShowListener(dialog -> {
                Button openButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button removeDownloadButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button cancelButton = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);

                // Set button colors
                int whiteColor = Color.WHITE;
                int greyColor = Color.GRAY;

                openButton.setTextColor(whiteColor);
                removeDownloadButton.setTextColor(whiteColor);
                cancelButton.setTextColor(whiteColor);

                openButton.setBackgroundColor(greyColor);
                removeDownloadButton.setBackgroundColor(greyColor);
                cancelButton.setBackgroundColor(greyColor);

                // Align buttons to center
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(16, 0, 16, 0);

                openButton.setLayoutParams(layoutParams);
                removeDownloadButton.setLayoutParams(layoutParams);
                cancelButton.setLayoutParams(layoutParams);
            });

// Show the AlertDialog
            alertDialog.show();
        } else {
            // TODO: check if the internet is available
            if (new NetworkHelper().isNetworkAvailable(holder.itemView.getContext())) {
                // Start the download
                startDownload(holder, assignment, downloadManager, request, file);
            } else {
                Toast.makeText(holder.itemView.getContext(), "No internet connection. Please check your network settings and try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }





    @Override
    public int getItemCount() {
        return assignments.size();
    }
}

