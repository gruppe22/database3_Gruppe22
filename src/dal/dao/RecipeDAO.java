package dal.dao;

import dal.dto.RecipeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements IRecipeDAO {
    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185015"
                + "?user=s185015&password=629FTiYG3DNSPQV3r4YIU");
    }

    @Override
    public void createRecipe(RecipeDTO recipe) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into Recipe values (?, ?, ?, ?)");
            statement.setInt(1, recipe.getRecipeId());
            statement.setString(2, recipe.getRecipeName());
            statement.setInt(3, recipe.getAuthorId());
            statement.setInt(4, recipe.getQuantity());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public RecipeDTO getRecipe(int recipeId) throws DALException {
        try (Connection c = createConnection()){

            RecipeDTO recipe = new RecipeDTO();

            PreparedStatement statement = c.prepareStatement("select * from Recipe where recipeId = ?");
            statement.setInt(1, recipeId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                recipe.setRecipeId(result.getInt("recipeId"));
                recipe.setRecipeName(result.getString("recipeName"));
                recipe.setAuthorId(result.getInt("authorId"));
                recipe.setQuantity(result.getInt("quantity"));
            }
            return recipe;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<RecipeDTO> getRecipeList() throws DALException {
        try (Connection connection = createConnection()) {
            List<RecipeDTO> recipeList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Recipe");

            while (resultSet.next()) {
                RecipeDTO recipe = new RecipeDTO();
                recipe.setRecipeId(resultSet.getInt("recipeId"));
                recipe.setRecipeName(resultSet.getString("recipeName"));
                recipe.setAuthorId(resultSet.getInt("authorId"));
                recipe.setQuantity(resultSet.getInt("quantity"));

                recipeList.add(recipe);
                }
            return recipeList;

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void updateRecipe(RecipeDTO recipe) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "update Recipe set recipeName = ?, authorId = ?, quantity = ? where userId = ?");
            statement.setString(1, recipe.getRecipeName());
            statement.setInt(2, recipe.getAuthorId());
            statement.setInt(3, recipe.getQuantity());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void deleteRecipe(int recipeId) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from Recipe where recipeId = ?");
            statement.setInt(1, recipeId);
            statement.execute();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void deleteRecipe(RecipeDTO recipe) throws DALException{
    }

}
