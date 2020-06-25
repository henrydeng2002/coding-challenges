def main():
    print(solution([0, 1], [4, 5], [[0, 0, 4, 6, 0, 0], [0, 0, 5, 2, 0, 0], [0, 0, 0, 0, 4, 4], [0, 0, 0, 0, 6, 6], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]]))

def solution(entrances, exits, path):
    rooms = []

    for i in range(len(path)):
        isExit = False;
        isEntrance = False;
        for e in exits:
            if e == i:
                isExit = True

        for e in entrances:
            if e == i:
                isEntrance = True

        rooms.append(Room(isEntrance, isExit, 0))

    for i in range(len(path)):
        for j in range(len(path)):
            if path[i][j] != 0:
                rooms[i].corridors.append(Corridor(rooms[i], rooms[j], path[i][j]))

    for i in entrances:
        sum = 0
        for j in range(len(path[i])):
            sum += path[i][j]
        findSum(rooms[i], sum)

    sum = 0
    for i in exits:
        sum += rooms[i].num
        print(rooms[i].num)

    return sum

def findSum(r, x):
    r.num += x

    if r.isExit:
        return 0

    for c in r.corridors:
        pass_on = c.max_width - c.used_width
        if pass_on > 0 and r.num > pass_on:
            r.num -= pass_on
            c.used_width += pass_on
            r.num += findSum(c.to_room, pass_on)
        elif pass_on > 0 and r.num > 0:
            pass_on = r.num
            r.num = 0
            c.used_width += pass_on
            r.num += findSum(c.to_room, pass_on)

    return r.num

class Room:
    def __init__ (self, en, e, n):
        self.isEntrance = en
        self.isExit = e
        self.num = n
        self.corridors = []


class Corridor:
    def __init__ (self, f, t, w):
        self.from_room = f
        self.to_room = t
        self.max_width = w
        self.used_width = 0

if __name__ == '__main__':
    main()
