class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y


class EllipticGroup:
    def __init__(self, a, b, M):
        self.__a = a
        self.__b = b
        self.__M = M
        self.__points = self.__generatePoints()

    def __generatePoints(self):
    
        result = []
        for i in range(self.__M):
            for j in range(self.__M):
                if (i * i * i + self.__a * i + self.__b) % self.__M == (j * j) % self.__M:

