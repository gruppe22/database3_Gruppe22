package dal.dao;

import dal.dto.ProductionDTO;
import dal.dto.RecipeDTO;
import dal.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

public interface I_ProductionDAO {
    ProductionDTO getProduction(int batchId) throws I_ProductionDAO.DALException;
    List<ProductionDTO> getProductionList() throws I_ProductionDAO.DALException;

    void createProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void updateProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void deleteProduction(ProductionDTO production) throws I_ProductionDAO.DALException;
    void deleteProduction(int productionId) throws I_ProductionDAO.DALException;
    List<ProductionDTO> getProductionListALL();
    List<ProductionDTO> getProductionList(RecipeDTO recipe);
    List<ProductionDTO> getProductionList(UserDTO Leader);
    List<ProductionDTO> getProductionList(Timestamp date1 , Timestamp date2);
    List<ProductionDTO> getProductionList(Timestamp afterThis);

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
