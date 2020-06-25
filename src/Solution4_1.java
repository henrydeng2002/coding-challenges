/**
 * Given adjacency matrix "path", and array "entrances" and "exits",
 * where each int in "path" represents the capacity of each edge in a flow network,
 * calculate the total number of objects that can pass through the flow network
 * from a node of value "entrances" to a node of value "exits"
 *
 */

import java.util.ArrayList;

public class Solution4_1 {
    public static void main(String[] args) {
        int[] entrances = {0};
        int[] exits = {3};
        int[][] paths = {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}};

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
                r.number += findSum(c.to, pass_on);
            } else if (pass_on > 0 && r.number > 0) {
                pass_on = r.number;
                r.number = 0;
                c.width_used += pass_on;
                r.number += findSum(c.to, pass_on);
            }
        }

        // returns excess numbers not used at the current level
        System.out.println(r.number);
        return r.number;
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