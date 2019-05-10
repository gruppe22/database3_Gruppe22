package test;

import dal.dao.*;
import dal.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductionDAOTest {
    // Utility Methods setting up tests data
    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
    }

    // SETUP NECESARY FUNCTIONS
    private IngredientDTO getSomeIngredients() throws Exception{
        IngredientDAO dao = new IngredientDAO();
        IngredientDTO dto = new IngredientDTO();
        dto.setAmount(1000);
        dto.setName("myIngredient");
        dto.setReOrder(false);
        dto.setActive(true);
        dto.setIngredientId(25);
        dto.setExpired(false);
        dao.createIngredient(dto); // Makes 1 New ingredient. no relational table entries.
        return dto;
    }
    private MaterialDTO   getSomeMaterials(IngredientDTO ingredients, int batchIdStart, int amount)throws Exception{
        MaterialDAO mDao = new MaterialDAO();
        MaterialDTO dto = new MaterialDTO();
        dto.setName("mitMaterialName");
        dto.setSupplier("min supplier");
        dto.setBatchId(25);
        dto.setExpired(false);
        dto.setIngredientId( ingredients.getIngredientId() );
        dto.setQuantity(1000);
        mDao.createMaterial(dto); // creates Material and returns
        return dto;
    }
    private RecipeDTO getRecipeDTO(IngredientDTO ingredients) throws Exception{

        RecipeDTO dto = new RecipeDTO();
        dto.setAuthorId(1);
        dto.setDescription("myDescription");
        dto.setName("myRecipetest");
        dto.setQuantity(500);
        dto.setRecipeId(25);
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        dto.setEndDate(dateTime );
        List<IngredientDTO> ingres = new LinkedList<>();
        ingres.add(ingredients);
        dto.setIngredients(ingres);// getsomeIngredients defined above.
        RecipeDAO recipeDAO = new RecipeDAO();
        recipeDAO.createRecipe(dto);
        return dto;
    }
    private ProductionDTO getTestProd(RecipeDTO recipe)throws Exception{
        ProductionDAO dao = new ProductionDAO();
        ProductionDTO dto = new ProductionDTO();
        // actual Attrs
        dto.setStatus("onGoing");
        dto.setProductionId(25);
        dto.setQuantity(4000);
        // FOREIGN KEYS
        //recipe - taking theese from the Premade Recipe.
        dto.setRecipeId(        recipe.getRecipeId() );
        dto.setRecipeEndDate(   recipe.getEndDate()  );
        //production Leaders // assuming 1, because dummy data 1 has all roles.
        dto.setProduktionsLeaderID( 1 );
        dao.createProduction(dto); // Creates Production with no Materials.
        return dto;
    }


// TEARDOWN all of the setups

