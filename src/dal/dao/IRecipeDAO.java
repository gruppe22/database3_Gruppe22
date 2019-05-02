package dal.dao;

import dal.dto.RecipeDTO;

import java.util.List;

public interface IRecipeDAO {
    RecipeDTO getRecipe(int recipeId) throws IRecipeDAO.DALException;
    List<RecipeDTO> getRecipeList() throws IRecipeDAO.DALException;

    void createRecipe(RecipeDTO recipe) throws IRecipeDAO.DALException;
    void updateRecipe(RecipeDTO recipe) throws IRecipeDAO.DALException;
    void deleteRecipe(RecipeDTO recipe) throws IRecipeDAO.DALException;
    void deleteRecipe(int recipeId) throws IRecipeDAO.DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }
    }
}
