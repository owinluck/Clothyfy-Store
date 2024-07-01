package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.ItemEntity;
import javafx.collections.ObservableList;

public interface ItemDao extends CrudDao<ItemEntity> {

    public ItemEntity searchItem(String itemCode);
    public ObservableList<ItemEntity> allItem();
    public boolean updateItemEntity(ItemEntity itemEntity);
    public boolean deleteItem(String itemCode);
}
