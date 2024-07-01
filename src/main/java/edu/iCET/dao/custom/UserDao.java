package edu.iCET.dao.custom;

import edu.iCET.dao.CrudDao;
import edu.iCET.entity.UserEntity;

public interface UserDao extends CrudDao<UserEntity> {
    public UserEntity searchUser(String userEmail);
}
