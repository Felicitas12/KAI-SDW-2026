public class Flight {
    private String destination;
    private String flightNumber;
    private String planeType;
    private String departureTime; // "HH:MM"
    private String dayOfWeek;
    private String planeNumber;

    public Flight(String destination, String flightNumber, String planeType,
                  String departureTime, String dayOfWeek, String planeNumber) {
        setDestination(destination);
        setFlightNumber(flightNumber);
        setPlaneType(planeType);
        setDepartureTime(departureTime);
        setDayOfWeek(dayOfWeek);
        setPlaneNumber(planeNumber);
    }

    // --- Setters with validation ---
    public void setDestination(String destination) {
        if (destination == null || destination.trim().isEmpty())
            throw new IllegalArgumentException("Пункт призначення не може бути порожнім.");
        this.destination = destination.trim();
    }

    public void setFlightNumber(String flightNumber) {
        if (flightNumber == null || !flightNumber.matches("[A-Z]{2}\\d{3,4}"))
            throw new IllegalArgumentException("Номер рейсу має бути у форматі: дві великі літери + 3-4 цифри (напр. UA101).");
        this.flightNumber = flightNumber;
    }

    public void setPlaneType(String planeType) {
        if (planeType == null || planeType.trim().isEmpty())
            throw new IllegalArgumentException("Тип літака не може бути порожнім.");
        this.planeType = planeType.trim();
    }

    public void setDepartureTime(String departureTime) {
        if (departureTime == null || !departureTime.matches("([01]\\d|2[0-3]):[0-5]\\d"))
            throw new IllegalArgumentException("Час вильоту має бути у форматі HH:MM (00:00–23:59).");
        this.departureTime = departureTime;
    }

    public void setDayOfWeek(String dayOfWeek) {
        String[] validDays = {"Понеділок","Вівторок","Середа","Четвер","П'ятниця","Субота","Неділя"};
        for (String d : validDays) {
            if (d.equalsIgnoreCase(dayOfWeek.trim())) {
                this.dayOfWeek = d;
                return;
            }
        }
        throw new IllegalArgumentException("День тижня введено неправильно. Допустимі значення: " +
                String.join(", ", validDays));
    }

    public void setPlaneNumber(String planeNumber) {
        if (planeNumber == null || planeNumber.trim().isEmpty())
            throw new IllegalArgumentException("Номер літака не може бути порожнім.");
        this.planeNumber = planeNumber.trim();
    }

    // --- Getters ---
    public String getDestination()   { return destination; }
    public String getFlightNumber()  { return flightNumber; }
    public String getPlaneType()     { return planeType; }
    public String getDepartureTime() { return departureTime; }
    public String getDayOfWeek()     { return dayOfWeek; }
    public String getPlaneNumber()   { return planeNumber; }

    // --- Comparison helpers ---
    /** Returns departure time in minutes since midnight */
    public int getDepartureMinutes() {
        String[] parts = departureTime.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-15s %-10s %-12s %-12s",
                destination, flightNumber, planeType, departureTime, dayOfWeek, planeNumber);
    }
}
