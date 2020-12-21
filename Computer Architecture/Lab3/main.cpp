#include <cstdio>
#include <random>
#include <ctime>
#include <mpi.h>
#include <fstream>
#include <iostream>
#include <chrono>

const int N = 400;

using namespace std;

int main(int argc, char **argv) {
    int map[N];
    double A[N][N], b[N], c[N], x[N], sum = 0.0;
    MPI_Init(&argc, &argv);
    int process_rank, process_size;
    MPI_Comm_rank(MPI_COMM_WORLD, &process_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &process_size);
    if (process_rank == 0) {
        mt19937_64 generator(time(nullptr));
        uniform_int_distribution<int> uid(-4, 0);
        A[0][0] = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    A[i][j] = uid(generator);
                    A[i][i] -= A[i][j];
                    b[i] += A[i][j] * j;
                }
            }
            b[i] += A[i][i] * i;
        }
        ofstream out("test.txt");
        out << "Matrix A:\n";
        for (auto &i : A) {
            for (double j : i) {
                out << j << " ";
            }
            out << '\n';
        }
        out << "Vector b:\n";
        for (double i : b) {
            out << i << " ";
        }
        out << '\n';
    }
    MPI_Barrier(MPI_COMM_WORLD);
    auto begin1 = chrono::steady_clock::now();
    MPI_Bcast(&A[0][0], N * N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Bcast(b, N, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    for (int i = 0; i < N; i++) {
        map[i] = i % process_size;
    }
    for (int k = 0; k < N; k++) {
        MPI_Bcast(&A[k][k], N - k, MPI_DOUBLE, map[k], MPI_COMM_WORLD);
        MPI_Bcast(&b[k], 1, MPI_DOUBLE, map[k], MPI_COMM_WORLD);
        for (int i = k + 1; i < N; i++) {
            if (map[i] == process_rank) {
                c[i] = A[i][k] / A[k][k];
            }
        }
        for (int i = k + 1; i < N; i++) {
            if (map[i] == process_rank) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = A[i][j] - (c[i] * A[k][j]);
                }
                b[i] = b[i] - (c[i] * b[k]);
            }
        }
    }
    auto end1 = chrono::steady_clock::now();
    MPI_Barrier(MPI_COMM_WORLD);
    if (process_rank == 0) {
        auto begin2 = chrono::steady_clock::now();
        x[N - 1] = b[N - 1] / A[N - 1][N - 1];
        for (int i = N - 2; i >= 0; i--) {
            sum = 0;
            for (int j = i + 1; j < N; j++) {
                sum = sum + A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        auto end2 = chrono::steady_clock::now();
        ofstream out("output.txt");
        out << "The solution is:\n";
        for (int i = 0; i < N; i++) {
            out << "x" << i << " = " << x[i] << '\n';
        }
        cout << "MPI Time: " << chrono::duration_cast<chrono::milliseconds>(end1 - begin1).count() / 1000.0 << '\n';
        cout << "Back substitution time: "
             << chrono::duration_cast<chrono::milliseconds>(end2 - begin2).count() / 1000.0
             << '\n';
    }
    MPI_Finalize();
    return (0);


}