package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CustomerTM01 {
    private String customerId;
    private String title;
    private String firstName;
    private String lastName;
    private String mobileNumber;
}