package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Order;
import edu.iCET.dto.OrderDetail;
import edu.iCET.entity.OrderEntity;
import javafx.collections.ObservableList;

public interface OrderBo extends SuperBo {
    public boolean placeOrder(Order order);
    public ObservableList<OrderDetail> lordOrder();
    public OrderEntity searchOrder(String id);
    public boolean updateOrder(OrderDetail dto);
}
