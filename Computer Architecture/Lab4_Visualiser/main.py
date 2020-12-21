import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D
import numpy as np
import pandas as pd

z = pd.read_csv('result1.csv', header=None, sep=',')
x = np.linspace(0, 1, 202)
y = np.linspace(0, 1, 202)
xv, yv = np.meshgrid(x, y)
fig = plt.figure(figsize=(20, 10))
z = np.reshape(z, (-1, 202))
ax = fig.add_subplot(1, 2, 1, projection='3d')
ax.plot_surface(xv, yv, z, cmap=cm.coolwarm, linewidth=0, antialiased=False)
ax.set_title('Лабораторная работа 4', fontsize=20, pad=30)
