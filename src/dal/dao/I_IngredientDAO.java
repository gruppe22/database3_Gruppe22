package dal.dao;

import dal.dto.IngredientDTO;

import java.util.List;

public interface I_IngredientDAO {



    int getSumOfIngredient(int id) throws DALException; // ental

    List<IngredientDTO> getReorders()throws DALException;
    List<IngredientDTO> getIngredientsAll()throws DALException;
    List<IngredientDTO> getIngredientsActive()throws DALException;
    List<IngredientDTO> getIngredientsInActive()throws DALException;

    void createIngredient(IngredientDTO ingredient)throws DALException;
    void updateIngredient(IngredientDTO ingredient)throws DALException;
    void deleteIngredient(int id) throws DALException;
    void deleteIngredient(IngredientDTO ingredient)throws DALException;


    IngredientDTO getIngredient (int ingredientId) throws DALException;
    IngredientDTO getIngredient(IngredientDTO ingredient)throws DALException;


    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
