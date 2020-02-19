#include <iostream>
#include <fstream>
#include <vector>
#include <climits>

int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");
    int N;
    in >> N;
    std::vector<std::vector<std::pair<long long, long long> > > polygon(2 * N,
                                                                        std::vector<std::pair<long long, long long> >(
                                                                                2 * N,
                                                                                std::make_pair(LLONG_MIN, LLONG_MAX)));
    std::vector<char> operations(N);
    for (int i = 0; i < N; i++) {
        in >> operations[i] >> polygon[i][i].first;
        polygon[i][i].second = polygon[i][i].first;
        polygon[i + N][i + N] = polygon[i][i];
    }
    long long ans = LLONG_MIN;
    int cnt = 0;
    for (int i = 2 * N - 1; i >= 0; i--) {
        for (int j = i + 1; j < 2 * N; j++) {
            for (int k = i; k < j; k++) {
                if (operations[(k + 1) % N] == 't') {
                    polygon[i][j].first = std::max(polygon[i][j].first, polygon[i][k].first + polygon[k + 1][j].first);
                    polygon[i][j].second = std::min(polygon[i][j].second,
                                                    polygon[i][k].second + polygon[k + 1][j].second);
                } else {
                    long long buf1 = polygon[i][k].first * polygon[k + 1][j].first;
                    long long buf2 = polygon[i][k].second * polygon[k + 1][j].second;
                    long long buf3 = polygon[i][k].first * polygon[k + 1][j].second;
                    long long buf4 = polygon[i][k].second * polygon[k + 1][j].first;
                    polygon[i][j].first = std::max(polygon[i][j].first,
                                                   std::max(buf1, std::max(buf2, std::max(buf3, buf4))));
                    polygon[i][j].second = std::min(polygon[i][j].second,
                                                    std::min(buf1, std::min(buf2, std::min(buf3, buf4))));
                }
            }
            if ((j - i == N - 1) && (i < N)) {
                if (polygon[i][j].first > ans) {
                    ans = polygon[i][j].first;
                    cnt = 0;
                }
                if (polygon[i][j].first == ans) {
                    cnt++;
                }
            }
        }
    }
    out << ans << '\n';
    for (int i = 0; i < N; i++) {
        if (polygon[i][i + N - 1].first == ans) {
            out << i + 1;
            cnt--;
            if (cnt > 0) {
                out << ' ';
            }
        }
    }
    out << '\n';
    return 0;
}
