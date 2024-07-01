package edu.iCET.bo.custom.impl;

import edu.iCET.bo.custom.UserBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.SupplirDao;
import edu.iCET.dao.custom.UserDao;
import edu.iCET.dto.Supplirs;
import edu.iCET.dto.User;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.entity.UserEntity;
import edu.iCET.util.DaoType;
import org.modelmapper.ModelMapper;

public class UserBoImpl implements UserBo {

    private UserDao userDao=DaoFactory.getInstance().getDao(DaoType.User);
    @Override
    public boolean saveUser(User dto) {

        return userDao.save(new ModelMapper().map(dto, UserEntity.class));
    }

    public User search(String email){

        UserEntity userEntity = userDao.searchUser(email);

        return new ModelMapper().map(userEntity, User.class);
    }
}
