#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <map>

struct request {
    int cmd;
    long long x1;
    long long x2;
    long long y1;
    long long y2;
    long k;
};

int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");
    std::vector<request> requests;
    std::priority_queue<long long, std::vector<long long>, std::greater<> > x_queue;
    std::priority_queue<long long, std::vector<long long>, std::greater<> > y_queue;
    request r{};
    in >> r.cmd >> r.k;
    requests.emplace_back(r);
    while (r.cmd != 3) {
        in >> r.cmd;
        if (r.cmd == 1) {
            in >> r.x1 >> r.y1 >> r.k;
            x_queue.push(r.x1);
            y_queue.push(r.y1);
        }
        if (r.cmd == 2) {
            in >> r.x1 >> r.y1 >> r.x2 >> r.y2;
            x_queue.push(r.x1);
            x_queue.push(r.x2);
            y_queue.push(r.y1);
            y_queue.push(r.y2);

        }
        requests.emplace_back(r);
    }
    std::map<long long, int> x_map;
    std::map<long long, int> y_map;
    int x_size = 0;
    int y_size = 0;
    while (!x_queue.empty()) {
        int x = x_queue.top();
        x_map[x] = x_size;
        while ((!x_queue.empty()) && (x_queue.top() == x)) {
            x_queue.pop();
        }
        x_size++;
    }
    while (!y_queue.empty()) {
        int y = y_queue.top();
        y_map[y] = y_size;
        while ((!y_queue.empty()) && (y_queue.top() == y)) {
            y_queue.pop();
        }
        y_size++;
    }
    int s = std::max(x_size, y_size);
    std::vector<std::vector<unsigned long long> > fenvik(s, std::vector<unsigned long long>(s, 0));

    for (auto &request : requests) {
        switch (request.cmd) {
            case 0: {
                break;
            }
            case 1: {
                for (int i = x_map[request.x1]; i < s; i = i | (i + 1)) {
                    for (int j = y_map[request.y1]; j < s; j = j | (j + 1)) {
                        fenvik[i][j] += request.k;
                    }
                }
                break;
            }
            case 2: {
                unsigned long long ans = 0;
                for (int i = x_map[request.x2]; i >= 0; i = (i & (i + 1)) - 1) {
                    for (int j = y_map[request.y2]; j >= 0; j = (j & (j + 1)) - 1) {
                        ans += fenvik[i][j];
                    }
                }
                for (int i = x_map[request.x1] - 1; i >= 0; i = (i & (i + 1)) - 1) {
                    for (int j = y_map[request.y1] - 1; j >= 0; j = (j & (j + 1)) - 1) {
                        ans += fenvik[i][j];
                    }
                }
                for (int i = x_map[request.x2]; i >= 0; i = (i & (i + 1)) - 1) {
                    for (int j = y_map[request.y1] - 1; j >= 0; j = (j & (j + 1)) - 1) {
                        ans -= fenvik[i][j];
                    }
                }
                for (int i = x_map[request.x1] - 1; i >= 0; i = (i & (i + 1)) - 1) {
                    for (int j = x_map[request.y2]; j >= 0; j = (j & (j + 1)) - 1) {
                        ans -= fenvik[i][j];
                    }
                }
                out << ans << '\n';
            }
        }
    }
    return 0;
}