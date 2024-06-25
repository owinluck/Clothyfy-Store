package edu.iCET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.mapping.PrimaryKey;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Customer {
    private String customerId;
    private String title;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String address;
    private String city;
    private String postalCode;
    private String province;
    private String imgUrl;
}