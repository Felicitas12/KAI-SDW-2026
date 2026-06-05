import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StudentFileProcessor {

    private final double threshold;

    public StudentFileProcessor(double threshold) {
        this.threshold = threshold;
    }

    /** Читає список студентів з файлу. Повертає список та виводить лог у консоль. */
    public List<Student> readFromFile(String path) throws IOException {
        List<Student> students = new ArrayList<>();
        System.out.println("\n[ЧИТАННЯ] Відкриваємо файл: " + path);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {

            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.isBlank()) { System.out.println("  Рядок " + lineNum + ": (порожній, пропускаємо)"); continue; }
                try {
                    Student s = Student.fromLine(line);
                    students.add(s);
                    System.out.printf("  Рядок %2d: зчитано  %-20s  оцінки: %s  середній: %.2f%n",
                            lineNum, s.getLastName(), gradesToStr(s.getGrades()), s.getAverage());
                } catch (IllegalArgumentException e) {
                    System.out.println("  Рядок " + lineNum + ": ПОМИЛКА — " + e.getMessage() + " (рядок пропущено)");
                }
            }
        }
        System.out.println("[ЧИТАННЯ] Успішно зчитано студентів: " + students.size());
        return students;
    }

    /**
     * Обробляє список: прізвища студентів із середнім балом > threshold
     * замінюються на ВЕЛИКІ ЛІТЕРИ.
     */
    public List<Student> process(List<Student> students) {
        System.out.printf("%n[ОБРОБКА] Критерій: середній бал > %.1f%n", threshold);
        List<Student> processed = new ArrayList<>();
        for (Student s : students) {
            double avg = s.getAverage();
            String name = s.getLastName();
            if (avg > threshold) {
                String upper = name.toUpperCase();
                System.out.printf("  %-20s  сер.бал=%.2f > %.1f  →  %s (прізвище у верхній регістр)%n",
                        name, avg, threshold, upper);
                processed.add(new Student(upper, s.getGrades()));
            } else {
                System.out.printf("  %-20s  сер.бал=%.2f ≤ %.1f  →  без змін%n", name, avg, threshold);
                processed.add(s);
            }
        }
        long changed = processed.stream()
                .filter(s -> s.getLastName().equals(s.getLastName().toUpperCase()) &&
                             !s.getLastName().isEmpty() &&
                             Character.isUpperCase(s.getLastName().charAt(0)))
                .count();
        System.out.println("[ОБРОБКА] Змінено прізвищ: " + changed);
        return processed;
    }

    /** Записує оброблений список у результуючий файл. */
    public void writeToFile(String path, List<Student> students) throws IOException {
        System.out.println("\n[ЗАПИС]   Відкриваємо результуючий файл: " + path);
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
            for (Student s : students) {
                bw.write(s.toFileLine());
                bw.newLine();
                System.out.printf("  Записано: %s%n", s.toFileLine());
            }
        }
        System.out.println("[ЗАПИС]   Записано студентів: " + students.size() + " → файл: " + path);
    }

    private static String gradesToStr(int[] grades) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < grades.length; i++) {
            sb.append(grades[i]);
            if (i < grades.length - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
