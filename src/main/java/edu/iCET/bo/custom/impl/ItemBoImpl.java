package edu.iCET.bo.custom.impl;

import edu.iCET.bo.custom.ItemBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.ItemDao;
import edu.iCET.dto.Customer;
import edu.iCET.dto.Item;
import edu.iCET.dto.Supplirs;
import edu.iCET.entity.CustomerEntity;
import edu.iCET.entity.ItemEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.Item);
    @Override
    public boolean saveItem(Item dto) {

        return itemDao.save(new ModelMapper().map(dto, ItemEntity.class));
    }

    public Item search(String itemCode){

        ItemEntity itemEntity = itemDao.searchItem(itemCode);

        return new ModelMapper().map(itemEntity, Item.class);
    }

    public ObservableList<Item> lordItem(){

        ObservableList<Item> items= FXCollections.observableArrayList();
        ObservableList<ItemEntity> itemEntities = itemDao.allItem();

        itemEntities.forEach(itemEntity ->{
            items.add(new Item(
                    itemEntity.getItemCode(),
                    itemEntity.getDescription(),
                    itemEntity.getQty(),
                    itemEntity.getBuyingPrice(),
                    itemEntity.getSellingPrice(),
                    itemEntity.getType(),
                    itemEntity.getSize(),
                    itemEntity.getProfit(),
                    itemEntity.getSupplierId(),
                    itemEntity.getImgUrl()
            ));
        });
        return items;
    }

    public boolean update(Item item){


        return itemDao.updateItemEntity(new ModelMapper().map(item, ItemEntity.class));
    }
}