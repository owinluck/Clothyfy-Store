package edu.iCET.bo.custom.impl;

import edu.iCET.bo.custom.ItemBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.ItemDao;
import edu.iCET.dto.Item;
import edu.iCET.entity.ItemEntity;
import edu.iCET.util.DaoType;
import org.modelmapper.ModelMapper;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.Item);
    @Override
    public boolean saveItem(Item dto) {
        return itemDao.save(new ModelMapper().map(dto, ItemEntity.class));
    }
}
