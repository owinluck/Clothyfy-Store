package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.OrderDao;
import edu.iCET.entity.OrderEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(OrderEntity entity) {

        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public OrderEntity search(String orderId){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM orderentity WHERE orderId='"+ orderId+"'");

            while (resultSet.next()){
                return new OrderEntity(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
