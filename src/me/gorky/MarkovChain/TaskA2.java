package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.Arrays;

public class TaskA2 {

    private static double[][] initialDistribution;

    private static int step;

    public static void go() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label initialDistributionLabel = new Label("Initial distribution:");
        GridPane.setConstraints(initialDistributionLabel, 0, 0);

        TextField initialDistributionField = new TextField();
        initialDistributionField.setPromptText(Main.transformationsMatrix.length + " floating-point numbers");
        GridPane.setConstraints(initialDistributionField, 1, 0);

        Label stepLabel = new Label("Step number:");
        GridPane.setConstraints(stepLabel, 0, 1);

        TextField stepField = new TextField();
        stepField.setPromptText("has to be an integer");
        GridPane.setConstraints(stepField, 1, 1);

        Button button = new Button("Run");
        GridPane.setConstraints(button, 1, 2);

        button.setOnAction(event -> {
            String stepInput = stepField.getText().trim();
            String initialDistributionInput = initialDistributionField.getText().trim();

            if (!stepInput.isEmpty() && !initialDistributionInput.isEmpty()) {
                try {
                    initialDistribution = DataHelper.parseInitialDistribution(initialDistributionInput);

                    if (!stepInput.matches("^\\d+$"))
                        throw new Exception("Invalid step number");

                    step = Integer.parseInt(stepInput);

                    if (step < 1) {
                        throw new Exception("Invalid step number");
                    } else if (step > 9999) {
                        throw new Exception("Step number is too big");
                    }

                    double[] newDistribution = getNewDistribution()[0];

                    for (int i = 0; i < newDistribution.length; i++) {
                        newDistribution[i] = DataHelper.round(newDistribution[i], 3);
                    }

                    String result = "(" + Arrays.toString(newDistribution).replaceAll("\\[|\\]", "") + ")";
                    Main.showResult("New distribution = " + result);

                } catch (Exception ex) {
                    Main.showAlert(ex);
                }
            }
        });

        grid.getChildren().addAll(initialDistributionLabel, initialDistributionField, stepLabel, stepField, button);

        Main.renderWindow(grid, "Task A2");
    }

    private static double[][] getNewDistribution() throws Exception {
        double[][] transformationsMatrix = MatrixHelper.matrixToPower(Main.transformationsMatrix, step);
        return MatrixHelper.multiplyTwoMatrices(initialDistribution, transformationsMatrix);
    }

}
