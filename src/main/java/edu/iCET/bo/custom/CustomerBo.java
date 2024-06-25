package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Customer;
import edu.iCET.dto.Supplirs;
import javafx.collections.ObservableList;

public interface CustomerBo extends SuperBo {
        boolean saveCustomer(Customer dto);
        public ObservableList<Customer> lordCustomer();

        public Customer searchCustomerForId(String customerId);
        public boolean delectCustomerForId(String customerId);
        public boolean update(Customer customer);
}
