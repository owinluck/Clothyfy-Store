package edu.iCET.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class CustomerEntity {

    @Id
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