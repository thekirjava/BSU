#include <iostream>
#include <vector>
#include <random>
#include <ctime>
#include <fstream>

using namespace std;

const int N = 4;
const int M = 5;
const int K = 100;

ofstream out("output.txt");

void gen(vector<vector<float> > A) {
    mt19937 generator(time(nullptr));
    uniform_int_distribution<> uid(-4, 0);
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            A[i][j] = uid(generator);
            A[i][i] -= A[i][j];
        }
        if (i == 0) {
            A[i][i]++;
        }
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

void print(vector<float> v, vector<float> u, float l, vector<vector<float> > &A) {


    vector<float> Au = A * u;
    out << l << '\n';
    out << '\n';
    for (int i = 0; i < N; i++) {
        out << v[i] - l * u[i] << " ";
    }
    for (int i = 0; i < N; i++) {
        out << Au[i] - l * u[i] << " ";
    }
    out << '\n';

}

float cube_norm(vector<float> v, vector<float> u, float l) {
    float ans = 0;
    for (int i = 0; i < N; i++) {
        if (ans < fabs(v[i] - l * u[i])) {
            ans = fabs(v[i] - l * u[i]);
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
            for (int j = 0; j < N; j++) {
                out << u[i] << " ";
            }
            out << '\n';
            print(v, u, l1, A);
            print(v, u, l2, A);
        }
        if (i == 49) {
            out << cube_norm(v, u, l1) << " " << cube_norm(v, u, l2) << '\n';
        }
        u = v;
        v = A * v;
    }
    return l1;
}

int main() {
    vector<vector<float> > A(N, vector<float>(N, 0));
    gen(A);
    float l = count1(A);
    return 0;
}