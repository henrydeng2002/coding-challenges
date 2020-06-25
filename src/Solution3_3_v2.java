/**
 * Given markov matrix "m",
 * calculate the probability that a state starting from 0
 * will end up at each absorbing state
 * and return all probabilities in an array of fractions
 * in the form of:
 * {numerator s1, numerator s2, numerator s3, denominator}
 * if s1, s2, and s3 are terminal states, and so on
 */

public class Solution3_3_v2 {
    public static void main(String[] args) {
        int[][] arr = {
                {1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 1, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int[] solution = solution(arr);
        for (int i : solution) {
            System.out.println(i);
        }
    }

    public static int[] solution(int[][] m) {
        if (m.length == 1 && m[0].length == 1) {
            if (m[0][0] == 0) {
                int[] solution = {1, 1};
                return solution;
            }
        }

        Fraction[][] m_frac = convertFraction(m);
        m_frac = normalize(m_frac);
        m_frac = sort(m_frac);

        int transient_num = transientNums(m_frac);
        Fraction[][] q = findQ(m_frac, transient_num);
        Fraction[][] r = findR(m_frac, transient_num);

        Fraction[][] i = identity(q.length);
        Fraction[][] subtract_m = subtractM(i, q);

        Fraction[][] inverse = matrixInverse(subtract_m);

        Fraction[][] multiply = multiply(inverse, r);

        int[] solution = findSolution(multiply);
        return solution;
    }

    static Fraction[][] sort(Fraction[][] m) {
        int absorb_row = -1;
        for (int i = 0; i < m.length; i++) {
            boolean absorb = true;
            for (int j = 0; j < m.length; j++) {
                if (m[i][j].numerator != 0) {
                    absorb = false;
                }
            }
            if (absorb) {
                absorb_row = i;
            }
            if (!absorb && absorb_row > -1) {
                Fraction[][] n = swap(m, i, absorb_row);
                return sort(n);
            }
        }
        return m;
    }

    static Fraction[][] swap (Fraction[][] m, int i, int j) {
        Fraction[][] n = new Fraction[m.length][m.length];

        for (int r = 0; r < m.length; r++) {
            Fraction[] temp2 = new Fraction[m.length];
            for (int c = 0; c < m.length; c++) {
                if (r != i && r != j) {
                    temp2[c] = new Fraction(m[r][c]);
                } else if (r == i) {
                    temp2[c] = new Fraction(m[j][c]);
                } else {
                    temp2[c] = new Fraction(m[i][c]);
                }
            }
            for (int c = 0; c < m.length; c++) {
                Fraction tempf;
                if (c != i && c != j) {
                    tempf = new Fraction(temp2[c]);
                } else if (c == i) {
                    tempf = new Fraction(temp2[j]);
                } else {
                    tempf = new Fraction(temp2[i]);
                }
                n[r][c] = new Fraction(tempf);
            }
        }

        return n;
    }

    static int[] findSolution(Fraction[][] m) {
        int[] solution = new int[m[0].length + 1];
        long[] denoms = new long[m[0].length];

        for (int i = 0; i < denoms.length; i++) {
            denoms[i] = m[0][i].denominator;
        }
        long lcm = findlcm(denoms);

        for (int i = 0; i < m[0].length; i++) {
            long temp = lcm / m[0][i].denominator;
            m[0][i].numerator *= temp;
            m[0][i].denominator *= temp;
        }

        for (int i = 0; i < m[0].length; i++) {
            solution[i] = (int) m[0][i].numerator;
        }

        solution[solution.length - 1] = (int) m[0][0].denominator;

        return solution;
    }

    static long findlcm(long[] arr)  {
        long lcm = arr[0];
        for (int i = 1; i < arr.length; i++) {
            lcm = lcm(lcm, arr[i]);
        }
        return lcm;
    }

    static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    static long gcd(long a, long b)
    {
        while (b > 0)
        {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    static Fraction[][] convertFraction(int[][] m) {
        Fraction[][] n = new Fraction[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                n[i][j] = new Fraction(m[i][j], 1);
            }
        }
        return n;
    }

    static Fraction[][] normalize(Fraction[][] m) {
        long sum_row = 0;
        Fraction[][] n = new Fraction[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                sum_row += m[i][j].numerator;
            }

            for (int j = 0; j < m.length; j++) {
                n[i][j] = new Fraction((m[i][j]));
                n[i][j].divide(sum_row);
            }
            sum_row = 0;
        }
        return n;
    }

    static int transientNums(Fraction[][] m) {
        int count = 0;
        long sum_row = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                sum_row += m[i][j].numerator;
            }

            if (sum_row != 0) {
                count ++;
            }
            sum_row = 0;
        }
        return count;
    }

