package me.gorky.MarkovChain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskB5 {
    
    public static void go() {
        Button button = new Button("Run");

        button.setOnAction(event -> {
            try {
                List<List<Integer>> equivalenceClasses = getEquivalenceClasses();
                String result = "{";

                for (int i = 0; i < equivalenceClasses.size(); i++) {
                    result += "{" + equivalenceClasses.get(i).toString().replaceAll("\\[|\\]", "") + "}";

                    if (i != equivalenceClasses.size() - 1) {
                        result += ", ";
                    }
                }

                Main.showResult("Equivalence classes = " + result + "}");

            } catch (Exception ex) {
                Main.showAlert(ex);
            }
        });

        HBox hbox = new HBox(0);
        hbox.setPrefHeight(50);
        hbox.setPadding(new Insets(0));
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(button);

        Main.renderWindow(hbox, "Task B5");
    }

    private static List<List<Integer>> getEquivalenceClasses() throws Exception {
        int[] equivalenceClasses = new int[Main.transformationsMatrix.length];
        Arrays.fill(equivalenceClasses, -1);
        int counter = 0;

        for (int i = 0; i < Main.transformationsMatrix.length; i++) {
            if (equivalenceClasses[i] == -1) {
                equivalenceClasses[i] = counter;

                for (int j = i + 1; j < Main.transformationsMatrix.length; j++) {
                    if (equivalenceClasses[j] == -1 && TaskB4.checkIfCommunicant(i, j)) {
                        equivalenceClasses[j] = counter;
                    }
                }

                counter++;
            }
        }

        List<List<Integer>> ecList = new ArrayList<>();

        for (int i = 0; i < counter; i++) {
            List<Integer> tempList = new ArrayList<>();

            for (int j = 0; j < Main.transformationsMatrix.length; j++) {
                if (equivalenceClasses[j] == i) {
                    tempList.add(j);
                }
            }

            ecList.add(tempList);
        }

        return ecList;
    }

}
