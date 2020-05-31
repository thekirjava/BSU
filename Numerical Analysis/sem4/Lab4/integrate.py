import math


def function(x):
    return (math.e ** (-(x ** 2))) * math.sin(x ** 2)


def weight(x):
    return math.sqrt(1 - x ** 2)


def quad_formula(a, b, n):
    ans = function(a)
    h = (b - a) / (n - 1)
    for i in range(1, n):
        ans += 2 * function(a + i * h) * weight(a + i * h)
    ans += function(b)
    ans *= h / 2
    return ans


wolfram_precount = 0.235606
precision = 6
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
A = [0.05750944903191309, 0.1963495408493621, 0.3351896326668111, 0.3926990816987241, 0.3351896326668105,
     0.196349540849362, 0.05750944903191318]
gauss_roots = [-0.9238795325112867561281831893972697602563805119401233730436,
               -0.7071067811865475244008443621048669961792062400138293572002,
               -0.3826834323650897717284599840303986707243745773259075570073, 0.,
               0.3826834323650897717284599840303986707243745773259075569883,
               0.7071067811865475244008443621048484711357463788684136230437,
               0.9238795325112867561281831893969270296270346024990809586718]
gauss = 0.0
for i in range(7):
    gauss += A[i] * function(gauss_roots[i])
print("Integrate with trapeze =", double, sep=" ")
print("Reached precision =", math.fabs(wolfram_precount - double), sep=" ")
print("Iterations =", counts, sep=" ")
print("Gauss formula =", gauss, sep=" ")
print("Reached gauss precision =", math.fabs(wolfram_precount - gauss), sep=" ")
