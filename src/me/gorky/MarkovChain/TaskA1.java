package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TaskA1 {

    private static int stateI, stateJ, step;

    public static void go() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label stateILabel = new Label("State i:");
        GridPane.setConstraints(stateILabel, 0, 0);

        ChoiceBox<Integer> stateIBox = new ChoiceBox<>();
        GridPane.setConstraints(stateIBox, 1, 0);

        Label stateJLabel = new Label("State j:");
        GridPane.setConstraints(stateJLabel, 0, 1);

        ChoiceBox<Integer> stateJBox = new ChoiceBox<>();
        GridPane.setConstraints(stateJBox, 1, 1);

        Label stepsLabel = new Label("Amount of steps:");
        GridPane.setConstraints(stepsLabel, 0, 2);

        TextField stepsField = new TextField();
        stepsField.setPromptText("has to be an integer");
        GridPane.setConstraints(stepsField, 1, 2);

        Button button = new Button("Run");
        GridPane.setConstraints(button, 1, 3);

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            stateIBox.getItems().add(i);
            stateJBox.getItems().add(i);
        }

        stateIBox.setValue(0);
        stateJBox.setValue(0);

        button.setOnAction(event -> {
            String stepsInput = stepsField.getText().trim();

            if (!stepsInput.isEmpty()) {
                try {
                    if (!stepsInput.matches("^\\d+$"))
                        throw new Exception("Invalid amount of steps");

                    int steps = Integer.parseInt(stepsInput);

                    if (steps < 1) {
                        throw new Exception("Invalid amount of steps");
                    } else if (steps > 9999) {
                        throw new Exception("Amount of steps is too big");
                    } else {
                        stateI = stateIBox.getValue();
                        stateJ = stateJBox.getValue();
                        step = steps;
                        Main.showResult("Probability = " + DataHelper.round(getProbability(), 4));
                    }

                } catch (Exception ex) {
                    Main.showAlert(ex);
                }
            }
        });

        grid.getChildren().addAll(stateILabel, stateIBox, stateJLabel, stateJBox, stepsLabel, stepsField, button);

        Main.renderWindow(grid, "Task A1");
    }

    private static double getProbability() throws Exception {
        return MatrixHelper.matrixToPower(Main.transformationsMatrix, step)[stateI][stateJ];
    }

}
