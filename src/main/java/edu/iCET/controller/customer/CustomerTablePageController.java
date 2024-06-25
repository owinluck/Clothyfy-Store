package edu.iCET.controller.customer;

import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.CustomerBo;
import edu.iCET.dto.Customer;
import edu.iCET.dto.tm.CustomerTM01;
import edu.iCET.dto.tm.CustomerTM02;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerTablePageController implements Initializable {
    public AnchorPane CustomerTableAnchorPane;
    public TableView tblCustomer01;
    public TableColumn colCustomerId;
    public TableColumn colTitle;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colPhoneNumber;
    public TableView tblCustomer02;
    public TableColumn colCustomerId2;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;


    private CustomerBo customerBo=BoFactory.getInstance().getBo(BoType.Customers);
    public void btnCustomerRegisterOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerRegisterForm.fxml"));
        try {
            CustomerTableAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lordtable01();
        lordtable02();
    }

    private void lordtable02() {
        colCustomerId2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        ObservableList<CustomerTM02> customerTable02= FXCollections.observableArrayList();
        ObservableList<Customer> customers = customerBo.lordCustomer();

        customers.forEach(customer -> {
            customerTable02.add(new CustomerTM02(
                    customer.getCustomerId(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            ));
        });
        tblCustomer02.setItems(customerTable02);
    }

    private void lordtable01() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));

        ObservableList<CustomerTM01> customerTable01= FXCollections.observableArrayList();
        ObservableList<Customer> customers = customerBo.lordCustomer();

        customers.forEach(customer -> {
            customerTable01.add(new CustomerTM01(
               customer.getCustomerId(),
               customer.getTitle(),
               customer.getFirstName(),
               customer.getLastName(),
               customer.getMobileNumber()
            ));
        });
        tblCustomer01.setItems(customerTable01);
    }
}
