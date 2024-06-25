package edu.iCET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Item {

    private String itemCode;
    private String supplierId;
    private String description;
    private Integer qty;
    private Integer buyingPrice;
    private Integer sellingPrice;
    private String type;
    private String size;
    private Integer profit;
}