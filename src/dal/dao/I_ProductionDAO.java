package dal.dao;

import dal.dto.ProductionDTO;

public interface I_ProductionDAO {
    ProductionDTO getBatch(int batchId) throws I_ProductionDAO.DALException;

    void createBatch(ProductionDTO batch) throws I_ProductionDAO.DALException;
    void updateBatch(ProductionDTO batch) throws I_ProductionDAO.DALException;
    void deleteBatch(ProductionDTO batch) throws I_ProductionDAO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
