package dal.dao;

import dal.dto.RecipeDTO;

import java.util.List;

public interface IRecipeDAO {
    RecipeDTO getRecipe(int recipeId) throws DALException;
    List<RecipeDTO> getRecipeList() throws DALException;
    List<RecipeDTO> getRecipeListSpecifik(int id)throws DALException;
    void createRecipe(RecipeDTO recipe) throws DALException;
    void updateRecipe(RecipeDTO recipe) throws DALException;
    void deleteRecipe(RecipeDTO recipe) throws DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
