package edu.iCET.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBordController implements Initializable{

    public AnchorPane dashBordAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnusersOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UsersViewForm.fxml"));
        try {
            dashBordAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnsuppliersOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SuppliersForm.fxml"));
        try {
            dashBordAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {

        try {
            Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/OrderFrom.fxml")).load();
            Stage stage=new Stage();
            stage.setScene(new Scene(fxmlLorder));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {

    }

    public void btnCustomersOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Customers-Table-page.fxml"));
        try {
            dashBordAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnItemsOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ItemManegForm.fxml"));
        try {
            dashBordAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRepotsOnAction(ActionEvent actionEvent) {
    }

    public void btnDashBordOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/DashBord-page2.fxml"));
        try {
            dashBordAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
