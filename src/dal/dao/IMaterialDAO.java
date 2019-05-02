package dal.dao;

import dal.dto.MaterialDTO;

import java.util.List;

public interface IMaterialDAO {
    MaterialDTO getMaterial(int materialId) throws IMaterialDAO.DALException;
    List<MaterialDTO> getMaterialList() throws IMaterialDAO.DALException;

    void createMaterial(MaterialDTO materialDTO) throws IMaterialDAO.DALException;
    void updateMaterial(MaterialDTO materialDTO) throws IMaterialDAO.DALException;
    void deleteMaterial(MaterialDTO materialDTO) throws IMaterialDAO.DALException;
    void deleteMaterial(int materialId) throws IMaterialDAO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
