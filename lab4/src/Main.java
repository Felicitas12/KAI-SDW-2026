import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  Лабораторна робота №4 — Обробка оцінок студентів       ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        // --- 1. Вхідний файл ---
        String inputPath = promptFilePath(sc,
                "\nВведіть шлях до вхідного файлу (або Enter для demo_input.txt): ",
                "demo_input.txt");

        // Якщо демо-файл не існує — створити
        if (inputPath.equals("demo_input.txt")) {
            createDemoFile(inputPath);
        }

        // --- 2. Вихідний файл ---
        String outputPath = promptNonEmpty(sc,
                "Введіть шлях до результуючого файлу (або Enter для result.txt): ",
                "result.txt");

        // --- 3. Поріг ---
        double threshold = promptDouble(sc,
                "Введіть поріг середнього балу (Enter = 90): ", 90.0, 0, 100);

        // --- 4. Обробка ---
        StudentFileProcessor processor = new StudentFileProcessor(threshold);
        try {
            List<Student> students  = processor.readFromFile(inputPath);
            List<Student> processed = processor.process(students);
            processor.writeToFile(outputPath, processed);

            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║  ГОТОВО! Результат збережено у: " +
                    padRight(outputPath, 26) + "║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

        } catch (FileNotFoundException e) {
            System.out.println("\n[ПОМИЛКА] Файл не знайдено: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("\n[ПОМИЛКА] Помилка вводу/виводу: " + e.getMessage());
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    static String promptFilePath(Scanner sc, String prompt, String defaultVal) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        return val.isEmpty() ? defaultVal : val;
    }

    static String promptNonEmpty(Scanner sc, String prompt, String defaultVal) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        return val.isEmpty() ? defaultVal : val;
    }

    static double promptDouble(Scanner sc, String prompt, double defaultVal,
                               double min, double max) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            if (raw.isEmpty()) return defaultVal;
            try {
                double v = Double.parseDouble(raw.replace(",", "."));
                if (v < min || v > max) {
                    System.out.printf("  Помилка: значення має бути від %.0f до %.0f.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  Помилка: введіть числове значення.");
            }
        }
    }

    static void createDemoFile(String path) {
        String[] lines = {
            "Іваненко 92 88 95 91",
            "Петренко 76 80 70 65",
            "Коваленко 95 97 99 100",
            "Мельник 55 60 50 45",
            "Бондаренко 91 93 90 88",
            "Шевченко 40 55 60 35",
            "Кравченко 88 85 90 87",
            "Ткаченко 91 95 92 94",
            "Савченко 70 72 68 75",
            "Гончаренко 60 55 65 58"
        };
        System.out.println("\n[ДЕМО] Вхідний файл не вказано — створюємо demo_input.txt...");
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), java.nio.charset.StandardCharsets.UTF_8))) {
            for (String line : lines) { bw.write(line); bw.newLine(); }
            System.out.println("[ДЕМО] Файл demo_input.txt створено (" + lines.length + " записів).");
        } catch (IOException e) {
            System.out.println("[ПОМИЛКА] Не вдалось створити демо-файл: " + e.getMessage());
        }
    }

    static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
