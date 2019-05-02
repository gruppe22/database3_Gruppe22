package dal.dao;

import dal.dto.MaterialDTO;

public interface IMaterialDAO {
    MaterialDTO getMaterial(int materialId) throws MaterialDTO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
