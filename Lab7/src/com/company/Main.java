package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static class MatrixSizeException extends Exception {
        MatrixSizeException(String s) {
            super(s);
        }
    }

    private static class BiggerMatrixException extends Exception {
        BiggerMatrixException(String s) {
            super(s);
        }
    }

    private static class PathException extends Exception {
        PathException(String s) {
            super(s);
        }
    }

    private static class DetException extends Exception {
        DetException(String s) {
            super(s);
        }
    }

    private static double[] Gauss(double[][] sys) {
        for (int i = 0; i < sys.length; i++) {
            boolean ch = false;
            for (int j = i; j < sys.length; i++) {
                if (sys[j][i] != 0) {
                    double[] buf = sys[i];
                    sys[i] = sys[j];
                    sys[j] = buf;
                    ch = true;
                    break;
                }
            }
            if (ch) {
                for (int j = i + 1; j < sys[i].length; j++) {
                    sys[i][j] /= sys[i][i];
                }
                sys[i][i] = 1;
                for (int j = i + 1; j < sys.length; j++) {
                    for (int k = i + 1; k < sys[j].length; k++) {
                        sys[j][k] -= sys[j][i] * sys[i][k];
                    }
                    sys[j][i] = 0;
                }
            }
        }
        double[] ans = new double[sys.length];
        for (int i = sys.length - 1; i >= 0; i--) {
            ans[i] = sys[i][sys[i].length - 1];
            for (int j = sys[i].length - 2; j > i; j--) {
                ans[i] -= ans[j] * sys[i][j];
            }
            ans[i] /= sys[i][i];
        }
        return ans;
    }

    private static double det(double[][] sys, int str, boolean[] used) {
        double ans = 0;
        int fl = 1;
        if (str == sys.length) {
            return 1;
        }
        for (int i = 0; i < sys.length; i++) {
            if (!used[i]) {
                used[i] = true;
                ans += fl * det(sys, str + 1, used) * sys[str][i];
                fl *= -1;
                used[i] = false;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new PathException("Input must contain filename");
            }
            Scanner s = new Scanner(new File("../" + args[0]));
            int N = Integer.parseInt(s.next());
            if (N <= 0) {
                throw new MatrixSizeException("Matrix size must be positive!");
            }
            double[][] sys = new double[N][N + 1];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= N; j++) {
                    sys[i][j] = Double.parseDouble(s.next());
                }
            }
            if (det(sys, 0, new boolean[N]) == 0) {
                throw new DetException("Matrix determinant equals zero!");
            }
            if (s.hasNext()) {
                throw new BiggerMatrixException("Matrix contains more elements then was declared!");
            }
            double[] ans = Gauss(sys);

            for (int i = 0; i < N; i++) {
                System.out.println("x" + (i + 1) + " = " + ans[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
        } catch (MatrixSizeException e) {
            System.out.println(e.getMessage());
        } catch (BiggerMatrixException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Matrix don't contain enough elements!");
        } catch (PathException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Input contains NaN");
        } catch (DetException e) {
            System.out.println(e.getMessage());
        }
    }
}