/* --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
        NOTE THAT TO RUN TESTS YOU NEED TO RUN A SQL SCRIPT BETWEEN TESTS; FOR SOME REASON IT DOSENT WORK WHEN AUTOMATED IN JAVA.

                -- removing relations
                delete from `relProdMat` where  `batchId` >= 25;
                delete from `relIngredientRecipe` where `ingredientId` >= 6;
                delete from `relIngredientRecipe` where `recipeId` >= 6;

                -- Ingredients + Materials,
                delete from `Materials` where `batchId` >= 25;
                delete FROM `Ingredient` where `ingredientId` >= 6;

                -- removing production
                delete from `Production` where  `productionId`= 6;

                -- removing the Recipe
                delete from `Recipe` where  `recipeId` =  6;

--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- */

    @Test
    private void tearDown1(Connection c)throws  Exception {
        try (Connection connection = createConnection() ) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM `relIngredientRecipe` WHERE `ingredientId` = 25;");
            statement.execute();

            statement = connection.prepareStatement("DELETE FROM `relProdMat` WHERE `batchId` = 25");
            statement.execute();

            MaterialDAO mDao = new MaterialDAO();
            mDao.SUPERdeleteMaterial(25);

            IngredientDAO idao = new IngredientDAO();
            idao.superDelete(25);

            ProductionDAO pDao = new ProductionDAO();
            pDao.superDelete(25);

            RecipeDAO rDao = new RecipeDAO();
            rDao.superDelete(25);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Asserting Methods
    private void assertMaterial( MaterialDTO exspected , MaterialDTO actual) {

        if (exspected != null) {
            assertEquals(exspected.isExpired(), actual.isExpired());
            assertEquals(exspected.getSupplier(), actual.getSupplier());
            assertEquals(exspected.getQuantity(), actual.getQuantity());
            assertEquals(exspected.getName(), actual.getName());
            assertEquals(exspected.getIngredientId(), actual.getIngredientId());
            assertEquals(exspected.getReservedAmount(), actual.getReservedAmount());
        } else {
            assert (false);
        }

    }
    private void assertProduction(ProductionDTO exspected, ProductionDTO actual){
        assertEquals(exspected.getProductionId(), actual.getProductionId());
        assertEquals(exspected.getStatus(), actual.getStatus());
        assertEquals(exspected.getRecipeId(), actual.getRecipeId());
        assertEquals(exspected.getRecipeEndDate(), actual.getRecipeEndDate());
        assertEquals(exspected.getQuantity(), actual.getQuantity());
        assertEquals(exspected.getProduktionsLeaderID(), actual.getProduktionsLeaderID());
    }

    @Test
    void createProduction() {
        try {

            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO material = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            ProductionDTO dto2 = dao.getProduction(production.getProductionId());
            assertProduction(production,dto2);

            for(int i = 0; i < dto2.getMateials().size(); i++){
                assertMaterial(production.getMateials().get(i), dto2.getMateials().get(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void getProductionList() {
        try {

            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO materials = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            List<ProductionDTO> dtoList = dao.getProductionList();

            boolean asserted = false;
            for(int u = 0; u < dtoList.size(); u++) {
                if(production.getProductionId() == dtoList.get(u).getProductionId()) {
                    asserted = true;
                    assertProduction(production, dtoList.get(u));
                    for (int i = 0; i < dtoList.get(u).getMateials().size(); i++) {
                        assertMaterial(production.getMateials().get(i), dtoList.get(u).getMateials().get(i));
                    }
                }else{

                }
            }
            assert(asserted);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void getProductionListALL(){
        try {
            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO materials = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            List<ProductionDTO> dtoList = dao.getProductionListALL();

            boolean asserted = false;
            for(int u = 0; u < dtoList.size(); u++) {
                if(production.getProductionId() == dtoList.get(u).getProductionId()) {
                    asserted = true;
                    assertProduction( production , dtoList.get(u) );
                    for (int i = 0; i < dtoList.get(u).getMateials().size(); i++) {
                        assertMaterial(production.getMateials().get(i), dtoList.get(u).getMateials().get(i));
                    }
                }else{

                }
            }
            assert(asserted);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void getProductionList(RecipeDTO recipe){
        try {

            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO materials = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe1 = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe1); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            List<ProductionDTO> dtoList = dao.getProductionList(recipe1);

            boolean asserted = false;
            for(int u = 0; u < dtoList.size(); u++) {
                if(production.getProductionId() == dtoList.get(u).getProductionId()) {
                    asserted = true;
                    assertProduction(production, dtoList.get(u));
                    for (int i = 0; i < dtoList.get(u).getMateials().size(); i++) {
                        assertMaterial(production.getMateials().get(i), dtoList.get(u).getMateials().get(i));
                    }
                }else{

                }
            }
            assert(asserted);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void updateProduction(){
        try {
            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO materials = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            production.setStatus("Dum And Annoying");
            dao.updateProduction(production);
            ProductionDTO dto2 = dao.getProduction(production.getProductionId());

            assertProduction(production,dto2);
            for(int i = 0; i < dto2.getMateials().size(); i++){
                assertMaterial(production.getMateials().get(i), dto2.getMateials().get(i));
            }

        }catch (Exception e){

        }
    }
    @Test
    void deleteProduction() {
        try {
            //1st Create Ingredients
            IngredientDTO ingredients = getSomeIngredients();

            //2nd Create Materials. for each ingredient
            MaterialDTO materials = getSomeMaterials( ingredients, 25, 3 );

            //3rd Create Recipe
            RecipeDTO recipe = getRecipeDTO( ingredients );

            //4th Create Production
            ProductionDTO production =  getTestProd(recipe); // this is a quick setup for testing purposes. now this also Creates a Production.

            ProductionDAO dao = new ProductionDAO();
            dao.deleteProduction(production);
            ProductionDTO dto2 = dao.getProduction(production.getProductionId());

            assertNotEquals(production.getStatus() , dto2.getStatus());
        }catch (Exception e){

        }
    }

}