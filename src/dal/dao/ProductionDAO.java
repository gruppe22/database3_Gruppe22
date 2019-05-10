package dal.dao;

import dal.dto.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static dal.dao.Connector.*;

public class ProductionDAO implements I_ProductionDAO {
    // todo Connect to relProdMat -- and connect it to tests.
    // todo Create Methods to get Available materials, and assignthem, and assignthem Seperately.

    // Util methods for simplyfying Coding.
    private ProductionDTO createDtoFromRes(ResultSet res) throws SQLException{
        ProductionDTO dto = new ProductionDTO();

        dto.setStatus(res.getString("status"));
        dto.setProductionId(res.getInt("productionId"));
        dto.setProduktionsLeaderID(res.getInt("productionLeaderId"));
        dto.setQuantity(res.getInt("quantity"));
        dto.setRecipeId(res.getInt("recipeId"));
        dto.setRecipeEndDate(res.getTimestamp("endDate"));

        return dto;
    }
    private ProductionDTO createProductionDto(ResultSet res) throws SQLException{
        ProductionDTO production = new ProductionDTO();
        production.setProductionId(res.getInt("productionId"));
        production.setQuantity(res.getInt("quantity"));
        production.setStatus(res.getString("status"));
        production.setRecipeId(res.getInt("recipeId"));
        production.setRecipeEndDate(res.getTimestamp("recipeEndDate"));
        production.setProduktionsLeaderID(res.getInt("produktionsLeaderID"));
        return production;


    }
    private List<MaterialDTO> getLinkedMaterials(int productionId){
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            List<MaterialDTO> mats = new LinkedList<>();
            PreparedStatement statement = conn.prepareStatement(
                    "select * from materials_pr_production  where `productionId`=?;"
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

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

            return mats;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<MaterialDTO> getLinkedMaterials(ProductionDTO dto){
        return getLinkedMaterials(dto.getProductionId());
    }

// todo implement this.
    /*     public void assignMaterials(List<MaterialDTO> materials, ProductionDTO production) throws DALException {
            try (Connection conn = static_createConnection()) {
      lock  static_lockTables(conn, "Materials");


            for(MaterialDTO material : materials){
                PreparedStatement statement = conn.prepareStatement("INSERT INTO `relProdMat` (`produktionId`,`amount`,`batchId`) values (?,?,?)")
                statement.setInt(1,production.getProductionId());
                statement.setInt(2,pr);
                statement.setInt(3,);
            }
    unlock static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }*/

    @Override
    public void createProduction(ProductionDTO production) throws DALException {
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);


            PreparedStatement statement = conn.prepareStatement("insert into `Production`(`productionId`,`quantity`,`status`,`recipeId`,`recipeEndDate`,`produktionsLeaderId`) values (?,?,?,?,?,?)");
            statement.setInt(1, production.getProductionId());
            statement.setInt(2, production.getQuantity());
            statement.setString(3, production.getStatus());
            statement.setInt(4,production.getRecipeId());
            statement.setTimestamp(5, production.getRecipeEndDate());
            statement.setInt(6,production.getProduktionsLeaderID());
            statement.execute();

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public ProductionDTO getProduction (int productionId) throws DALException {
        try (Connection conn = static_createConnection()){

            /* lock  */ static_lockTables(conn,"Production");

            ProductionDTO production = new ProductionDTO();

            PreparedStatement statement = conn.prepareStatement("select * from `Production` where `productionId` = ?");
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


            /* unlock */ static_unlockTables(conn);

            return production;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<ProductionDTO> getProductionList(String completedSentences, String deletedSentence) throws DALException {
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");

            List<ProductionDTO> productionList = new LinkedList<>();
            PreparedStatement statement = conn.prepareStatement("select * from `Production` where `status` != ? OR `status` != ?");
            statement.setString(1,completedSentences);
            statement.setString(2,deletedSentence);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }
            /* unlock */ static_unlockTables(conn);
            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void updateProduction(ProductionDTO production) throws DALException {
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement(
                    "update Production set quantity =?, status = ?, recipeId = ?, recipeEndDate = ?, produktionsLeaderId = ? where productionId = ?");
            statement.setInt(2, production.getQuantity());
            statement.setString(3, production.getStatus());
            statement.setInt(4,production.getRecipeId());
            statement.setTimestamp(5, production.getRecipeEndDate());
            statement.setInt(6,production.getProduktionsLeaderID());
            statement.executeUpdate();

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteProduction(int productionId, String deletedSentence) throws DALException {
        try (Connection conn = static_createConnection()) {
            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement("update `Production` SET `status`= ? where `productionId`= ?");
            statement.setString(1,deletedSentence);
            statement.setInt(2,productionId);
            statement.executeUpdate();

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteProduction(ProductionDTO production,String deletedSentence) throws DALException{
        deleteProduction(production.getProductionId() , deletedSentence );
    }

    @Override
    public List<ProductionDTO> getProductionList() throws DALException {
        try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Production");
/* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement("select * from `Production`");
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<ProductionDTO> getProductionList(RecipeDTO recipe)throws DALException{
        try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Production");
/* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement("select * from `Production` where  `recipeId` =  ? ");
            statement.setInt( 1, recipe.getRecipeId() );
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

/* commit */ static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);

            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<ProductionDTO> getProductionList(UserDTO Leader) throws DALException{
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement("select * from `Production` where  `recipeId` =  ? ");
            statement.setInt(1, Leader.getUserId() );
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }


    }

    @Override
    public List<ProductionDTO> getProductionList(Timestamp lesserThan , Timestamp greaterThan)throws DALException{
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            java.sql.Timestamp dateTime1 = new java.sql.Timestamp( lesserThan.getTime() );
            java.sql.Timestamp dateTime2 = new java.sql.Timestamp( greaterThan.getTime() );

            PreparedStatement statement = conn.prepareStatement("select * from `Production` where  `recipeEndDate` >  ? AND `recipeEndDate` < ? ");
            statement.setTimestamp(1,dateTime1);
            statement.setTimestamp(2,dateTime2);
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<ProductionDTO> getProductionList(Timestamp afterThis)throws DALException {
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            java.sql.Timestamp dateTime1 = new java.sql.Timestamp( afterThis.getTime() );
            PreparedStatement statement = conn.prepareStatement("select * from `Production` where  `recipeEndDate` >  ? AND `recipeEndDate` < ? ");
            statement.setTimestamp(1,afterThis);
            ResultSet res = statement.executeQuery();
            List<ProductionDTO> productionList = new LinkedList<>();

            while (res.next()) {
                ProductionDTO production = createProductionDto(res);
                productionList.add(production);
            }

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

            return productionList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public void superDelete(int id) throws DALException{
        try (Connection conn = static_createConnection()) {

            /* lock  */ static_lockTables(conn,"Production");
            /* start */ static_startTransAction(conn);

            PreparedStatement statement = conn.prepareStatement("delete from `Production` where  `productionId` = ? ");
            statement.setInt(1, id);
            statement.execute();

            /* commit */ static_commitTransAction(conn);
            /* unlock */ static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
