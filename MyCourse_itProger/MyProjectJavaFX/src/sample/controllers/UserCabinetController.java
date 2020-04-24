package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

import java.io.*;
import java.sql.SQLException;

public class UserCabinetController {

    @FXML
    private Button btn_news;

    @FXML
    private Button btn_conv;

    @FXML
    private Button btn_calc;

    @FXML
    private Button btn_user;

    @FXML
    private Button btn_exit;

    private DB db = new DB();

    @FXML
    void initialize() {
        btn_exit.setOnAction(event -> returnReg(event));
        btn_user.setOnAction(event -> inUserChange(event));
        btn_news.setOnAction(event -> inNews(event));
        btn_calc.setOnAction(event -> inCalculator(event));
        btn_conv.setOnAction(event -> inConverter(event));
    }


    private void returnReg(ActionEvent event) {

        try {
            FileOutputStream fos = new FileOutputStream("user.settings");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new User(""));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/reg.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Регистрация и Авторизация");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inUserChange(ActionEvent event) {
        String login = "";
        String loginEmail = "";

        File file = new File("user.settings");
        boolean exists = file.exists();

        if (exists) {
            try {
                FileInputStream fis = new FileInputStream("user.settings");
                ObjectInputStream ois = new ObjectInputStream(fis);
                User user = (User) ois.readObject();
                login = user.getLogin();
                loginEmail = db.authUserEmail(login);
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/scenes/change.fxml"));
            root = (Parent) loader.load();

            ((UserChangeController) loader.getController()).setLoginEmailAuth(login, loginEmail);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Данные пользователя");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inNews(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/news.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Новости");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inCalculator(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/calculator.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Калькулятор");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inConverter(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/converter.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Конвертер");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

