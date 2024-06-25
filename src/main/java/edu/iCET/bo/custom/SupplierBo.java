package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Supplirs;
import edu.iCET.entity.SupplirEntity;
import javafx.collections.ObservableList;

import java.util.function.Supplier;

public interface SupplierBo extends SuperBo {

    boolean saveSupplier(Supplirs dto);

    public ObservableList<Supplirs> lordCustomers();
    public Supplirs search(String id);
    public boolean update(Supplirs dto);

    public boolean delete(String id);
}
