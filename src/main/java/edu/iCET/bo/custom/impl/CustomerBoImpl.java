package edu.iCET.bo.custom.impl;

import edu.iCET.bo.custom.CustomerBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.CustomerDao;
import edu.iCET.dto.Customer;
import edu.iCET.dto.Supplirs;
import edu.iCET.entity.CustomerEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDao= DaoFactory.getInstance().getDao(DaoType.Customers);
    @Override
    public boolean saveCustomer(Customer dto) {

        return customerDao.save(new ModelMapper().map(dto, CustomerEntity.class));
    }

    public ObservableList<Customer> lordCustomer(){

        ObservableList<Customer> customers= FXCollections.observableArrayList();
        ObservableList<CustomerEntity> customerEntities = customerDao.allCustomers();

        customerEntities.forEach(customerEntity ->{
            customers.add(new Customer(
                customerEntity.getCustomerId(),
                customerEntity.getTitle(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getMobileNumber(),
                customerEntity.getAddress(),
                customerEntity.getCity(),
                customerEntity.getPostalCode(),
                customerEntity.getProvince(),
                customerEntity.getImgUrl()
            ));
        });
        return customers;
    }

    public Customer searchCustomerForId(String customerId){
        CustomerEntity customerEntity = customerDao.searchCustomer(customerId);

        return  new ModelMapper().map(customerEntity, Customer.class);
    }

    public boolean delectCustomerForId(String customerId){
        return customerDao.deleteCustomer(customerId);
    }
    public boolean update(Customer customer){

        return customerDao.updateSupplierEntity(new ModelMapper().map(customer, CustomerEntity.class));
    }
}
