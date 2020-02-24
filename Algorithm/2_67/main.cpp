#include <iostream>

using namespace std;

int main() {
    long long ans = 0;
    for (int i = 1; i < 8; i++) {
        for (int j = 1; j < 8; j++) {
            for (int k = 1; k < 8; k++) {
                if ((i ^ j ^ k != 0) && (i != j) && (i != k) && (j != k)) {
                    ans++;
                }
            }
        }
    }
    cout << ans << " " << 168 * 6 << " " << ans / 168 << " " << ans % 168;
    return 0;
}
