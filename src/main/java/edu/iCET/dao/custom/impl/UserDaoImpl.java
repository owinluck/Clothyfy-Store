package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.UserDao;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.entity.UserEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) {

        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public UserEntity searchUser(String userEmail){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM userentity WHERE email ='"+ userEmail+"'");

            while (resultSet.next()){
                return new UserEntity(
                        resultSet.getString(3),
                        resultSet.getString(1),
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getString(5)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public UserEntity searchUserById(String userId){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM userentity WHERE empId ='"+ userId+"'");

            while (resultSet.next()){
                return new UserEntity(
                        resultSet.getString(3),
                        resultSet.getString(1),
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getString(5)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<UserEntity> allUsers(){
        ObservableList<UserEntity> userList= FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM userentity;");

            while (resultSet.next()){
                userList.add(new UserEntity(
                        resultSet.getString(3),
                        resultSet.getString(1),
                        resultSet.getString(4),
                        resultSet.getString(2),
                        resultSet.getString(5)
                        )
                );
            }

            return userList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
