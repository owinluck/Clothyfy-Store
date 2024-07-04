package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.OrderDao;
import edu.iCET.entity.OrderEntity;
import edu.iCET.util.HibernateUtil;
import org.hibernate.Session;

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
}
