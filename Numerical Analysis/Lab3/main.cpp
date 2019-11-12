#include <iostream>
#include <vector>
#include <fstream>
#include <cmath>
#include <iomanip>

using namespace std;

const int N = 12;
const int M = 3;
const int K = 3;
ofstream out("output.txt");

void gen(vector<vector<float> > &A, vector<float> &y, vector<float> &f) {
    A[0][0] = M;
    A[0][1] = M - 1;
    y[0] = 1;
    for (int i = 1; i < N; i++) {
        A[i][i - 1] = -K;
        A[i][i] = M + K + i - 1;
        if (i + 1 != N) {
            A[i][i + 1] = M + i - 1;
        }
        y[i] = i + 1;
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            f[i] += A[i][j] * y[j];
        }
    }
}

void TradMatAlgo(vector<vector<float> > &A, vector<float> &f, vector<float> &ans) {
    vector<float> alpha(N - 1, 0);
    vector<float> beta(N, 0);
    alpha[0] = -(A[0][1] / A[0][0]);
    beta[0] = f[0] / A[0][0];
    for (int i = 1; i < N - 1; i++) {
        alpha[i] = (-A[i][i + 1]) / (A[i][i] + alpha[i - 1] * A[i][i - 1]);
        beta[i] = (f[i] - A[i][i - 1] * beta[i - 1]) / (A[i][i] + alpha[i - 1] * A[i][i - 1]);
    }
    beta[N - 1] = (f[N - 1] - A[N - 1][N - 2] * beta[N - 2]) / (A[N - 1][N - 1] + alpha[N - 2] * A[N - 1][N - 2]);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < i; j++) {
            out << 0 << " ";
        }
        out << 1 << " ";
        if (i + 1 != N) {
            out << -alpha[i] << " ";
        }
        for (int j = i + 2; j < N; j++) {
            out << 0 << " ";
        }
        out << beta[i] << '\n';
    }
    out << '\n';
    ans[N - 1] = beta[N - 1];
    for (int i = N - 2; i >= 0; i--) {
        ans[i] = alpha[i] * ans[i + 1] + beta[i];
    }
}

float fault(vector<float> &ans, vector<float> &X) {
    float normalX = 0;
    float normalAnsX = 0;
    for (int i = 0; i < N; i++) {
        normalX += X[i] * X[i];
        normalAnsX += (X[i] - ans[i]) * (X[i] - ans[i]);
    }
    normalX = sqrt(normalX);
    normalAnsX = sqrt(normalAnsX);
    float f = normalAnsX / normalX;
    return f;
} // Подсчет относительной погрешности


int main() {
    vector<vector<float> > A(N, vector<float>(N, 0));
    vector<float> f(N, 0);
    vector<float> y(N, 0);
    vector<float> ans(N, 0);
    gen(A, y, f);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << A[i][j] << " ";
        }
        out << f[i] << '\n';
    }
    out << '\n';
    TradMatAlgo(A, f, ans);
    out << fixed;
    for (int i = 0; i < N; i++) {
        out << setprecision(10) << ans[i] << " ";
    }
    out << '\n';
    out << fault(ans, y) * 100 << '%';
    return 0;
}