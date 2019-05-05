package dal.dao;

import dal.dto.ProductionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.io.IOException;

public class ProductionDAO implements I_ProductionDAO {
        private Connection createConnection() throws SQLException {
            return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185015"
                    + "?user=s185015&password=629FTiYG3DNSPQV3r4YIU");
        }

        @Override
        public void createProduction(ProductionDTO production) throws DALException {
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("insert into Production values (?, ?, ?, ?, ?, ?)");
                statement.setInt(1, production.getProductionId());
                statement.setInt(2, production.getQuantity());
                statement.setString(3, production.getStatus());
                statement.setInt(4,production.getRecipeId());
                statement.setDate(5, production.getRecipeEndDate());
                statement.setInt(6,production.getProduktionsLeaderID());
                statement.executeUpdate();

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public ProductionDTO getProduction (int productionId) throws DALException {
            try (Connection c = createConnection()){

                ProductionDTO production = new ProductionDTO();

                PreparedStatement statement = c.prepareStatement("select * from Production where productionId = ?");
                statement.setInt(1, productionId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    production.setProductionId(result.getInt("productionId"));
                    production.setQuantity(result.getInt("quantity"));
                    production.setStatus(result.getString("status"));
                    production.setRecipeId(result.getInt("recipeId"));
                    production.setRecipeEndDate(result.getDate("recipeEndDate"));
                    production.setProduktionsLeaderID(result.getInt("produktionsLeaderID"));
                }
                return production;
            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public List<ProductionDTO> getProductionList() throws DALException {
            try (Connection connection = createConnection()) {
                List<ProductionDTO> productionList = new ArrayList<>();

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Production where expired = 0");

                while (resultSet.next()) {
                    ProductionDTO production = new ProductionDTO();
                    production.setProductionId(resultSet.getInt("productionId"));
                    production.setQuantity(resultSet.getInt("quantity"));
                    production.setStatus(resultSet.getString("status"));
                    production.setRecipeId(resultSet.getInt("recipeId"));
                    production.setRecipeEndDate(resultSet.getDate("recipeEndDate"));
                    production.setProduktionsLeaderID(resultSet.getInt("produktionsLeaderID"));

                    productionList.add(production);
                }
                return productionList;

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
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
                statement.setDate(5, production.getRecipeEndDate());
                statement.setInt(6,production.getProduktionsLeaderID());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteProduction(int productionId) throws DALException {
                  }

        @Override
        public void deleteProduction(ProductionDTO production) throws DALException{
        }


    }
