package edu.iCET.dao;

import edu.iCET.bo.custom.impl.ItemBoImpl;
import edu.iCET.dao.custom.impl.*;
import edu.iCET.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance(){

        return instance != null ? instance : (instance=new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case Supplirs:return (T) new SupplirDaoImpl();
            case Customers:return (T)new CustomerDaoImpl();
            case Item:return (T) new ItemDaoImpl();
            case User:return (T) new UserDaoImpl();
            case Order:return (T) new OrderDaoImpl();
            case OrderDetail:return (T) new OrderDetailDaoImpl();
        }
        return null;
    }
}