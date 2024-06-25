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
@Entity
public class ItemEntity {

    @Id
    private String itemCode;
    private String description;
    private Integer qty;
    private Integer buyingPrice;
    private Integer sellingPrice;
    private String type;
    private String size;
    private Double profit;
    private String supplierId;
    private String imgUrl;
}