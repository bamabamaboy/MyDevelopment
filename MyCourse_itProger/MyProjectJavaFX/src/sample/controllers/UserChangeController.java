package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class UserChangeController {

    @FXML
    private TextField login_change;

    @FXML
    private TextField email_change;

    @FXML
    private PasswordField pass_change;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_exit;

    private DB db = new DB();
    private String oldLogin;

    @FXML
    void initialize() {
        btn_exit.setOnAction(event -> returnUserCabinet(event));
        btn_change.setOnAction(event -> userChange(event));
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

    private void userChange(ActionEvent event) {
        login_change.setStyle("-fx-border-color: #fafafa");
        email_change.setStyle("-fx-border-color: #fafafa");
        pass_change.setStyle("-fx-border-color: #fafafa");
        btn_change.setText("Изменить данные");

        if (login_change.getCharacters().length() <= 3) {
            login_change.setStyle("-fx-border-color: red");
            return;
        } else if (email_change.getCharacters().length() <= 3) {
            email_change.setStyle("-fx-border-color: red");
            return;
        } else if (pass_change.getCharacters().length() <= 3) {
            pass_change.setStyle("-fx-border-color: red");
            return;
        }

        String pass = RegController.md5String(pass_change.getCharacters().toString());

        try {
            boolean isUser = db.isUser(login_change.getCharacters().toString());

            if (!isUser) {

                db.updateUser(oldLogin, login_change.getCharacters().toString(), email_change.getCharacters().toString(), pass);

                try {
                    FileOutputStream fos = new FileOutputStream("user.settings");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(new User(login_change.getCharacters().toString()));
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

            } else {
                btn_change.setText("Логин уже существует");
                login_change.setStyle("-fx-border-color: red");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setLoginEmailAuth(String loginAuth, String email) {
        oldLogin = loginAuth;
        login_change.setText(loginAuth);
        email_change.setText(email);
    }
}
