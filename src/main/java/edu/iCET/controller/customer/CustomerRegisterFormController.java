package edu.iCET.controller.customer;

import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.CustomerBo;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.Customer;
import edu.iCET.dto.Supplirs;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerRegisterFormController implements Initializable {
    public Label lblCustomerId;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtMobileNumber;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public ChoiceBox cmdProvince;
    public JFXTextField txtPostalCode;
    public JFXTextField txtSearchId;
    public ChoiceBox cmdTitle;
    public AnchorPane customerFormAnchorPane;
    public ImageView imgCustomer;
    public ComboBox cmdCustomerId;


    private CustomerBo customerBo=BoFactory.getInstance().getBo(BoType.Customers);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        generateOrderId();
        lordDropMenuForTitle();
        lordDropMenuForProvince();
        lordCustomerId();

        cmdCustomerId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            System.out.println(newValue);

            setCustomerDataFortxtField((String)newValue);
        });
    }

    private void setCustomerDataFortxtField(String newValue) {

        Customer customer = customerBo.searchCustomerForId(newValue);
        System.out.println(customer);
        lblCustomerId.setText(customer.getCustomerId());
        cmdTitle.setValue(customer.getTitle());
        txtFirstName.setText(customer.getFirstName());
        txtLastName.setText(customer.getLastName());
        txtMobileNumber.setText(customer.getMobileNumber());
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtPostalCode.setText(customer.getPostalCode());
        cmdProvince.setValue(customer.getProvince());

        String imgUrl=customer.getImgUrl();
        if(imgUrl != null && !imgUrl.trim().isEmpty()){

            Path imagePath= Paths.get("E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + customer.getImgUrl() + ".png");
            //Image image=new Image("E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + customer.getImgUrl() + ".png");
            String imageUrl=imagePath.toUri().toString();

            try {
                Image image=new Image(imageUrl);
                imgCustomer.setImage(image);
                imgCustomer.setFitWidth(121);
                imgCustomer.setFitHeight(103);
                imgCustomer.setPreserveRatio(true);
            }catch (Exception e){
                new Alert(Alert.AlertType.WARNING,"Customer image not available!").show();
                imgCustomer.setImage(null);
            }

        }else {
            new Alert(Alert.AlertType.WARNING,"Customer image not available!").show();
            imgCustomer.setImage(null);
        }
    }


    private void lordCustomerId() {

        ObservableList<Customer> customers = customerBo.lordCustomer();

        ObservableList<String> customerIds=FXCollections.observableArrayList();

        customers.forEach(customer -> {
            customerIds.add(customer.getCustomerId());
        });
        cmdCustomerId.setItems(customerIds);
    }

    private void lordDropMenuForProvince() {
        ObservableList<Object> item= FXCollections.observableArrayList();
        item.add("Northern Province");
        item.add("North Central Province");
        item.add("North Western Province");
        item.add("Eastern Province");
        item.add("Central Province");
        item.add("Western Province");
        item.add("Sabaragamuwa Province");
        item.add("Uva Province");
        item.add("Southern Province");
        cmdProvince.setItems(item);
    }

    private void lordDropMenuForTitle() {
        ObservableList<Object> item= FXCollections.observableArrayList();
        item.add("MRS");
        item.add("MR");
        item.add("MS");
        item.add("MISS");
        cmdTitle.setItems(item);
    }

    public void btnAddPhotoOnAction(ActionEvent actionEvent) {
        Stage stage=new Stage();
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        File file=fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 121, 103, true, true);
            imgCustomer.setImage(image);
            imgCustomer.setFitWidth(121);
            imgCustomer.setFitHeight(103);
            imgCustomer.setPreserveRatio(true);

            try {
                BufferedImage myImage = ImageIO.read(new File(file.getAbsolutePath()));
                ImageIO.write(myImage, "png", new File(
                        "E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + lblCustomerId.getText() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error appropriately
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {

        String cusId=lblCustomerId.getText();
        String title=cmdTitle.getValue() != null ? cmdTitle.getValue().toString() : "";
        String firstName=txtFirstName.getText();
        String lastName=txtLastName.getText();
        String mobilNumber=txtMobileNumber.getText();
        String addres=txtAddress.getText();
        String city=txtCity.getText();
        String postalCode=txtPostalCode.getText();
        String province=cmdProvince.getValue() != null ? cmdProvince.getValue().toString() : "";
        String imgUrl=lblCustomerId.getText();

        if(cusId == null || cusId.trim().isEmpty() ||
            title==null || title.trim().isEmpty() ||
            firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            mobilNumber == null || mobilNumber.trim().isEmpty() ||
            addres == null || addres.trim().isEmpty() ||
            city ==null || city.trim().isEmpty() ||
            postalCode== null || postalCode.trim().isEmpty() ||
            province == null || province.trim().isEmpty() ||
            imgUrl == null || imgUrl.trim().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"All fields must be filled in.").show();
            return;
        }


        Customer customer = new Customer(cusId, title, firstName,
                lastName, mobilNumber, addres,
                city, postalCode, province,
                imgUrl
        );
        boolean b = customerBo.saveCustomer(customer);

        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Added....").show();
            clearText();
            generateOrderId();
            lordCustomerId();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Added....").show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean isDelete = customerBo.delectCustomerForId(lblCustomerId.getText());
        if(isDelete){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Delete....").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Delete....").show();
            clearText();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Customers-Table-page.fxml"));
        try {
            customerFormAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void generateOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM customerentity");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count + "  count ");
            }
            if (count == 0) {
                lblCustomerId.setText("C001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT customerId\n" +
                    "FROM customerentity\n" +
                    "ORDER BY customerId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblCustomerId.setText(String.format("C%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtFirstName.setText(null);
        cmdProvince.setValue(null);
        cmdTitle.setValue(null);
        txtLastName.setText(null);
        txtMobileNumber.setText(null);
        Image image=new Image("E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\icons8-user-100.png");
        imgCustomer.setImage(image);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtPostalCode.setText(null);
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        String cusId=lblCustomerId.getText();
        String title=cmdTitle.getValue() != null ? cmdTitle.getValue().toString() : "";
        String firstName=txtFirstName.getText();
        String lastName=txtLastName.getText();
        String mobilNumber=txtMobileNumber.getText();
        String addres=txtAddress.getText();
        String city=txtCity.getText();
        String postalCode=txtPostalCode.getText();
        String province=cmdProvince.getValue() != null ? cmdProvince.getValue().toString() : "";
        String imgUrl=lblCustomerId.getText();

        Customer customer = new Customer(cusId, title, firstName,
                lastName, mobilNumber, addres,
                city, postalCode, province,
                imgUrl
        );
        boolean isUpdate = customerBo.update(customer);

        if(isUpdate){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Update....").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Update....").show();
            clearText();
            generateOrderId();
        }
    }
}