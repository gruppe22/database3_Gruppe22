package dal.dao;

import dal.dto.RecipeDTO;

import java.sql.*;
import java.time.LocalDate;
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
            PreparedStatement statement = connection.prepareStatement("insert into Recipe values (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, recipe.getRecipeId());
            statement.setDate(2,recipe.getEndDate());
            statement.setString(3, recipe.getName());
            statement.setInt(4, recipe.getAuthorId());
            statement.setString(5,recipe.getDescription());
            statement.setInt(6, recipe.getQuantity());
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
                recipe.setEndDate(result.getDate("endDate"));
                recipe.setName(result.getString("name"));
                recipe.setAuthorId(result.getInt("authorId"));
                recipe.setDescription(result.getNString("description"));
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
            ResultSet resultSet = statement.executeQuery("select * from Recipe where endDate > ?");
            Date today = new Date(System.currentTimeMillis());

            while (resultSet.next()) {
                RecipeDTO recipe = new RecipeDTO();
                recipe.setRecipeId(resultSet.getInt("recipeId"));
                recipe.setEndDate(resultSet.getDate("endDate"));
                recipe.setName(resultSet.getString("name"));
                recipe.setAuthorId(resultSet.getInt("authorId"));
                recipe.setDescription(resultSet.getNString("description"));
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
                    "update Recipe set endDate =?, name = ?, authorId = ?, description =? quantity = ? where userId = ?");
            statement.setDate(2,recipe.getEndDate());
            statement.setString(3, recipe.getName());
            statement.setInt(4, recipe.getAuthorId());
            statement.setString(5, recipe.getDescription());
            statement.setInt(6, recipe.getQuantity());
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
