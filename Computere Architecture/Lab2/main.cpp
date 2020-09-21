#include <iostream>
#include <mpi.h>
#include <chrono>
#include <fstream>
#include <vector>

using namespace std;

int main(int argc, char *argv[]) {
    ifstream in("input.txt");
    ofstream out("output.txt");
    int process_rank, process_count;
    auto t1 = chrono::steady_clock::now();
    MPI_Init(&argc, &argv);
    auto t2 = chrono::steady_clock::now();
    MPI_Comm_size(MPI_COMM_WORLD, &process_count);
    MPI_Comm_rank(MPI_COMM_WORLD, &process_rank);
    int N, M, K;
    in >> N >> M >> K;
    int localN = max(N / process_count, 1);

    int A_size = N * M;
    int a_size = min(A_size, localN * M);
    int B_size = M * K;
    int C_size = N * K;
    int c_size = min(C_size, localN * K);
    auto *A = new double[A_size];
    auto *a = new double[a_size];
    auto *B = new double[B_size];
    auto *C = new double[C_size];
    auto *c = new double[c_size];

    if (process_rank == 0) {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++) {
                in >> A[i * M + j];
            }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < K; j++) {
                in >> B[i * K + j];
            }
        }
    }
    double time_start = MPI_Wtime();
    MPI_Bcast(B, B_size, MPI_DOUBLE, 0, MPI_COMM_WORLD);
    MPI_Scatter(A, a_size, MPI_DOUBLE, a, a_size, MPI_DOUBLE, 0,
                MPI_COMM_WORLD);
    auto t3 = MPI_Wtime();
    for (int i = 0; i < localN; i++) {
        for (int j = 0; j < K; j++) {
            c[i * K + j] = 0;
            for (int k = 0; k < M; k++)
                c[i * K + j] += a[i * M + k] * B[k * K + j];
        }

    }
    auto t4 = MPI_Wtime();
    MPI_Gather(c, c_size, MPI_DOUBLE, C, c_size, MPI_DOUBLE, 0,
               MPI_COMM_WORLD);
    double time_end = MPI_Wtime();

    //вывод результатов
    if (process_rank == 0) {
        for (auto i = 0; i < N; i++) {
            for (auto j = 0; j < K; j++) {
                out << C[i * K + j] << " ";
            }
            out << '\n';
        }
        auto dur = t2 - t1;

        cout << "Init: " << chrono::duration_cast<chrono::milliseconds>(dur).count() << std::endl;
        std::cout << t4 - t3 << std::endl;
        std::cout << time_end - time_start;
    }
    MPI_Finalize(); //завершение работы MPI
    return 0;
}