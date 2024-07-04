package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.OrderDao;
import edu.iCET.dao.custom.OrderDetailDao;
import edu.iCET.dto.OrderDetail;
import edu.iCET.entity.OrderDetailEntity;
import edu.iCET.entity.OrderEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
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

    @Override
    public boolean save(OrderDetailEntity dto) {
        return false;
    }
}


