package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CartTM {

    private String itemCode;
    private String description;
    private int qty;
    private double unitPrice;
    private String size;
    private double discount;
    private String type;
    private double amount;
    private String paymentType;
}