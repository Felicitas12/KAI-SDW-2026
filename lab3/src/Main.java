import java.util.List;
import java.util.Scanner;

public class Main {
    static final String HEADER =
            String.format("%-20s %-10s %-15s %-10s %-12s %-12s",
                    "Пункт призначення", "Рейс", "Тип літака", "Час вильоту", "День тижня", "№ літака");
    static final String SEPARATOR = "-".repeat(82);

    public static void main(String[] args) {
        Airport airport = new Airport();
        Scanner scanner = new Scanner(System.in);

        // --- Ініціалізація тестових даних ---
        System.out.println("=== Аеропорт: система управління рейсами ===\n");
        System.out.println("Завантаження тестових даних...");

        String[][] data = {
            {"Варшава",   "LO101", "Boeing 737",  "08:30", "Понеділок", "SP-LNA"},
            {"Берлін",    "PS201", "Airbus A320", "10:00", "Понеділок", "UR-PSA"},
            {"Варшава",   "LO103", "Embraer 175", "14:45", "Середа",    "SP-LNB"},
            {"Відень",    "PS302", "Boeing 777",  "07:15", "П'ятниця",  "UR-PSB"},
            {"Берлін",    "UA401", "Airbus A321", "17:20", "Понеділок", "UR-UAA"},
            {"Варшава",   "LO105", "Boeing 737",  "19:00", "П'ятниця",  "SP-LNC"},
            {"Лондон",    "PS501", "Boeing 777",  "09:30", "Середа",    "UR-PSC"},
            {"Амстердам", "KL601", "Airbus A330", "06:45", "Четвер",    "PH-BGA"},
            {"Лондон",    "BA701", "Boeing 787",  "21:10", "П'ятниця",  "G-ZBKP"},
            {"Відень",    "OS801", "Airbus A320", "13:55", "Четвер",    "OE-LBQ"}
        };

        for (String[] row : data) {
            try {
                airport.addFlight(new Flight(row[0], row[1], row[2], row[3], row[4], row[5]));
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка у тестових даних: " + e.getMessage());
            }
        }

        printFlights("Усі рейси:", airport.getAllFlights());

        // --- Меню ---
        while (true) {
            System.out.println("\n=== МЕНЮ ПОШУКУ ===");
            System.out.println("1. Рейси до заданого пункту призначення");
            System.out.println("2. Рейси для заданого дня тижня");
            System.out.println("3. Рейси для дня тижня, час вильоту яких пізніше заданого");
            System.out.println("4. Додати новий рейс");
            System.out.println("0. Вихід");
            System.out.print("\nВаш вибір: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> searchByDestination(scanner, airport);
                case "2" -> searchByDay(scanner, airport);
                case "3" -> searchByDayAndTime(scanner, airport);
                case "4" -> addNewFlight(scanner, airport);
                case "0" -> { System.out.println("До побачення!"); return; }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    static void searchByDestination(Scanner sc, Airport airport) {
        String dest = promptNonEmpty(sc, "Введіть пункт призначення: ");
        try {
            List<Flight> result = airport.findByDestination(dest);
            printFlights("Рейси до \"" + dest + "\":", result);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    static void searchByDay(Scanner sc, Airport airport) {
        String day = promptNonEmpty(sc, "Введіть день тижня: ");
        try {
            List<Flight> result = airport.findByDayOfWeek(day);
            printFlights("Рейси у " + day + ":", result);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    static void searchByDayAndTime(Scanner sc, Airport airport) {
        String day = promptNonEmpty(sc, "Введіть день тижня: ");
        String time = "";
        while (true) {
            System.out.print("Введіть час (HH:MM), пізніше якого шукати: ");
            time = sc.nextLine().trim();
            if (time.matches("([01]\\d|2[0-3]):[0-5]\\d")) break;
            System.out.println("Помилка: час має бути у форматі HH:MM (наприклад 14:30).");
        }
        try {
            List<Flight> result = airport.findByDayAndAfterTime(day, time);
            printFlights("Рейси у " + day + " після " + time + ":", result);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    static void addNewFlight(Scanner sc, Airport airport) {
        System.out.println("\n--- Додавання нового рейсу ---");
        String dest      = promptNonEmpty(sc, "Пункт призначення: ");
        String flightNum = promptValidated(sc, "Номер рейсу (напр. UA101): ",
                "[A-Z]{2}\\d{3,4}", "Формат: дві великі літери + 3-4 цифри.");
        String planeType = promptNonEmpty(sc, "Тип літака: ");
        String depTime   = promptValidated(sc, "Час вильоту (HH:MM): ",
                "([01]\\d|2[0-3]):[0-5]\\d", "Формат HH:MM, наприклад 09:45.");
        String day       = promptNonEmpty(sc, "День тижня: ");
        String planeNum  = promptNonEmpty(sc, "Номер літака: ");

        try {
            Flight f = new Flight(dest, flightNum, planeType, depTime, day, planeNum);
            airport.addFlight(f);
            System.out.println("✓ Рейс успішно додано!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка при створенні рейсу: " + e.getMessage());
        }
    }

    // --- Utility ---
    static String promptNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("Помилка: значення не може бути порожнім. Спробуйте ще раз.");
        }
    }

    static String promptValidated(Scanner sc, String prompt, String regex, String hint) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (val.matches(regex)) return val;
            System.out.println("Помилка: " + hint);
        }
    }

    static void printFlights(String title, List<Flight> flights) {
        System.out.println("\n" + title);
        System.out.println(SEPARATOR);
        System.out.println(HEADER);
        System.out.println(SEPARATOR);
        if (flights.isEmpty()) {
            System.out.println("   Рейсів за заданим критерієм не знайдено.");
        } else {
            for (Flight f : flights) System.out.println(f);
        }
        System.out.println(SEPARATOR);
        System.out.println("Знайдено рейсів: " + flights.size());
    }
}
