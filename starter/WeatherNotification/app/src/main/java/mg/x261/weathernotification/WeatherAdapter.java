package mg.x261.weathernotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends BaseAdapter {
    private Context context;
    private List<WeatherForecast> forecasts;

    public WeatherAdapter(Context context, List<WeatherForecast> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
    }

    @Override
    public int getCount() {
        return forecasts.size();
    }

    @Override
    public Object getItem(int position) {
        return forecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_weather, parent, false);
            holder = new ViewHolder();
            holder.dateTextView = convertView.findViewById(R.id.date_text_view);
            holder.temperatureTextView = convertView.findViewById(R.id.temperature_text_view);
            holder.descriptionTextView = convertView.findViewById(R.id.description_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WeatherForecast forecast = forecasts.get(position);
        holder.dateTextView.setText(forecast.getDate());
        holder.temperatureTextView.setText(String.valueOf(forecast.getTemperature()) + "°C");
        holder.descriptionTextView.setText(forecast.getDescription());

        return convertView;
    }

    public void setData(List<WeatherForecast> forecasts) {
        this.forecasts = forecasts;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView dateTextView;
        TextView temperatureTextView;
        TextView descriptionTextView;
    }
}
