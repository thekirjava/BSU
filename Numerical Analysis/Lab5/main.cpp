#include <iostream>
#include <vector>
#include <fstream>
#include <cstdlib>
#include <ctime>
#include <cmath>

using namespace std;

const int N = 12;
const int M = 3;
const float eps = 0.0001;
const int k = 1000;

ofstream out("output.txt");

void gen(vector<vector<float> > &A, vector<float> &X, vector<float> &B) {
    srand(time(nullptr));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i != j) {
                A[i][j] = -(rand() % 5);
                A[i][i] -= A[i][j];
            }
        }
        if (i == 0) {
            A[i][i] += 1;
        }
        X[i] = M + i;
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            B[i] += A[i][j] * X[j];
        }

    }
}

int Jacobi(vector<vector<float> > &A, vector<float> &B, vector<float> &ans) {
    for (int i = 0; i < N; i++) {
        ans[i] = B[i] / A[i][i];
    }
    int cnt = 0;
    float delta = 100;
    while ((cnt <= k) && (fabs(delta) >= eps)) {
        delta = 0;
        vector<float> buf = ans;
        for (int i = 0; i < N; i++) {
            ans[i] = B[i];
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    ans[i] -= A[i][j] * buf[j];
                }
            }
            ans[i] /= A[i][i];
        }
        for (int i = 0; i < N; i++) {
            if (fabs(buf[i] - ans[i]) > fabs(delta)) {
                delta = fabs(buf[i] - ans[i]);
            }
        }
        if (cnt == 0) {
            delta = 100;
        }
        cnt++;
    }
    return cnt - 1;
}

int Relaxation(vector<vector<float> > &A, vector<float> &B, vector<float> &ans, const float omega) {
    for (int i = 0; i < N; i++) {
        ans[i] = B[i] / A[i][i];
    }
    int cnt = 0;
    float delta = 100;
    while ((cnt <= k) && (fabs(delta) >= eps)) {
        delta = 0;
        vector<float> buf = ans;
        for (int i = 0; i < N; i++) {
            ans[i] = B[i];
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    ans[i] -= A[i][j] * buf[j];
                }
            }
            ans[i] /= A[i][i];
            ans[i] *= omega;
            ans[i] += buf[i] * (1 - omega);
        }
        for (int i = 0; i < N; i++) {
            if (fabs(buf[i] - ans[i]) > fabs(delta)) {
                delta = fabs(buf[i] - ans[i]);
            }
        }
        if (cnt == 0) {
            delta = 100;
        }
        cnt++;
    }
    return cnt - 1;
}

int main() {
    vector<vector<float> > A(N, vector<float>(N, 0));
    vector<float> X(N, 0);
    vector<float> B(N, 0);
    gen(A, X, B);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << A[i][j] << " ";
        }
        out << B[i] << '\n';
    }
    vector<float> ans1(N, 0);
    vector<float> ans2(N, 0);
    vector<float> ans3(N, 0);
    vector<float> ans4(N, 0);
    int i1 = Jacobi(A, B, ans1);
    int i2 = Relaxation(A, B, ans2, 0.5);
    int i3 = Relaxation(A, B, ans3, 1);
    int i4 = Relaxation(A, B, ans4, 1.5);
    out << "Метод Якоби:\n";
    out << i1 << '\n';
    for (int i = 0; i < N; i++) {
        out << ans1[i] << " ";
    }
    out << '\n';
    out << "Метод релаксации(омега = 0.5):\n";
    out << i2 << '\n';
    for (int i = 0; i < N; i++) {
        out << ans2[i] << " ";
    }
    out << '\n';
    out << "Метод релаксации(омега = 1):\n";
    out << i3 << '\n';
    for (int i = 0; i < N; i++) {
        out << ans3[i] << " ";
    }
    out << '\n';
    out << "Метод релаксации(омега = 1.5):\n";
    out << i4 << '\n';
    for (int i = 0; i < N; i++) {
        out << ans4[i] << " ";
    }
    return 0;
}