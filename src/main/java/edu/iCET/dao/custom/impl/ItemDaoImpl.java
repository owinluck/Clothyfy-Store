package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.ItemDao;
import edu.iCET.entity.ItemEntity;
import edu.iCET.util.HibernateUtil;
import org.hibernate.Session;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }
}
