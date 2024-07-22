package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.OrderEntity;

public interface OrderDao extends CrudDao<OrderEntity> {
    public OrderEntity search(String orderId);
}
