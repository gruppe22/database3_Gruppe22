package dal.dao;

import dal.dto.ProductionDTO;
import dal.dto.RecipeDTO;
import dal.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

public interface I_ProductionDAO {
    ProductionDTO getProduction(int batchId) throws DALException;
    List<ProductionDTO> getProductionList(String completedSentences, String deletedSentence) throws DALException;

    void createProduction(ProductionDTO production) throws DALException;
    void updateProduction(ProductionDTO production) throws DALException;
    void deleteProduction(int productionId, String deletedSentence) throws DALException;
    void deleteProduction(ProductionDTO production , String deletedSentence) throws DALException;
    List<ProductionDTO> getProductionList() throws DALException;
    List<ProductionDTO> getProductionList(RecipeDTO recipe) throws DALException;
    List<ProductionDTO> getProductionList(UserDTO Leader) throws DALException;
    List<ProductionDTO> getProductionList(Timestamp date1 , Timestamp date2)  throws DALException;
    List<ProductionDTO> getProductionList(Timestamp afterThis) throws DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
