package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.OrderDao;
import edu.iCET.dao.custom.OrderDetailDao;
import edu.iCET.db.DbConnection;
import edu.iCET.dto.OrderDetail;
import edu.iCET.entity.ItemEntity;
import edu.iCET.entity.OrderDetailEntity;
import edu.iCET.entity.OrderEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {


    public boolean saveOrderDetail(List<OrderDetail> orderDetailList) {
        boolean isAdd = false;
        for (OrderDetail orderDetail : orderDetailList) {
            if (!exists(orderDetail.getOrderId(), orderDetail.getItemCode())) {
                isAdd = addOrderDetail(orderDetail);
            } else {
                // You can either update or ignore the existing record
                System.out.println("Duplicate entry: " + orderDetail.getOrderId() + " - " + orderDetail.getItemCode());
            }
        }
        return isAdd;
    }

    private boolean addOrderDetail(OrderDetail orderDetail) {
        try {
            Object isAdd = CrudUtil.execute(
                    "INSERT INTO orderdetail VALUES(?,?,?,?,?,?,?,?,?,?)",
                    orderDetail.getOrderId(),
                    orderDetail.getItemCode(),
                    orderDetail.getDescription(),
                    orderDetail.getQty(),
                    orderDetail.getUnitPrice(),
                    orderDetail.getAmount(),
                    orderDetail.getSize(),
                    orderDetail.getDiscount(),
                    orderDetail.getType(),
                    orderDetail.getPaymentType()
            );
            return (boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean exists(String orderId, String itemCode) {
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT 1 FROM orderdetail WHERE orderId = ? AND itemCode = ?",
                    orderId, itemCode);
            return resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<OrderDetailEntity> allOrder(){
        ObservableList<OrderDetailEntity> orderList= FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM orderdetail");

            while (resultSet.next()){
                orderList.add(new OrderDetailEntity(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getDouble(5),
                                resultSet.getDouble(6),
                                resultSet.getString(7),
                                resultSet.getDouble(8),
                                resultSet.getString(9),
                                resultSet.getString(10)
                        )
                );
            }
            //System.out.println(customerList);
            return orderList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean save(OrderDetailEntity dto) {
        return false;
    }

    public boolean update(OrderDetailEntity orderEntity){

        //System.out.println(orderEntity);

        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("UPDATE orderdetail SET orderId='" + orderEntity.getOrderId() + "'"+",itemCode='" + orderEntity.getItemCode() + "'"+",description='"+orderEntity.getDescription()+"'"+",qty='"+orderEntity.getQty()+"'"+",unitPrice='"+orderEntity.getUnitPrice()+"'"+",amount='"+orderEntity.getAmount()+"'"+",size='"+orderEntity.getSize()+"'"+",discount='"+orderEntity.getDiscount()+"'"+",type='"+orderEntity.getType()+"'"+",paymentType='"+orderEntity.getPaymentType()+"'"+"WHERE orderId='"+orderEntity.getOrderId()+"'"+"AND itemCode='"+orderEntity.getItemCode()+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

//"WHERE supplierId='"+supplirEntity.getSupplierId()+"'"


