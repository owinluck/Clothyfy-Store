package edu.iCET.dao;

import edu.iCET.bo.custom.impl.ItemBoImpl;
import edu.iCET.dao.custom.impl.CustomerDaoImpl;
import edu.iCET.dao.custom.impl.ItemDaoImpl;
import edu.iCET.dao.custom.impl.SupplirDaoImpl;
import edu.iCET.dao.custom.impl.UserDaoImpl;
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
        }
        return null;
    }
}