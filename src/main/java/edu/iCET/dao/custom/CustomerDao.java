package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.CustomerEntity;
import edu.iCET.entity.SupplirEntity;
import javafx.collections.ObservableList;

public interface CustomerDao extends CrudDao<CustomerEntity> {
    public ObservableList<CustomerEntity> allCustomers();
    public CustomerEntity searchCustomer(String customerId);
    public boolean deleteCustomer(String customerId);
    public boolean updateSupplierEntity(CustomerEntity customerEntity);
}
