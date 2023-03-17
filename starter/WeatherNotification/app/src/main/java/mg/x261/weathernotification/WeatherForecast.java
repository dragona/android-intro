package mg.x261.weathernotification;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherForecast implements Parcelable {
    private String date;
    private int temperature;
    private String description;

    public WeatherForecast(String date, int temperature, String description) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
    }

    // Implement the Parcelable interface methods
    protected WeatherForecast(Parcel in) {
        date = in.readString();
        temperature = in.readInt();
        description = in.readString();
    }

    public static final Creator<WeatherForecast> CREATOR = new Creator<WeatherForecast>() {
        @Override
        public WeatherForecast createFromParcel(Parcel in) {
            return new WeatherForecast(in);
        }

        @Override
        public WeatherForecast[] newArray(int size) {
            return new WeatherForecast[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeInt(temperature);
        dest.writeString(description);
    }

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
