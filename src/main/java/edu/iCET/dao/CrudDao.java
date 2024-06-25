package edu.iCET.dao;

import edu.iCET.entity.SupplirEntity;
import javafx.collections.ObservableList;

public interface CrudDao <T> extends SuperDao{

    boolean save(T dto);
}
