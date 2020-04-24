package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsListController {

    @FXML
    private Button btn_exit;

    @FXML
    private Button btn_add_article;

    @FXML
    private VBox paneVBox;

    private DB db = new DB();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        btn_exit.setOnAction(event -> returnUserCabinet(event));
        btn_add_article.setOnAction(event -> inNewArticle(event));
        workWithArticle();
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

    private void inNewArticle(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/scenes/addArticle.fxml"));
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Новая статья");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void workWithArticle() throws SQLException, ClassNotFoundException {
        ResultSet res = db.getArticles();
        while(res.next()) {
            Node node = null;
            try {
                String titleArticle = res.getString("title");
                String introArticle = res.getString("intro");
                String textArticle = res.getString("text");

                node = FXMLLoader.load(getClass().getResource("/sample/scenes/article.fxml"));

                Label title = (Label) node.lookup("#title");
                title.setText(titleArticle);

                Label intro = (Label) node.lookup("#intro");
                intro.setText(introArticle);

                final Node nodeSet = node;
                node.setOnMouseEntered(event -> {
                    nodeSet.setStyle("-fx-background-color: #707173");
                });

                node.setOnMouseExited(event -> {
                    nodeSet.setStyle("-fx-background-color: #343434");
                });

                node.setOnMousePressed(event -> {
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/scenes/fullArticle.fxml"));
                        root = (Parent) loader.load();
                        ((FullArticleController) loader.getController()).setArticle(titleArticle, textArticle);

                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        primaryStage.setTitle("Статья");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
        }
    }
}
