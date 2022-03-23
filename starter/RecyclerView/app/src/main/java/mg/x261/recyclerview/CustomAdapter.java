package mg.x261.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<ObjectItem> localDataSet;
    // TODO 8
    MyListener myListener;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    // TODO 2: implement the onclicklistener
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private final TextView tvCountryName;
        private final TextView tvContinentName;
        private final ImageView imFlag;
        // TODO 4: add a global MyListener
        MyListener myListener;

        // TODO 5: update the ViewHolder to accept the listener
        public ViewHolder(View view, MyListener listener) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvCountryName = view.findViewById(R.id.txtCountryName);
            tvContinentName = view.findViewById(R.id.txtContinentName);
            imFlag = view.findViewById(R.id.imageViewFlag);
            // TODO 6: update the global listener
            this.myListener = listener;

            // Todo 3: attach the onclick listener to the entire view holder
            view.setOnClickListener(this);
        }

        public TextView getTvCountryName() {
            return tvCountryName;
        }


        @Override
        public void onClick(View view) {
            // TODO 7: call the onObjectItemClick here
            //      and pass the position of the item that was clicked
            myListener.onObjectItemClick(getAdapterPosition());

        }
    }

    /**
     * TODO 1: Interface to be using the the MainActivity
     */
    public interface MyListener{
        void onObjectItemClick(int position);
    }

    /**
     * Initialize the dataset of the Adapter.
     * <p>
     * ArrayList<ObjectItem> containing the data to populate views to be used
     * by RecyclerView.
     */
    // TODO 9: update the constructor
    public CustomAdapter(ArrayList<ObjectItem> dataSet, MyListener myListener) {

        localDataSet = dataSet;
        this.myListener = myListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
        return new ViewHolder(view, myListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.getTvCountryName().setText(localDataSet[position]);


        // Get the Subject based on the current position
        ObjectItem currentItem = localDataSet.get(position);

        // Setting views with the corresponding data
        ImageView imageView = viewHolder.imFlag;
        imageView.setImageResource(currentItem.getImageFlagId());

        TextView continentName = viewHolder.tvContinentName;
        continentName.setText(currentItem.getContinentName());

        TextView countryName = viewHolder.tvCountryName;
        countryName.setText(currentItem.getCountryName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();

    }


}
