import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        printHeader();


        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║          ЗАВДАННЯ 1: Аналіз чотиризначних чисел      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");

        Random random = new Random();
        int totalGenerated = 10; // Кількість чисел для генерації

        System.out.println("Генерується " + totalGenerated + " випадкових чотиризначних чисел:\n");
        System.out.println("  ┌────────────┬──────────────────────────────────────────────┐");
        System.out.println("  │   Число    │  Результат аналізу                           │");
        System.out.println("  ├────────────┼──────────────────────────────────────────────┤");

        int foundCount = 0;

        for (int i = 0; i < totalGenerated; i++) {
            // Генерація чотиризначного числа від 1000 до 9999
            int number = 1000 + random.nextInt(9000);
            String numStr = String.valueOf(number);

            boolean hasThreeIdentical = hasThreeIdenticalDigits(numStr);
            if (hasThreeIdentical) foundCount++;

            String result = hasThreeIdentical
                    ? "✔ Є три однакові цифри: " + findTripleDigit(numStr)
                    : "✘ Немає трьох однакових цифр";

            System.out.printf("  │  %-10s│  %-44s│%n", numStr, result);
        }

        System.out.println("  └────────────┴──────────────────────────────────────────────┘");
        System.out.printf("%n  Підсумок: знайдено %d число(чисел) з трьома однаковими цифрами з %d.%n%n",
                foundCount, totalGenerated);


        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║       ЗАВДАННЯ 2: Підрахунок знаків пунктуації       ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");

        // Текстовий літерал для обробки
        String text =
                "Програмування — це мистецтво, яке вимагає терпіння, логіки та творчості!\n" +
                        "Мова Java є однією з найпопулярніших мов у світі; вона використовується\n" +
                        "у банківській сфері, мобільних додатках та великих корпоративних системах.\n" +
                        "Чи знаєте ви, що Java була створена у 1995 році? Так, це вже майже 30 років!\n" +
                        "Основні принципи ООП: інкапсуляція, наслідування, поліморфізм — і абстракція.";

        System.out.println("Текст до обробки:");
        System.out.println("─".repeat(56));
        System.out.println(text);
        System.out.println("─".repeat(56));

        // Підрахунок знаків пунктуації
        Map<Character, Integer> punctuationMap = countPunctuation(text);

        int totalPunctuation = punctuationMap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("\nЗнаки пунктуації, знайдені в тексті:\n");
        System.out.println("  ┌───────────┬──────────────────────────────┬──────────┐");
        System.out.println("  │  Символ   │  Назва                       │  К-сть   │");
        System.out.println("  ├───────────┼──────────────────────────────┼──────────┤");

        for (Map.Entry<Character, Integer> entry : punctuationMap.entrySet()) {
            char ch = entry.getKey();
            int count = entry.getValue();
            String name = getPunctuationName(ch);
            System.out.printf("  │  %-9s│  %-30s│  %-8d│%n",
                    "'" + ch + "'", name, count);
        }

        System.out.println("  ├───────────┴──────────────────────────────┼──────────┤");
        System.out.printf( "  │  РАЗОМ                                   │  %-8d│%n", totalPunctuation);
        System.out.println("  └──────────────────────────────────────────┴──────────┘");

        // Формуємо текст після «обробки» — виділяємо знаки пунктуації дужками
        String processedText = highlightPunctuation(text);
        System.out.println("\nТекст після обробки (знаки пунктуації виділені дужками []):");
        System.out.println("─".repeat(56));
        System.out.println(processedText);
        System.out.println("─".repeat(56));

        System.out.println("\n  Загальна кількість знаків пунктуації: " + totalPunctuation);
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("  Завдання виконано успішно.");
        System.out.println("══════════════════════════════════════════════════════");
    }

    // ──────────────────────────────────────────────────────────
    //  МЕТОДИ ДЛЯ ЗАВДАННЯ 1
    // ──────────────────────────────────────────────────────────

    /**
     * Перевіряє, чи є серед цифр числа три однакові цифри.
     *
     * @param numStr рядкове представлення чотиризначного числа
     * @return true, якщо є три або більше однакових цифри
     */
    static boolean hasThreeIdenticalDigits(String numStr) {
        for (char digit = '0'; digit <= '9'; digit++) {
            int count = 0;
            for (int i = 0; i < numStr.length(); i++) {
                if (numStr.charAt(i) == digit) count++;
            }
            if (count >= 3) return true;
        }
        return false;
    }

    /**
     * Знаходить цифру, яка зустрічається три або більше разів.
     *
     * @param numStr рядкове представлення числа
     * @return рядок із цифрою та кількістю повторень
     */
    static String findTripleDigit(String numStr) {
        for (char digit = '0'; digit <= '9'; digit++) {
            int count = 0;
            for (int i = 0; i < numStr.length(); i++) {
                if (numStr.charAt(i) == digit) count++;
            }
            if (count >= 3) {
                return "'" + digit + "' (" + count + " рази)";
            }
        }
        return "";
    }

    // ──────────────────────────────────────────────────────────
    //  МЕТОДИ ДЛЯ ЗАВДАННЯ 2
    // ──────────────────────────────────────────────────────────

    /**
     * Підраховує кількість кожного знаку пунктуації в тексті.
     * Використовує LinkedHashMap для збереження порядку знаходження.
     *
     * @param text вхідний текст
     * @return map із символами та їхньою кількістю
     */
    static Map<Character, Integer> countPunctuation(String text) {
        String punctuationChars = ".,!?;:—–-()[]\"'…";
        Map<Character, Integer> result = new LinkedHashMap<>();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (punctuationChars.indexOf(ch) >= 0) {
                result.put(ch, result.getOrDefault(ch, 0) + 1);
            }
        }
        return result;
    }

    /**
     * Повертає назву знаку пунктуації українською мовою.
     *
     * @param ch символ пунктуації
     * @return назва символу
     */
    static String getPunctuationName(char ch) {
        return switch (ch) {
            case '.'  -> "Крапка";
            case ','  -> "Кома";
            case '!'  -> "Знак оклику";
            case '?'  -> "Знак питання";
            case ';'  -> "Крапка з комою";
            case ':'  -> "Двокрапка";
            case '—', '–' -> "Тире";
            case '-'  -> "Дефіс";
            case '('  -> "Відкрита дужка";
            case ')'  -> "Закрита дужка";
            case '['  -> "Відкрита квадратна дужка";
            case ']'  -> "Закрита квадратна дужка";
            case '"'  -> "Лапки";
            case '\'' -> "Апостроф";
            case '…'  -> "Три крапки";
            default   -> "Інший знак";
        };
    }

    /**
     * Замінює кожен знак пунктуації в тексті на його обрамлення у квадратні дужки.
     * Наприклад: "Привіт, світе!" → "Привіт[,] світе[!]"
     *
     * @param text вхідний текст
     * @return текст із виділеними знаками пунктуації
     */
    static String highlightPunctuation(String text) {
        String punctuationChars = ".,!?;:—–-()[]\"'…";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (punctuationChars.indexOf(ch) >= 0) {
                sb.append('[').append(ch).append(']');
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Виводить заголовок програми з іменем розробника.
     */
    static void printHeader() {
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("  Розробник: Журбенко І. Г.");
        System.out.println("  Лабораторна робота №2 — Обробка текстових даних");
        System.out.println("══════════════════════════════════════════════════════\n");
    }
}