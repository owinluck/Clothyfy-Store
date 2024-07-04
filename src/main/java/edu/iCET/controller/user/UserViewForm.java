package edu.iCET.controller.user;

import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.UserBo;
import edu.iCET.dto.Supplirs;
import edu.iCET.dto.User;
import edu.iCET.dto.tm.SupplierTM;
import edu.iCET.dto.tm.UserTM;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserViewForm implements Initializable {
    public AnchorPane userViewAnchorPane;


    public TableView tblUserDetails;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colUserPhoneNumber;
    public TableColumn colEmail;

    private UserBo userBo= BoFactory.getInstance().getBo(BoType.User);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableLord();
    }

    private void tableLord() {

        colUserId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        ObservableList<UserTM> table= FXCollections.observableArrayList();
        ObservableList<User> users = userBo.lordUser();

        users.forEach(user ->{
            UserTM userTM = new UserTM(
                    user.getEmpId(),
                    user.getName(),
                    user.getPhoneNumber(),
                    user.getEmail());
            table.add(userTM);
        });
        tblUserDetails.setItems(table);

    }

    public void btnRegisterUserOnAction(ActionEvent actionEvent) {

        try {
            Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/User-Register-Form.fxml")).load();
            Stage stage=new Stage();
            stage.setScene(new Scene(fxmlLorder));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
