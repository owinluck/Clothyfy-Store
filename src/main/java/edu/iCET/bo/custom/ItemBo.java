package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Item;
import edu.iCET.dto.Supplirs;

public interface ItemBo extends SuperBo {

    boolean saveItem(Item dto);
}
