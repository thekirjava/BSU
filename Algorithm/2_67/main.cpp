#include <iostream>
#include <fstream>
#include <vector>

long long const int module = 1000000007;

long long binary_pow(long long x, long long y) {

    std::vector<long long> pre_count(1000, 0);
    pre_count[0] = 1;
    pre_count[1] = x % module;
    long long counter = 2;
    long long i = 2;
    for (; counter <= y; counter *= 2, i++) {
        pre_count[i] = pre_count[i - 1] * pre_count[i - 1];
        pre_count[i] %= module;
    }
    long long ans = 1;
    i--;
    counter /= 2;
    for (; i >= 0; counter /= 2, i--) {
        if (counter <= y) {
            ans *= pre_count[i];
            ans %= module;
            y -= counter;
        }
        if (y == 0) {
            break;
        }
    }
    return ans;
}

int main() {
    std::ifstream in("nim.in");
    std::ofstream out("nim.out");
    long long n, m;
    in >> n >> m;
    m = binary_pow(2, m);
    long long ans;
    long long placement = 1;
    long long cur = -1, next = -(m - 1), next_next = 0;
    for (int i = 1; i <= n; i++) {
        cur += placement;
        placement *= m - i;
        placement %= module;
        cur %= module;
        if (i != n) {
            next -= cur;
            next += module;
            next %= module;
            long long buf = cur * (m - 1 - i);
            buf %= module;
            buf *= (i + 1);
            buf %= module;
            next_next -= buf;
            next_next += module;
            next_next %= module;
            cur = next;
            next = next_next;
            next_next = 0;
        }
    }
    ans = placement - cur;
    ans += module;
    ans %= module;
    out << ans;
    return 0;
}