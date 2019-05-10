package dal.dao;

import dal.dto.*;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;

public class ProductionDAO implements I_ProductionDAO {
        // Util methods for simplyfying Coding.
        private Connection createConnection() throws SQLException {
            Connector connector = new Connector();
            return connector.createConnection();
        }
        private ProductionDTO createDtoFromRes(ResultSet res) throws Exception{
            ProductionDTO dto = new ProductionDTO();

            dto.setStatus(res.getString("status"));
            dto.setProductionId(res.getInt("productionId"));
            dto.setProduktionsLeaderID(res.getInt("productionLeaderId"));
            dto.setQuantity(res.getInt("quantity"));
            dto.setRecipeId(res.getInt("recipeId"));
            dto.setRecipeEndDate(res.getTimestamp("endDate"));

            return dto;
        }

        private List<MaterialDTO> getLinkedMaterials(int productionId){
            try (Connection connection = createConnection()) {
                List<MaterialDTO> mats = new LinkedList<>();
                PreparedStatement statement = connection.prepareStatement(
                    "select * from materials_pr_production  where `produktionId`=?;"
                );
                statement.setInt(1,productionId);
                ResultSet materials = statement.executeQuery();
                while(materials.next()){
                    MaterialDTO dto = new MaterialDTO();
                    dto.setReservedAmount( materials.getInt("reservedAmount") ); // if the material has bin assigned then the reserved ammount has already bin taken from the quantity;
                    dto.setQuantity( materials.getInt("quantity") );
                    dto.setIngredientId( materials.getInt("ingredientId") );
                    dto.setExpired( materials.getBoolean("expired"));
                    dto.setBatchId( materials.getInt("batchId"));
                    dto.setSupplier( materials.getString("supplier"));
                    dto.setName("batchName");
                }
                return mats;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }
        private List<MaterialDTO> getLinkedMaterials(ProductionDTO dto){
            return getLinkedMaterials(dto);
        }
        public void assignRandomMaterials(ProductionDTO production) throws Exception{
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement(
                        "Select DISTINCT `ingredientId` " +
                                "from `batch_pr_Ingredient_pr_Recipe` " +
                                "where `recipeId`= ?;"
                );
                ResultSet Ingredients = statement.executeQuery();

                List<MaterialDTO> ingredientsMaterials = new LinkedList<>();
                while(Ingredients.next()){

                    String sql = "select DISTINCT `batchId`,`batchName`,`remainingQuantity`, `supplier`,`requiredAmount` from batch_pr_Ingredient_pr_Recipe where `expired`=0 AND `ingredientId`=?";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, Ingredients.getInt("ingredientId"));
                    ResultSet materials = statement.executeQuery();

                    while(materials.next()){
                        if(materials.getInt("remainingQuantity") > materials.getInt("requiredAmount")){
                            // 1. get the Material DTO up and running.
                            // 2. process the remaining quantity.
                            // 3. process the data base quantity.


                            MaterialDTO mDto = new MaterialDTO();
                            mDto.setName(materials.getString("batchName"));
                            mDto.setSupplier(materials.getString("supplier"));
                            mDto.setBatchId(materials.getInt("batchId"));
                            mDto.setExpired(false); // the selection is from NOT expired batches.
                            mDto.setIngredientId(Ingredients.getInt("ingredientId"));

                            // NOW TO PROCES WITH DRAWING THE RESERVED AMOUNT FROM THE QUANTITY;
                            int remainingQuantity = materials.getInt("remainingQuantity") - materials.getInt("requiredAmount");
                            mDto.setQuantity(remainingQuantity);
                            mDto.setReservedAmount(materials.getInt("requiredAmount")); // set an int to tell how much is reserved.

                            // UPDATING THE QUANTITY IN THE DATA BASE, AFTER RESERVATION.
                            statement = connection.prepareStatement(
                                    "UPDATE `Materials`" +
                                            "SET `quantity`=?" +
                                            "WHERE `batchId`=? "
                            );
                            statement.setInt(1,remainingQuantity);
                            statement.setInt(2,materials.getInt("batchId") );
                            statement.executeUpdate();

                            ingredientsMaterials.add(mDto);
                            break;
                        }else{

                        }
                    }
                }

                production.setMateials(ingredientsMaterials);
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        // Rutine Calls
        private ProductionDTO createProductionDto(ResultSet res) throws Exception{
            ProductionDTO production = new ProductionDTO();
            production.setProductionId(res.getInt("productionId"));
            production.setQuantity(res.getInt("quantity"));
            production.setStatus(res.getString("status"));
            production.setRecipeId(res.getInt("recipeId"));
            production.setRecipeEndDate(res.getTimestamp("recipeEndDate"));
            production.setProduktionsLeaderID(res.getInt("produktionsLeaderID"));
            return production;
        }

        @Override
        public void createProduction(ProductionDTO production) throws DALException {
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("insert into `Production`(`productionId`,`quantity`,`status`,`recipeId`,`recipeEndDate`,`produktionsLeaderId`) values (?,?,?,?,?,?)");
                statement.setInt(1, production.getProductionId());
                statement.setInt(2, production.getQuantity());
                statement.setString(3, production.getStatus());
                statement.setInt(4,production.getRecipeId());
                statement.setTimestamp(5, production.getRecipeEndDate());
                statement.setInt(6,production.getProduktionsLeaderID());
                statement.execute();

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public ProductionDTO getProduction (int productionId) throws DALException {
            try (Connection c = createConnection()){

                ProductionDTO production = new ProductionDTO();

                PreparedStatement statement = c.prepareStatement("select * from `Production` where `productionId` = ?");
                statement.setInt(1, productionId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    production.setProductionId(result.getInt("productionId"));
                    production.setQuantity(result.getInt("quantity"));
                    production.setStatus(result.getString("status"));
                    production.setRecipeId(result.getInt("recipeId"));
                    production.setRecipeEndDate(result.getTimestamp("recipeEndDate"));
                    production.setProduktionsLeaderID(result.getInt("produktionsLeaderID"));
                }

                List<MaterialDTO> materials = getLinkedMaterials(production);
                production.setMateials(materials);

                return production;

            } catch (Exception e) {

                throw new DALException(e.getMessage());

            }
        }

        @Override
        public List<ProductionDTO> getProductionList() throws DALException {
            try (Connection connection = createConnection()) {

                List<ProductionDTO> productionList = new LinkedList<>();
                PreparedStatement statement = connection.prepareStatement("select * from `Production` where `expired` = 0");
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    ProductionDTO production = createProductionDto(res);
                    productionList.add(production);
                }

                return productionList;

            } catch (Exception e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public void updateProduction(ProductionDTO production) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "update Production set quantity =?, status = ?, recipeId = ?, recipeEndDate = ?, produktionsLeaderId = ? where productionId = ?");
                statement.setInt(2, production.getQuantity());
                statement.setString(3, production.getStatus());
                statement.setInt(4,production.getRecipeId());
                statement.setTimestamp(5, production.getRecipeEndDate());
                statement.setInt(6,production.getProduktionsLeaderID());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteProduction(int productionId) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement("update `Production` SET `status`= 'deleted' where `productionId`= ?");
                statement.setInt(1,productionId);
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteProduction(ProductionDTO production) throws DALException{
            deleteProduction(production.getProductionId());
        }

        @Override
        public List<ProductionDTO> getProductionListALL(){
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("select * from `Production`");
                ResultSet res = statement.executeQuery();
                List<ProductionDTO> productionList = new LinkedList<>();

                while (res.next()) {
                    ProductionDTO production = createProductionDto(res);
                    productionList.add(production);
                }
                return productionList;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<ProductionDTO> getProductionList(RecipeDTO recipe){
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("select * from `Production` where  `recipeId` =  ? ");
                statement.setInt( 1,recipe.getRecipeId() );
                ResultSet res = statement.executeQuery();
                List<ProductionDTO> productionList = new LinkedList<>();

                while (res.next()) {
                    ProductionDTO production = createProductionDto(res);
                    productionList.add(production);
                }

                return productionList;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<ProductionDTO> getProductionList(UserDTO Leader) {

            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("select * from `Production` where  `recipeId` =  ? ");
                statement.setInt(1, Leader.getUserId() );
                ResultSet res = statement.executeQuery();
                List<ProductionDTO> productionList = new LinkedList<>();

                while (res.next()) {
                    ProductionDTO production = createProductionDto(res);
                    productionList.add(production);
                }

                return productionList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        public List<ProductionDTO> getProductionList(Timestamp lesserThan , Timestamp greaterThan){
            try (Connection connection = createConnection()) {


                java.sql.Timestamp dateTime1 = new java.sql.Timestamp( lesserThan.getTime() );
                java.sql.Timestamp dateTime2 = new java.sql.Timestamp( greaterThan.getTime() );

                PreparedStatement statement = connection.prepareStatement("select * from `Production` where  `recipeEndDate` >  ? AND `recipeEndDate` < ? ");
                statement.setTimestamp(1,dateTime1);
                statement.setTimestamp(2,dateTime2);
                ResultSet res = statement.executeQuery();
                List<ProductionDTO> productionList = new LinkedList<>();

                while (res.next()) {
                    ProductionDTO production = createProductionDto(res);
                    productionList.add(production);
                }

                return productionList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<ProductionDTO> getProductionList(Timestamp afterThis){
        try (Connection connection = createConnection()) {


            java.sql.Timestamp dateTime1 = new java.sql.Timestamp( afterThis.getTime() );
            PreparedStatement statement = connection.prepareStatement("select * from `Production` where  `recipeEndDate` >  ? AND `recipeEndDate` < ? ");
            statement.setTimestamp(1,afterThis);
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

            return productionList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        public void superDelete(int id){
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("delete from `Production` where  `productionId` = ? ");
                statement.setInt(1, id);
                statement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
