/**
 * Given large number (up to 309 digits) in form of a string "x",
 * calculate the least amount of operations needed for the number
 * to reach 0
 * the only operations allowed are add 1, subtract 1, divide by 2
 */

public class Solution3_2 {
    public static void main(String[] args) {
        System.out.println(solution("3"));
    }

    public static int solution(String x) {
        if (x.equals("1")) {
            return 0;
        } else if ((x.charAt(x.length() - 1) - 48) % 2 == 0) {
            return 1 + solution(divideByTwo(x));
        } else if (x.equals("3")) {
            return 2;
        } else {
            return 1 + (biggestDivisor(minusOne(x)) >= biggestDivisor(addOne(x)) ? solution(minusOne(x)) : solution(addOne(x)));
        }
    }

    static int biggestDivisor(String x) {
        int count = 0;

        while ((x.charAt(x.length() - 1) - 48) % 2 == 0) {
            count++;
            x = divideByTwo(x);
        }

        return count;
    }

    static String divideByTwo(String x) {
        char[] oldNum = x.toCharArray();
        char[] newNum = new char[oldNum.length];

        for (int i = 0; i < oldNum.length; i++) {
            oldNum[i] -= 48;
            if (oldNum[i] % 2 != 0) {
                oldNum[i] -= 1;
                oldNum[i + 1] += 10;
            }

            newNum[i] = (char) ((oldNum[i] / 2) + 48);

        }

        String newString = "";
        for (char a : newNum) {
            newString += a;
        }

        if (newString.charAt(0) == 48) {
            newString = newString.substring(1);
        }
        return newString;
    }

    static String addOne(String oldNum) {
        oldNum = "0" + oldNum;
        char[] newNum = oldNum.toCharArray();
        newNum[newNum.length - 1] += 1;
        for (int i = newNum.length - 1; newNum[i] == 58; i--) {
            newNum[i] = 48;
            newNum[i - 1]++;
        }
        String newString = "";
        for (char a : newNum) {
            newString += a;
        }
        if (newString.charAt(0) == '0') {
            newString = newString.substring(1);
        }
        return newString;
    }

    static String minusOne(String oldNum) {
        char[] newNum = oldNum.toCharArray();
        newNum[newNum.length - 1] -= 1;
        for (int i = newNum.length - 1; newNum[i] == 47; i--) {
            newNum[i] = 57;
            newNum[i - 1]--;
        }
        String newString = "";
        for (char a : newNum) {
            newString += a;
        }
        if (newString.charAt(0) == '0') {
            newString = newString.substring(1);
        }
        return newString;
    }
}

//    static Boolean isPowerOfTwo (String target) {
//        String power = "2";
//
//        while (power.length() <= target.length()) {
//            if (power.length() == target.length()) {
//                if (power.equals(target)) {
//                    return true;
//                }
//            }
//
//            power = timesByTwo(power);
//        }
//        return false;
//    }
//
//    static String timesByTwo (String x) {
//        char[] oldNum = x.toCharArray();
//        char[] newNum = new char[oldNum.length + 1];
//
//        int addNum = 0;
//        for (int i = oldNum.length - 1; i >= 0; i--) {
//            oldNum[i] -= 48;
//            newNum[i + 1] = (char) ((oldNum[i] * 2) + 48 + addNum);
//            if (newNum[i + 1] - 48 > 9) {
//                addNum = 1;
//                newNum[i + 1] -= 10;
//            } else {
//                addNum = 0;
//            }
//        }
//
//        newNum[0] = (char) (addNum + 48);
//
//        String newString = "";
//        for (char a : newNum) {
//            newString += a;
//        }
//
//        if (newString.charAt(0) == 48) {
//            newString = newString.substring(1);
//        }
//        return newString;
//    }
//
//}
//
//    static int solution_up(String x) {
//        if (x.equals("1"))
//            return 0;
//        else if ((x.charAt(x.length() - 1) - 48) % 2 == 0) {
//            return 1 + solution_up(divideByTwo(x));
//        }
//        else if (isPowerOfTwo(minusOne(x))) {
//            return 1 + solution_up(minusOne(x));
//        } else
//            return 1 + solution_up(addOne(x));
//    }
//
//    static int solution_down(String x) {
//        if (x.equals("1"))
//            return 0;
//        else if ((x.charAt(x.length() - 1) - 48) % 2 == 0) {
//            return 1 + solution_down(divideByTwo(x));
//        }
//        else if (isPowerOfTwo(addOne(x)) && !x.equals("3")) {
//            return 1 + solution_down(addOne(x));
//        } else
//            return 1 + solution_down(minusOne(x));
//    }
