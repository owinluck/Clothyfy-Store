package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ItemTM01 {

    private String itemCode;
    private String description;
    private Integer qty;
    private Integer buyingPrice;
    private Integer sellingPrice;
    private String type;
}
