package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.SupplirDao;
import edu.iCET.db.DbConnection;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static edu.iCET.util.HibernateUtil.getSession;

public class SupplirDaoImpl implements SupplirDao {

    @Override
    public boolean save(SupplirEntity entity) {

        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public ObservableList<SupplirEntity> allSuppliers(){
        ObservableList<SupplirEntity> supplirList= FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplirentity");

            while (resultSet.next()){
                supplirList.add(new SupplirEntity(
                                resultSet.getString(1),
                                resultSet.getString(5),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )
                );
            }
            return supplirList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public SupplirEntity searchSupplier(String supplierId){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplirentity WHERE supplierId='"+ supplierId+"'");

            while (resultSet.next()){
                return new SupplirEntity(
                        resultSet.getString(1),
                        resultSet.getString(5),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean updateSupplierEntity(SupplirEntity supplirEntity){

        System.out.println(supplirEntity);
        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("UPDATE supplirentity SET supplierId='" + supplirEntity.getSupplierId() + "'"+",name='" + supplirEntity.getName() + "'"+",Company='"+supplirEntity.getCompany()+"'"+",Email='"+supplirEntity.getEmail()+"'"+",imageUrl='"+supplirEntity.getImageUrl()+"'"+"WHERE supplierId='"+supplirEntity.getSupplierId()+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteSupplier(String supplierId){

        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("DELETE FROM  supplirentity WHERE supplierId='"+supplierId+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}