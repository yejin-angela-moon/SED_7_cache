package ic.doc;

public interface FakeClock {
    boolean hasPassedTime(long currentTime, ForecastWithTime forecastWithTime, long duration);
}
