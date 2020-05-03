import math
import numpy as np
from matplotlib import pyplot as plt
import time


def function(x):
    return x ** 2 + 2 * math.sin(10 * x)


def divided_difference(x1, x2, x3):
    f1 = (function(x2) - function(x1)) / (x2 - x1)
    f2 = (function(x3) - function(x2)) / (x3 - x2)
    return (f2 - f1) / (x3 - x1)


def spline(alpha, beta, gamma, delta, node, x):
    return alpha + beta * (x - node) + gamma * ((x - node) ** 2) / 2 + delta * ((x - node) ** 3) / 6


a = -2.0
b = 2.0
N = 100
h = (b - a) / (N - 1)
alpha_list = [0.0]
beta_list = [0.0]
gamma_list = np.array([0.0])
delta_list = [0.0]
nodes = np.linspace(a, b, N)
A = np.array([np.array([0.0 for i in range(N - 2)]) for j in range(N - 2)])
for i in range(N - 2):
    A[i][i] = 2
    if i > 0:
        A[i][i - 1] = 0.5
    if i + 1 < N - 2:
        A[i][i + 1] = 0.5
B = np.array([])
for i in range(N - 2):
    B = np.append(B, 6 * divided_difference(nodes[i], nodes[i + 1], nodes[i + 2]))
t = time.time()
gamma_list = np.append(gamma_list, np.linalg.solve(A, B))
gamma_list = np.append(gamma_list, 0.0)
for i in range(1, N):
    beta_list.append(((function(nodes[i]) - function(nodes[i - 1])) / h))
    beta_list[i] += + (2 * gamma_list[i] + gamma_list[i - 1]) * h / 6
    delta_list.append((gamma_list[i] - gamma_list[i - 1]) / h)
    alpha_list.append(function(nodes[i]))
plot_x = np.linspace(a, b, 1000)
function_y = []
spline_y = []
pos = 1
norm = 0.0
for i in plot_x:
    if i > nodes[pos]:
        pos += 1
    f = function(i)
    s = spline(alpha_list[pos], beta_list[pos], gamma_list[pos], delta_list[pos], nodes[pos], i)
    function_y.append(f)
    spline_y.append(s)
    norm = max(norm, abs(f - s))
print("Time is -", "%.3f" % (time.time() - t), ' ')
plt.plot(plot_x, function_y, label="Function")
plt.plot(plot_x, spline_y, label="Spline")
plt.legend()
plt.xlabel("x")
plt.ylabel("y")
plt.ylim(-5, 10)
plt.show()
print("Norm is -", norm, ' ')
