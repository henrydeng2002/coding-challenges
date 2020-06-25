/**
 * not functioning - please refer to solution3_3_v2
 */

public class Solution3_3 {
    public static void main(String[] args) {
        int[][] arr = {{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0,0}, {0, 0, 0, 0, 0}};
        solution(arr);
    }

    public static int[] solution (int[][] m) {
        int[] solution = new int[m.length - 1];
        int[] cases_row = new int[m.length];

        solution[solution.length - 1] = findDenominator(m);
        findTotalRow(m, cases_row, solution[solution.length - 1]);
        changeTotals(m, cases_row);
        findProbability(m, solution);
        removeRepeat(m, cases_row, solution);

        return solution;
    }

    static int findDenominator(int[][] m) {
        int denominator = 1;
        int total_row = 0;
        for (int i = 0; i < m.length; i++) {
            total_row = 0;
            for (int j = 0; j < m.length; j++) {
                total_row += m[i][j];
            }
            if (total_row != 0) {
                denominator *= total_row;
            }
        }
        return denominator;
    }

    static void findTotalRow (int[][] m, int[] r, int denominator) {
        int total_s0 = 0;
        for (int i = 0; i < m.length; i++) {
            total_s0 += m[0][i];
        }

        for (int i = 0; i < m.length; i++) {
            r[i] = (m[0][i] * denominator) / total_s0;
        }

        r[0] = denominator;
    }

    static void changeTotals (int[][] m, int[] r) {
        int total_row = 0;

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                total_row += m[i][j];
            }

            for (int j = 0; j < m.length; j++) {
                if (total_row != 0) {
                    m[i][j] = m[i][j] * r[i] / total_row;
                }
            }

            total_row = 0;
        }
    }

    static void findProbability (int[][] m, int[] p) {
        for (int i = 0; i < m.length - 1; i++) {
            for (int j = 2; j < m.length; j++) {
                p[j - 2] += m[i][j];
            }
        }
    }

    static void removeRepeat (int[][]m, int[]p, int[] solution) {
        solution[solution.length - 1] -= m[1][0];
    }
}
