package edu.iCET.bo.custom.impl;

import edu.iCET.bo.BoFactory;
import edu.iCET.bo.custom.OrderBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.OrderDao;
import edu.iCET.dao.custom.OrderDetailDao;
import edu.iCET.dto.Item;
import edu.iCET.dto.Order;
import edu.iCET.dto.OrderDetail;
import edu.iCET.entity.ItemEntity;
import edu.iCET.entity.OrderDetailEntity;
import edu.iCET.entity.OrderEntity;
import edu.iCET.util.BoType;
import edu.iCET.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

import java.util.List;

public class OrderBoImpl implements OrderBo {

    private OrderDao orderDao= DaoFactory.getInstance().getDao(DaoType.Order);
    private OrderDetailDao orderDetailDao=DaoFactory.getInstance().getDao(DaoType.OrderDetail);
    public boolean placeOrder(Order order){

        OrderEntity orderEntity = new OrderEntity(order.getOrderId(), order.getCustomerId(), order.getDate());

        boolean orderSave = orderDao.save(orderEntity);
        if(orderSave){
            boolean isOrderDetailAdd = orderDetailDao.saveOrderDetail(order.getOrderDetails());
            if(isOrderDetailAdd){
                return true;
            }
        }
        return false;
    }

    public ObservableList<OrderDetail> lordOrder(){

        ObservableList<OrderDetail> order= FXCollections.observableArrayList();
        ObservableList<OrderDetailEntity> orderDetailEntities = orderDetailDao.allOrder();

        orderDetailEntities.forEach(orderEntity ->{
            order.add(new OrderDetail(
                    orderEntity.getOrderId(),
                    orderEntity.getItemCode(),
                    orderEntity.getDescription(),
                    orderEntity.getQty(),
                    orderEntity.getUnitPrice(),
                    orderEntity.getAmount(),
                    orderEntity.getSize(),
                    orderEntity.getDiscount(),
                    orderEntity.getType(),
                    orderEntity.getPaymentType()
            ));
        });
        return order;
    }

    public OrderEntity searchOrder(String id){
        return orderDao.search(id);
    }

    public boolean updateOrder(OrderDetail dto){

        orderDetailDao.update(new ModelMapper().map(dto, OrderDetailEntity.class));
        return false;
    }
}