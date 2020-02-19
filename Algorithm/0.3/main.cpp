#include <iostream>
#include <vector>

int binary_search_min(std::vector<long long> &v, long long x) {
    long long l = 0;
    long long r = v.size();
    while (l != r) {
        long long mid = (l + r) / 2;
        if (x <= v[mid]) {
            r = mid;
        } else {
            l = mid + 1;
        }
    }
    return l;
}

int binary_search_max(std::vector<long long> &v, long long x) {
    long long l = 0;
    long long r = v.size();
    while (l != r) {
        long long mid = (l + r) / 2;
        if (x < v[mid]) {
            r = mid;
        } else {
            l = mid + 1;
        }
    }
    return l;
}

int main() {
    int N;
    std::cin >> N;
    std::vector<long long> v(N, 0);
    for (int i = 0; i < N; i++) {
        std::cin >> v[i];
    }
    int k;
    std::cin >> k;
    for (int i = 0; i < k; i++) {
        long long x;
        std::cin >> x;
        long long b, l, r;
        l = binary_search_min(v, x);
        if (v[l] == x) {
            b = 1;
            r = binary_search_max(v, x);
        } else {
            b = 0;
            r = l;
        }
        std::cout << b << ' ' << l << ' ' << r << '\n';
    }
    return 0;
}
