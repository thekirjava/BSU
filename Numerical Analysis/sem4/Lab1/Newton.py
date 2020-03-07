import scipy.integrate
import random
import numpy as np


def function(x, i):
    return ((1 - x) ** 2) * (1 + x) * (x ** i)


def g_counter(size):
    ans = []
    for i in range(2 * size + 2):
        ans.append(scipy.integrate.quad(function, -1, 1, i)[0])
    return ans


def jacobian(v, size):
    ans = np.array([[float(0) for i in range(2 * size + 2)] for i in range(2 * size + 2)])
    ans[0][0] = 1
    ans[0][2] = 1
    for i in range(1, 2 * size + 2):
        for j in range(2 * size + 2):
            if j == 0:
                ans[i][j] = ans[i - 1][j] * v[1]
            elif j == 1:
                ans[i][j] = v[0] * i * ans[i - 1][j - 1]
            elif j == 2:
                ans[i][j] = ans[i - 1][j] * v[3]
            else:
                ans[i][j] = v[2] * i * ans[i - 1][j - 1]
    return ans


def f(x, b, size):
    ans = np.array([float(0) for i in range(2 * size + 2)])
    for i in range(len(ans)):
        for j in range(0, len(x), 2):
            ans[i] += x[j] * pow(x[j + 1], i)
        ans[i] -= b[i]
    return ans


m = 1
g = np.array(g_counter(m))
Ax = np.array([random.uniform(-1, 1) for i in range(len(g))])
print(Ax)
S = np.inf
eps = pow(10, -10)
k = 0
while S > eps:
    omega = jacobian(Ax, m)
    delta_x = np.linalg.solve(omega, f(Ax, g, m))
    S = np.linalg.norm(delta_x)
    Ax -= delta_x
    k += 1
    print(S)
print(Ax)
