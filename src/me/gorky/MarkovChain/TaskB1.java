package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TaskB1 {

    private static int stateI, stateJ;

    public static void go() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label stateILabel = new Label("From state:");
        GridPane.setConstraints(stateILabel, 0, 0);

        ChoiceBox<Integer> stateIBox = new ChoiceBox<>();
        GridPane.setConstraints(stateIBox, 1, 0);

        Label stateJLabel = new Label("To state:");
        GridPane.setConstraints(stateJLabel, 0, 1);

        ChoiceBox<Integer> stateJBox = new ChoiceBox<>();
        GridPane.setConstraints(stateJBox, 1, 1);

        Button button = new Button("Run");
        GridPane.setConstraints(button, 1, 2);

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            stateIBox.getItems().add(i);
            stateJBox.getItems().add(i);
        }

        stateIBox.setValue(0);
        stateJBox.setValue(0);

        button.setOnAction(event -> {
            try {
                stateI = stateIBox.getValue();
                stateJ = stateJBox.getValue();

                String result = checkIfReachable() ? "" : "not ";
                Main.showResult("State " + stateJ + " is " + result + "reachable from state " + stateI);

            } catch (Exception ex) {
                Main.showAlert(ex);
            }
        });

        grid.getChildren().addAll(stateILabel, stateIBox, stateJLabel, stateJBox, button);

        Main.renderWindow(grid, "Task B1");
    }

    public static boolean checkIfReachable(int stateI, int stateJ) throws Exception {
        TaskB1.stateI = stateI;
        TaskB1.stateJ = stateJ;
        return checkIfReachable();
    }

    private static boolean checkIfReachable() throws Exception {
        double[][] transformationsMatrix = Main.transformationsMatrix;

        for (int i = 0; i < Main.transformationsMatrix.length - 1; i++) {
            if (i > 0) {
                transformationsMatrix = MatrixHelper.multiplyTwoMatrices(transformationsMatrix, Main.transformationsMatrix);
            }

            if (transformationsMatrix[stateI][stateJ] > 0)
                return true;
        }

        return false;
    }

}
