package dal.dao;

import dal.dto.IngredientDTO;

import java.util.List;

public interface I_IngredientDAO {

    int getSumOfIngredient() throws I_IngredientDAO.DALException; // ental
    void getIngredient (int ingredientId) throws I_IngredientDAO.DALException;
    List<IngredientDTO> getReorders()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsAll()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsActive()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsInActive()throws I_IngredientDAO.DALException;
    void CreateIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    void UpdateIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    void DeleteIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    IngredientDTO getIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    IngredientDTO getIngredient(String SearchParameter)throws I_IngredientDAO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
