/**
 * Given int "n",
 * calculate the number of unique ways "n" can be partitioned
 * without a repeating number
 *
 * for example, 3 can only have 1 way of partitioning without a repeating number,
 * {2, 1}
 * but 5 can have 2 ways of partitioning without a repeating number
 * {4, 1}, {3, 2}
 *
 * the partitioned numbers should always go from big to small,
 * so {3, 2} and {2, 3} count as only 1 unique way
 */

public class Solution {
    public static void main(String[] args) {
        System.out.println(solution(200));
    }

    public static int solution(int n) {
        int total_possibilities = 0;
        int remainder = n;
        if (remainder > 3) {
            for (int i = n - 1; (i/2) * (i-1) >= (remainder - i); i--) {
                total_possibilities += possibilities(i, remainder - i);
            }
        } else if (remainder == 3) {
            total_possibilities++;
        }
        return total_possibilities;
    }

    static int possibilities(int curr_step, int remainder) {
        int total_possibilities = 0;

        if (remainder > 4) {
            if (curr_step > remainder) {
                total_possibilities ++;
            }
            for (int i = remainder - 1; ((i+0.5)/2) * (i-1) >= (remainder - i); i--) {

                if (i < curr_step) {

                    total_possibilities += possibilities(i, remainder - i);
                }
            }
        } else if (remainder <= 2 && curr_step > remainder) {
            total_possibilities++;
        } else if (curr_step > remainder) {
            total_possibilities+=2;
        } else if (curr_step == remainder) {
            total_possibilities++;
        } else {
            return 0;
        }
        return total_possibilities;
    }
}