package edu.iCET.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class OrderDetailEntity {

    private String orderId;
    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private double amount;
    private String size;
    private double discount;
    private String type;
    private String paymentType;

}