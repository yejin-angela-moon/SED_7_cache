package ic.doc;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WeatherCacheTest {

    @Test
    public void addToWeatherCacheTwiceThenHit() {
        WeatherCache weatherCache = new WeatherCache(new Forecaster(), 10);
        Forecast forecast1 = weatherCache.forecastFor(Region.LONDON, Day.FRIDAY);
        Forecast forecast2 = weatherCache.forecastFor(Region.LONDON, Day.FRIDAY);
        assertThat(weatherCache.getSize(), equalTo(1));
        assertEquals(forecast1, forecast2);
    }

    @Test
    public void ifCacheSizeExceedsLimitRemoveTheOldestEntry() {
        WeatherCache weatherCache = new WeatherCache(new Forecaster(), 2);
        Forecast forecast1 = weatherCache.forecastFor(Region.LONDON, Day.FRIDAY);
        Forecast forecast2 = weatherCache.forecastFor(Region.EDINBURGH, Day.FRIDAY);
        Forecast forecast3 = weatherCache.forecastFor(Region.GLASGOW, Day.SATURDAY);
        assertThat(weatherCache.getSize(), equalTo(2));
        assertFalse(weatherCache.cacheContainsForecast(Region.LONDON, Day.FRIDAY));
    }

    @Test
    public void ifOneHourHasPassedRefreshTheCache() {
        long currentTime = System.currentTimeMillis();
        JUnit4Mockery context = new JUnit4Mockery();
        FakeClock clock = context.mock(FakeClock.class);
        context.checking(new Expectations() {{
            exactly()
        }});

        Forecast forecast1 = weatherCache.forecastFor(Region.LONDON, Day.FRIDAY);

        Forecast forecast2 = weatherCache.forecastFor(Region.EDINBURGH, Day.FRIDAY);


    }

}
