package dal.dao;

import dal.dto.IngredientDTO;
import dal.dto.RecipeDTO;

import java.sql.*;
import java.util.*;

public class RecipeDAO implements IRecipeDAO {
    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
    }
    private Timestamp getThisDate()throws Exception{
        // MySQL needs a date in a specifik Format, this was the method that worked to achive the right format and a SQL date.
        java.sql.Timestamp dateTime = new java.sql.Timestamp(System.currentTimeMillis());
        // Date dateFormated =  date.valueOf(date.toString());
        return dateTime;
    }
    private List<IngredientDTO> getIngredsRelationalTable(RecipeDTO recipe, Connection connection) throws SQLException {
        PreparedStatement ingStatement;
        if(recipe.getEndDate() ==null){
            ingStatement = connection.prepareStatement("SELECT * FROM `Ingredient` t1 INNER JOIN (SELECT * FROM `relIngredientRecipe` Where `recipeId`=? AND `endDate`= '9999-12-31 23:59:59') t2 ON t1.`ingredientId` = t2.`ingredientId`");
        }
        else{
            ingStatement = connection.prepareStatement("SELECT * FROM `Ingredient` t1 INNER JOIN (SELECT * FROM `relIngredientRecipe` Where `recipeId`=? AND `endDate`=?) t2 ON t1.`ingredientId` = t2.`ingredientId`");
            ingStatement.setTimestamp(2, recipe.getEndDate());
        }
        ingStatement.setInt(1, recipe.getRecipeId());
        ResultSet res = ingStatement.executeQuery();

        List<IngredientDTO> ingres = new LinkedList<>();
        while (res.next()) {
            IngredientDTO ing =  new IngredientDTO();
            ing.setIngredientId(res.getInt("ingredientId"));
            ing.setName(res.getString("name"));
            ing.setActive(res.getBoolean("active"));
            ing.setReOrder(res.getBoolean("reOrder"));
            ing.setExpired(res.getBoolean("expired"));
            ing.setAmount(res.getInt("amount"));
            ingres.add(ing);
        }
        return ingres;
    }
    private RecipeDTO getDtoFromList(int dtoId, Timestamp time, List<RecipeDTO> list ){
        for(int i = 0; i < list.size() ; i++){
            if( list.get(i).getEndDate().getTime() == time.getTime() ){
                return list.get(i);
            }
        }//list.get(i).getRecipeId() == dtoId &&
        return null;
    }

    @Override
    public void createRecipe(RecipeDTO recipe) throws DALException {
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("insert into `Recipe` (`recipeId`, `endDate`, `name`, `authorId`, `description`, `quantity`) values (?,'9999-12-31 23:59:59',?,?,?,?)");
            statement.setInt(1, recipe.getRecipeId());
            statement.setString(2, recipe.getName());
            statement.setInt(3, recipe.getAuthorId());
            statement.setString(4,recipe.getDescription());
            statement.setInt(5, recipe.getQuantity());
            statement.executeUpdate();

            // INGREDIENTS PR RECIPE!
            for(int i = 0; i < recipe.getIngredients().size() ; i++){
                //insert into `relIngredientRecipe` (`ingredientId`,`amount`, `recipeId`, `endDate`)values(1,250,3,'2018-09-29 12:24:13');
                PreparedStatement ingreStatement = connection.prepareStatement("insert into `relIngredientRecipe` (`ingredientId`,`amount` ,`recipeId`, `endDate`) values (?,?,?,'9999-12-31 23:59:59')");
                int ingreId  = recipe.getIngredients().get(i).getIngredientId() ;
                int recipeId = recipe.getRecipeId() ;
                ingreStatement.setInt(1, ingreId );
                ingreStatement.setInt(2, recipe.getIngredients().get(i).getAmount() );
                ingreStatement.setInt(3, recipeId );
                ingreStatement.execute();
            }

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }
    @Override
    public RecipeDTO getRecipe(int recipeId) throws DALException {
        try (Connection connection = createConnection()){

            RecipeDTO recipe = new RecipeDTO();

            PreparedStatement statement = connection.prepareStatement("select * from Recipe where recipeId = ?");
            statement.setInt(1, recipeId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                recipe.setRecipeId(result.getInt("recipeId"));
                recipe.setEndDate(result.getTimestamp("endDate"));
                recipe.setName(result.getString("name"));
                recipe.setAuthorId(result.getInt("authorId"));
                recipe.setDescription(result.getNString("description"));
                recipe.setQuantity(result.getInt("quantity"));
            }
            // Now all the Ingredients..
            recipe.setIngredients(getIngredsRelationalTable(recipe, connection));

            return recipe;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public List<RecipeDTO> getRecipeList() throws DALException {
        try (Connection connection = createConnection()) {

            List<RecipeDTO> recipeList = new LinkedList<>();
            Timestamp date = getThisDate();

            PreparedStatement statement = connection.prepareStatement("select * from `Recipe` where `endDate`='9999-12-31 23:59:59'" );
            ResultSet res = statement.executeQuery();

            // for Each row gotten, create DTO , and insert all ingredientDTOs for that one
            while (res.next()) {
                RecipeDTO recipe = new RecipeDTO();
                recipe.setRecipeId(res.getInt("recipeId"));
                recipe.setEndDate(res.getTimestamp("endDate"));
                recipe.setName(res.getString("name"));
                recipe.setAuthorId(res.getInt("authorId"));
                recipe.setDescription(res.getNString("description"));
                recipe.setQuantity(res.getInt("quantity"));

                recipeList.add(recipe);
            }

            PreparedStatement ingStatement = connection.prepareStatement("SELECT * FROM (SELECT * from `relIngredientRecipe` where `endDate`='9999-12-31 23:59:59') t1  JOIN `Ingredient` t2 ON t1.`ingredientId` = t2.`ingredientId`; \n" );
            ResultSet ingRes = ingStatement.executeQuery();
            while (ingRes.next()) {
                int ingId = ingRes.getInt("recipeId");

                IngredientDTO ingDto = new IngredientDTO();
                ingDto.setIngredientId(ingRes.getInt("ingredientId"));
                ingDto.setName(ingRes.getString("expired"));
                ingDto.setActive(ingRes.getBoolean("active"));
                ingDto.setReOrder(ingRes.getBoolean("reOrder"));
                ingDto.setName(ingRes.getString("name"));
                ingDto.setAmount(ingRes.getInt("amount"));

                recipeList.get(ingId).addIngredient(ingDto);
            }
            return recipeList;

        } catch (Exception e) {

            throw new DALException(e.getMessage());

        }
    }
    @Override
    public List<RecipeDTO> getRecipeListSpecifik(int id){
        try (Connection connection = createConnection()) {

            List<RecipeDTO> recipeList = new LinkedList<>();
            // STEP ONE. get the recipees in question and put them Into a List
            PreparedStatement statement = connection.prepareStatement("select * from `Recipe` where `recipeId`= ?" );
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                RecipeDTO recipe = new RecipeDTO();
                recipe.setRecipeId(res.getInt("recipeId"));
                recipe.setEndDate(res.getTimestamp("endDate"));
                recipe.setName(res.getString("name"));
                recipe.setAuthorId(res.getInt("authorId"));
                recipe.setDescription(res.getNString("description"));
                recipe.setQuantity(res.getInt("quantity"));
                recipeList.add(recipe);
            }

            //STEP TWO gettting a table Where Ingredients are lined up with Coresponding Recipees.
            PreparedStatement ingStatement = connection.prepareStatement("SELECT * FROM `Ingredient` t1 INNER JOIN (SELECT * from `relIngredientRecipe` where `recipeId` = 6)  t2 ON t1.`ingredientId` = t2.`ingredientId`" );
            ResultSet ingRes = ingStatement.executeQuery();

            while (ingRes.next()) { // fejlen kommer af at jeg tager iden fra SQL recipy iden. og bruger den som loop index.
                int ingId = ingRes.getInt("recipeId");

                IngredientDTO ingDto = new IngredientDTO();
                ingDto.setIngredientId(ingRes.getInt("ingredientId"));
                ingDto.setName(ingRes.getString("expired"));
                ingDto.setActive(ingRes.getBoolean("active"));
                ingDto.setReOrder(ingRes.getBoolean("reOrder"));
                ingDto.setName(ingRes.getString("name"));
                ingDto.setAmount(ingRes.getInt("amount"));

                getDtoFromList(ingId, ingRes.getTimestamp("endDate") , recipeList).addIngredient(ingDto);
            }
            return recipeList;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void updateRecipe(RecipeDTO recipe) throws DALException {
        try(Connection connection = createConnection()) {
            RecipeDAO dao = new RecipeDAO();
            List<IngredientDTO> oldIngredients = getIngredsRelationalTable(recipe, connection);
            Timestamp today = getThisDate();

            // FIRST deleting the Foreign keys, to be able to change primary key of Recipees.
            PreparedStatement statement = connection.prepareStatement("delete FROM `relIngredientRecipe` where `recipeId` = ? AND `endDate`='9999-12-31 23:59:59'");
            statement.setInt(1, recipe.getRecipeId());
            statement.executeUpdate();


            // SECOND - changing primary key of the Recipe. and setting date = today
            statement = connection.prepareStatement("UPDATE `Recipe` SET `endDate`=? WHERE `recipeId`=? AND `endDate`='9999-12-31 23:59:59' ");
            statement.setTimestamp(1, today);
            statement.setInt(2, recipe.getRecipeId());
            statement.executeUpdate();

            // THIRD re-enabeling the ingredients for the old recipe. and setting the date to TODAY
            for(int i = 0 ; i < oldIngredients.size(); i++){
                statement = connection.prepareStatement("insert into `relIngredientRecipe` (`ingredientId`,`amount`, `recipeId`, `endDate`)values(?,?,?,?);");
                statement.setInt(1 , oldIngredients.get(i).getIngredientId()    );
                statement.setInt(2 , oldIngredients.get(i).getAmount()          );
                statement.setInt(3 , recipe.getRecipeId()                       );
                statement.setTimestamp(4 , today                                );
                statement.executeUpdate();
            }

            // FOURTH Creating a new Version of the Same Recipe.
            dao.createRecipe(recipe);

        } catch (Exception ex) {
            throw new DALException(ex.getMessage());
        }
    }
    @Override
    public void deleteRecipe(RecipeDTO recipe) throws DALException {
        try(Connection connection = createConnection()) {
            RecipeDAO dao = new RecipeDAO();
            List<IngredientDTO> oldIngredients = getIngredsRelationalTable(recipe, connection);
            Timestamp today = getThisDate();

            // FIRST deleting the Foreign keys, to be able to change primary key of Recipees.
            PreparedStatement statement = connection.prepareStatement("delete FROM `relIngredientRecipe` where `recipeId` = ? AND `endDate`='9999-12-31 23:59:59'");
            statement.setInt(1, recipe.getRecipeId());
            statement.executeUpdate();


            // SECOND - changing primary key of the Recipe. and setting date = today
            statement = connection.prepareStatement("UPDATE `Recipe` SET `endDate`=? WHERE `recipeId`=? AND `endDate`='9999-12-31 23:59:59' ");
            statement.setTimestamp(1, today);
            statement.setInt(2, recipe.getRecipeId());
            statement.executeUpdate();

            // THIRD re enabeling the ingredients for the old recipe. and setting the date to TODAY
            for (int i = 0; i < oldIngredients.size(); i++) {
                statement = connection.prepareStatement("insert into `relIngredientRecipe` (`ingredientId`,`amount`, `recipeId`, `endDate`)values(?,?,?,?);");
                statement.setInt(1, oldIngredients.get(i).getIngredientId());
                statement.setInt(2, oldIngredients.get(i).getAmount());
                statement.setInt(3, recipe.getRecipeId());
                statement.setTimestamp(4, today);
                statement.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void superDelete(int id) throws DALException{
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("delete FROM relIngredientRecipe where `recipeId` = ?");
            statement.setInt(1, id);
            statement.execute();

            statement = connection.prepareStatement("delete from `Recipe` where `recipeId` = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }
    public void superDelete(RecipeDTO dto) throws DALException{
        superDelete(dto.getRecipeId());
    }

}
