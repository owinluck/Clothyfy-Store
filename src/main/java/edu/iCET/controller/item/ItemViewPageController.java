package edu.iCET.controller.item;

import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.ItemBo;
import edu.iCET.dto.Item;
import edu.iCET.dto.tm.CustomerTM02;
import edu.iCET.dto.tm.ItemTM01;
import edu.iCET.dto.tm.ItemTM02;
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

public class ItemViewPageController implements Initializable {
    public TableView tblItemModel01;
    public TableView tblItemModel02;

    public AnchorPane itemViewAnchorPane;
    public TableColumn colItemCode01;
    public TableColumn colDescription;
    public TableColumn tblQty;
    public TableColumn cloBuyingPrice;
    public TableColumn colSellingPrice;
    public TableColumn colType;
    public TableColumn colItemCode02;
    public TableColumn colSize;
    public TableColumn colProfit;
    public TableColumn colSupplierId;
    public TableColumn colImageUrl;
    public TableColumn colQty;


    private ItemBo itemBo= BoFactory.getInstance().getBo(BoType.Item);
    public void btnBackOnAction(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ItemManegForm.fxml"));
        try {
            itemViewAnchorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lordTable01();
        lordTable02();
    }

    private void lordTable02() {

        colItemCode02.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colProfit.setCellValueFactory(new PropertyValueFactory<>("profit"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colImageUrl.setCellValueFactory(new PropertyValueFactory<>("imgUrl"));

        ObservableList<ItemTM02> itemTable02= FXCollections.observableArrayList();
        ObservableList<Item> items = itemBo.lordItem();

        items.forEach(item -> {
            itemTable02.add(new ItemTM02(
                    item.getItemCode(),
                    item.getSize(),
                    item.getProfit(),
                    item.getSupplierId(),
                    item.getImgUrl()+".URL"
            ));
        });
        tblItemModel02.setItems(itemTable02);
    }

    private void lordTable01() {

        colItemCode01.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cloBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        ObservableList<ItemTM01> itemTable01= FXCollections.observableArrayList();
        ObservableList<Item> items = itemBo.lordItem();

        items.forEach(item -> {
            itemTable01.add(new ItemTM01(
                    item.getItemCode(),
                    item.getDescription(),
                    item.getQty(),
                    item.getBuyingPrice(),
                    item.getSellingPrice(),
                    item.getType()
            ));
        });
        tblItemModel01.setItems(itemTable01);
    }
}
