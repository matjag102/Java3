
class room:
    def __init__ (self, name, flag=1):
        
        self.name = name
        self.flag = flag


class robot:
    def __init__ (self):
       
        self.room_left = room("A")
        self.room_right = room("B")
        self.room_now = self.room_left

    def go_left(self):
       
        if self.room_now == self.room_right:
            self.room_now = self.room_left

    def go_right(self):
       
        if self.room_now == self.room_left:
            self.room_now = self.room_right

    def fun(self):
        
        self.room_now.flag = 0

    def fun2(self):
        
        counter = 0

        while self.room_left.flag == 1 or self.room_right.flag == 1:
            if self.room_now.flag:
                self.fun()
            elif self.room_now == self.room_left:
                self.go_right()
            elif self.room_now == self.room_right:
                self.go_left

            counter = counter + 1
            print("Step: {0}".format(counter))
            print(self)

        return counter

    def __str__(self):
        return "now room: {0}, {1} brod: {2}, {3} brod: {4}".format(self.room_now.name, self.room_left.name, self.room_left.flag, self.room_right.name, self.room_right.flag)


class Stan:
    def __init__ (self, room_now, dirt_on_left, dirt_on_right):
       
        self.room_now = room_now
        self.dirt_on_left = dirt_on_left
        self.dirt_on_right = dirt_on_right

    def __str__(self):
        return "Robot in room: {0}, syfA: {1}, syfB: {2}".format(self.room_now, self.dirt_on_left, self.dirt_on_right)


class Graph:
    
    def __init__ (self):
        self.A = Stan("A", 1, 1)
        self.B = Stan("B", 1, 1)
        self.C = Stan("A", 0, 1)
        self.D = Stan("B", 1, 0)
        self.E = Stan("B", 0, 1)
        self.F = Stan("A", 1, 0)
        self.G = Stan("B", 0, 0)
        self.H = Stan("A", 0, 0)

        self.graph = {
            self.A: [self.B, self.C],
            self.B: [self.A, self.D],
            self.C: [self.A, self.E],
            self.D: [self.B, self.F],
            self.E: [self.C, self.G],
            self.F: [self.D, self.H],
            self.G: [self.H],
            self.H: [self.G]
        }

        self.visited = []
        self.queue = []

    def bfs(self):
        self.visited.append(self.A)
        self.queue.append(self.A)

        while self.queue:
            s = self.queue.pop(0)
            print(s)

            for neighbour in self.graph[s]:
                if neighbour not in self.visited:
                    self.visited.append(neighbour)
                    self.queue.append(neighbour)


if __name__ == '__main__':

    g = Graph()
    g.bfs()