package edu.iCET.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserTM {

    private String empId;
    private String name;
    private String phoneNumber;
    private String email;
}
