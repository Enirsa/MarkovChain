package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;

public class TaskB6 {

    public static void go() {
        Button button = new Button("Run");

        button.setOnAction(event -> {
            try {
                List<Integer> absorptiveStates = getAllAbsorptiveStates();
                String result = "{" + absorptiveStates.toString().replaceAll("\\[|\\]", "") + "}";

                Main.showResult("All absorptive states = " + result);

            } catch (Exception ex) {
                Main.showAlert(ex);
            }
        });

        HBox hbox = new HBox(0);
        hbox.setPrefHeight(50);
        hbox.setPadding(new Insets(0));
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(button);

        Main.renderWindow(hbox, "Task B6");
    }

    private static List<Integer> getAllAbsorptiveStates() {
        List<Integer> absorptiveStates = new ArrayList<>();

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            if (Main.transformationsMatrix[i][i] == 1.0) {
                absorptiveStates.add(i);
            }
        }

        return absorptiveStates;
    }

}
