package edu.iCET.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.sql.ast.SqlTreeCreationException;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User {

    private String name;
    private String email;
    private String password;
    private String empId;
    private String phoneNumber;
}