#include <iostream>
#include <random>
#include <fstream>
#include <vector>
#include <ctime>

using namespace std;
const int M = 3;
const int N = 200;

int main() {
    ofstream system("input.txt");
    ofstream ans("answer.txt");
    vector<vector<double> > A(N, vector<double>(N, 0));
    vector<double> B(N, 0);
    vector<double> x(N);
    x[0] = M;
    ans << x[0];
    for (int i = 1; i < N; i++) {
        x[i] = x[i - 1] + 1;
        ans << " " << x[i];
    }
    mt19937_64 generator(time(nullptr));
    uniform_int_distribution<int> uid(-4, 0);
    A[0][0] = 1;
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (i != j) {
                A[i][j] = uid(generator);
                A[i][i] -= A[i][j];
                B[i] += A[i][j] * x[j];
            }
        }
        B[i] += A[i][i] * x[i];
    }
    system << N << '\n';
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            system << A[i][j] << " ";
        }
        system << B[i] << '\n';
    }
    return 0;
}
