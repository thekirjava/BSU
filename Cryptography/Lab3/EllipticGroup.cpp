#include "EllipticGroup.h"
#include <ctime>


std::ostream &operator<<(std::ostream &out, const EllipticGroup::Point &p) {
    out << "{" << p.x << ", " << p.y << "}";
    return out;
}

bool operator==(const EllipticGroup::Point &lhs, const EllipticGroup::Point &rhs) {
    return (lhs.x == rhs.x) && (lhs.y == rhs.y);
}

EllipticGroup::Point operator+(const EllipticGroup::Point &lhs, const EllipticGroup::Point &rhs) {
    int lambda;
    if (lhs == EllipticGroup::Point{-1, -1}) {
        return rhs;
    } else if (rhs == EllipticGroup::Point{-1, -1}) {
        return lhs;
    }
    if (lhs == rhs) {
        lambda = (3 * lhs.x * lhs.x + EllipticGroup::a);
        lambda *= EllipticGroup::findReverse(2 * lhs.y);
        lambda %= EllipticGroup::M;
    } else {
        if (lhs.x == rhs.x) {
            return {-1, -1};
        }
        lambda = rhs.y - lhs.y;
        if (lambda < 0) {
            lambda += EllipticGroup::M;
        }
        lambda *= EllipticGroup::findReverse(rhs.x - lhs.x);
        lambda %= EllipticGroup::M;
    }

    EllipticGroup::Point result{(lambda * lambda - lhs.x - rhs.x) % EllipticGroup::M, 0};
    if (result.x < 0) {
        result.x += EllipticGroup::M;
    }
    result.y = (lambda * (result.x - lhs.x) + lhs.y) % EllipticGroup::M;
    if (result.y < 0) {
        result.y += EllipticGroup::M;
    }
    return result;
}

EllipticGroup::Point operator*(const EllipticGroup::Point &lhs, int rhs) {
    EllipticGroup::Point result = lhs;
    for (int i = 1; i < rhs; i++) {
        result = result + lhs;
    }
    return result;
}

EllipticGroup::EllipticGroup() {
    for (int i = 0; i < EllipticGroup::M; i++) {
        for (int j = 0; j < EllipticGroup::M; j++) {
            if ((i * i * i + EllipticGroup::a * i + EllipticGroup::b) % EllipticGroup::M
                == (j * j) % EllipticGroup::M) {
                this->points.push_back({i, j});
            }
        }
    }
    for (const auto &point: points) {
        std::cout << point << '\n';
    }
    this->generator = std::mt19937(std::time(nullptr));
    this->findG();
    //this->G = getRandomPoint();
}

int EllipticGroup::getRandomIdx() const {
    std::uniform_int_distribution<> uid(0, this->points.size() - 1);
    return uid(this->generator);
}

EllipticGroup::Point EllipticGroup::getRandomPoint() const {

    return this->points[getRandomIdx()];
}

const EllipticGroup::Point &EllipticGroup::getG() const {
    return G;
}

void EllipticGroup::findG() {
    int N = this->points.size();
    int h = N;
    for (const auto &point: points) {
        Point cur = point;
        int n = 1;
        while (!(cur == Point{-1, -1})) {
            cur = cur + point;
            n += 1;
            if (cur == point) {
                n = N + 1;
                break;
            }
        }
        std::cout << point <<": " << n <<'\n';
        if (N % n != 0) {
            continue;
        }
        bool flag = true;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                flag = false;
                break;
            }
        }
        if (flag) {
            h = std::min(h, N / n);
        }
    }
    this->G = Point{-1, -1};
    while (this->G == Point{-1, -1}) {
        this->G = getRandomPoint() * h;
    }
    std::cout << N << " " << h << '\n';
}

std::tuple<int, int, int> extendedGCD(int a, int b) {
    int s = 0;
    int oldS = 1;
    int t = 1;
    int oldT = 0;
    int r = b;
    int oldR = a;
    while (r != 0) {
        int quotient = oldR / r;
        int buf = oldR - quotient * r;
        oldR = r;
        r = buf;
        buf = oldS - quotient * s;
        oldS = s;
        s = buf;
        buf = oldT - quotient * t;
        oldT = t;
        t = buf;
    }
    return {oldR, oldS, oldT};
}

int EllipticGroup::findReverse(int value) {
    auto[gcd, x, y] = extendedGCD(value, EllipticGroup::M);
    x %= EllipticGroup::M;
    if (x < 0) {
        x += EllipticGroup::M;
    }
    return x;
}