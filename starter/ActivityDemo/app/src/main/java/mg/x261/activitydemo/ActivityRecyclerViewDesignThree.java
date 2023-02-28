package mg.x261.activitydemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ActivityRecyclerViewDesignThree extends AppCompatActivity {


    String inputString = "[{\"country\":\"Afghanistan\",\"capital\":\"Kabul\"},{\"country\":\"Albania\",\"capital\":\"Tirana\"}]";
    RecyclerView mRecyclerView;
    String[] data_up;
    String[] data_down;
    Button button;
    String buttonText;
    // Initialize a counter to keep track of the state
    int state = 0;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view_design_three);
        mRecyclerView = findViewById(R.id.my_recycler_view_three);
        button = findViewById(R.id.button_handler);

        // Initialize the data arrays as empty arrays
        data_up = new String[0];
        data_down = new String[0];

        myAdapter = new MyAdapter(data_up, data_down);


    }

    public void mButtonHandler(View view) {
        // Get the current text of the button
        buttonText = button.getText().toString().toLowerCase();
        // Cycle through the different states and methods each time the button is clicked
        if (buttonText.equals("load content") || buttonText.equals("load data")) {
            state = 1;
            loadJsonDataToRecyclerView();
            button.setText("Clear Content");
        } else if (buttonText.equals("clear content")) {
            state = 2;
            clearRecyclerView();
            button.setText("Add Item");
        } else {
            state = 0;
            addNewCountryAndCapital();
            button.setText("Load Content");
        }


    }


    public void clearRecyclerView() {
        // Clear the content of the recycler view
        data_up = new String[0];
        data_down = new String[0];
        myAdapter = new MyAdapter(data_up, data_down);
        mRecyclerView.setAdapter(myAdapter);
    }

    public void loadJsonDataToRecyclerView() {
        try {
            JSONArray jsonArray = new JSONArray(inputString);
            String[] _data_up = new String[jsonArray.length()];
            String[] _data_down = new String[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String country = jsonObject.getString("country");
                String capital = jsonObject.getString("capital");
                _data_up[i] = country;
                _data_down[i] = capital;
            }

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            myAdapter = new MyAdapter(_data_up, _data_down);
            mRecyclerView.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addNewCountryAndCapital() {
        // Create the dialog to prompt the user for new country and capital
        AlertDialog dialog = createNewCountryAndCapitalDialog(new NewCountryAndCapitalListener() {
            @Override
            public void onNewCountryAndCapitalEntered(String newCountry, String newCapital) {
                handleNewCountryAndCapitalEntered(newCountry, newCapital);
            }
        });

        dialog.show();
    }

    private AlertDialog createNewCountryAndCapitalDialog(final NewCountryAndCapitalListener listener) {
        // Create the view for the dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_add_item, null);
        final EditText countryEditText = view.findViewById(R.id.country_edit_text);
        final EditText capitalEditText = view.findViewById(R.id.capital_edit_text);

        // Create the dialog with the view
        return new AlertDialog.Builder(this)
                .setTitle("Add New Country and Capital")
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newCountry = countryEditText.getText().toString();
                        String newCapital = capitalEditText.getText().toString();

                        // Notify the listener that new country and capital data was entered
                        listener.onNewCountryAndCapitalEntered(newCountry, newCapital);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }

    private void handleNewCountryAndCapitalEntered(String newCountry, String newCapital) {
        // Initialize the data arrays if they are null or have length 0
        if (data_up == null || data_up.length == 0 || data_down == null || data_down.length == 0) {
            data_up = new String[1];
            data_down = new String[1];
            data_up[0] = newCountry;
            data_down[0] = newCapital;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityRecyclerViewDesignThree.this));
            myAdapter = new MyAdapter(data_up, data_down);
            mRecyclerView.setAdapter(myAdapter);
        } else {
            // Add the new strings to the data arrays
            String[] newDataUp = Arrays.copyOf(data_up, data_up.length + 1);
            String[] newDataDown = Arrays.copyOf(data_down, data_down.length + 1);
            newDataUp[newDataUp.length - 1] = newCountry;
            newDataDown[newDataDown.length - 1] = newCapital;

            // Set the new data arrays on the adapter
            myAdapter.setData(newDataUp, newDataDown);
        }
    }

    private interface NewCountryAndCapitalListener {
        void onNewCountryAndCapitalEntered(String newCountry, String newCapital);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private String[] data_up;
        private String[] data_down;

        public MyAdapter(String[] data_up, String[] data_down) {
            this.data_up = data_up;
            this.data_down = data_down;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_view_design_two, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textViewUp.setText(data_up[position]);
            holder.textViewDown.setText(data_down[position]);
        }

        public void setData(String[] data_up, String[] data_down) {
            this.data_up = data_up;
            this.data_down = data_down;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return data_up.length;
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
                        String itemText = data_up[position];
                        Toast.makeText(itemView.getContext(), "Clicked on " + itemText, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        }
    }
}
