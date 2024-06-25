package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CustomerTM02 {
    private String customerId;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}