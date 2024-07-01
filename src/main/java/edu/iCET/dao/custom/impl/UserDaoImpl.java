package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.UserDao;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.entity.UserEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
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
}
