package edu.iCET.entity;

import edu.iCET.dto.OrderDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class OrderEntity {
        @Id
        private String orderId;
        private String customerId;
        private Date date;
}