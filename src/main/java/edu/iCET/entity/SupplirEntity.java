package edu.iCET.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity//(name = "Supplirs")
public class SupplirEntity {
    @Id
    private String supplierId;
    private String name;
    private String Company;
    private String Email;
    private String imageUrl;
}