package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class CalculatorController {

    @FXML
    private TextField label_field;

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_clear;

    @FXML
    private Button btn_plus_minus;

    @FXML
    private Button btn_percent;

    @FXML
    private Button btn_del;

    @FXML
    private Button btn_7;

    @FXML
    private Button btn_8;

    @FXML
    private Button btn_9;

    @FXML
    private Button btn_mult;

    @FXML
    private Button btn_4;

    @FXML
    private Button btn_5;

    @FXML
    private Button btn_6;

    @FXML
    private Button btn_minus;

    @FXML
    private Button btn_1;

    @FXML
    private Button btn_2;

    @FXML
    private Button btn_3;

    @FXML
    private Button btn_plus;

    @FXML
    private Button btn_0;

    @FXML
    private Button btn_equal;

    @FXML
    private Button btn_comma;

    private String label_num = "";
    private float first_num;
    private char operation;

    @FXML
    void initialize() {
        btn_exit.setOnAction(event -> returnUserCabinet(event));

        btn_0.setOnAction(event -> addNumber(0));
        btn_1.setOnAction(event -> addNumber(1));
        btn_2.setOnAction(event -> addNumber(2));
        btn_3.setOnAction(event -> addNumber(3));
        btn_4.setOnAction(event -> addNumber(4));
        btn_5.setOnAction(event -> addNumber(5));
        btn_6.setOnAction(event -> addNumber(6));
        btn_7.setOnAction(event -> addNumber(7));
        btn_8.setOnAction(event -> addNumber(8));
        btn_9.setOnAction(event -> addNumber(9));

        btn_plus.setOnAction(event -> mathAction('+'));
        btn_minus.setOnAction(event -> mathAction('-'));
        btn_mult.setOnAction(event -> mathAction('*'));
        btn_del.setOnAction(event -> mathAction('/'));
        btn_percent.setOnAction(event -> mathAction('%'));

        btn_equal.setOnAction(event -> {
            if (this.operation == '+' || this.operation == '-' ||
                    this.operation == '*' || this.operation == '/') {
                equalMethod();
            }
        });

        btn_comma.setOnAction(event -> {
            if(!this.label_num.contains(".")) {
                this.label_num += ".";
                label_field.setText(label_num);
            }
        });

        btn_percent.setOnAction(event -> {
            if (this.label_num != "") {
                float num = Float.parseFloat(this.label_num) * 0.1f;
                this.label_num = Float.toString(num);
                label_field.setText(label_num);
            }
        });

        btn_plus_minus.setOnAction(event -> {
            if (this.label_num != "") {
                float num = Float.parseFloat(this.label_num) * -1;
                this.label_num = Float.toString(num);
                label_field.setText(label_num);
            }
        });

        btn_clear.setOnAction(event -> {
            label_field.setText("0");
            this.label_num = "";
            this.first_num = 0;
            this.operation = '0';
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

    private void addNumber(int number) {
        this.label_num += Integer.toString(number);
        label_field.setText(label_num);
    }

    private void mathAction(char operation) {
        if (this.operation != '+' && this.operation != '-' &&
                this.operation != '*' && this.operation != '/') {

            this.first_num = Float.parseFloat(this.label_num);
            label_field.setText(String.valueOf(operation));
            this.label_num = "";
            this.operation = operation;
        }
    }

    private void equalMethod() {
        float res = 0;

        switch (this.operation) {
            case '+':
                res = this.first_num + Float.parseFloat(this.label_num);
                break;
            case '-':
                res = this.first_num - Float.parseFloat(this.label_num);
                break;
            case '*':
                res = this.first_num * Float.parseFloat(this.label_num);
                break;
            case '/':
                if(Float.parseFloat(this.label_num) != 0)
                    res = this.first_num / Float.parseFloat(this.label_num);
                else
                    res = 0;
                break;
        }

        label_field.setText(Float.toString(res));

        this.label_num = "";
        this.operation = '0';
        this.first_num = 0;
    }

}

