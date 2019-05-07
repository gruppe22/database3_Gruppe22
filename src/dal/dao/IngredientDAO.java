package dal.dao;

import dal.dto.IngredientDTO;
import dal.dto.MaterialDTO;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class IngredientDAO implements I_IngredientDAO {

    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
    }

    @Override
    public int getSumOfIngredient(int id) {
        try (Connection connection = createConnection()) {
        PreparedStatement statement = connection.prepareStatement("Select `quantity` FROM `Materials` WHERE `ingredientId` = ? AND `expired` = 0;");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        int inputs  = 0;
        int summary = 0;

        while(resultSet.next()){
            inputs++;
            summary = summary + resultSet.getInt("quantity");
        }

        return summary;

        } catch ( SQLException e ) {
            e.printStackTrace();
            return 0;
            //throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getReorders() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new LinkedList<>();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Ingredient WHERE reOrder = 1");
            ResultSet res = statement.executeQuery();

            while (res.next()) {

                IngredientDTO ingredient = new IngredientDTO();
                ingredient.setIngredientId(res.getInt("ingredientId"));
                ingredient.setName(res.getString("name"));
                ingredient.setActive(res.getBoolean("active"));
                ingredient.setReOrder(res.getBoolean("reOrder"));
                ingredientList.add(ingredient);

            }
            return ingredientList;

        } catch ( SQLException ex ) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getIngredientsAll() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new LinkedList<>();

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

        } catch ( SQLException ex ) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getIngredientsActive() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new LinkedList<>();

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

        } catch ( SQLException ex ) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public List<IngredientDTO> getIngredientsInActive() throws DALException {
        try (Connection connection = createConnection()) {
            List<IngredientDTO> ingredientList = new LinkedList<>();

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

        } catch ( SQLException ex ) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void createIngredient(IngredientDTO ingredient) throws DALException {
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("insert into `Ingredient`(`ingredientId`,`name`,`active`,`reOrder`,`expired`) values (?, ?, ?, ?,?)");
            statement.setInt(1, ingredient.getIngredientId());
            statement.setString(2, ingredient.getName());
            statement.setBoolean(3, ingredient.isActive());
            statement.setBoolean(4, ingredient.isReOrder());
            statement.setBoolean(5,false);
            statement.execute();

        } catch ( SQLException e ) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateIngredient(IngredientDTO ingredient) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "update Ingredient set name = ?, active = ?, reOrder =? where ingredientId = ?");
            statement.setString(1, ingredient.getName());
            statement.setBoolean(2, ingredient.isActive());
            statement.setBoolean(3, ingredient.isReOrder());
            statement.setInt(4, ingredient.getIngredientId());

            statement.executeUpdate();
        } catch ( SQLException ex ) {
            throw new DALException(ex.getMessage());
        }

    }

    @Override
    public void deleteIngredient(int id){
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("update Ingredient set expired = 1 where ingredientId = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch ( SQLException e ) {

            e.printStackTrace();
            //throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void deleteIngredient(IngredientDTO ingredient) throws DALException {
        deleteIngredient(ingredient.getIngredientId());
    }

     @Override
    public IngredientDTO getIngredient(int id){
         try (Connection connection = createConnection()) {


             PreparedStatement statement = connection.prepareStatement("select * from Ingredient where ingredientId = ?");
             statement.setInt(1, id);
             ResultSet result = statement.executeQuery();

             if (result.next()) {
                 IngredientDTO ingredient = new IngredientDTO();

                 ingredient.setIngredientId(result.getInt("ingredientId"));
                 ingredient.setName(result.getString("name"));
                 ingredient.setActive(result.getBoolean("active"));
                 ingredient.setReOrder(result.getBoolean("reOrder"));
                 ingredient.setExpired(result.getBoolean("expired"));
                 return ingredient;
             }
             else{
                 return null;
             }


         } catch ( SQLException e ) {
             e.printStackTrace();
             return null;
         }
     }

    @Override
    public IngredientDTO getIngredient(IngredientDTO ingredient) throws DALException {
        return getIngredient(ingredient.getIngredientId());
    }

    public void superDelete(int id){
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Ingredient WHERE `ingredientId` = ?");
            statement.setInt(1, id);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}




