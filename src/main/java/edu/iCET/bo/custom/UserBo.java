package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Supplirs;
import edu.iCET.dto.User;

public interface UserBo extends SuperBo {

    boolean saveUser(User dto);
    public User search(String email);
}
