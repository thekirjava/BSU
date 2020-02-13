#include <iostream>
#include <fstream>
#include <random>
#include <vector>
#include <ctime>

using namespace std;

const int N = 4;
ofstream out("output.txt");

void gen(vector<vector<float> > &A) {
    out << "Матрица А\n";
    mt19937 gen(time(nullptr));
    uniform_int_distribution<> uid(-50, 50);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            A[i][j] = uid(gen);
            out << A[i][j] << " ";
        }
        out << '\n';
    }
}

vector<vector<float> > operator*(const vector<vector<float> > &A, const vector<vector<float> > &B) {
    vector<vector<float> > ans(N, vector<float>(N, 0));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            for (int k = 0; k < N; k++) {
                ans[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    return ans;
}

vector<vector<float> > findM(const vector<vector<float> > &A, int k) {
    vector<vector<float> > M(N, vector<float>(N, 0));
    for (int i = 0; i < N; i++) {
        if (i == k - 1) {
            for (int j = 0; j < N; j++) {
                if (j != k - 1) {
                    M[i][j] = -A[k][j] / A[k][k - 1];
                } else {
                    M[i][j] = 1 / A[k][k - 1];
                }
            }
        } else {
            M[i][i] = 1;
        }
    }
    return M;
}

vector<vector<float> > findInvertM(const vector<vector<float> > &A, int k) {
    vector<vector<float> > invertM(N, vector<float>(N, 0));
    for (int i = 0; i < N; i++) {
        if (i == k - 1) {
            for (int j = 0; j < N; j++) {
                invertM[i][j] = A[k][j];
            }
        } else {
            invertM[i][i] = 1;
        }
    }
    return invertM;
}

float trace(const vector<vector<float> > &A) {
    float ans = 0;
    for (int i = 0; i < N; i++) {
        ans += A[i][i];
    }
    return ans;
}

int main() {
    out << fixed;
    vector<vector<float> > A(N, vector<float>(N, 0));
    gen(A);
    float t = trace(A);
    for (int i = 0; i < N - 1; i++) {
        int k = N - i - 1;
        while (fabs(A[k][k - 1]) < 1e-8) {
            out << "Нерегулярная матрица!\n";
            gen(A);
            i = 0;
            k = N - 1;
            t = trace(A);
        }
        vector<vector<float> > M = findM(A, k);
        vector<vector<float> > invertM = findInvertM(A, k);
        A = A * M;
        A = invertM * A;
        out << "M" << k << ":\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                out << M[i][j] << " ";
            }
            out << '\n';
        }
    }
    out << "Коэффициент p1: " << A[0][0] << '\n';
    out << "След матрицы: " << t << '\n';
    out << "Форма Фробениуса: \n";
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << A[i][j] << " ";
        }
        out << '\n';
    }
    return 0;
}