package mg.x261.recyclerview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.MyListener {
    ArrayList<ObjectItem> objectItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view);

        // Create an Arraylist that holds some objectItems
        objectItems = new ArrayList<>();
        objectItems.add(new ObjectItem("Madagascar", R.drawable.flag_madagascar, "Africa"));
        objectItems.add(new ObjectItem("France", R.drawable.flag_france, "Europe"));
        objectItems.add(new ObjectItem("China", R.drawable.flag_china, "Asia"));
        objectItems.add(new ObjectItem("Uganda", R.drawable.flag_uganda, "Africa"));
        objectItems.add(new ObjectItem("Ghana", R.drawable.flag_ghana, "Africa"));

        //
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        CustomAdapter adapter = new CustomAdapter(objectItems, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false ));
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns ));



    }


    @Override
    public void onObjectItemClick(int position) {
        Toast.makeText(this, " This is the position " + position, Toast.LENGTH_LONG).show();
        Log.d("TAG", "onObjectItemClick: "+ objectItems.get(position).getContinentName());
        Log.d("TAG", "onObjectItemClick: "+ objectItems.get(position).getCountryName());
        Log.d("TAG", "onObjectItemClick: "+ objectItems.get(position).getImageFlagId());

    }
}