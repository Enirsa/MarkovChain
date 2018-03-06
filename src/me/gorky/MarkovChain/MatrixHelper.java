package me.gorky.MarkovChain;

import java.util.Arrays;

public final class MatrixHelper {

    private MatrixHelper() {
    }

    public static void printMatrix(double[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                String separator = (j == M[i].length - 1) ? "" : " ";
                System.out.print(M[i][j] + separator);
            }

            System.out.print(System.lineSeparator());
        }
    }

    public static double[][] multiplyTwoMatrices(double[][] A, double[][] B) throws Exception {
        if (!validateMatrix(A) || !validateMatrix(B))
            throw new Exception("Invalid or empty matrix");

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows)
            throw new Exception("Matrices cannot be multiplied");

        double[][] C = new double[aRows][bColumns];

        for (int i = 0; i < C.length; i++) {
            Arrays.fill(C[i], 0.0);
        }

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    public static double[][] matrixToPower(double[][] M, int p) throws Exception {
        if (p < 0)
            throw new Exception("Invalid power");

        if (!validateMatrix(M))
            throw new Exception("Invalid or empty matrix");

        double[][] C = new double[M.length][M[0].length];

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[0].length; j++) {
                C[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }

        for (int i = 0; i < p; i++) {
            C = multiplyTwoMatrices(C, M);
        }

        return C;
    }

    public static boolean validateTransformationsMatrix(double[][] M) {
        if (!validateMatrix(M))
            return false;

        for (int i = 0; i < M.length; i++) {
            double rowSum = 0.0;

            for (int j = 0; j < M[0].length; j++) {
                if (M[i][j] < 0)
                    return false;

                rowSum += M[i][j];
            }

            if (rowSum != 1.0)
                return false;
        }

        return true;
    }

    private static boolean validateMatrix(double[][] M) {
        if (M.length == 0 || M[0].length == 0)
            return false;

        for (int i = 1; i < M.length; i++) {
            if (M[i].length != M[0].length)
                return false;
        }

        return true;
    }

}
