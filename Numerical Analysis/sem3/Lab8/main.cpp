#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>

std::ifstream in("input.txt");
std::ofstream out("output.txt");
const long double STEP = 1e-1;
const long double EPSILON = 1e-6;

long double count(std::vector<long double> &P, long double root) {
    long double ans = 0;
    long double lambda = 1;
    for (int i = P.size() - 1; i >= 0; i--) {
        ans += P[i] * lambda;
        lambda *= root;
    }
    return ans;
}

long double iteration_method(std::vector<long double> &P, std::vector<long double> &derivative, long double root) {
    long double C = count(derivative, root) * 2;
    C = -1 / C;
    long double cur = root;
    long double prev = cur - 1;
    while (fabs(cur - prev) > EPSILON) {
        prev = cur;
        cur += C * count(P, cur);
    }
    return cur;
}

long double newton(std::vector<long double> &P, std::vector<long double> &derivative, long double root) {
    long double cur = root;
    long double prev = cur - 1;
    while (fabs(cur - prev) > EPSILON) {
        prev = cur;
        cur = prev - count(P, prev) / count(derivative, prev);
    }
    return cur;
}

long double binary(std::vector<long double> &P, long double l, long double r) {
    long double mid = (l + r) / 2;
    while (r - l > EPSILON) {
        if (count(P, mid) * count(P, l) < 0) {
            r = mid;
        } else {
            l = mid;
        }
        mid = (l + r) / 2;
    }
    return mid;
}

std::pair<long double, long double> range(std::vector<long double> &P, int der) {
    long double b = P[der];
    long double c = P[der + 1];
    for (int i = der + 1; i < P.size() - 1; i++) {
        b = std::max(b, P[i]);
        c = std::max(c, P[i]);
    }
    c = std::max(c, P[P.size() - 1]);
    return std::make_pair(std::fabs(P[P.size() - 1]) / (b + P[P.size() - 1]), 1 + (c / P[der]));
}

std::vector<long double> derivative(std::vector<long double> &P) {
    std::vector<long double> ans = P;
    for (int i = ans.size() - 1; i > 0; i--) {
        ans[i] = ans[i - 1] * (ans.size() - i);
    }
    ans[0] = 0;
    return ans;
}


std::vector<std::pair<long double, long double> > root_range(std::vector<long double> &P, std::pair<long double, long double> roots) {
    std::vector<std::pair<long double, long double> > ans;
    long double prev = count(P, -roots.second);
    for (long double i = -roots.second; i <= -roots.first; i += STEP) {
        long double cur = count(P, i);
        if (((prev <= 0) && (cur >= 0)) || ((prev >= 0) && (cur <= 0))) {
            ans.emplace_back(i - STEP, i);
        }
        prev = cur;
    }
    prev = count(P, roots.first);
    for (long double i = roots.first; i <= roots.second; i += STEP) {
        long double cur = count(P, i);
        if (((prev <= 0) && (cur >= 0)) || ((prev >= 0) && (cur <= 0))) {
            ans.emplace_back(i - STEP, i);
        }
        prev = cur;
    }
    return ans;
}

void
print(std::vector<long double> &P, std::pair<long double, long double> R, std::vector<long double> &der, std::vector<long double> &der_roots) {
    long double prev = 0;
    long double cur = R.first;
    std::cout << "Newton Iteration Binary search\n";
    for (auto root:der_roots) {
        prev = cur;
        cur = root;
        std::cout << newton(P, der, (prev + cur) / 2) << " ";
        std::cout << iteration_method(P, der, (prev + cur) / 2) << " ";
        std::cout << binary(P, prev, cur) << '\n';
    }
    std::cout << newton(P, der, (prev + cur) / 2) << " ";
    std::cout << iteration_method(P, der, (prev + cur) / 2) << " ";
    std::cout << binary(P, cur, R.second) << '\n';
}

int main() {
    std::cout << std::fixed;
    std::vector<long double> P(5);
    P[0] = 1;
    for (int i = 1; i < 5; i++) {
        in >> P[i];
    }
    std::vector<long double> der_1 = derivative(P);
    std::vector<long double> der_2 = derivative(der_1);
    std::pair<long double, long double> derivative_root = range(der_1, 1);
    std::vector<std::pair<long double, long double> > roots = root_range(der_1, derivative_root);
    std::vector<long double> newton_roots;
    std::vector<long double> iteration_roots;
    for (auto r: roots) {
        newton_roots.emplace_back(newton(der_1, der_2, (r.second + r.first) / 2));
        iteration_roots.emplace_back(iteration_method(der_1, der_2, (r.second + r.first) / 2));
    }
    std::pair<long double, long double> R = range(P, 0);
    std::cout << "Derivative by Newton\n";
    print(P, R, der_1, newton_roots);
    std::cout << "Derivative by iteration\n";
    print(P, R, der_1, iteration_roots);
    return 0;
}
