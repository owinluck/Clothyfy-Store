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
    //private String supplierId;
    private String description;
    private Integer qty;
    private Integer buyingPrice;
    private Integer sellingPrice;
    private String type;
    private String size;
    private Integer profit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierId",referencedColumnName = "supplierId")
    private SupplirEntity supplier;
}