    static Fraction[][] findQ(Fraction[][] m, int t) {
        Fraction[][] q = new Fraction[t][t];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                q[i][j] = new Fraction(m[i][j]);
            }
        }
        return q;
    }

    static Fraction[][] findR(Fraction[][] m, int t) {
        Fraction[][] r = new Fraction[t][m.length - t];
        for (int i = 0; i < t; i++) {
            for (int j = t; j < m.length; j++) {
                r[i][j - t] = new Fraction(m[i][j]);
            }
        }
        return r;
    }

    static Fraction[][] identity(int l) {
        Fraction[][] i = new Fraction[l][l];
        for (int k = 0; k < l; k++) {
            for (int j = 0; j < l; j++) {
                if (j == k) {
                    i[j][k] = new Fraction(1, 1);
                } else {
                    i[j][k] = new Fraction(0, 0);
                }
            }
        }
        return i;
    }

    static Fraction[][] subtractM(Fraction[][] i, Fraction[][] q) {
        Fraction[][] s = new Fraction[i.length][i.length];
        for (int j = 0; j < i.length; j++) {
            for (int k = 0; k < i.length; k++) {
                Fraction f = new Fraction(i[j][k]);
                f.subtract(q[j][k]);
                s[j][k] = f;
            }
        }
        return s;
    }

    static Fraction[][] matrixInverse(Fraction[][] s) {
        Fraction d = matrixDeterminant(s);

        if (s.length == 2) {
            Fraction[][] f = new Fraction[2][2];
            f[0][0] = new Fraction(s[1][1]);
            f[0][0].divide(d);

            f[0][1] = new Fraction(s[0][1]);
            f[0][1].divide(d);
            f[0][1].multiply(-1);

            f[1][0] = new Fraction(s[1][0]);
            f[1][0].divide(d);
            f[1][0].multiply(-1);

            f[1][1] = new Fraction(s[1][1]);
            f[1][1].divide(d);
            return f;
        }

        Fraction[][] cofactors = new Fraction[s.length][s.length];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length; j++) {
                Fraction[][] minor = matrixMinor(s, i, j);
                cofactors[i][j] = matrixDeterminant(minor);
                cofactors[i][j].multiply((int) Math.pow(-1, (i+j)));
            }
        }

        cofactors = transposeMatrix(cofactors);
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length; j++) {
                cofactors[i][j].divide(d);
            }
        }
        return cofactors;
    }

    static Fraction matrixDeterminant(Fraction[][] m) {
        Fraction d = new Fraction(0, 1);

        if (m.length == 2) {
            Fraction f1 = new Fraction(m[0][0]);
            f1.multiply(m[1][1]);
            Fraction f2 = new Fraction(m[1][0]);
            f2.multiply(m[0][1]);
            f1.subtract(f2);
            return f1;
        }

        for (int i = 0; i < m.length; i++) {
            Fraction f = new Fraction(m[0][i]);
            f.multiply((int)(Math.pow(-1, i)));
            f.multiply(matrixDeterminant(matrixMinor(m, 0, i)));
            d.add(f);
        }
        return d;
    }

    static Fraction[][] matrixMinor(Fraction[][] m, int row, int column) {
        Fraction[][] minor = new Fraction[m.length - 1][m.length - 1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (i != row && j != column) {
                    if (i < row) {
                        if (j < column) {
                            minor[i][j] = new Fraction(m[i][j]);
                        } else {
                            minor[i][j - 1] = new Fraction(m[i][j]);
                        }
                    } else {
                        if (j < column) {
                            minor[i - 1][j] = new Fraction(m[i][j]);
                        } else {
                            minor[i - 1][j - 1] = new Fraction(m[i][j]);
                        }
                    }
                }
            }
        }
        return minor;
    }

    static Fraction[][] transposeMatrix(Fraction[][] m) {
        Fraction[][] t = new Fraction[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                t[j][i] = new Fraction(m[i][j]);
            }
        }
        return t;
    }

    static Fraction[][] multiply(Fraction[][] i, Fraction[][] r){
        Fraction[][] m = new Fraction[i.length][r[0].length];

        for (int j = 0; j < i.length; j++) {
            for (int k = 0; k < r[0].length; k++) {
                Fraction sum = new Fraction(0, 1);
                for (int s = 0; s < i[0].length; s++) {
                    Fraction temp = new Fraction(i[j][s]);
                    temp.multiply(r[s][k]);

                    sum.add(temp);
                }
                m[j][k] = sum;
            }
        }
        return m;
    }
}

class Fraction {
    long numerator;
    long denominator;

    Fraction(Fraction f){
        numerator = f.numerator;
        denominator = f.denominator;
        reduce();
    }

    Fraction(long n, long d) {
        numerator = n;
        denominator = d;
        reduce();
    }

    void add (long x) {
        x *= denominator;
        numerator += x;
        reduce();
    }

    void add (Fraction f) {
        numerator *= f.denominator;
        f.numerator *= denominator;
        f.denominator *= denominator;
        denominator = f.denominator;

        numerator += f.numerator;
        reduce();
    }

    void divide (long x) {
        denominator *= x;
        reduce();
    }

    void divide (Fraction f) {
        numerator *= f.denominator;
        denominator *= f.numerator;
        reduce();
    }

    void multiply (long x) {
        numerator *= x;
        reduce();
    }

    void multiply (Fraction f) {
        numerator *= f.numerator;
        denominator *= f.denominator;
        reduce();
    }

    void reduce() {
        if (numerator != 0) {
            long gcd = gcd(numerator, denominator);
            numerator /= gcd;
            denominator /= gcd;
        }
        if (numerator == 0) {
            denominator = 1;
        }
    }

    // subtracts this fraction by a fraction s
    void subtract(Fraction s) {
        numerator *= s.denominator;
        s.numerator *= denominator;
        s.denominator *= denominator;
        denominator = s.denominator;

        numerator -= s.numerator;
        reduce();
    }

    long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }
}