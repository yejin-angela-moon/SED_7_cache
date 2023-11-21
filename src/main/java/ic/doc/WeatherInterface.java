package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public interface WeatherInterface {
    Forecast forecastFor(Region region, Day day);
}
