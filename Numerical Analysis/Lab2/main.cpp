#include <iostream>
#include <vector>
#include <ctime>
#include <cstdlib>
#include <cmath>
#include <fstream>
#include <iomanip>

using namespace std;

const int N = 3;
const int K = 0;
const int M = 3;
ofstream out("output.txt");

float round4(float x) {
    float cnt = 1;
    if (x != 0) {
        while (fabs(x) < 1000) {
            x *= 10;
            cnt *= 10;
        }
        x = round(x);
        x /= cnt;
    }
    return x;
}

void gen(vector<vector<float> > &A, vector<float> &X, vector<float> &B) {
    float x = 1;
    for (int i = 0; i < K; i++) {
        x /= 10;
    }
    srand(time(nullptr));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i < j) {
                A[i][j] = -(rand() % 5);
                A[i][i] -= A[i][j];
            } else {
                if (j < i) {
                    A[i][j] = A[j][i];
                    A[i][i] -= A[i][j];
                }
            }
        }
        if (i == 0) {
            A[i][i] += x;
        }
        X[i] = M + i;
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            B[i] += A[i][j] * X[j];
        }
    }
}  //Генерация матрицы по первой схеме

void LDLt(vector<vector<float> > &A) {
    int *t = new int[N];
    for (int k = 0; k < N - 1; k++) {
        for (int i = k + 1; i < N; i++) {
            t[i] = A[i][k];
            A[i][k] = A[i][k] / A[k][k];
            A[i][k] = round4(A[i][k]);
            for (int j = k + 1; j < i; j++) {
                A[i][j] = A[i][j] - A[i][k] * t[j];
                A[i][j] = round4(A[i][j]);
            }
        }
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (j > i) {
                A[i][j] = A[j][i];
            }
        }
    }
}

void Gauss(vector<vector<float> > &A, vector<float> &B, vector<float> &ans) {
    vector<vector<float> > solution(N, vector<float>(N + 1));
    for (int i = 0; i < N; i++) { //Составление матрицы СЛАУ
        for (int j = 0; j <= N; j++) {
            if (j != N) {
                solution[i][j] = A[i][j];

            } else {
                solution[i][j] = B[i];
            }

        }
    }
    for (int i = 0; i < N; i++) { //Прямой ход
        float coef = solution[i][i];
        for (int j = i; j <= N; j++) {
            solution[i][j] /= coef;
            solution[i][j] = round4(solution[i][j]);
        }
        for (int j = i + 1; j < N; j++) {
            coef = solution[j][i] / solution[i][i];
            for (int k = 0; k <= N; k++) {
                float x = coef * solution[i][k];
                solution[j][k] -= x;
                solution[j][k] = round4(solution[j][k]);
            }
        }
    }
    for (int i = N - 1; i >= 0; i--) { //Обратный ход
        ans[i] = solution[i][N];
        for (int j = i + 1; j < N; j++) {
            ans[i] -= ans[j] * solution[i][j];
        }
        ans[i] /= solution[i][i];
        ans[i] = round4(ans[i]);
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
}

int main() {
    vector<vector<float> > A(N, vector<float>(N, 0));
    vector<float> X(N, 0);
    vector<float> B(N, 0);
    /* gen(A, X, B);
     for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
             out << setw(5) << A[i][j] << " ";
         }
         out << '\n';
     }
     out << '\n';*/
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cin >> A[i][j];
        }
    }

    LDLt(A);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << A[i][j] << " ";
        }
        cout << '\n';
    }
    /*vector<vector<float> > L(N, vector<float>(N, 0));
    vector<vector<float> > D(N, vector<float>(N, 0));
    vector<vector<float> > Lt(N, vector<float>(N, 0));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i > j) {
                L[i][j] = A[i][j];
            }
            if (i < j) {
                Lt[i][j] = A[i][j];
            }
            if (i == j) {
                L[i][i] = 1;
                Lt[i][i] = 1;
                D[i][i] = A[i][i];
            }
        }
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << setw(10) << L[i][j] << " ";
        }
        out << '\n';
    }
    out << '\n';
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << setw(4) << D[i][j] << " ";
        }
        out << '\n';
    }
    out << '\n';
    vector<float> Y(N, 0);
    Gauss(L, B, Y);
    vector<vector<float> > DLt(N, vector<float>(N, 0));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            for (int k = 0; k < N; k++) {
                DLt[i][j] += D[i][k] * Lt[k][j];
            }
        }
    }
    vector<float> ans(N, 0);
    Gauss(DLt, Y, ans);
    for (int i = 0; i < N; i++) {
        out << ans[i] << " ";
    }
    out << '\n';
    out << fault(ans, X) * 100 << "%";*/
    return 0;
}