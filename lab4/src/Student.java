public class Student {
    private String lastName;
    private int[] grades;

    public Student(String lastName, int[] grades) {
        setLastName(lastName);
        setGrades(grades);
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            throw new IllegalArgumentException("Прізвище студента не може бути порожнім.");
        this.lastName = lastName.trim();
    }

    public void setGrades(int[] grades) {
        if (grades == null || grades.length == 0)
            throw new IllegalArgumentException("Масив оцінок не може бути порожнім.");
        for (int g : grades) {
            if (g < 0 || g > 100)
                throw new IllegalArgumentException("Оцінка " + g + " виходить за межі 0–100.");
        }
        this.grades = grades.clone();
    }

    public String getLastName()  { return lastName; }
    public int[]  getGrades()    { return grades.clone(); }

    public double getAverage() {
        double sum = 0;
        for (int g : grades) sum += g;
        return sum / grades.length;
    }

    /** Формує рядок оцінок для запису у файл: "Іванов 85 90 78" */
    public String toFileLine() {
        StringBuilder sb = new StringBuilder(lastName);
        for (int g : grades) sb.append(" ").append(g);
        return sb.toString();
    }

    /** Парсить рядок формату "Іванов 85 90 78" */
    public static Student fromLine(String line) {
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("Порожній рядок.");
        String[] parts = line.trim().split("\\s+");
        if (parts.length < 2)
            throw new IllegalArgumentException(
                "Рядок \"" + line + "\" має містити прізвище та хоча б одну оцінку.");
        String name = parts[0];
        int[] grades = new int[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            try {
                grades[i - 1] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Не вдалося розпізнати оцінку \"" + parts[i] + "\" у рядку: " + line);
            }
        }
        return new Student(name, grades);
    }
}
