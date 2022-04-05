import math


class node():
  def __init__(self, value=None, parent=None):
    self.value = value
    self.parent = parent
    self.child = []
    self.a = -math.inf
    self.b = math.inf
    if parent != None:
      parent.child.append(self)


def fun(node): 
  for child in node.child:
    if child.value > node.a:
      node.a = child.value
      if node.a > node.parent.b:
        return
      node.parent.a = node.a
  node.b = node.a
  node.parent.b = node.b


def fun2(root): 
  for child in root.child:
    fun(child)

if __name__ == "__main__":

  
  A = node(None, None) 

  B = node(None, A)
  C = node(None, A)
  D = node(None, A)

  E = node(3, B)
  F = node(12, B)
  G = node(8, B)

  H = node(2, C)
  I = node(4, C) 
  J = node(6, C)

  K = node(14, D)
  L = node(5, D) 
  M = node(2, D)

  l1=node(None, None)

  l2=node(67, l1)
  l3=node(32, l1)
  l4=node(91, l1)
  
  l5=node(55, l2)
  l6=node(12, l2)
  l7=node(176, l2)

  l8=node(1, l3)
  l9=node(200, l3)
  l10=node(87, l3)

  l11=node(12, l4)
  l12=node(1, l4)
  l13=node(1, l4)


  fun2(A)
  print ("A: [{0}, {1}]".format(A.a, A.b))
  print ("B: [{0}, {1}]".format(B.a, B.b))
  print ("C: [{0}, {1}]".format(C.a, C.b))
  print ("D: [{0}, {1}]".format(D.a, D.b))

  fun2(l1)
  print ("l1: [{0}, {1}]".format(l1.a, l1.b))
  print ("l2: [{0}, {1}]".format(l2.a, l2.b))
  print ("l3: [{0}, {1}]".format(l3.a, l3.b))
  print ("l4: [{0}, {1}]".format(l4.a, l4.b))
