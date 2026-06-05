import java.util.ArrayList;
import java.util.List;

public class Airport {
    private List<Flight> flights = new ArrayList<>();

    public void addFlight(Flight f) {
        flights.add(f);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    /** Завдання 1: Список рейсів до заданого пункту призначення */
    public List<Flight> findByDestination(String destination) {
        if (destination == null || destination.trim().isEmpty())
            throw new IllegalArgumentException("Пункт призначення для пошуку не може бути порожнім.");
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDestination().equalsIgnoreCase(destination.trim()))
                result.add(f);
        }
        return result;
    }

    /** Завдання 2: Список рейсів для заданого дня тижня */
    public List<Flight> findByDayOfWeek(String day) {
        if (day == null || day.trim().isEmpty())
            throw new IllegalArgumentException("День тижня для пошуку не може бути порожнім.");
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDayOfWeek().equalsIgnoreCase(day.trim()))
                result.add(f);
        }
        return result;
    }

    /** Завдання 3: Список рейсів для заданого дня тижня, час вильоту яких пізніше заданого */
    public List<Flight> findByDayAndAfterTime(String day, String afterTime) {
        if (day == null || day.trim().isEmpty())
            throw new IllegalArgumentException("День тижня для пошуку не може бути порожнім.");
        if (afterTime == null || !afterTime.matches("([01]\\d|2[0-3]):[0-5]\\d"))
            throw new IllegalArgumentException("Час має бути у форматі HH:MM.");

        int[] parts = { Integer.parseInt(afterTime.split(":")[0]),
                         Integer.parseInt(afterTime.split(":")[1]) };
        int afterMinutes = parts[0] * 60 + parts[1];

        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getDayOfWeek().equalsIgnoreCase(day.trim())
                    && f.getDepartureMinutes() > afterMinutes)
                result.add(f);
        }
        return result;
    }
}
