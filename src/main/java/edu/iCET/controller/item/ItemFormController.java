package edu.iCET.controller.item;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.ItemBo;
import edu.iCET.bo.custom.SupplierBo;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.Customer;
import edu.iCET.dto.Item;
import edu.iCET.dto.Supplirs;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFormController implements Initializable {


    public JFXTextField txtItemCode;
    public JFXComboBox cmdSelectSupplierId;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtBuyingPrice;
    public JFXTextField txtSellingPrice;
    public JFXComboBox cmdSelectType;
    public JFXComboBox cmdSelectSize;
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public Label lblProfit;
    public JFXTextField txtSearch;
    public JFXTextField txtSupplierName;
    public ImageView imgProduct;

    private SupplierBo supplierBo= BoFactory.getInstance().getBo(BoType.Supplirs);
    private ItemBo itemBo=BoFactory.getInstance().getBo(BoType.Item);
    ArrayList<String> value=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lordSupplierId();
        lordDropMenuForType();
        generateOrderId();

        cmdSelectSupplierId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            //System.out.println(newValue);
            setSupplierDataFortxtFieldName((String)newValue);
        });
    }
    private void lordDropMenuForType() {
        ObservableList<Object> itemType= FXCollections.observableArrayList();
        itemType.add("Ladies");
        itemType.add("Gents");
        itemType.add("Kids");
        cmdSelectType.setItems(itemType);
    }
    private void setSupplierDataFortxtFieldName(String newValue) {
        Supplirs search = supplierBo.search(newValue);
        txtSupplierName.setText(search.getName());
    }

    private void lordSupplierId() {

        ObservableList<Supplirs> supplirs = supplierBo.lordCustomers();

        ObservableList<String> supplierIds= FXCollections.observableArrayList();

        supplirs.forEach(supplier -> {
            supplierIds.add(supplier.getSupplierId());
        });
        cmdSelectSupplierId.setItems(supplierIds);
    }
    private void clearText(){
        cmdSelectSupplierId.setValue(null);
        txtSupplierName.setText(null);
        txtDescription.setText(null);
        txtQty.setText(null);
        txtBuyingPrice.setText(null);
        txtSellingPrice.setText(null);
        cmdSelectType.setValue(null);
        txtSize.setText(null);
        lblProfit.setText(null);
    }
    public void btnAddItemOnAction(ActionEvent actionEvent) {
        String itemCode=txtItemCode.getText();
        String description=txtDescription.getText();
        Integer qty = Integer.valueOf(txtQty.getText());
        Integer buyingPrice= Integer.valueOf(txtBuyingPrice.getText());
        Integer sellingPrice= Integer.valueOf(txtSellingPrice.getText());
        String type= String.valueOf(cmdSelectType.getValue());
        String size= txtSize.getText();
        Double profit= Double.valueOf(lblProfit.getText());
        String supplierId= String.valueOf(cmdSelectSupplierId.getValue());

        if(itemCode == null || itemCode.trim().isEmpty() ||
               description == null || description.trim().isEmpty()||
                qty == null || qty <= 0 ||
                buyingPrice == null || buyingPrice <= 0 ||
                sellingPrice == null || sellingPrice <= 0 ||
                type == null || type.trim().isEmpty() ||
                size == null || size.trim().isEmpty() ||
                profit == null || profit <= 0 ||
                supplierId == null || supplierId.trim().isEmpty()){

            new Alert(Alert.AlertType.ERROR,"All fields must be filled in.").show();
            return;
        }

        Item item = new Item(itemCode, description, qty, buyingPrice,
                sellingPrice, type, size, profit, supplierId,itemCode
        );
        boolean isAdd = itemBo.saveItem(item);
        if(isAdd){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Added....").show();
            //clearText();
            //generateOrderId();
            //lordCustomerId();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Not Added....").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnViewItemOnAction(ActionEvent actionEvent) {
    }


    public void btnAddPhotoOnAction(ActionEvent actionEvent) {
        Stage stage=new Stage();
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select Image");
        File file=fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 121, 103, true, true);
            imgProduct.setImage(image);
            imgProduct.setFitWidth(121);
            imgProduct.setFitHeight(103);
            imgProduct.setPreserveRatio(true);

            try {
                BufferedImage myImage = ImageIO.read(new File(file.getAbsolutePath()));
                ImageIO.write(myImage, "png", new File(
                        "E:\\iCM106\\Java Fx\\Java_FX Final Project\\clothify-store-final-project-fX\\src\\main\\resources\\img\\" + txtItemCode.getText() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error appropriately
            }
        } else {
            System.out.println("File selection cancelled.");
        }
    }
    public  void generateOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM itementity");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println(count + "  count ");
            }
            if (count == 0) {
                txtItemCode.setText("P001");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT itemCode\n" +
                    "FROM itementity\n" +
                    "ORDER BY itemCode DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtItemCode.setText(String.format("P%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING, "hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtSellingPriceOnAction(KeyEvent keyEvent) {
        try {
            String buyPriceText = txtBuyingPrice.getText();
            String sellPriceText = txtSellingPrice.getText();

            if (buyPriceText.isEmpty() || sellPriceText.isEmpty()) {
                lblProfit.setText("0.0");
                return;
            }

            Integer buyPrice = Integer.valueOf(buyPriceText);
            Integer sellPrice = Integer.valueOf(sellPriceText);

            Double profit = (double) (sellPrice - buyPrice);
            lblProfit.setText(String.valueOf(profit));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter valid numbers.").show();
        }
    }
}
