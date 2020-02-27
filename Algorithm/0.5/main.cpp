#include <iostream>
#include <fstream>
#include <vector>

int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");
    int N;
    in >> N;
    std::vector<long long> a(N);
    std::vector<long long> ans(N + 1, -1);
    for (int i = 0; i < N; i++) {
        in >> a[i];
    }
    ans[0] = a[0];
    for (int i = 0; i < N - 2; i++) {
        if (ans[i] != -1) {
            ans[i + 2] = std::max(ans[i + 2], ans[i] + a[i + 2]);
            ans[i + 3] = std::max(ans[i + 3], ans[i] + a[i + 3]);
        }
    }
    out << ans[N - 1];
    return 0;
}
