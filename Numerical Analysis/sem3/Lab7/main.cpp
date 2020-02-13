#include <iostream>
#include <vector>
#include <random>
#include <ctime>
#include <fstream>

using namespace std;

const int N = 12;
const int M = 50;
const int K = 50;

ofstream out("output.txt");

void gen(vector<vector<float> > &A) {
    mt19937 generator(time(nullptr));
    uniform_int_distribution<> uid(-4, 0);
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            A[i][j] = uid(generator);
            A[j][i] = A[i][j];
            A[i][i] -= A[i][j];
            A[j][j] -= A[j][i];
        }
        if (i == 0) {
            A[i][i]++;
        }
    }
    out << "Входные данные:\n";
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            out << A[i][j] << " ";
        }
        out << '\n';
    }
}


vector<float> operator*(vector<vector<float> > &A, vector<float> &B) {
    vector<float> ans(N, 0);
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            ans[i] += A[i][j] * B[j];
        }
    }
    return ans;
}

vector<float> operator*(float l, vector<float> &A) {
    vector<float> ans(N, 0);
    for (int i = 0; i < N; i++) {
        ans[i] = A[i] * l;
    }
    return ans;
}

vector<float> operator/(vector<float> &A, float l) {
    vector<float> ans(N, 0);
    for (int i = 0; i < N; i++) {
        ans[i] = A[i] / l;
    }
    return ans;
}

vector<float> operator-(vector<float> &A, vector<float> &B) {
    vector<float> ans(N, 0);
    for (int i = 0; i < N; i++) {
        ans[i] = A[i] - B[i];
    }
    return ans;
}

template<typename T>
int sgn(T x) {
    return (T(0) < x) - (x < T(0));
}

float lambda_1(vector<float> &v, vector<float> &u) {
    int m = 0;
    for (int i = 0; i < N; i++) {
        if (fabs(u[i]) > fabs(u[m])) {
            m = i;
        }
    }
    return v[m] * sgn(u[m]);
}


float lambda_2(vector<float> &v, vector<float> &u) {
    float l = 0, divisor = 0;
    for (int i = 0; i < N; i++) {
        l += v[i] * u[i];
        divisor += u[i] * u[i];
    }
    l /= divisor;
    return l;
}

void print1(vector<float> v, vector<float> u, float l, vector<vector<float> > &A) {
    vector<float> Au = A * u;
    out << "Собственное значение - " << l << '\n';
    out << "Проверка точности:\n";
    for (int i = 0; i < N; i++) {
        out << v[i] - l * u[i] << " ";
    }
    for (int i = 0; i < N; i++) {
        out << Au[i] - l * u[i] << " ";
    }
    out << '\n';
}

float cube_norm(vector<float> v) {
    float ans = 0;
    for (int i = 0; i < N; i++) {
        if (ans < fabs(v[i])) {
            ans = fabs(v[i]);
        }
    }
    return ans;
}

float count1(vector<vector<float> > &A) {
    vector<float> u(N, 0);
    u[0] = 1;
    vector<float> v = A * u;
    float l1 = 0, l2 = 0;
    for (int i = 0; i < K; i++) {
        l1 = lambda_1(v, u);
        l2 = lambda_2(v, u);
        if (i >= 45) {
            out << "Собственный вектор :\n";
            for (int j = 0; j < N; j++) {
                out << u[j] << " ";
            }
            out << '\n';
            print1(v, u, l1, A);
            print1(v, u, l2, A);
            out<<'\n';
        }
        if (i == 49) {
            vector<float> buf1 = l1 * u;
            vector<float> buf2 = l2 * u;
            out << "Кубические нормы для по разному посчитанных векторов: \n";
            out << cube_norm(v - buf1) << " " << cube_norm(v - buf2) << '\n';
        }

        u = v / cube_norm(v);
        v = A * u;
    }
    return l2;
}

float lambda(vector<float> v_plus, vector<float> v, vector<float> u, float l) {
    int m = 0;
    for (int i = 0; i < N; i++) {
        if (fabs(v[m] - u[m] * l) < fabs(v[i] - u[i] * l)) {
            m = i;
        }
    }
    return ((cube_norm(v) * v_plus[m]) - (l * v[m])) / (v[m] - (l * u[m]));
}


void count2(vector<vector<float> > A, float l) {
    vector<float> u(N, 0);
    u[0] = 1;
    vector<float> v = A * u;
    vector<float> u_plus = v / cube_norm(v);
    vector<float> v_plus = A * u_plus;
    float lmbd = 0;
    for (int i = 0; i < M; i++) {
        lmbd = lambda(v_plus, v, u, l);
        u = v / cube_norm(v);
        v = A * u;
        u_plus = v / cube_norm(v);
        v_plus = A * u_plus;
    }
    out << "Собственное значение - " << lmbd << '\n';
    vector<float> x = l * u;
    x = v - x;
    out << "Собственный вектор\n";
    for (int i = 0; i < N; i++) {
        out << x[i] << " ";
    }
    out << '\n';
    vector<float> Ax = A * x;
    out << "Проверка точности\n";
    float cn = 0;
    for (int i = 0; i < N; i++) {
        out << Ax[i] - lmbd * x[i] << " ";
        if (fabs(Ax[i] - lmbd * x[i]) > fabs(cn)) {
            cn = Ax[i] - lmbd * x[i];
        }
    }
    out << '\n';
    out << "Кубическая норма - " << cn << '\n';
}

int main() {
    out << fixed;
    vector<vector<float> > A(N, vector<float>(N, 0));
    gen(A);
    out << "\nПоиск первого собственного значения:\n";
    float l = count1(A);
    out << "\nПоиск второго собственного значения:\n";
    count2(A, l);
    return 0;
}