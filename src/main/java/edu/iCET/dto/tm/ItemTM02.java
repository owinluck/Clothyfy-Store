package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ItemTM02 {

    private String itemCode;
    private String size;
    private Double profit;
    private String supplierId;
    private String imgUrl;
}