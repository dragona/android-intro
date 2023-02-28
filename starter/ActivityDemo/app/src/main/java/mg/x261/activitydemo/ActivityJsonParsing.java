package mg.x261.activitydemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityJsonParsing extends AppCompatActivity {

    String inputString = "[{\"country\":\"Afghanistan\",\"capital\":\"Kabul\"},{\"country\":\"Albania\",\"capital\":\"Tirana\"},{\"country\":\"Algeria\",\"capital\":\"Algiers\"},{\"country\":\"Andorra\",\"capital\":\"Andorra la Vella\"},{\"country\":\"Angola\",\"capital\":\"Luanda\"},{\"country\":\"Antigua and Barbuda\",\"capital\":\"Saint John's\"},{\"country\":\"Argentina\",\"capital\":\"Buenos Aires\"},{\"country\":\"Armenia\",\"capital\":\"Yerevan\"},{\"country\":\"Australia\",\"capital\":\"Canberra\"},{\"country\":\"Austria\",\"capital\":\"Vienna\"},{\"country\":\"Azerbaijan\",\"capital\":\"Baku\"},{\"country\":\"Bahamas\",\"capital\":\"Nassau\"},{\"country\":\"Bahrain\",\"capital\":\"Manama\"},{\"country\":\"Bangladesh\",\"capital\":\"Dhaka\"},{\"country\":\"Barbados\",\"capital\":\"Bridgetown\"},{\"country\":\"Belarus\",\"capital\":\"Minsk\"},{\"country\":\"Belgium\",\"capital\":\"Brussels\"},{\"country\":\"Belize\",\"capital\":\"Belmopan\"},{\"country\":\"Benin\",\"capital\":\"Porto-Novo\"},{\"country\":\"Bhutan\",\"capital\":\"Thimphu\"},{\"country\":\"Bolivia\",\"capital\":\"La Paz\"},{\"country\":\"Bosnia and Herzegovina\",\"capital\":\"Sarajevo\"},{\"country\":\"Botswana\",\"capital\":\"Gaborone\"},{\"country\":\"Brazil\",\"capital\":\"Bras\\u00edlia\"},{\"country\":\"Brunei\",\"capital\":\"Bandar Seri Begawan\"},{\"country\":\"Bulgaria\",\"capital\":\"Sofia\"},{\"country\":\"Burkina Faso\",\"capital\":\"Ouagadougou\"},{\"country\":\"Burundi\",\"capital\":\"Bujumbura\"},{\"country\":\"Cabo Verde\",\"capital\":\"Praia\"},{\"country\":\"Cambodia\",\"capital\":\"Phnom Penh\"},{\"country\":\"Cameroon\",\"capital\":\"Yaound\\u00e9\"},{\"country\":\"Canada\",\"capital\":\"Ottawa\"},{\"country\":\"Central African Republic\",\"capital\":\"Bangui\"},{\"country\":\"Chad\",\"capital\":\"N'Djamena\"},{\"country\":\"Chile\",\"capital\":\"Santiago\"},{\"country\":\"China\",\"capital\":\"Beijing\"},{\"country\":\"Colombia\",\"capital\":\"Bogot\\u00e1\"},{\"country\":\"Comoros\",\"capital\":\"Moroni\"},{\"country\":\"Republic of the Congo\",\"capital\":\"Brazzaville\"},{\"country\":\"Democratic Republic of the Congo \",\"capital\":\"Kinshasa\"},{\"country\":\"Costa Rica\",\"capital\":\"San Jos\\u00e9\"},{\"country\":\"Cote d'Ivoire\",\"capital\":\"Yamoussoukro\"},{\"country\":\"Croatia\",\"capital\":\"Zagreb\"},{\"country\":\"Cuba\",\"capital\":\"Havana\"},{\"country\":\"Cyprus\",\"capital\":\"Nicosia\"},{\"country\":\"Czech Republic\",\"capital\":\"Prague\"},{\"country\":\"Denmark\",\"capital\":\"Copenhagen\"},{\"country\":\"Djibouti\",\"capital\":\"Djibouti\"},{\"country\":\"Dominica\",\"capital\":\"Roseau\"},{\"country\":\"Dominican Republic\",\"capital\":\"Santo Domingo\"},{\"country\":\"Ecuador\",\"capital\":\"Quito\"},{\"country\":\"Egypt\",\"capital\":\"Cairo\"},{\"country\":\"El Salvador\",\"capital\":\"San Salvador\"},{\"country\":\"Equatorial Guinea\",\"capital\":\"Malabo\"},{\"country\":\"Eritrea\",\"capital\":\"Asmara\"},{\"country\":\"Estonia\",\"capital\":\"Tallinn\"},{\"country\":\"Eswatini\",\"capital\":\"Mbabane\"},{\"country\":\"Ethiopia\",\"capital\":\"Addis Ababa\"},{\"country\":\"Fiji\",\"capital\":\"Suva\"},{\"country\":\"Finland\",\"capital\":\"Helsinki\"},{\"country\":\"France\",\"capital\":\"Paris\"},{\"country\":\"Gabon\",\"capital\":\"Libreville\"},{\"country\":\"Gambia\",\"capital\":\"Banjul\"},{\"country\":\"Georgia\",\"capital\":\"Tbilisi\"},{\"country\":\"Germany\",\"capital\":\"Berlin\"},{\"country\":\"Ghana\",\"capital\":\"Accra\"},{\"country\":\"Greece\",\"capital\":\"Athens\"},{\"country\":\"Grenada\",\"capital\":\"St. George's\"},{\"country\":\"Guatemala\",\"capital\":\"Guatemala City\"},{\"country\":\"Guinea\",\"capital\":\"Conakry\"},{\"country\":\"Guinea-Bissau\",\"capital\":\"Bissau\"},{\"country\":\"Guyana\",\"capital\":\"Georgetown\"},{\"country\":\"Haiti\",\"capital\":\"Port-au-Prince\"},{\"country\":\"Honduras\",\"capital\":\"Tegucigalpa\"},{\"country\":\"Hungary\",\"capital\":\"Budapest\"},{\"country\":\"Iceland\",\"capital\":\"Reykjavik\"},{\"country\":\"India\",\"capital\":\"New Delhi\"},{\"country\":\"Indonesia\",\"capital\":\"Jakarta\"},{\"country\":\"Iran\",\"capital\":\"Tehran\"},{\"country\":\"Iraq\",\"capital\":\"Baghdad\"},{\"country\":\"Ireland\",\"capital\":\"Dublin\"},{\"country\":\"Israel\",\"capital\":\"Jerusalem\"},{\"country\":\"Italy\",\"capital\":\"Rome\"},{\"country\":\"Jamaica\",\"capital\":\"Kingston\"},{\"country\":\"Japan\",\"capital\":\"Tokyo\"},{\"country\":\"Jordan\",\"capital\":\"Amman\"},{\"country\":\"Kazakhstan\",\"capital\":\"Nur-Sultan\"},{\"country\":\"Kenya\",\"capital\":\"Nairobi\"},{\"country\":\"Kiribati\",\"capital\":\"South Tarawa\"},{\"country\":\"Kosovo\",\"capital\":\"Pristina\"},{\"country\":\"Kuwait\",\"capital\":\"Kuwait City\"},{\"country\":\"Kyrgyzstan\",\"capital\":\"Bishkek\"},{\"country\":\"Laos\",\"capital\":\"Vientiane\"},{\"country\":\"Latvia\",\"capital\":\"Riga\"},{\"country\":\"Lebanon\",\"capital\":\"Beirut\"},{\"country\":\"Lesotho\",\"capital\":\"Maseru\"},{\"country\":\"Liberia\",\"capital\":\"Monrovia\"},{\"country\":\"Libya\",\"capital\":\"Tripoli\"},{\"country\":\"Liechtenstein\",\"capital\":\"Vaduz\"},{\"country\":\"Lithuania\",\"capital\":\"Vilnius\"},{\"country\":\"Luxembourg\",\"capital\":\"Luxembourg\"},{\"country\":\"Madagascar\",\"capital\":\"Antananarivo\"},{\"country\":\"Malawi\",\"capital\":\"Lilongwe\"},{\"country\":\"Malaysia\",\"capital\":\"Kuala Lumpur\"},{\"country\":\"Maldives\",\"capital\":\"Mal\\u00e9\"},{\"country\":\"Mali\",\"capital\":\"Bamako\"},{\"country\":\"Malta\",\"capital\":\"Valletta\"},{\"country\":\"Marshall Islands\",\"capital\":\"Majuro\"},{\"country\":\"Mauritania\",\"capital\":\"Nouakchott\"},{\"country\":\"Mauritius\",\"capital\":\"Port Louis\"},{\"country\":\"Mexico\",\"capital\":\"Mexico City\"},{\"country\":\"Micronesia\",\"capital\":\"Palikir\"},{\"country\":\"Moldova\",\"capital\":\"Chisinau\"},{\"country\":\"Monaco\",\"capital\":\"Monaco\"},{\"country\":\"Mongolia\",\"capital\":\"Ulaanbaatar\"},{\"country\":\"Montenegro\",\"capital\":\"Podgorica\"},{\"country\":\"Morocco\",\"capital\":\"Rabat\"},{\"country\":\"Mozambique\",\"capital\":\"Maputo\"},{\"country\":\"Myanmar (Burma)\",\"capital\":\"Naypyidaw\"},{\"country\":\"Namibia\",\"capital\":\"Windhoek\"},{\"country\":\"Nauru\",\"capital\":\"Yaren\"},{\"country\":\"Nepal\",\"capital\":\"Kathmandu\"},{\"country\":\"Netherlands\",\"capital\":\"Amsterdam\"},{\"country\":\"New Zealand\",\"capital\":\"Wellington\"},{\"country\":\"Nicaragua\",\"capital\":\"Managua\"},{\"country\":\"Niger\",\"capital\":\"Niamey\"},{\"country\":\"Nigeria\",\"capital\":\"Abuja\"},{\"country\":\"North Korea\",\"capital\":\"Pyongyang\"},{\"country\":\"North Macedonia (Macedonia)\",\"capital\":\"Skopje\"},{\"country\":\"Norway\",\"capital\":\"Oslo\"},{\"country\":\"Oman\",\"capital\":\"Muscat\"},{\"country\":\"Pakistan\",\"capital\":\"Islamabad\"},{\"country\":\"Palau\",\"capital\":\"Ngerulmud\"},{\"country\":\"Panama\",\"capital\":\"Panama City\"},{\"country\":\"Papua New Guinea\",\"capital\":\"Port Moresby\"},{\"country\":\"Paraguay\",\"capital\":\"Asunci\\u00f3n\"},{\"country\":\"Peru\",\"capital\":\"Lima\"},{\"country\":\"Philippines\",\"capital\":\"Manila\"},{\"country\":\"Poland\",\"capital\":\"Warsaw\"},{\"country\":\"Portugal\",\"capital\":\"Lisbon\"},{\"country\":\"Qatar\",\"capital\":\"Doha\"},{\"country\":\"Romania\",\"capital\":\"Bucharest\"}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_json_parsing);
    }

    public void btnParse(View view) {


        try {
            // Create a new JSONArray object by passing a string of JSON data as a parameter
            JSONArray jsonArray = new JSONArray(inputString);

            // Loop through each JSON object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                // Get the i-th JSON object from the array
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the "country" and "capital" values from the JSON object
                String country = jsonObject.getString("country");
                String capital = jsonObject.getString("capital");

                Log.d("TAG", country + " - " + capital);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
