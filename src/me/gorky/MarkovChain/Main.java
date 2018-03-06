package me.gorky.MarkovChain;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static Stage mainWindow;

    public static MenuBar menuBar;

    public static double[][] transformationsMatrix;

    private static final String APPLICATION_NAME = "Markov Chain Solver";

    @Override
    public void start(Stage primaryStage) {
        mainWindow = primaryStage;
        menuBar = formMenuBar();

        showFileChooserPage();
    }

    public static void showFileChooserPage() {
        menuBar.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        Button openButton = new Button("Select an input file");

        openButton.setOnAction(e -> {
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(mainWindow);

            if (file != null) {
                try {
                    transformationsMatrix = DataHelper.getTransformationsMatrix(file);
                    menuBar.setDisable(false);
                    showTaskChooserPage();

                } catch (Exception ex) {
                    showAlert(ex);
                }
            }
        });

        HBox hbox = new HBox(0);
        hbox.setPrefHeight(60);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(openButton);

        renderWindow(hbox);
    }

    public static void showTaskChooserPage() {
        Label label = new Label("Choose a task");

        HBox hbox = new HBox(0);
        hbox.setPrefHeight(60);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(label);

        renderWindow(hbox);
    }

    public static void showResult(String result) {
        showPopup("Result", result, "#4F8A10");
    }

    public static void showAlert(Exception ex) {
        showPopup("Error", ex.getMessage(), "#D8000C");
    }

    public static void showPopup(String title, String text, String color) {
        Stage alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(250);

        Label label = new Label();
        label.setText(text);
        label.setTextFill(Color.web(color));
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> alertWindow.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(7));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, closeButton);

        Scene scene = new Scene(layout);
        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }

    public static void renderWindow(Pane content) {
        renderWindow(content, "");
    }

    public static void renderWindow(Pane content, String title) {
        VBox layout = new VBox(0);
        layout.setPadding(new Insets(0));
        layout.getChildren().addAll(menuBar, content);
        layout.setPrefWidth(400);

        mainWindow.setTitle(title.equals("") ? APPLICATION_NAME : title + " - " + APPLICATION_NAME);
        mainWindow.setScene(new Scene(layout));
        mainWindow.setResizable(false);
        mainWindow.sizeToScene();
        mainWindow.show();
    }

    private static MenuBar formMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(event -> showFileChooserPage());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> Platform.exit());

        fileMenu.getItems().addAll(newFile, new SeparatorMenuItem(), exit);

        Menu taskMenu = new Menu("_Tasks");
        ToggleGroup taskToggle = new ToggleGroup();

        List<RadioMenuItem> moduleA = new ArrayList<>();
        moduleA.add(new RadioMenuItem("Task A1"));
        moduleA.add(new RadioMenuItem("Task A2"));
        moduleA.add(new RadioMenuItem("Task A3"));

        List<RadioMenuItem> moduleB = new ArrayList<>();
        moduleB.add(new RadioMenuItem("Task B1"));
        moduleB.add(new RadioMenuItem("Task B2"));
        moduleB.add(new RadioMenuItem("Task B3"));
        moduleB.add(new RadioMenuItem("Task B4"));
        moduleB.add(new RadioMenuItem("Task B5"));
        moduleB.add(new RadioMenuItem("Task B6"));

        List<List<RadioMenuItem>> modules = new ArrayList<>();
        modules.add(moduleA);
        modules.add(moduleB);

        for (int i = 0; i < modules.size(); i++) {
            for (RadioMenuItem item : modules.get(i)) {
                item.setToggleGroup(taskToggle);
                taskMenu.getItems().add(item);

                item.setOnAction(event -> {
                    for (List<RadioMenuItem> module : modules) {
                        for (RadioMenuItem _item : module) {
                            _item.setDisable(false);
                        }
                    }

                    item.setDisable(true);

                    try {
                        Class taskClass = Class.forName(item.getText().replaceAll("\\s+", ""));
                        Method goMethod = taskClass.getMethod("go");
                        goMethod.invoke(null);

                    } catch (Exception ex) {
                        showAlert(new Exception("Program structure is corrupted"));
                    }
                });
            }

            if (i != modules.size() - 1) {
                taskMenu.getItems().add(new SeparatorMenuItem());
            }
        }

        menuBar.getMenus().addAll(fileMenu, taskMenu);

        return menuBar;
    }

    private static void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Select an input file");
        fileChooser.setInitialDirectory(
                FileSystemView.getFileSystemView().getHomeDirectory()
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

}