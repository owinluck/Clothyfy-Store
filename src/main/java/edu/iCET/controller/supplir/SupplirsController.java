package edu.iCET.controller.supplir;


import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.SupplierBo;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.Supplirs;
import edu.iCET.dto.tm.SupplierTM;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplirsController implements Initializable {

    public TableView tblSuppliers;
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colEmail;
    public JFXTextField txtSearchId;
    public JFXTextField txtName;
    public JFXTextField txtCompany;
    public JFXTextField txtEmail;
    public Label lblEmailError;
    public ImageView imgSupplier;
    public Label lblSupplierId;
    String emailText=null;

    private SupplierBo supplierBo=BoFactory.getInstance().getBo(BoType.Supplirs);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblEmailError.setText("");
        generateOrderId();
        lordTable();
    }

    public void checkField(KeyEvent keyEvent) {
        emailText = txtEmail.getText();

        if(isValid(emailText)){
            txtEmail.setStyle("-fx-border-color:blue");
            lblEmailError.setText("");
        }else {
            txtEmail.setStyle("-fx-border-color: red");
            lblEmailError.setText("Invalid Email...");
        }
    }
    private boolean isValid(String emailText){
        String emailRegex="^(.+)@(.+)$*";

        Pattern pat=Pattern.compile(emailRegex);
        if(txtEmail == null){
            return false;
        }
        return pat.matcher(emailText).matches();
    }

    public void btnAddOnAction(javafx.event.ActionEvent actionEvent) {

        String supplierId=lblSupplierId.getText();
        String name=txtName.getText();
        String company=txtCompany.getText();
        String email=txtEmail.getText();
        String imageUrl=lblSupplierId.getText();

        if(supplierId==null || supplierId.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                company == null || company.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                imageUrl == null || imageUrl.trim().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"All fields must be filled in.").show();
            return;
        }

        Supplirs supplirs = new Supplirs(
            supplierId,name,company,email,imageUrl
        );
        //System.out.println(supplirs);

        boolean b=supplierBo.saveSupplier(supplirs);

        System.out.println(">>>>>>>"+b);
        if(b){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Added....").show();
            clearText();
            lordTable();
            generateOrderId();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Added....").show();
        }
    }

    private void setImage() {
        Stage stage=new Stage();
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        File file=fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 121, 103, true, true);
            imgSupplier.setImage(image);
            imgSupplier.setFitWidth(121);
            imgSupplier.setFitHeight(103);
            imgSupplier.setPreserveRatio(true);

            try {
                BufferedImage myImage = ImageIO.read(new File(file.getAbsolutePath()));
                ImageIO.write(myImage, "png", new File(
                        "E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + lblSupplierId.getText() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error appropriately
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    public void btnImageOnAction(ActionEvent actionEvent) {
        setImage();
    }

    //---------- GenerateOrderId -------------------
    public  void generateOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM supplirentity");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);
                System.out.println(count+"  count ");
            }
            if (count == 0) {
                lblSupplierId.setText("S001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT supplierId\n" +
                    "FROM supplirentity\n" +
                    "ORDER BY supplierId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblSupplierId.setText(String.format("S%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtName.setText(null);
        txtCompany.setText(null);
        txtEmail.setText(null);
        Image image=new Image("E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\icons8-user-100.png");
        imgSupplier.setImage(image);
        txtSearchId.setText(null);
        lblSupplierId.setText(null);
    }

    private void lordTable(){


        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));

        ObservableList<SupplierTM> table= FXCollections.observableArrayList();
        ObservableList<Supplirs> supplirs = supplierBo.lordCustomers();

        supplirs.forEach(Supplirs ->{
            SupplierTM supplierTM = new SupplierTM(
                    Supplirs.getSupplierId(),
                    Supplirs.getName(),
                    Supplirs.getCompany(),
                    Supplirs.getEmail());
            table.add(supplierTM);
        });
        tblSuppliers.setItems(table);
        //System.out.println(supplirs);
    }

    public void btnDeletOnAction(ActionEvent actionEvent) {

        boolean isdelete = supplierBo.delete(txtSearchId.getText());

        if(isdelete){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Not Delete....").show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Suppliers Delete....").show();
            clearText();
            lordTable();
            generateOrderId();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        Supplirs supplirs = new Supplirs(
                lblSupplierId.getText(),
                txtName.getText(),
                txtCompany.getText(),
                txtEmail.getText(),
                lblSupplierId.getText()
        );
        boolean update = supplierBo.update(supplirs);
        lordTable();
        clearText();
        generateOrderId();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

        String searchId=txtSearchId.getText();

        if(searchId == null){
            new Alert(Alert.AlertType.ERROR,"No supplier found with the provided ID!").show();
            return;
        }

        Supplirs searchSupplier = null;

        try {
            searchSupplier=supplierBo.search(txtSearchId.getText());
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR,"An error occurred while searching for the supplier.");
            return;
        }

        if(searchSupplier == null){
            new Alert(Alert.AlertType.ERROR,"No supplier found with the provided ID!").show();
            return;
        }
        lblSupplierId.setText(searchSupplier.getSupplierId());
        txtEmail.setText(searchSupplier.getEmail());
        txtCompany.setText(searchSupplier.getCompany());
        txtName.setText(searchSupplier.getName());

        String imgUrl=searchSupplier.getImageUrl();
        if(imgUrl != null && !imgUrl.trim().isEmpty()){
            Image image=new Image("E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + searchSupplier.getImageUrl() + ".png");

            try {
                imgSupplier.setImage(image);
                imgSupplier.setFitWidth(121);
                imgSupplier.setFitHeight(103);
                imgSupplier.setPreserveRatio(true);
            }catch (Exception e){
                new Alert(Alert.AlertType.WARNING,"upplier image not available!");
                imgSupplier.setImage(null);
            }

        }else {
            new Alert(Alert.AlertType.WARNING,"Supplier image not available!").show();
            imgSupplier.setImage(null);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearText();
        generateOrderId();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}