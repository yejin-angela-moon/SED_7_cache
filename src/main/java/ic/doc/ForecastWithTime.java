package ic.doc;

import com.weather.Forecast;

public class ForecastWithTime {
    private final Forecast forecast;
    private final long timestamp;

    public ForecastWithTime(Forecast forecast) {
        this.forecast = forecast;
        this.timestamp = System.currentTimeMillis();
    }


    public Forecast getForecast() {
        return forecast;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
