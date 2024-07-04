package edu.iCET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data

public class Order {

    private String orderId;
    private String customerId;
    private Date date;
    private List<OrderDetail> orderDetails;
}