#include <algorithm>
#include <chrono>
#include <cmath>
#include <fstream>
#include <iostream>
#include <random>
#include <vector>

#include "mpi.h"

double func(double x, double y) {
    return x * y;
}

double funcLeft(double y) {
    return y * y;
}

double funcRight(double y) {
    return std::sin(y);
}

double funcDown(double x) {
    return x * x * x;
}

double funcUp(double x) {
    return x * x;
}

const int N = 200;
const double EPSILON = 1e-6;

void Decomposition1() {
    int processRank = 0;
    int processCount = 0;
    MPI_Status mpiStatus;
    MPI_Comm_rank(MPI_COMM_WORLD, &processRank);
    MPI_Comm_size(MPI_COMM_WORLD, &processCount);
    double step = 1.0 / (N + 1.0);
    if (N % processCount != 0) {
        throw std::runtime_error("invalid n and/or proc count");
    }
    const int processHeight = N / processCount;
    std::vector<std::vector<double>> u(processHeight + 2, std::vector<double>(N + 2));
    if (processRank == 0) {
        for (int j = 1; j <= N; j++) {
            u.front()[j] = funcDown(step * (j));
        }
    }
    if (processRank == processCount - 1) {
        for (int j = 1; j <= N; j++) {
            u.back()[j] = funcUp(step * (j));
        }
    }
    for (int i = 0; i <= processHeight + 1; i++) {
        u[i].front() = funcLeft(step * (processRank * processHeight + i));
        u[i].back() = funcRight(step * (processRank * processHeight + i));
    }
    MPI_Barrier(MPI_COMM_WORLD);
    auto start = std::chrono::steady_clock::now();
    int count = 0;
    double delta = 0;
    do {
        if (processRank != 0) {
            MPI_Sendrecv(u[1].data() + 1, N, MPI_DOUBLE, processRank - 1, 98,
                         u[0].data() + 1, N, MPI_DOUBLE, processRank - 1, 99,
                         MPI_COMM_WORLD, &mpiStatus);
        }
        if (processRank != processCount - 1) {
            MPI_Sendrecv(
                    u[processHeight].data() + 1, N, MPI_DOUBLE, processRank + 1, 99,
                    u[processHeight + 1].data() + 1, N, MPI_DOUBLE, processRank + 1, 98,
                    MPI_COMM_WORLD, &mpiStatus);
        }
        double maxDelta = 0;
        for (int i = 1; i <= processHeight; i++) {
            for (int j = 1; j <= N; j++) {
                double prev = u[i][j];
                u[i][j] = (u[i - 1][j] + u[i + 1][j] + u[i][j - 1] + u[i][j + 1] -
                           step * step * func(step * (j), step * (processRank * processHeight + i))) / 4.0;
                maxDelta = std::fmax(maxDelta, std::fabs(u[i][j] - prev));
            }
        }
        MPI_Allreduce(&maxDelta, &delta, 1, MPI_DOUBLE, MPI_MAX, MPI_COMM_WORLD);
        ++count;
    } while (delta > EPSILON);
    auto end = std::chrono::steady_clock::now();
    MPI_Barrier(MPI_COMM_WORLD);
    if (processRank != 0) {
        for (int i = 0; i < processHeight + 2; i++) {
            MPI_Send(u[i].data(), N + 2, MPI_DOUBLE, 0, 100, MPI_COMM_WORLD);
        }
        return;
    }
    auto duration = end - start;
    std::cout << "1D decomposition:\t";
    std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(duration).count() << "ms\t";
    std::cout << count << " iterations\n";
    std::ofstream out("result1.csv");
    out << std::fixed;
    for (int i = 0; i < N + 2; i++) {
        out << u.front()[i];
        if (i + 1 != N + 2) {
            out << ",";
        }
    }
    out << "\n";
    for (int process = 0; process < processCount; ++process) {
        if (process != 0) {
            for (int i = 0; i < processHeight + 2; i++) {
                MPI_Recv(u[i].data(), N + 2, MPI_DOUBLE, process, 100, MPI_COMM_WORLD, &mpiStatus);
            }
        }
        for (int i = 1; i <= processHeight; i++) {
            for (int j = 0; j < N + 2; j++) {
                out << u[i][j];
                if (j + 1 != N + 2) {
                    out << ",";
                }
            }
            out << "\n";
        }
    }
    for (int i = 0; i < N + 2; i++) {
        out << u.back()[i];
        if (i + 1 != N + 2) {
            out << ",";
        }
    }
    out << "\n";
}

