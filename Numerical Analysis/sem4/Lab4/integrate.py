import math


def function(x):
    return (math.e ** (-(x ** 2))) * math.sin(x ** 2)


def quad_formula(a, b, n):
    ans = function(a)
    h = (b - a) / (n - 1)
    for i in range(1, n):
        ans += 2 * function(a + i * h)
    ans += function(b)
    ans *= h / 2
    return ans


precision = 4
left = -1
right = 1
TETA = 1 / 3
EPSILON = 10 ** -precision
n0 = 10
cur = quad_formula(left, right, n0)
double = quad_formula(left, right, 2 * n0)
counts = 2
while TETA * math.fabs(cur - double) >= EPSILON:
    n0 *= 2
    cur = double
    double = quad_formula(left, right, 2 * n0)
    counts += 1
A = [0.0575094, 0.196349, 0.33519, 0.392698, 0.33519, 0.19635, 0.0575094]
nast_roots = [-0.92388, -0.707107, -0.382683, 0, 0.382683, 0.707107, 0.92388]
nast = 0.0
for i in range(7):
    nast += A[i] * function(nast_roots[i])
print(double)
print(nast)
print(counts)
