package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.CustomerDao;
import edu.iCET.db.DbConnection;
import edu.iCET.entity.CustomerEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) {

        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public ObservableList<CustomerEntity> allCustomers(){
        ObservableList<CustomerEntity> customerList= FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customerentity");

            while (resultSet.next()){
                customerList.add(new CustomerEntity(
                                resultSet.getString(1),
                                resultSet.getString(10),
                                resultSet.getString(4),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getString(5)
                        )
                );
            }
            System.out.println(customerList);
            return customerList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerEntity searchCustomer(String customerId){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customerentity WHERE customerId='"+ customerId+"'");

            while (resultSet.next()){
                return new CustomerEntity(
                        resultSet.getString(1),
                        resultSet.getString(10),
                        resultSet.getString(4),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(5)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean deleteCustomer(String customerId){

        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("DELETE FROM  customerentity WHERE customerId='"+customerId+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateSupplierEntity(CustomerEntity customerEntity){

        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("UPDATE customerentity SET customerId='" + customerEntity.getCustomerId() + "'"+",address='" + customerEntity.getAddress() + "'"+",city='"+customerEntity.getCity()+"'"+",firstName='"+customerEntity.getFirstName()+"'"+",imgUrl='"+customerEntity.getImgUrl()+"'"+",lastName='"+customerEntity.getLastName()+"'"+",mobileNumber='"+customerEntity.getMobileNumber()+"'"+",postalCode='"+customerEntity.getMobileNumber()+"'"+",province='"+customerEntity.getProvince()+"'"+",title='"+customerEntity.getTitle()+"'"+"WHERE customerId='"+customerEntity.getCustomerId()+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}