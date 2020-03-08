import scipy.integrate
import random
import numpy as np
import matplotlib.pyplot as plt


def function(x, i):
    return ((1 - x) ** 2) * (1 + x) * (x ** i)


def g_counter(size):
    ans = []
    for i in range(2 * size + 2):
        ans.append(scipy.integrate.quad(function, -1, 1, i)[0])
    return ans


def jacobi_matrix(v, size):
    ans = np.array([[float(0) for i in range(2 * size + 2)] for i in range(2 * size + 2)])
    for i in range(0, 2 * size + 2, 2):
        ans[0][i] = 1
    for i in range(1, 2 * size + 2):
        for j in range(2 * size + 2):
            if j % 2 == 0:
                ans[i][j] = ans[i - 1][j] * v[j + 1]
            else:
                ans[i][j] = v[j - 1] * i * ans[i - 1][j - 1]
    return ans


def f(x, b, size):
    ans = np.array([float(0) for i in range(2 * size + 2)])
    for i in range(len(ans)):
        for j in range(0, len(x), 2):
            ans[i] += x[j] * (x[j + 1] ** i)
        ans[i] -= b[i]
    return ans


m = 1
g = np.array(g_counter(m))
Ax = np.array([random.uniform(-1, 1) for i in range(len(g))])
print(Ax)
S = np.inf
eps = 10 ** -10
k = 0
convergence = []
while S > eps:
    omega = jacobi_matrix(Ax, m)
    delta_x = np.linalg.solve(omega, f(Ax, g, m))
    S = np.linalg.norm(delta_x)
    Ax -= delta_x
    k += 1
    convergence.append(S)
print(Ax)
plt.plot([int(i) for i in range(1, k + 1)], convergence)
plt.xlabel("Итерационные шаги")
plt.ylabel("Норма приближения")
plt.yscale("log")
plt.show()
