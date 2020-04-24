package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ConverterController {

    @FXML
    private ChoiceBox<String> box;

    @FXML
    private TextField field;

    @FXML
    private Button btn_exit;

    @FXML
    private Label label_t;

    @FXML
    private Label label_kg;

    @FXML
    private Label label_gr;

    ObservableList<String> choice = FXCollections.observableArrayList("Тонны", "Килограммы", "Граммы");

    @FXML
    void fieldChange(KeyEvent event) {
        String boxValue = box.getValue();
        convert(boxValue);
    }

    @FXML
    void initialize() {
        btn_exit.setOnAction(event -> returnUserCabinet(event));

        box.setValue("Тонны");
        box.setItems(choice);

        box.setOnAction(actionEvent -> {
            String boxValue = box.getValue();
            convert(boxValue);
        });

    }

    private void returnUserCabinet(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/cabinet.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Кабинет пользователя");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void convert(String value) {
        long long_num;
        double double_num;

        try {
            long_num = Long.parseLong(field.getText());
            double_num = Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            long_num = 0;
            double_num = 0.0D;
        }

        switch (value) {
            case "Тонны":
                label_t.setText(String.valueOf(long_num) + "  т");
                label_kg.setText(String.valueOf(long_num * 1000) + "  кг");
                label_gr.setText(String.valueOf(long_num * 1000000) + "  гр");
                break;
            case "Килограммы":
                if (long_num % 100 == 0) {

                    if (long_num != 0) {
                        label_t.setText(String.format("%.1f", Double.valueOf(double_num / 1000.D)) + "  т");
                    } else {
                        label_t.setText(String.format("%.0f", Double.valueOf(double_num / 1000.D)) + "  т");
                    }

                } else if (long_num % 10 == 0) {
                    label_t.setText(String.format("%.2f", Double.valueOf(double_num / 1000.D)) + "  т");
                } else {
                    label_t.setText(String.format("%.3f", Double.valueOf(double_num / 1000.D)) + "  т");
                }
                label_kg.setText(String.valueOf(long_num) + "  кг");
                label_gr.setText(String.valueOf(long_num * 1000) + "  гр");
                break;
            case "Граммы":
                if (long_num % 10000 == 0) {

                    if (double_num != 0) {
                        label_t.setText(String.format("%.2f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    } else {
                        label_t.setText(String.format("%.0f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    }

                    label_kg.setText(String.format("%.0f", Double.valueOf(double_num / 1000.D)) + "  кг");
                } else if (long_num % 1000 == 0) {
                    label_t.setText(String.format("%.3f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    label_kg.setText(String.format("%.0f", Double.valueOf(double_num / 1000.D)));
                } else if (long_num % 100 == 0) {
                    label_t.setText(String.format("%.4f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    label_kg.setText(String.format("%.1f", Double.valueOf(double_num / 1000.D)) + "  кг");
                } else if (long_num % 10 == 0) {
                    label_t.setText(String.format("%.5f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    label_kg.setText(String.format("%.2f", Double.valueOf(double_num / 1000.D)) + "  кг");
                } else {
                    label_t.setText(String.format("%.6f", Double.valueOf(double_num / 1000000.D)) + "  т");
                    label_kg.setText(String.format("%.3f", Double.valueOf(double_num / 1000.D)) + "  кг");
                }
                label_gr.setText(String.valueOf(long_num) + "  гр");
                break;
        }
    }

}
