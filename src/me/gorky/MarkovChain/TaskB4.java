package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TaskB4 {

    private static int stateI, stateJ;

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

                String result = checkIfCommunicant() ? "" : "not ";
                Main.showResult("States " + stateI + " and " + stateJ + " are " + result + "communicant");

            } catch (Exception ex) {
                Main.showAlert(ex);
            }
        });

        grid.getChildren().addAll(stateILabel, stateIBox, stateJLabel, stateJBox, button);

        Main.renderWindow(grid, "Task B4");
    }

    public static boolean checkIfCommunicant(int stateI, int stateJ) throws Exception {
        TaskB4.stateI = stateI;
        TaskB4.stateJ = stateJ;
        return checkIfCommunicant();
    }

    private static boolean checkIfCommunicant() throws Exception {
        return TaskB1.checkIfReachable(stateI, stateJ) && TaskB1.checkIfReachable(stateJ, stateI);
    }

}
