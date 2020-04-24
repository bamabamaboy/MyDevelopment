package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB;
import sample.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegController {

    @FXML
    private TextField login_reg;

    @FXML
    private TextField email_reg;

    @FXML
    private PasswordField pass_reg;

    @FXML
    private CheckBox confidentials;

    @FXML
    private Button btn_reg;

    @FXML
    private TextField login_auth;

    @FXML
    private PasswordField pass_auth;

    @FXML
    private Button btn_auth;

    private DB db = new DB();

    private String loginAuth;
    private String emailAuth;

    @FXML
    void initialize() {
        btn_reg.setOnAction(event -> userReg(event));
        btn_auth.setOnAction(event -> userAuth(event));
    }

    private void userReg(ActionEvent event) {
        login_reg.setStyle("-fx-border-color: #fafafa");
        email_reg.setStyle("-fx-border-color: #fafafa");
        pass_reg.setStyle("-fx-border-color: #fafafa");
        btn_reg.setText("Зарегистрироваться");

        if (login_reg.getCharacters().length() <= 3) {
            login_reg.setStyle("-fx-border-color: red");
            return;
        } else if (email_reg.getCharacters().length() <= 5) {
            email_reg.setStyle("-fx-border-color: red");
            return;
        } else if (pass_reg.getCharacters().length() <= 3) {
            pass_reg.setStyle("-fx-border-color: red");
            return;
        } else if (!confidentials.isSelected()) {
            btn_reg.setText("Поставьте галочку");
            return;
        }

        String pass = md5String(pass_reg.getCharacters().toString());

        try {
            boolean isReg = db.regUser(login_reg.getCharacters().toString(), email_reg.getCharacters().toString(), pass);

            if (isReg) {
                login_reg.setText("");
                email_reg.setText("");
                pass_reg.setText("");
                btn_reg.setText("Готово");
            } else {
                btn_reg.setText("Введите другой логин");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void userAuth(ActionEvent event) {
        login_auth.setStyle("-fx-border-color: #fafafa");
        pass_auth.setStyle("-fx-border-color: #fafafa");
        btn_auth.setText("Войти");

        if (login_auth.getCharacters().length() <= 3) {
            login_auth.setStyle("-fx-border-color: red");
            return;
        } else if (pass_auth.getCharacters().length() <= 3) {
            pass_auth.setStyle("-fx-border-color: red");
            return;
        }

        String pass = md5String(pass_auth.getCharacters().toString());

        try {
            boolean isAuth = db.authUser(login_auth.getCharacters().toString(), pass);

            if (isAuth) {
                FileOutputStream fos = new FileOutputStream("user.settings");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(new User(login_auth.getCharacters().toString()));
                oos.close();

                loginAuth = login_auth.getText();
                emailAuth = db.authUserEmail(login_auth.getText());

                login_auth.setText("");
                pass_auth.setText("");
                btn_auth.setText("Готово");

                inUserCabinet(event);

            } else {
                btn_auth.setText("Не найден");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inUserCabinet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/scenes/cabinet.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Кабинет пользователя");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String mD5Hex = bigInteger.toString(16);

        while(mD5Hex.length() < 32) {
            mD5Hex = "0" + mD5Hex;
        }

        return mD5Hex;
    }
}
