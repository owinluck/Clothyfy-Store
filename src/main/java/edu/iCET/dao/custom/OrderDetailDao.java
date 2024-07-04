package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.dto.OrderDetail;
import edu.iCET.entity.OrderDetailEntity;
import edu.iCET.entity.OrderEntity;

import java.util.List;

public interface OrderDetailDao extends CrudDao<OrderDetailEntity> {
    public boolean saveOrderDetail(List<OrderDetail> orderDetailList);

}