void Decomposition2() {
    int processRank = 0;
    int processCount = 0;
    MPI_Status status;
    MPI_Comm_rank(MPI_COMM_WORLD, &processRank);
    MPI_Comm_size(MPI_COMM_WORLD, &processCount);
    double step = 1.0 / (N + 1.0);
    int processCountX = 0, processCountY = 0;
    if (processCount % static_cast<int>(std::sqrt(processCount)) == 0) {
        processCountX = std::sqrt(processCount);
        processCountY = std::sqrt(processCount);
    } else if (processCount % 2 == 0) {
        processCountX = processCount / 2;
        processCountY = 2;
    } else {
        processCountX = processCount;
        processCountY = 1;
    }
    MPI_Bcast(&processCountX, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&processCountY, 1, MPI_INT, 0, MPI_COMM_WORLD);
    if ((processCountX * processCountY != processCount) ||
        (N % processCountX != 0) || (N % processCountY != 0)) {
        throw std::runtime_error("invalid n and/or proc count");
    }
    const int processHeight = N / processCountY;
    const int processWidth = N / processCountX;
    const int processRankX = processRank % processCountX;
    const int processRankY = processRank / processCountX;
    std::vector<std::vector<double>> u(processHeight + 2, std::vector<double>(processWidth + 2));
    if (processRankY == 0) {
        for (int j = 0; j <= processWidth + 1; j++) {
            u.front()[j] = funcDown(step * (processRankX * processWidth + j));
        }
    }
    if (processRankY == processCountY - 1) {
        for (int j = 0; j <= processWidth + 1; j++) {
            u.back()[j] = funcUp(step * (processRankX * processWidth + j));
        }
    }
    if (processRankX == 0) {
        for (int i = 0; i <= processHeight + 1; i++) {
            u[i].front() = funcLeft(step * (processRankY * processHeight + i));
        }
    }
    if (processRankX == processCountX - 1) {
        for (int i = 0; i <= processHeight + 1; i++) {
            u[i].back() = funcRight(step * (processRankY * processHeight + i));
        }
    }
    MPI_Barrier(MPI_COMM_WORLD);
    auto start = std::chrono::steady_clock::now();
    int count = 0;
    double delta = 0;
    std::vector<double> sendBuffer(processHeight);
    std::vector<double> recvBuffer(processHeight);
    do {
        if (processRankY != 0) {
            MPI_Sendrecv(
                    u[1].data() + 1, processWidth, MPI_DOUBLE, processRank - processCountX, 198,
                    u[0].data() + 1, processWidth, MPI_DOUBLE, processRank - processCountX, 199,
                    MPI_COMM_WORLD, &status);
        }
        if (processRankY != processCountY - 1) {
            MPI_Sendrecv(
                    u[processHeight].data() + 1, processWidth, MPI_DOUBLE, processRank + processCountX, 199,
                    u[processHeight + 1].data() + 1, processWidth, MPI_DOUBLE, processRank + processCountX, 198,
                    MPI_COMM_WORLD, &status);
        }
        if (processRankX != 0) {
            for (int i = 0; i < processHeight; i++) {
                sendBuffer[i] = u[i + 1][1];
            }
            MPI_Sendrecv(sendBuffer.data(), processHeight, MPI_DOUBLE, processRank - 1, 200,
                         recvBuffer.data(), processHeight, MPI_DOUBLE, processRank - 1, 201,
                         MPI_COMM_WORLD, &status);
            for (int i = 0; i < processHeight; i++) {
                u[i + 1][0] = recvBuffer[i];
            }
        }
        if (processRankX != processCountX - 1) {
            for (int i = 0; i < processHeight; i++) {
                sendBuffer[i] = u[i + 1][processWidth];
            }
            MPI_Sendrecv(sendBuffer.data(), processHeight, MPI_DOUBLE, processRank + 1, 201,
                         recvBuffer.data(), processHeight, MPI_DOUBLE, processRank + 1, 200,
                         MPI_COMM_WORLD, &status);
            for (int i = 0; i < processHeight; i++) {
                u[i + 1][processWidth + 1] = recvBuffer[i];
            }
        }
        double maxDelta = 0;
        for (int i = 1; i <= processHeight; i++) {
            for (int j = 1; j <= processWidth; j++) {
                double prev = u[i][j];
                u[i][j] = (u[i - 1][j] + u[i + 1][j] + u[i][j - 1] + u[i][j + 1] -
                           step * step * func(step * (j), step * (processRankY * processHeight + i))) / 4.0;
                maxDelta = std::max(maxDelta, std::abs(u[i][j] - prev));
            }
        }
        MPI_Allreduce(&maxDelta, &delta, 1, MPI_DOUBLE, MPI_MAX, MPI_COMM_WORLD);
        ++count;
    } while (delta > EPSILON);
    auto end = std::chrono::steady_clock::now();
    MPI_Barrier(MPI_COMM_WORLD);
    if (processRank != 0) {
        for (int i = 0; i < processHeight + 2; i++) {
            MPI_Send(u[i].data(), processWidth + 2, MPI_DOUBLE, 0, 202, MPI_COMM_WORLD);
        }
        return;
    }
    auto duration = end - start;
    std::cout << "2D decomposition:\t";
    std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(duration).count() << "ms\t";
    std::cout << count << " iterations\n";
    std::ofstream out("result2.csv");
    out << std::fixed;
    std::vector<std::vector<double>> ans(N + 2, std::vector<double>(N + 2));
    for (int process = 0; process < processCount; process++) {
        int curProcessX = process % processCountX;
        int curProcessY = process / processCountX;
        if (process != 0) {
            for (int i = 0; i < processHeight + 2; i++) {
                MPI_Recv(u[i].data(), processWidth + 2, MPI_DOUBLE, process, 202, MPI_COMM_WORLD, &status);
            }
        }
        if (curProcessX == 0) {
            for (int i = 1; i <= processHeight; i++) {
                ans[curProcessY * processHeight + i].front() = u[i].front();
            }
        }
        if (curProcessX == processCountX - 1) {
            for (int i = 1; i <= processHeight; i++) {
                ans[curProcessY * processHeight + i].back() = u[i].back();
            }
        }
        if (curProcessY == 0) {
            std::copy(u.front().begin(), u.front().end(),
                      ans.front().begin() + curProcessX * processWidth);
        }
        if (curProcessY == processCountY - 1) {
            std::copy(u.back().begin(), u.back().end(),
                      ans.back().begin() + curProcessX * processWidth);
        }
        for (int i = 1; i <= processHeight; i++) {
            std::copy(u[i].begin(), u[i].end(),
                      ans[curProcessY * processHeight + i].begin() +
                      curProcessX * processWidth);
        }
    }
    for (int i = 0; i < N + 2; i++) {
        for (int j = 0; j < N + 2; j++) {
            out << ans[i][j];
            if (j + 1 != N + 2) {
                out << ",";
            }
        }
        out << "\n";
    }
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);       /* initialize MPI system */
    Decomposition1();
    Decomposition2();
    MPI_Finalize();
}

