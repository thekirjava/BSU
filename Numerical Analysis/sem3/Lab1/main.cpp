#include <iostream>
#include <vector>
#include <cstdlib>
#include <time.h>
#include <cmath>
#include <fstream>
#include <iomanip>

using namespace std;

const int N = 15;
const int M = 3;
const int K = 0;
ofstream out("output.txt");

void gen1(vector<vector<float> > &A, vector<float> &X, vector<float> &B) {
    float x = 1;
    for (int i = 0; i < K; i++) {
        x /= 10;
    }
    srand(time(nullptr));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i != j) {
                A[i][j] = -(rand() % 5);
                A[i][i] -= A[i][j];
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

void gen2(vector<vector<float> > &A, vector<float> &X, vector<float> &B) {
    srand(time(nullptr));
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            A[i][j] = (rand() % 201) - 100;
        }
        X[i] = M + i;
    }
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            B[i] += A[i][j] * X[j];
        }
    }
} //Генерация матрицы по второй схеме

void solve_1(vector<vector<float> > &A, vector<float> &X, vector<float> &B, vector<float> &ans) {
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
    out << "Исходная матрица:\n";
    for (int j = 0; j < N; j++) {
        for (int k = 0; k <= N; k++) {
            out << solution[j][k] << " ";
        }
        out << '\n';
    }
    out << '\n';
    for (int i = 0; i < N; i++) { //Прямой ход
        float coef = solution[i][i];
        for (int j = i; j <= N; j++) {
            solution[i][j] /= coef;
        }
        for (int j = i + 1; j < N; j++) {
            coef = solution[j][i] / solution[i][i];
            for (int k = 0; k <= N; k++) {
                float x = coef * solution[i][k];
                solution[j][k] -= x;
            }
        }
        if (i == 0) {
            out << "После первого шага:\n";
            for (int j = 0; j < N; j++) {
                for (int k = 0; k <= N; k++) {
                    out << solution[j][k] << " ";
                }
                out << '\n';
            }
            out << '\n';
        }
    }
    for (int i = N - 1; i >= 0; i--) { //Обратный ход
        ans[i] = solution[i][N];
        for (int j = i + 1; j < N; j++) {
            ans[i] -= ans[j] * solution[i][j];
        }
        ans[i] /= solution[i][i];
    }
} //Метод Гаусса без выбора

void solve_2(vector<vector<float> > &A, vector<float> &X, vector<float> &B, vector<float> &ans) {
    vector<vector<float> > solution(N, vector<float>(N + 1));
    for (int i = 0; i < N; i++) { //Составление матрицы СЛАУ
        for (int j = 0; j < N; j++) {
            solution[i][j] = A[i][j];
        }
        solution[i][N] = B[i];
    }
    out << "Исходная матрица:\n";
    for (int j = 0; j < N; j++) {
        for (int k = 0; k <= N; k++) {
            out << solution[j][k] << " ";
        }
        out << '\n';
    }
    out << '\n';
    for (int i = 0; i < N; i++) { //Прямой ход
        int mx = i;
        for (int j = i + 1; j < N; j++) { //Выбор ведущего элемента
            if (fabs(solution[j][i]) > fabs(solution[mx][i])) {
                mx = j;
            }
        }
        swap(solution[i], solution[mx]);
        float coef = solution[i][i];
        for (int j = i; j <= N; j++) {
            solution[i][j] /= coef;
        }
        for (int j = i + 1; j < N; j++) {
            coef = solution[j][i] / solution[i][i];
            for (int k = 0; k <= N; k++) {
                float x = coef * solution[i][k];
                solution[j][k] -= x;
            }
        }
        if (i == 0) {
            out << "После первого шага(номер ведущего - " << mx + 1 << "):\n";
            for (int j = 0; j < N; j++) {
                for (int k = 0; k <= N; k++) {
                    out << solution[j][k] << " ";
                }
                out << '\n';
            }
            out << '\n';
        }
    }
    for (int i = N - 1; i >= 0; i--) { //Обратный ход
        ans[i] = solution[i][N];
        for (int j = i + 1; j < N; j++) {
            ans[i] -= ans[j] * solution[i][j];
        }
        ans[i] /= solution[i][i];
    }
} //Метод Гаусса с выбором

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
    out << fixed << setprecision(5);
    vector<vector<float> > A(N, vector<float>(N, 0));
    vector<float> X(N, 0);
    vector<float> B(N, 0);
    gen1(A, X, B);
    vector<float> ans1(N, 0);
    vector<float> ans2_1(N, 0);
    vector<float> ans2_2(N, 0);
    solve_1(A, X, B, ans1);
    out << "Вектор приближенного решения:\n";
    for (int i = 0; i < N; i++) {
        out << ans1[i] << " ";
    }
    out << '\n' << '\n';
    out << "Относительная погрешность:\n" << fault(ans1, X) * 100.0 << " %\n" << '\n';
    A.resize(N, vector<float>(N, 0));
    X.resize(N, 0);
    B.resize(N, 0);
    gen2(A, X, B);
    solve_1(A, X, B, ans2_1);
    solve_2(A, X, B, ans2_2);
    out << "Вектор приближенного решения 1 :\n";
    for (int i = 0; i < N; i++) {
        out << ans2_1[i] << " ";
    }
    out << '\n' << '\n';
    out << "Относительная погрешность 1:\n" << fault(ans2_1, X) * 100.0 << " %\n" << '\n';
    out << "Вектор приближенного решения 2:\n";
    for (int i = 0; i < N; i++) {
        out << ans2_2[i] << " ";
    }
    out << '\n' << '\n';
    out << "Относительная погрешность 2:\n" << fault(ans2_2, X) * 100.0 << " %\n" << '\n';
    return 0;
}