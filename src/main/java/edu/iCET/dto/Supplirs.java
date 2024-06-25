package edu.iCET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Supplirs {
    private String supplierId;
    private String name;
    private String Company;
    private String Email;
    private String imageUrl;
}
