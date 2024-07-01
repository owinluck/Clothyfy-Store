package edu.iCET.dao.custom.impl;

import edu.iCET.dao.custom.ItemDao;
import edu.iCET.db.DbConnection;
import edu.iCET.entity.CustomerEntity;
import edu.iCET.entity.ItemEntity;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.CrudUtil;
import edu.iCET.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity entity) {
        Session session= HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public ItemEntity searchItem(String itemCode){

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM itementity WHERE itemCode='"+ itemCode+"'");

            while (resultSet.next()){
                return new ItemEntity(
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getInt(6),
                        resultSet.getInt(2),
                        resultSet.getInt(7),
                        resultSet.getString(10),
                        resultSet.getString(8),
                        resultSet.getDouble(5),
                        resultSet.getString(9),
                        resultSet.getString(4)
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ObservableList<ItemEntity> allItem(){
        ObservableList<ItemEntity> itemList= FXCollections.observableArrayList();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM itementity");

            while (resultSet.next()){
                itemList.add(new ItemEntity(
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getInt(6),
                        resultSet.getInt(2),
                        resultSet.getInt(7),
                        resultSet.getString(10),
                        resultSet.getString(8),
                        resultSet.getDouble(5),
                        resultSet.getString(9),
                        resultSet.getString(4)
                        )
                );
            }
            //System.out.println(customerList);
            return itemList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateItemEntity(ItemEntity itemEntity){


        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("UPDATE itementity set buyingPrice='"+itemEntity.getBuyingPrice()+"'"+", description='"+itemEntity.getDescription()+"'"+", imgUrl='"+itemEntity.getImgUrl()+"'"+", profit='"+itemEntity.getProfit()+"'"+", qty='"+itemEntity.getQty()+"'"+", sellingPrice='"+itemEntity.getSellingPrice()+"'"+", size='"+itemEntity.getSize()+"'"+", supplierId='"+itemEntity.getSupplierId()+"'"+", type='"+itemEntity.getType()+"'"+" WHERE itemCode='"+itemEntity.getItemCode()+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteItem(String itemCode){

        try {
            return DbConnection.getInstance().getConnection().createStatement().execute("DELETE FROM  itementity WHERE itemCode='"+itemCode+"'");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
