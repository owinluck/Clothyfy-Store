package edu.iCET.controller.order;

import com.jfoenix.controls.JFXTextField;
import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.OrderBo;
import edu.iCET.dto.OrderDetail;
import edu.iCET.dto.tm.ItemTM02;
import edu.iCET.dto.tm.ViewOrderTM;
import edu.iCET.entity.OrderEntity;
import edu.iCET.util.BoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrderManeg implements Initializable {
    public TableView<ViewOrderTM> tblViewOrder;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colAmount;
    public TableColumn colSize;
    public TableColumn colDiscount;
    public TableColumn colType;
    public TableColumn colPaymentType;
    public JFXTextField txtOrderId;
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtAmount;
    public JFXTextField txtSize;
    public JFXTextField txtDiscount;
    public JFXTextField txtType;
    public JFXTextField txtPaymentType;
    public JFXTextField txtCustomerId;

    private OrderBo orderBo= BoFactory.getInstance().getBo(BoType.Order);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lordTable();

    }

    private void lordTable() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

        ObservableList<ViewOrderTM> orderTable= FXCollections.observableArrayList();
        ObservableList<OrderDetail> orderDetails = orderBo.lordOrder();

        orderDetails.forEach(order -> {
            orderTable.add(new ViewOrderTM(
                    order.getOrderId(),
                    order.getItemCode(),
                    order.getDescription(),
                    order.getQty(),
                    order.getUnitPrice(),
                    order.getAmount(),
                    order.getSize(),
                    order.getDiscount(),
                    order.getType(),
                    order.getPaymentType()
            ));
        });
        tblViewOrder.setItems(orderTable);
    }

    public void rowClick(MouseEvent mouseEvent) {
        ViewOrderTM selectedItem = tblViewOrder.getSelectionModel().getSelectedItem();
        txtOrderId.setText(selectedItem.getOrderId());
        txtItemCode.setText(selectedItem.getItemCode());
        txtDescription.setText(selectedItem.getDescription());
        txtQty.setText(String.valueOf(selectedItem.getQty()));
        txtUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
        txtAmount.setText(String.valueOf(selectedItem.getAmount()));
        txtSize.setText(selectedItem.getSize());
        txtDiscount.setText(String.valueOf(selectedItem.getDiscount()));
        txtType.setText(selectedItem.getType());
        txtPaymentType.setText(selectedItem.getPaymentType());

        OrderEntity orderEntity = orderBo.searchOrder(selectedItem.getOrderId());
        txtCustomerId.setText(orderEntity.getCustomerId());
        System.out.println(orderEntity.getDate());
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        int qty= Integer.parseInt(txtQty.getText());
        double unitPrice= Double.parseDouble(txtUnitPrice.getText());
        double amount= Double.parseDouble(txtAmount.getText());
        double discount= Double.parseDouble(txtDiscount.getText());
        OrderDetail orderDetail = new OrderDetail(
                txtOrderId.getText(),
                txtItemCode.getText(),
                txtDescription.getText(),
                qty,
                unitPrice,
                amount,
                txtSize.getText(),
                discount,
                txtType.getText(),
                txtPaymentType.getText()
        );

        orderBo.updateOrder(orderDetail);
        lordTable();
    }
}