package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.SupplirEntity;
import javafx.collections.ObservableList;

public interface SupplirDao extends CrudDao<SupplirEntity> {

    public ObservableList<SupplirEntity> allSuppliers();

    public SupplirEntity searchSupplier(String supplierId);
    public boolean updateSupplierEntity(SupplirEntity supplirEntity);
    public boolean deleteSupplier(String supplierId);
}
