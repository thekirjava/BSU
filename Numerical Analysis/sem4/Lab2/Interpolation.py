import numpy as np
import matplotlib.pyplot as plt
import math
from decimal import *


def function(x):
    return Decimal(x ** 2 + 2 * math.sin(10 * x))


def lagrange_polynomial(x, nodes):
    ans = Decimal(0)
    for lagrange_i in range(len(nodes)):
        basis = Decimal(1)
        for lagrange_j in range(len(nodes)):
            if lagrange_i != lagrange_j:
                basis *= Decimal((x - nodes[lagrange_j])) / Decimal((nodes[lagrange_i] - nodes[lagrange_j]))
        ans += function(nodes[lagrange_i]) * basis
    return ans


a = -2
b = 2
N = 60
equidistant_nodes = np.linspace(a, b, N)
chebyshev_nodes = []
for i in range(1, N + 1):
    chebyshev_nodes.append((a + b) / 2 + 0.5 * (b - a) * math.cos((2 * i - 1) / (2 * N) * math.pi))
chebyshev_nodes.reverse()
plot_x = np.linspace(a, b, 1000)
function_y = []
equidistant_y = []
chebyshev_y = []
equidistant_norm = 0.0
chebyshev_norm = 0.0
for i in plot_x:
    function_y.append(function(i))
    equidistant_P = lagrange_polynomial(i, equidistant_nodes)
    chebyshev_P = lagrange_polynomial(i, chebyshev_nodes)
    equidistant_y.append(equidistant_P)
    chebyshev_y.append(chebyshev_P)
    equidistant_norm = max(abs(function(i) - equidistant_P), equidistant_norm)
    chebyshev_norm = max(abs(function(i) - chebyshev_P), chebyshev_norm)
plt.plot(plot_x, function_y, label="Function")
plt.plot(plot_x, equidistant_y, label="Equidistant nodes")
plt.plot(plot_x, chebyshev_y, label="Chebyshev nodes")
plt.legend()
plt.xlabel("x")
plt.ylabel("y")
plt.ylim(-5, 10)
plt.show()
print("Norm for equidistant nodes is " + str(equidistant_norm))
print("Norm for Chebyshev nodes is " + str(chebyshev_norm))
