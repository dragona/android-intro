package mg.x261.assignmenttrackerpro;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;

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


    private void downloadFile(ViewHolder holder, Assignment assignment) {
        // Get download URL
        //String downloadUrl = assignment.getUrl();
        String downloadUrl = "https://studio.mg/submission2023/assignments/Assignment_001.pdf"; //TODO: need to be dynamic

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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, assignment.getFilename());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Check if MANAGE_EXTERNAL_STORAGE permission is granted
            if (Environment.isExternalStorageManager()) {
                // Permission granted, enqueue the download
                long downloadReference = downloadManager.enqueue(request);
                Log.d("AssignmentAdapter", "Download enqueued with referenceId: " + downloadReference);
            } else {
                // Permission not granted, request it at runtime
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", holder.itemView.getContext().getPackageName(), null);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        } else {
            // Check if WRITE_EXTERNAL_STORAGE permission is granted
            if (ContextCompat.checkSelfPermission(holder.itemView.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted, request it at runtime
                ActivityCompat.requestPermissions((Activity) holder.itemView.getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                // Permission granted, enqueue the download
                long downloadReference = downloadManager.enqueue(request);
                Log.d("AssignmentAdapter", "Download enqueued with referenceId: " + downloadReference);

            }
        }



    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }
}

