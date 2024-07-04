package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.UserEntity;
import javafx.collections.ObservableList;

public interface UserDao extends CrudDao<UserEntity> {
    public UserEntity searchUser(String userEmail);
    public ObservableList<UserEntity> allUsers();
    public UserEntity searchUserById(String userId);
}
