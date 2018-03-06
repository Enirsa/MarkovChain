package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.List;

public class TaskB3 {

    private static int state;

    public static void go() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label stateLabel = new Label("State:");
        GridPane.setConstraints(stateLabel, 0, 0);

        ChoiceBox<Integer> stateBox = new ChoiceBox<>();
        GridPane.setConstraints(stateBox, 1, 0);

        Button button = new Button("Run");
        GridPane.setConstraints(button, 1, 1);

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            stateBox.getItems().add(i);
        }

        stateBox.setValue(0);

        button.setOnAction(event -> {
            try {
                state = stateBox.getValue();

                String result = checkIfFundamental() ? "" : "not ";
                Main.showResult("State " + state + " is " + result + "fundamental");

            } catch (Exception ex) {
                Main.showAlert(ex);
            }
        });

        grid.getChildren().addAll(stateLabel, stateBox, button);

        Main.renderWindow(grid, "Task B3");
    }

    private static boolean checkIfFundamental() throws Exception {
        List<Integer> reachableStates = TaskB2.getReachableStates(state);

        for (int s : reachableStates) {
            if (!TaskB1.checkIfReachable(s, state))
                return false;
        }

        return true;
    }

}
