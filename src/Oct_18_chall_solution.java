/**
 * Given adjacency matrix "path", and array "entrances" and "exits",
 * where each int in "path" represents the capacity of each edge in a flow network,
 * calculate the total number of objects that can pass through the flow network
 * from a node of value "entrances" to a node of value "exits"
 *
 */

import java.util.ArrayList;

public class Oct_18_chall_solution {
    public static void main(String[] args) {
        int[] entrances = {0};
        int[] exits = {31};
        int[][] paths = {{0, 4, 21, 46, 15, 34, 0, 0, 0, 0, 15, 0, 0, 0, 3, 0, 0, 24, 0, 0, 0, 0, 23, 0, 0, 1, 0, 0, 17, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 22, 9, 1, 2, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 7, 1, 14, 0, 21, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 5, 21, 7, 8, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 7, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 6, 3, 0, 21, 23, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 2, 1, 13, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 22, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 12, 2, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 1, 21, 8, 0, 0, 0, 12, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1, 8, 14, 2, 0, 0, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 8, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 15, 5, 2, 9, 0, 0, 0, 0, 0, 0, 0, 24, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 5, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 2, 5, 6, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 11, 5, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 6, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 8, 2, 2, 14, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 11, 8, 11, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 9, 16, 14, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 18, 7, 9, 0, 0, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 2, 1, 8, 2, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 23, 3, 17, 22, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 15, 11, 10, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 25, 2, 9, 25, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 9, 15, 21, 15, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 20, 5, 6, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 43},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        System.out.println(solution(entrances, exits, paths));
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        // array of rooms that track all of the rooms (corresponding to the index)
        Room[] rooms = new Room[path.length];

        // populate room array with rooms
        for (int i = 0; i < path.length; i++) {
            boolean isExit = false;
            boolean isEntrance = false;
            for (int e : exits) {
                if (e == i) {
                    isExit = true;
                }
            }
            for (int e : entrances) {
                if (e == i) {
                    isEntrance = true;
                }
            }
            rooms[i] = new Room(isExit, isEntrance, 0);
        }

        // create graph
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                if (path[i][j] != 0) {
                    rooms[i].corridors.add(new Corridor(rooms[i], rooms[j], path[i][j]));
                }
            }
        }

        for (int i : entrances) {
            int sum = 0;
            for (int j = 0; j < path[i].length; j++) {
                sum += path[i][j];
            }
            findSum(rooms[i], sum);
        }

        int sum = 0;
        for (int i : exits) {
            sum += rooms[i].number;
        }

        return sum;
    }


    static int findSum(Room r, int num) {
        r.number += num;

        if (r.isExit) {
            return 0;
        }

        for (Corridor c : r.corridors) {
            int pass_on = c.width - c.width_used;
            if (pass_on > 0 && r.number > pass_on) {
                r.number -= pass_on;
                c.width_used += pass_on;
                int temp = findSum(c.to, pass_on);
                r.number += temp;
                c.width_used -= temp;
            } else if (pass_on > 0 && r.number > 0) {
                pass_on = r.number;
                r.number = 0;
                c.width_used += pass_on;
                int temp = findSum(c.to, pass_on);
                r.number += temp;
                c.width_used -= temp;
            }
        }

        // returns excess numbers not used at the current level
        int temp = r.number;
        // remove number from current room to not repeat numbers
        r.number = 0;
        return temp;
    }
}


class Room {
    ArrayList<Corridor> corridors;
    boolean isExit;
    boolean isEntrance;
    int number;

    Room(boolean e, boolean en, int n) {
        corridors = new ArrayList<Corridor>();
        isExit = e;
        isEntrance = en;
        number = n;
    }

}


class Corridor {
    Room from;
    Room to;
    int width;
    int width_used;

    Corridor(Room f, Room t, int w) {
        from = f;
        to = t;
        width = w;
        width_used = 0;
    }
}