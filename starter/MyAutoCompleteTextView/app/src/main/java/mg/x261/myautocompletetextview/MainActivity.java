package mg.x261.myautocompletetextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Create an array of strings to use as the data source for the autocomplete suggestions
        String[] africanCountries = new String[]{
                "Algeria",
                "Angola",
                "Benin",
                "Botswana",
                "Burkina Faso",
                "Burundi",
                "Cabo Verde",
                "Cameroon",
                "Central African Republic",
                "Chad",
                "Comoros",
                "Democratic Republic of the Congo",
                "Republic of the Congo",
                "Cote d'Ivoire",
                "Djibouti",
                "Egypt",
                "Equatorial Guinea",
                "Eritrea",
                "Eswatini",
                "Ethiopia",
                "Gabon",
                "Gambia",
                "Ghana",
                "Guinea",
                "Guinea-Bissau",
                "Kenya",
                "Lesotho",
                "Liberia",
                "Libya",
                "Madagascar",
                "Malawi",
                "Mali",
                "Mauritania",
                "Mauritius",
                "Morocco",
                "Mozambique",
                "Namibia",
                "Niger",
                "Nigeria",
                "Rwanda",
                "Sao Tome and Principe",
                "Senegal",
                "Seychelles",
                "Sierra Leone",
                "Somalia",
                "South Africa",
                "South Sudan",
                "Sudan",
                "Tanzania",
                "Togo",
                "Tunisia",
                "Uganda",
                "Zambia",
                "Zimbabwe"
        };


        // Create an ArrayAdapter using the data array and the default layout for a simple dropdown item
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, africanCountries);

        // Set the adapter on the AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter);

    }
}