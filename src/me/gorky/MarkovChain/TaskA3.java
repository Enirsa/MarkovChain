package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.Arrays;

public class TaskA3 {

    private static double[][] initialDistribution;

    private static int step;

    public static void go() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label distributionTypeLabel = new Label("Initial distribution is:");
        GridPane.setConstraints(distributionTypeLabel, 0, 0);

        ChoiceBox<String> distributionTypeBox = new ChoiceBox<>();
        GridPane.setConstraints(distributionTypeBox, 1, 0);
        distributionTypeBox.getItems().addAll("uniform", "concentrated");
        distributionTypeBox.setValue("uniform");

        Label stateLabel = new Label("In state:");
        stateLabel.setVisible(false);
        GridPane.setConstraints(stateLabel, 0, 1);

        ChoiceBox<Integer> stateBox = new ChoiceBox<>();
        stateBox.setVisible(false);
        GridPane.setConstraints(stateBox, 1, 1);

        Label stepLabel = new Label("Step number:");
        GridPane.setConstraints(stepLabel, 0, 2);

        TextField stepField = new TextField();
        stepField.setPromptText("has to be an integer");
        GridPane.setConstraints(stepField, 1, 2);

        Button button = new Button("Run");
        GridPane.setConstraints(button, 1, 3);

        distributionTypeBox.setOnAction(event -> {
            String currentValue = distributionTypeBox.getValue();

            if (currentValue.equals("uniform")) {
                stateLabel.setVisible(false);
                stateBox.setVisible(false);
            } else {
                stateLabel.setVisible(true);
                stateBox.setVisible(true);
            }
        });

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            stateBox.getItems().add(i);
        }

        stateBox.setValue(0);

        button.setOnAction(event -> {
            String stepInput = stepField.getText().trim();
            String distributionType = distributionTypeBox.getValue();

            if (!stepInput.isEmpty()) {
                try {
                    if (!stepInput.matches("^\\d+$"))
                        throw new Exception("Invalid step number");

                    step = Integer.parseInt(stepInput);

                    if (step < 1) {
                        throw new Exception("Invalid step number");
                    } else if (step > 9999) {
                        throw new Exception("Step number is too big");
                    }

                    if (distributionType.equals("uniform")) {
                        formUniformInitialDistribution();
                    } else {
                        formConcentratedInitialDistribution(stateBox.getValue());
                    }

                    Main.showResult("Expected value = " + DataHelper.round(getExpectedValue(), 2));

                } catch (Exception ex) {
                    Main.showAlert(ex);
                }
            }
        });

        grid.getChildren().addAll(distributionTypeLabel, distributionTypeBox, stateLabel, stateBox, stepLabel, stepField, button);

        Main.renderWindow(grid, "Task A3");
    }

    private static void formUniformInitialDistribution() {
        initialDistribution = new double[1][Main.transformationsMatrix.length];
        Arrays.fill(initialDistribution[0], (double) 1 / Main.transformationsMatrix.length);
    }

    private static void formConcentratedInitialDistribution(int i) {
        initialDistribution = new double[1][Main.transformationsMatrix.length];
        Arrays.fill(initialDistribution[0], 0.0);
        initialDistribution[0][i] = 1.0;
    }

    private static double getExpectedValue() throws Exception {
        double[][] transformationsMatrix = MatrixHelper.matrixToPower(Main.transformationsMatrix, step);
        double[][] newDistribution = MatrixHelper.multiplyTwoMatrices(initialDistribution, transformationsMatrix);
        double expectedValue = 0.0;

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            expectedValue += newDistribution[0][i] * i;
        }

        return expectedValue;
    }

}
