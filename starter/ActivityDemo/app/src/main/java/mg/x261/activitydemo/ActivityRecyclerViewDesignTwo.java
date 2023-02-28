package mg.x261.activitydemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityRecyclerViewDesignTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view_design_two);

        // Create data to display
        String[] data_up = new String[]{
                "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
                "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
                "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan",
                "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria",
                "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada",
                "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
                "Republic of the Congo", "Democratic Republic of the Congo ", "Costa Rica", "Cote d'Ivoire",
                "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica",
                "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
                "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon",
                "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala",
                "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland",
                "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy",
                "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo",
                "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia",
                "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia",
                "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico",
                "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
                "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
                "Niger", "Nigeria", "North Korea", "North Macedonia (Macedonia)", "Norway", "Oman", "Pakistan",
                "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland",
                "Portugal", "Qatar", "Romania"};

        String[] data_down = new String[]{
                "Kabul", "Tirana", "Algiers", "Andorra la Vella", "Luanda", "Saint John's",
                "Buenos Aires", "Yerevan", "Canberra", "Vienna", "Baku", "Nassau", "Manama",
                "Dhaka", "Bridgetown", "Minsk", "Brussels", "Belmopan", "Porto-Novo", "Thimphu",
                "La Paz", "Sarajevo", "Gaborone", "Brasília", "Bandar Seri Begawan", "Sofia",
                "Ouagadougou", "Bujumbura", "Praia", "Phnom Penh", "Yaoundé", "Ottawa",
                "Bangui", "N'Djamena", "Santiago", "Beijing", "Bogotá", "Moroni",
                "Brazzaville", "Kinshasa", "San José", "Yamoussoukro", "Zagreb", "Havana",
                "Nicosia", "Prague", "Copenhagen", "Djibouti", "Roseau", "Santo Domingo",
                "Quito", "Cairo", "San Salvador", "Malabo", "Asmara", "Tallinn",
                "Mbabane", "Addis Ababa", "Suva", "Helsinki", "Paris", "Libreville",
                "Banjul", "Tbilisi", "Berlin", "Accra", "Athens", "St. George's", "Guatemala City",
                "Conakry", "Bissau", "Georgetown", "Port-au-Prince", "Tegucigalpa", "Budapest", "Reykjavik",
                "New Delhi", "Jakarta", "Tehran", "Baghdad", "Dublin", "Jerusalem", "Rome",
                "Kingston", "Tokyo", "Amman", "Nur-Sultan", "Nairobi", "South Tarawa", "Pristina",
                "Kuwait City", "Bishkek", "Vientiane", "Riga", "Beirut", "Maseru", "Monrovia",
                "Tripoli", "Vaduz", "Vilnius", "Luxembourg", "Antananarivo", "Lilongwe", "Kuala Lumpur",
                "Malé", "Bamako", "Valletta", "Majuro", "Nouakchott", "Port Louis", "Mexico City",
                "Palikir", "Chisinau", "Monaco", "Ulaanbaatar", "Podgorica", "Rabat", "Maputo",
                "Naypyidaw", "Windhoek", "Yaren", "Kathmandu", "Amsterdam", "Wellington", "Managua",
                "Niamey", "Abuja", "Pyongyang", "Skopje", "Oslo", "Muscat", "Islamabad",
                "Ngerulmud", "Panama City", "Port Moresby", "Asunción", "Lima", "Manila", "Warsaw",
                "Lisbon", "Doha", "Bucharest"};


        // Set up the RecyclerView with a LinearLayoutManager and the created adapter
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(data_up, data_down));
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
