package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Order;

public interface OrderBo extends SuperBo {
    public boolean placeOrder(Order order);
}
