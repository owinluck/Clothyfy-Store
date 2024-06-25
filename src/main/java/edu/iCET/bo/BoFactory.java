package edu.iCET.bo;

import edu.iCET.bo.custom.impl.CustomerBoImpl;
import edu.iCET.bo.custom.impl.SupplierBoImpl;
import edu.iCET.util.BoType;

public class BoFactory {

    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance != null ? instance : (instance=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case Supplirs:return  (T) new SupplierBoImpl();
            case Customers:return (T) new CustomerBoImpl();
        }
        return null;
    }
}
