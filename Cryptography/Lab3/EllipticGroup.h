#pragma once
#include <iostream>
#include <vector>
#include <random>
#include <tuple>

class EllipticGroup {
public:
    struct Point{
        int x;
        int y;
    };
    EllipticGroup();
    friend std::ostream &operator<<(std::ostream &out, const Point &p);
    friend bool operator==(const Point &lhs, const Point &rhs);
    friend EllipticGroup::Point operator+(const Point &lhs, const Point &rhs);
    friend EllipticGroup::Point operator*(const Point& lhs, int rhs);
    static int findReverse(int value);
    int getRandomIdx() const;
    Point getRandomPoint() const;
    const static int M = 47;
    const static int a = 5;
    const static int b = 13;

    const Point &getG() const;

private:
    void findG();
    std::vector<Point> points;
    Point G;
    mutable std::mt19937 generator;
};

std::tuple<int, int, int> extendedGCD(int a, int b);