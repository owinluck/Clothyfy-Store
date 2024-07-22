package edu.iCET.controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.CustomerBo;
import edu.iCET.bo.custom.ItemBo;
import edu.iCET.bo.custom.OrderBo;
import edu.iCET.bo.custom.UserBo;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.*;
import edu.iCET.dto.tm.CartTM;
import edu.iCET.util.BoType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderManegFromController implements Initializable {
    public JFXComboBox cmbSelectEmpId;
    public JFXComboBox cmbCustomerSelect;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerContactNumber;
    public Label lblDate;
    public DatePicker dateField;
    public CheckBox chkCashBox;
    public CheckBox chkCardBox;
    public JFXTextField txtQty;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtProfit;
    public JFXTextField txtType;
    public JFXTextField txtSize;
    public JFXTextField txtDiscount;
    public JFXComboBox cmbSelectItemCode;
    public JFXTextField txtDescription;
    public TableView tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDate;
    public TableColumn colDiscount;
    public TableColumn colType;
    public TableColumn colAmount;
    public JFXTextField txtType1;
    public TableColumn colSize;
    public Label lblTotal;
    public Label lblDiscount;
    public JFXTextField txtOrderId;
    public TableColumn colPayementType;
    public Label txtBalance;
    public JFXTextField txtCash;
    public Label lblBalance;
    public Label lblTime;
    public AnchorPane orderAnchorPane;
    private double netTotal=0;
    private double netDiscount=0;
    private UserBo userBo= BoFactory.getInstance().getBo(BoType.User);
    private CustomerBo customerBo=BoFactory.getInstance().getBo(BoType.Customers);
    private ItemBo itemBo=BoFactory.getInstance().getBo(BoType.Item);
    private OrderBo orderBo=BoFactory.getInstance().getBo(BoType.Order);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lordDateAndTime();
        lordEmployerIds();
        lordCustomerIds();
        lordItemIds();
        generateOrderId();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPayementType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        //-------------------------------------------------------------------------//
        cmbSelectEmpId.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            System.out.println(newValue);

            setEmployerForName((String)newValue);
        });
        cmbCustomerSelect.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            System.out.println(newValue);

            setCustomerForName((String)newValue);
        });
        cmbSelectItemCode.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            System.out.println(newValue);

            setItemsForData((String)newValue);
        });
    }

    private void setItemsForData(String newValue) {

        Item search = itemBo.search(newValue);
        txtDescription.setText(search.getDescription());
        txtQtyOnHand.setText(String.valueOf(search.getQty()));
        txtSellingPrice.setText(String.valueOf(search.getSellingPrice()));
        txtProfit.setText(String.valueOf(search.getProfit()));
        txtType.setText(search.getType());
        txtSize.setText(search.getSize());
    }

    private void lordItemIds() {

        ObservableList<Item> items = itemBo.lordItem();
        ObservableList ids= FXCollections.observableArrayList();

        items.forEach(item -> {
            ids.add(item.getItemCode());
        });
        cmbSelectItemCode.setItems(ids);
    }

    private void setCustomerForName(String newValue) {

        Customer customer = customerBo.searchCustomerForId(newValue);
        txtCustomerName.setText(customer.getFirstName());
        txtCustomerContactNumber.setText(customer.getMobileNumber());
    }

    private void setEmployerForName(String newValue) {

        User user = userBo.searchbyId(newValue);
        txtEmployeeName.setText(user.getName());
    }

    private void lordCustomerIds() {
        ObservableList<Customer> customers = customerBo.lordCustomer();
        ObservableList ids= FXCollections.observableArrayList();

        customers.forEach(customer -> {
            ids.add(customer.getCustomerId());
        });
        cmbCustomerSelect.setItems(ids);

    }

    private void lordEmployerIds() {
        ObservableList<User> users = userBo.lordUser();
        ObservableList ids= FXCollections.observableArrayList();

        users.forEach(user -> {
            ids.add(user.getEmpId());
        });
        cmbSelectEmpId.setItems(ids);
    }

    private void lordDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(f.format(date));
        lblDate.setText(f.format(date));

        Timeline timeline=new Timeline(new KeyFrame(Duration.ZERO,e->{
            LocalTime time=LocalTime.now();
            lblTime.setText(
                    time.getHour()+" : "+time.getMinute()+" : "+time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {



        try {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());

            String employerName=txtEmployeeName.getText();
            String customerName=txtCustomerName.getText();
            String cusPhoneNumer=txtCustomerContactNumber.getText();
            if(employerName.isEmpty() || cusPhoneNumer.isEmpty() || customerName.isEmpty()){

                showAlert("Please fill in all fields.");
                return;
            }

            List<OrderDetail> orderDetailList=new ArrayList<>();
            String itemeCode=null;

            for(CartTM cartTM : cartList){
                String oId=txtOrderId.getText();
                itemeCode=cartTM.getItemCode();
                String description=cartTM.getDescription();
                int qty=cartTM.getQty();
                double unitPrice=cartTM.getUnitPrice();
                double amount=cartTM.getAmount();
                String size=cartTM.getSize();
                double discount=cartTM.getDiscount();
                String type=cartTM.getType();
                String paymentType=cartTM.getPaymentType();
                orderDetailList.add(new OrderDetail(oId,itemeCode,description,qty,unitPrice,amount,size,discount,type,paymentType));
            }

            //System.out.println(orderDetailList);
            String orderId=txtOrderId.getText();
            String cusId= (String) cmbCustomerSelect.getValue();

            Order order = new Order(orderId, cusId, orderDate, orderDetailList);
            boolean isOrderPlace = orderBo.placeOrder(order);
            if(isOrderPlace){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Place...").show();
                generateOrderId();
                clearTextClickToPlaceOrder();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order is not place").show();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<CartTM> cartList=FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        try {
            String qtyText = txtQty.getText().trim();
            String sellingPriceText = txtSellingPrice.getText().trim();
            String discountText = txtDiscount.getText().trim();

            if (qtyText.isEmpty() || sellingPriceText.isEmpty() || discountText.isEmpty()) {
                showAlert("Please fill in all fields.");
                return;
            }

            int qty = Integer.parseInt(qtyText);
            double sellingPrice = Double.parseDouble(sellingPriceText);
            double discountRate = Double.parseDouble(discountText);

            if (qty == 0 || discountRate == 0) {
                showAlert("Quantity and discount rate must be greater than zero.");
                return;
            }

            double discount = ((sellingPrice * discountRate) / 100) * qty;
            double total = (sellingPrice * qty) - discount;
            netTotal+=total;
            netDiscount+=discount;

            String payemetType;

            if(chkCardBox.isSelected()){
                payemetType="Card";
            }else if(chkCashBox.isSelected()){
                payemetType="Cash";
            }else {
                showAlert("Please select a payment type.");
                return;
            }
            String itemCode= String.valueOf(cmbSelectItemCode.getValue());
            String description=txtDescription.getText();
            double unitPrice= Double.parseDouble(txtSellingPrice.getText());
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate=format.parse(lblDate.getText());
            System.out.println(orderDate);
            String type=txtType.getText();
            String size=txtSize.getText();

            CartTM cartTM=new CartTM(itemCode,description,qty,unitPrice,size,discount,type,total,payemetType);
            int qtyStock= Integer.parseInt(txtQtyOnHand.getText());
            if(qtyStock<qty){
                showAlert("Invalied Qty");
                return;
            }

            cartList.add(cartTM);
            tblOrderDetails.setItems(cartList);
            nextQty(qty,qtyStock,itemCode);
            lblTotal.setText(netTotal+"0");
            lblDiscount.setText(netDiscount+"0");
            cleartextClicktoAddtoCart();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter numeric values.");
        } catch (ParseException e) {
            showAlert("Invalied date format.Please enter a valied date.");
        }
    }

    private void nextQty(int qty, int qtyStock, String itemCode) {




    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public  void generateOrderId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orderentity");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);
                System.out.println(count+"  count ");
            }
            if (count == 0) {
                txtOrderId.setText("D001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT orderId\n" +
                    "FROM orderentity\n" +
                    "ORDER BY orderId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    txtOrderId.setText(String.format("D%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleartextClicktoAddtoCart() {

            txtDescription.setText("");
            txtQty.setText("");
            txtQtyOnHand.setText("");
            txtSellingPrice.setText("");
            txtProfit.setText("");
            txtType.setText("");
            txtSize.setText("");
            txtDiscount.setText("");
    }

    public void clearTextClickToPlaceOrder(){
        txtEmployeeName.setText("");
        txtCustomerName.setText("");
        txtCustomerContactNumber.setText("");
        txtEmployeeName.setText("");
        chkCashBox.setText("");
        chkCashBox.setText("");
        lblTotal.setText("00.00");
        lblDiscount.setText("00.00");
        lblBalance.setText("00.00");
        txtCash.setText("00.00");
    }

    public void txtCashOnAction(ActionEvent actionEvent) {

        double cash= Double.parseDouble(txtCash.getText());

        double blance=cash-netTotal;

        lblBalance.setText(blance+"0 /=");

    }

    public void btnViewOrderOnAction(ActionEvent actionEvent) {

        try {
            Parent fxmlLorder= new FXMLLoader(getClass().getResource("/view/OrderManegForm.fxml")).load();
            Stage stage=new Stage();
            stage.setScene(new Scene(fxmlLorder));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}