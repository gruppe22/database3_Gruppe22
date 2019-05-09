package test;

import com.mysql.cj.result.SqlDateValueFactory;
import dal.dao.Connector;
import dal.dao.IRecipeDAO;
import dal.dao.IngredientDAO;
import dal.dao.RecipeDAO;
import dal.dto.IngredientDTO;
import dal.dto.RecipeDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDAOTest {
    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
    }
    private  List<IngredientDTO> getSomeIngredients(){
        IngredientDAO dao = new IngredientDAO();
        List<IngredientDTO> ingre = new LinkedList<>();
        ingre.add(dao.getIngredient(1));
        ingre.add(dao.getIngredient(2));
        ingre.add(dao.getIngredient(3));
        ingre.add(dao.getIngredient(4));
        ingre.add(dao.getIngredient(5));
        return ingre;
    }
    private void assertIngredientsLists(List<IngredientDTO> exspectedList, List<IngredientDTO> ActualList ){
        for(int i = 0; i < exspectedList.size(); i++){
            assertEquals(exspectedList.get(i).getIngredientId() , ActualList.get(i).getIngredientId() );
        }
    }
    private RecipeDTO getTestDTO(){
        RecipeDTO dto = new RecipeDTO();
        dto.setAuthorId(1);
        dto.setDescription("do this then that. bub");
        dto.setName("myRecipe");
        dto.setQuantity(500);
        dto.setRecipeId(6);
        dto.setIngredients(getSomeIngredients());// getsomeIngredients defined above.

        return dto;
    }
    private void assertRecipes( RecipeDTO exspected,  RecipeDTO actual){

        assertEquals( exspected.getDescription(), actual.getDescription()           );
        assertEquals( exspected.getName()       , actual.getName()                  );
        assertEquals( exspected.getAuthorId()   , actual.getAuthorId()              );
        assertEquals( exspected.getQuantity()   , actual.getQuantity()              );
        assertIngredientsLists( actual.getIngredients() , exspected.getIngredients()  );

    }
    @AfterEach
    void tearDown() {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete FROM `relIngredientRecipe` where `recipeId` = 6;");
            statement.executeUpdate();
            statement = connection.prepareStatement("delete from `Recipe` where `recipeId`= 6;");
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createRecipe() {
        RecipeDAO dao = new RecipeDAO();
        RecipeDTO dto = getTestDTO();

        try {

            dao.createRecipe(dto);
            RecipeDTO dto2 = dao.getRecipe(dto.getRecipeId());
            assertRecipes(dto,dto2);

            dao.superDelete(dto);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getRecipe() {
        RecipeDAO dao = new RecipeDAO();
        RecipeDTO dto = getTestDTO();
        try {
            dao.createRecipe(dto);
            RecipeDTO newdto = dao.getRecipe(dto.getRecipeId());
            assertRecipes( newdto , dto);
            dao.superDelete(dto.getRecipeId());


        }catch (Exception e){
            e.printStackTrace();
        }
        assert(true);
    }

    @Test
    void getRecipeList() {
        RecipeDAO dao = new RecipeDAO();
        try {
            RecipeDTO dto = getTestDTO();
            dao.createRecipe(dto);
            List<RecipeDTO> DtoList = dao.getRecipeList();

            for(int i = 0; i < DtoList.size(); i++){
                if(DtoList.get(i).getRecipeId() == dto.getRecipeId()){
                    assert(true);
                }
                else{
                    assert(false);
                }
            }

            dao.superDelete(dto);
        }catch (Exception e){

        }



    }

    @Test
    void updateRecipe() {
        RecipeDAO dao = new RecipeDAO();
        RecipeDTO dto = getTestDTO();

        try {
            dto.setName("version 1");
            dao.createRecipe(dto);
            dto.setName("version 2");
            dao.updateRecipe(dto);
            //TimeUnit.SECONDS.sleep(1);
            dto.setName("version 3");
            dao.updateRecipe(dto);

            List<RecipeDTO> dtoList = dao.getRecipeListSpecifik(dto.getRecipeId());

            assertNotEquals(dtoList.get(0).getName(),dtoList.get(1).getName() );
            assertNotEquals(dtoList.get(0).getName(),dtoList.get(2).getName() );
            assertNotEquals(dtoList.get(2).getName(),dtoList.get(1).getName() );

            List<IngredientDTO> exspected = dto.getIngredients();
            for(int i = 0 ; i < dtoList.size() ; i++){

                // do all of them have all of the ingredients aswell?? VERY IMPORTANT!
                assertIngredientsLists(exspected, dtoList.get(i).getIngredients());

            }

            dao.superDelete(dto);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteRecipe() {
        RecipeDAO dao = new RecipeDAO();
        RecipeDTO dto = getTestDTO();
        try {
            dao.createRecipe(dto);
            dao.deleteRecipe(dto);
            List<RecipeDTO> list = dao.getRecipeListSpecifik(dto.getRecipeId());

            boolean asserted = false ;
            for(int i = 0 ; i < list.size(); i++){
                if(list.get(i).getName().equals(dto.getName()) ){
                    asserted = true;
                }
            }
            assert(asserted);

            dao.superDelete(dto);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void getRecipeListSpecifik() {
        RecipeDAO dao = new RecipeDAO();
        RecipeDTO dto = getTestDTO();

        try {
            dto.setName("version 1");
            dao.createRecipe(dto);
            dto.setName("version 2");
            dao.updateRecipe(dto);
            //TimeUnit.SECONDS.sleep(1);
            dto.setName("version 3");
            dao.updateRecipe(dto);

            List<RecipeDTO> dtoList = dao.getRecipeListSpecifik(dto.getRecipeId());

            assertNotEquals(dtoList.get(0).getName(),dtoList.get(1).getName() );
            assertNotEquals(dtoList.get(0).getName(),dtoList.get(2).getName() );
            assertNotEquals(dtoList.get(2).getName(),dtoList.get(1).getName() );

            // i am exspecting NO time will be the same so if i get 3 different recipees with different timers then its asserted to be true
            // so every time i get check a Recipe i want to add the timestamp to a checker lsit
            assertNotEquals(dtoList.get(0).getEndDate(), dtoList.get(1).getEndDate());
            assertNotEquals(dtoList.get(0).getEndDate(), dtoList.get(2).getEndDate());
            assertNotEquals(dtoList.get(2).getEndDate(), dtoList.get(1).getEndDate());

            // Now just making sure that all the Ids are INFACT the same recipee.
            assertEquals(dtoList.get(0).getRecipeId(), dtoList.get(1).getRecipeId());
            assertEquals(dtoList.get(0).getRecipeId(), dtoList.get(2).getRecipeId());
            assertEquals(dtoList.get(2).getRecipeId(), dtoList.get(1).getRecipeId());

            dao.superDelete(dto);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
