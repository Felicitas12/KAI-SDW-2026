import java.util.Random;

public class Main {

    // Розмір матриці
    static final int N = 3;

    public static void main(String[] args) {

        Random random = new Random();

        int[][] B = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                B[i][j] = random.nextInt(21) - 10; // діапазон [-10, 10]
            }
        }

        System.out.println("Матриця B(3,3):");
        printMatrix(B);

        System.out.println("Суми елементів допоміжних діагоналей (по модулю):");
        System.out.println("(Допоміжна діагональ — елементи, де i + j = const)\n");

        for (int d = 0; d <= 2 * (N - 1); d++) {
            int sum = 0;
            StringBuilder elements = new StringBuilder();
            boolean first = true;

            for (int i = 0; i < N; i++) {
                int j = d - i;
                if (j >= 0 && j < N) {
                    sum += Math.abs(B[i][j]);
                    if (!first) elements.append(" + ");
                    elements.append("|").append(B[i][j]).append("|");
                    first = false;
                }
            }

            System.out.printf("  Діагональ (i+j=%d): %s = %d%n", d, elements, sum);
        }

        System.out.println("\n============================================");
        System.out.println("  Завдання виконано успішно.");
        System.out.println("============================================");
    }
    static void printMatrix(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("  [ ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%4d", matrix[i][j]);
                if (j < matrix[i].length - 1) System.out.print(",");
            }
            System.out.println("  ]");
        }
        System.out.println();
    }
}