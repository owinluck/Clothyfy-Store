package edu.iCET.bo.custom;

import edu.iCET.bo.SuperBo;
import edu.iCET.dto.Supplirs;
import edu.iCET.dto.User;
import javafx.collections.ObservableList;

public interface UserBo extends SuperBo {

    boolean saveUser(User dto);
    public User search(String email);
    public ObservableList<User> lordUser();
    public User searchbyId(String id);
}
