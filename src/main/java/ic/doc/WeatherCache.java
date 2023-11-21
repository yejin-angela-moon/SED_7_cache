package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WeatherCache {
    private Forecaster forecaster;
    private Map<Region, Map<Day, ForecastWithTime>> weatherMap;
    private final int size;
    private final long HOUR = TimeUnit.HOURS.toMillis(1);

    public WeatherCache(Forecaster forecaster, int size) {
        this.forecaster = forecaster;
        this.weatherMap = new HashMap<>();
        this.size = size;
    }

    public Forecast forecastFor(Region region, Day day) {
        long currentTime = System.currentTimeMillis();
        // Look into cache first for the forecast
        if (weatherMap.containsKey(region) && weatherMap.get(region).containsKey(day)) {
            ForecastWithTime forecastWithTime = weatherMap.get(region).get(day);
            // if time elapsed is less than an hour
            if (currentTime - forecastWithTime.getTimestamp() < HOUR) {
                return forecastWithTime.getForecast();
            } else {
//                Refresh hashmap if 1 hour has passed
//                Fetch from the third party if 1 hour has passed and add to the cache, remove the old entry
                Forecast newForecast = forecaster.forecastFor(region, day);
                weatherMap.remove(region);
                weatherMap.computeIfAbsent(region, k -> new HashMap<>()).put(day, new ForecastWithTime(newForecast));
                return newForecast;
            }
        } else {
            // if cache doesn't contain the forecast look into the third party client
            if (weatherMap.size() >= size) {
                // if maximum size is reached, evict the old entry
                weatherMap.remove(weatherMap.keySet().iterator().next());
            }
            // put a new entry into the cache map
            Forecast newForecast = forecaster.forecastFor(region, day);
            weatherMap.computeIfAbsent(region, k -> new HashMap<>()).put(day, new ForecastWithTime(newForecast));
            return newForecast;
        }
    }


}
