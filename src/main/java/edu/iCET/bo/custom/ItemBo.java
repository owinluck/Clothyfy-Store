package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Item;
import edu.iCET.dto.Supplirs;
import javafx.collections.ObservableList;

public interface ItemBo extends SuperBo {

    boolean saveItem(Item dto);
    public Item search(String itemCode);
    public ObservableList<Item> lordItem();
}
