package me.gorky.MarkovChain;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public final class DataHelper {

    private DataHelper() {
    }

    public static double[][] getTransformationsMatrix(File file) throws Exception {
        List<double[]> al = parseAdjacencyList(file);
        double[][] M = makeTransformationsMatrix(al);

        if (!MatrixHelper.validateTransformationsMatrix(M))
            throw new Exception("Incorrect data");

        return M;
    }

    private static List<double[]> parseAdjacencyList(File file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            if (line == null)
                throw new Exception("Selected file is empty");

            List<double[]> adjacencyList = new ArrayList<>();

            while (line != null) {
                line = line.trim().replaceAll(",", ".");

                if (!line.isEmpty()) {
                    if (!line.matches("^\\d{1,2}\\s+[0-1]\\.\\d+(\\s+\\d{1,2}\\s+[0-1]\\.\\d+)*$"))
                        throw new Exception("Invalid adjacency list format");

                    adjacencyList.add(Arrays.asList(line.split("\\s+"))
                            .stream()
                            .mapToDouble(Double::parseDouble)
                            .toArray());
                }

                line = reader.readLine();
            }

            return adjacencyList;
        }
    }

    private static double[][] makeTransformationsMatrix(List<double[]> adjacencyList) throws Exception {
        double[][] transformationsMatrix = new double[adjacencyList.size()][adjacencyList.size()];
        int maxIndex = adjacencyList.size() - 1;

        for (int i = 0; i < transformationsMatrix.length; i++) {
            Arrays.fill(transformationsMatrix[i], 0.0);
        }

        for (int i = 0; i < adjacencyList.size(); i++) {
            for (int k = 0; k < adjacencyList.get(i).length; k += 2) {
                int j = (int) adjacencyList.get(i)[k];

                if (j > maxIndex)
                    throw new Exception("Invalid adjacency list");

                transformationsMatrix[i][j] = adjacencyList.get(i)[k + 1];
            }
        }

        return transformationsMatrix;
    }

    public static double[][] parseInitialDistribution(String str) throws Exception {
        str = str.trim().replaceAll(",", ".").replaceAll(";", " ");

        if (!str.matches("^[0-1](\\.\\d+)?(\\s+[0-1](\\.\\d+)?)*$"))
            throw new Exception("Invalid initial distribution format");

        double[][] initialDistribution = new double[1][];
        initialDistribution[0] = Arrays.stream(str.split("\\s+"))
                .mapToDouble(Double::parseDouble)
                .toArray();

        if (!validateInitialDistribution(initialDistribution))
            throw new Exception("Invalid initial distribution");

        return initialDistribution;
    }

    private static boolean validateInitialDistribution(double[][] initialDistribution) {
        if (initialDistribution.length != 1 || initialDistribution[0].length != Main.transformationsMatrix.length)
            return false;

        double sum = 0.0;

        for (double p : initialDistribution[0]) {
            if (p < 0 || p > 1)
                return false;

            sum += p;
        }

        if (sum != 1.0)
            return false;

        return true;
    }

    public static double round(double value, int places) throws Exception {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

}
