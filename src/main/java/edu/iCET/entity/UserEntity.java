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
public class UserEntity {

    private String name;
    @Id
    private String email;
    private String password;
    private String empId;
    private String phoneNumber;
}
