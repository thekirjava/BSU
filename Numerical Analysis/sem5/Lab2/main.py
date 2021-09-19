from matplotlib import pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import time
import numpy as np


def f(t, x):
    return 10 * (np.pi ** 2) * np.exp(t) * np.cos(np.pi * x) + np.exp(t) * (x + np.cos(np.pi * x))


def exact_solution(t, x):
    return np.exp(t) * (x + np.cos(np.pi * x))


def up(t):
    return 0


def left(x):
    return x + np.cos(np.pi * x)


def solve(N, M):
    h = 1 / N
    tau = 1 / M
    u = [[0 for _ in range(M + 1)] for _ in range(N + 1)]
    u[N] = [up(t) for t in np.linspace(0, 1, M + 1)]
    for i in range(N + 1):
        u[i][0] = left(i * h)
    err = np.infty
    for j in range(M):
        t_j = tau * j
        t_j1 = t_j + tau
        u[0][j + 1] = u[0][j] + tau * (20 / h * ((u[1][j] - u[0][j]) / h - t_j) + f(t_j, 0))
        err = min(err, np.abs(exact_solution(t_j1, 0) - u[0][j + 1]))
        for i in range(1, N):
            x_i = i * h
            u[i][j + 1] = u[i][j] + tau * (10 / (h ** 2) * (u[i - 1][j] - 2 * u[i][j] + u[i + 1][j]) + f(t_j, x_i))
            err = min(err, np.abs(exact_solution(t_j1, x_i) - u[0][j + 1]))
    return u, err


def main(N):
    M = (N * 6) ** 2 + 1
    start_time = time.time()
    u, err = solve(N, M)
    end_time = time.time()
    print('N:', N)
    print('M:', M)
    print('Error:', err)
    print('Time:', end_time - start_time)
    t = np.linspace(0, 1, M + 1)
    x = np.linspace(0, 1, N + 1)
    xv, yv = np.meshgrid(x, t)
    u = np.array(u)
    u = np.transpose(u)
    fig = plt.figure(figsize=(20, 10))
    ax = fig.add_subplot(1, 2, 1, projection='3d')
    ax.set_title('Solution')
    ax.set_xlabel('$t$')
    ax.set_ylabel('$x$')
    ax.set_zlabel('$f(t, x)$')
    ax.plot_surface(xv, yv, u, linewidth=0, antialiased=False, alpha=0.9)


if __name__ == '__main__':
    main(10)
