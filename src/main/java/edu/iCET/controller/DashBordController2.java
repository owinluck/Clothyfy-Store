package edu.iCET.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashBordController2 {
    public AnchorPane dashBordAnchorPane2;

    public void btnCustomersOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Customers-Table-page.fxml"));
        try {
            dashBordAnchorPane2.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnItemsOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ItemManegForm.fxml"));
        try {
            dashBordAnchorPane2.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRepotsOnAction(ActionEvent actionEvent) {
    }
}
