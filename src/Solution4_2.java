/**
 * Given adjacency matrix "times" and int "times_limit"
 * find a path from 0 to the last node of weight "times_limit" that passes through the most nodes
 * and return the values of the intermediate nodes in sorted order
 *
 * repeated visits to a node are allowed
 */

import java.util.Arrays;
import java.util.ArrayList;

import static java.lang.Integer.MAX_VALUE;

public class Solution4_2 {
    public static void main(String[] args) {
        int[][] t = {{0, 1, 1, 1, -3}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}};
        int tl = 0;
        int[] solution = solution(t, tl);

        for (int i : solution) {
            System.out.println(i);
        }
    }


    public static int[] solution(int[][] times, int times_limit) {
        if (findLoop(times)) {
            int[] r = new int[times.length - 2];
            for (int i = 0; i < r.length; i++) {
                r[i] = i;
            }
            return r;
        }

        Bunny[] b;

        b = buildGraph(times);

        int[][] shortestMatrix = findShortestPathMatrix(times, b);

        int[] solution = findSolution(shortestMatrix, times_limit);

        for (int i = 0; i < solution.length; i++) {
            solution[i] --;
        }

        Arrays.sort(solution);

        return solution;

    }

    static Bunny[] buildGraph(int[][] m) {
        Bunny[] bunnies = new Bunny[m.length];
        for (int i = 0; i < m.length; i++) {
            Bunny b = new Bunny(i);
            bunnies[i] = b;
        }

        for (int i = 0; i < bunnies.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (i != j) {
                    bunnies[i].p_to.add(new Path(bunnies[j], bunnies[i], m[i][j]));
                }
            }
        }
        return bunnies;
    }

    static boolean findLoop(int[][] t) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                if (i != j) {
                    if (t[i][j] + t[j][i] < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // find why this is infinitely looping
    static int findShortestPath(int[] path, int target, Bunny curr_bunny) {
        int[] new_path = new int[path.length + 1];
        for (int i = 0; i < path.length; i++) {
            new_path[i] = path[i];
        }
        new_path[new_path.length - 1] = curr_bunny.id;


        int curr_total = MAX_VALUE;

        if (curr_bunny.id == target) {
            return 0;
        }

        for (Path p : curr_bunny.p_to) {
            boolean not_traversed = true;
            for (int i : new_path) {
                if (p.to.id == i) {
                    not_traversed = false;
                }
            }
            if (not_traversed) {
                curr_total = Math.min(findShortestPath(new_path, target, p.to) + p.weight, curr_total);
            }
        }
        // figure out what to return
        return curr_total;
    }

    static int[][] findShortestPathMatrix(int[][] m, Bunny[] bunnies_index) {
        int[][] new_m = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                // run findShortestPath from i to j
                if (i != j) {
                    int[] empty = {};
                    new_m[i][j] = findShortestPath(empty, j, bunnies_index[i]);
                } else {
                    new_m[i][j] = 0;
                }
            }
        }
        return new_m;
    }

    // input shortest path matrix and output the solution
    static int[] findSolution(int[][] m, int target) {
        int[] all_nodes = new int[m.length - 2];

        // since start and end are always the same, no need to include them in the permutations program
        for (int i = 0; i < all_nodes.length; i++) {
            all_nodes[i] = i + 1;
        }

        int[][] perms = allPermutations(all_nodes);


        for (int i = perms[0].length; i > 0; i--) {
            for (int k = 0; k < perms.length; k++) {
                int[] temp = new int[i];
                for (int j = 0; j < i; j++) {
                    temp[j] = perms[k][j];
                }
                perms[k] = temp;
                int path_total = findPathLength(m, perms[k]);

                if (path_total <= target) {
                    return perms[k];
                }
            }
        }

        int[] temp = {};
        return temp;
    }

    static int findPathLength(int[][] m, int[] path) {
        int sum = 0;
        sum += m[0][path[0]];

        for(int i = 0; i < path.length - 1; i++) {
            sum += m[path[i]][path[i+1]];
        }
        sum += m[path[path.length - 1]][m[0].length - 1];

        return sum;
    }

    static int[][] allPermutations(int[] nums) {
        int num_of_perms = 1;
        for (int i = 2; i <= nums.length; i++) {
            num_of_perms *= i;
        }
        int[][] perms = new int[num_of_perms][nums.length];

        for (int i =0; i < nums.length; i++) {
            perms[0][i] = nums[i];
        }
        int curr_perm = 1;

        int[] p = new int[nums.length];
        int i = 1;

        while (i < nums.length) {
            if (p[i] < i) {
                int j = ((i % 2) == 0) ? 0 : p[i];
                nums = swap(nums, i, j);

                for (int k = 0; k < nums.length; k++) {
                    perms[curr_perm][k] = nums[k];
                }
                curr_perm ++;

                p[i] ++;
                i = 1;
            } else {
                p[i] = 0;
                i++;
            }
        }
        return perms;
    }

    static int[] swap(int[] n, int i, int j) {
        int temp1 = n[i];
        int temp2 = n[j];

        int[] new_arr = new int[n.length];
        for (int k = 0; k < new_arr.length; k++) {
            new_arr[k] = n[k];
        }

        new_arr[j] = temp1;
        new_arr[i] = temp2;

        return new_arr;
    }
}

class Bunny {
    // 0 for start and the biggest number for end
    int id;
    ArrayList<Path> p_to;
    Bunny(int n) {
        id = n;
        p_to = new ArrayList<>();
    }
}

class Path {
    Bunny to;
    Bunny from;
    int weight;
    Path (Bunny i, Bunny j, int k) {
        to = i;
        from = j;
        weight = k;
    }
}
