package dal.dao;

import dal.dto.ProductionDTO;

import java.util.List;

public interface I_ProductionDAO {
    ProductionDTO getProduction(int batchId) throws I_ProductionDAO.DALException;
    List<ProductionDTO> getProductionList() throws I_ProductionDAO.DALException;

    void createProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void updateProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void deleteProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void deleteProduction(int productionId) throws I_ProductionDAO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
