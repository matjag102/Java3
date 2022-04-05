import numpy as np

class Node:
    def __init__(self, array, parent=None):
        
        self.array = array
        self.parent = parent
        self.a = 0
        self.b = self.heu()
        self.c = self.a + self.b

    def wyswietl(self):
        print(self.array)

    def heu(self):
        cel = np.array([[1, 2, 3],[4, 5, 6],[7, 8, 0]])
        result = 0
        for x in range(1, 9):
            lx, ly = np.where(cel == x)
            cx, cy = np.where(self.array == x)

            result = result + abs(lx - cx) + abs(ly - cy)

        return int(result)

    def update_heu(self):
       
        self.b = self.array.heu()
        self.c = self.a + self.b

    def __lt__(self, other):
        return self.c < other.c

def fun(list1, node):
    for x in list1:
        if np.all(node.array == x.array) and node.c >= x.c:
            return False
    return True

def fun2(tablica):

    cel = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 0]])

    start = Node(tablica)
    end = Node(cel)

    list1 = []
    list2 = []

    list1.append(start)

    while len(list1) > 0:

        list1.sort()
        cn = list1.pop(0)
        list2.append(cn)

        if np.all(cn.array == end.array):
            p = []
            while cn != start:
                p.append(cn)
                cn = cn.parent

            return p

        

        tab1 = []
        bx, by = np.where(cn.array == 0)
        bx = int(bx)
        by = int(by)

        if 0 <= by - 1 <= 2:
            left_array = cn.array.copy()
            left_array[bx][by] = cn.array[bx][by-1]
            left_array[bx][by - 1] = 0
            tab1.append(Node(left_array, cn))

        

        if 0 <= bx + 1 <= 2:
            top_array = cn.array.copy()
            top_array[bx][by] = cn.array[bx + 1][by]
            top_array[bx + 1][by] = 0
            tab1.append(Node(top_array, cn))

        if 0 <= by + 1 <= 2:
            right_array = cn.array.copy()
            right_array[bx][by] = cn.array[bx][by + 1]
            right_array[bx][by + 1] = 0
            tab1.append(Node(right_array, cn))

        if 0 <= bx - 1 <= 2:
            bottom_array = cn.array.copy()
            bottom_array[bx][by] = cn.array[bx - 1][by]
            bottom_array[bx - 1][by] = 0
            tab1.append(Node(bottom_array, cn))

        for tab1 in tab1:
            for nodes in list2:
                if np.all(nodes.array == tab1.array):
                    continue

            if fun(list1, tab1):
                list1.append(tab1)


if __name__ == '__main__':

    tablica = np.array([[1, 0, 2],[5, 6, 3],[4, 7, 8]])

    final_p = fun2(tablica)
    p_length = len(final_p)
    final_p = reversed(final_p)

    print(tablica,"\n")
    for node in final_p:
        print("--------------\n",node.array)
    print("\n dlugosc: ", p_length)