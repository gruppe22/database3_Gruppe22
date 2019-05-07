package dal.dao;

import dal.dto.IngredientDTO;

import java.util.List;

public interface I_IngredientDAO {



    int getSumOfIngredient(int id) throws I_IngredientDAO.DALException; // ental

    List<IngredientDTO> getReorders()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsAll()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsActive()throws I_IngredientDAO.DALException;
    List<IngredientDTO> getIngredientsInActive()throws I_IngredientDAO.DALException;

    void createIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    void updateIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;
    void deleteIngredient(int id);
    void deleteIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;


    IngredientDTO getIngredient (int ingredientId) throws I_IngredientDAO.DALException;
    IngredientDTO getIngredient(IngredientDTO ingredient)throws I_IngredientDAO.DALException;


    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
