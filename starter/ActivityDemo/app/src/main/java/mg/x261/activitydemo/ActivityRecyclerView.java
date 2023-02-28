package mg.x261.activitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityRecyclerView extends AppCompatActivity {

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);

        // Create data to display
        data = new String[]{"Item 1", "Item 2", "Item 3"};

        // Set up the RecyclerView with a LinearLayoutManager and the created adapter
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(data));
    }


    // Define the MyAdapter class that extends RecyclerView.Adapter<MyViewHolder>
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] data;

        // Initialize the adapter with the data to be displayed
        public MyAdapter(String[] data) {
            this.data = data;
        }

        // Inflate the layout for each item in the RecyclerView
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(
                    parent.getContext()).inflate(
                        R.layout.item_recycler_view,
                        parent,
                        false);
            return new MyViewHolder(itemView);
        }

        // Set the text of each item in the RecyclerView
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(data[position]);
        }

        // Return the number of items in the RecyclerView
        @Override
        public int getItemCount() {
            return data.length;
        }

        // Define the MyViewHolder class that extends RecyclerView.ViewHolder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            // Set the textView attribute to the "item_recycler_textview" view in the itemView
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.item_recycler_textview);
                // Set click listener on the item view
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getBindingAdapterPosition();
                        String itemText = data[position];
                        Toast.makeText(itemView.getContext(), "Clicked on " + itemText, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }

    }
}
