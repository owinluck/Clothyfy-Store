package edu.iCET.bo.custom.impl;

import edu.iCET.bo.custom.SupplierBo;
import edu.iCET.dao.DaoFactory;
import edu.iCET.dao.custom.SupplirDao;
import edu.iCET.dto.Supplirs;
import edu.iCET.entity.SupplirEntity;
import edu.iCET.util.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;

public class SupplierBoImpl implements SupplierBo {

    private SupplirDao supplirDao= DaoFactory.getInstance().getDao(DaoType.Supplirs);
    @Override
    public boolean saveSupplier(Supplirs dto) {

        return supplirDao.save(new ModelMapper().map(dto, SupplirEntity.class));
    }

    public ObservableList<Supplirs> lordCustomers(){

        ObservableList<Supplirs> supplirs= FXCollections.observableArrayList();
        ObservableList<SupplirEntity> supplirEntities = supplirDao.allSuppliers();

        supplirEntities.forEach(supplirEntity ->{
            supplirs.add(new Supplirs(
                supplirEntity.getSupplierId(),
                supplirEntity.getName(),
                supplirEntity.getCompany(),
                supplirEntity.getEmail(),
                supplirEntity.getImageUrl()
            ));
        });
        return supplirs;
    }

    public Supplirs search(String id){

        SupplirEntity supplirEntity = supplirDao.searchSupplier(id);

        return new ModelMapper().map(supplirEntity, Supplirs.class);
    }

    public boolean update(Supplirs dto) {

        return supplirDao.updateSupplierEntity(new ModelMapper().map(dto, SupplirEntity.class));
    }

    public boolean delete(String id){

        return supplirDao.deleteSupplier(id);
    }
}
