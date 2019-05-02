package dal.dao;

import dal.dto.IngredientDTO;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class IngredientDAO implements I_IngredientDAO {

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185015"
                + "?user=s185015&password=629FTiYG3DNSPQV3r4YIU");
    }

    @Override
    public int getSumOfIngredient() {
        return 0;
    }

    @Override
    public IngredientDTO getIngredient(int ingredientId) throws DALException {
        try (Connection c = createConnection()){

            IngredientDTO ingredient = new IngredientDTO();

            PreparedStatement statement = c.prepareStatement("select * from Ingredient where ingredientId = ?");
            statement.setInt(1, ingredientId;
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                ingredient.setIngredientId(result.getInt("ingredientId"));
                ingredient.setName(result.getString("name"));
                ingredient.setActive(result.getBoolean("active"));
                ingredient.setReOrder(result.getBoolean("reOrder"));
            }
            return ingredient;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getReorders() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new ArrayList<>();

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Ingredient where reOrder = 1");

                while (resultSet.next()) {
                    IngredientDTO ingredient = new IngredientDTO();
                    ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                    ingredient.setName(resultSet.getString("name"));
                    ingredient.setActive(resultSet.getBoolean("active"));
                    ingredient.setReOrder(resultSet.getBoolean("reOrder"));

                    ingredientList.add(ingredient);
                }
                return ingredientList;

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
    }


    @Override
    public List<IngredientDTO> getIngredientsAll() throws DALException{
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Ingredient");

            while (resultSet.next()) {
                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setActive(resultSet.getBoolean("active"));
                ingredient.setReOrder(resultSet.getBoolean("reOrder"));

                ingredientList.add(ingredient);
                }
                return ingredientList;

        } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getIngredientsActive() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Ingredient where active = 1");

            while (resultSet.next()) {
                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setActive(resultSet.getBoolean("active"));
                ingredient.setReOrder(resultSet.getBoolean("reOrder"));

                ingredientList.add(ingredient);
            }
            return ingredientList;

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getIngredientsInActive() throws DALException{
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Ingredient where active = 0");

            while (resultSet.next()) {
                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientId(resultSet.getInt("ingredientId"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setActive(resultSet.getBoolean("active"));
                ingredient.setReOrder(resultSet.getBoolean("reOrder"));

                ingredientList.add(ingredient);
            }
            return ingredientList;

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void CreateIngredient(IngredientDTO ingredient) throws DALException {
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("insert into Ingredient values (?, ?, ?, ?)");
            statement.setInt(1, ingredient.getIngredientId());
            statement.setString(2, ingredient.getName());
            statement.setBoolean(3, ingredient.isActive());
            statement.setBoolean(3, ingredient.isReOrder());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }

    }

    @Override
    public void UpdateIngredient(IngredientDTO ingredient) {

    }

    @Override
    public void DeleteIngredient(IngredientDTO ingredient) {

    }

    @Override
    public IngredientDTO getIngredient(IngredientDTO ingredient) {
        return null;
    }

    @Override
    public IngredientDTO getIngredient(String SearchParameter) {
        return null;
    }
}
