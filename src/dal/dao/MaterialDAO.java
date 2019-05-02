package dal.dao;

import dal.dto.MaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MaterialDAO implements IMaterialDAO{

        private Connection createConnection() throws SQLException {
            return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185015"
                    + "?user=s185015&password=629FTiYG3DNSPQV3r4YIU");
        }

        @Override
        public void createMaterial(MaterialDTO material) throws DALException {
            try (Connection connection = createConnection()) {

                PreparedStatement statement = connection.prepareStatement("insert into Materials values (?, ?, ?, ?, ?, ?)");
                statement.setInt(1, material.getBatchId());
                statement.setString(2, material.getName());
                statement.setInt(3,material.getIngredientId());
                statement.setInt(4, material.getQuantity());
                statement.setString(5, material.getSupplier());
                statement.setBoolean(6,material.isExpired());
                statement.executeUpdate();

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public MaterialDTO getMaterial (int batchId) throws DALException {
            try (Connection c = createConnection()){

                MaterialDTO material = new MaterialDTO();

                PreparedStatement statement = c.prepareStatement("select * from Materials where batchId = ?");
                statement.setInt(1, batchId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    material.setBatchId(result.getInt("batchId"));
                    material.setName(result.getString("name"));
                    material.setSupplier(result.getString("supplier"));
                    material.setIngredientId(result.getInt("ingredientId"));
                    material.setQuantity(result.getInt("quantity"));
                }
                return material;
            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public List<MaterialDTO> getMaterialList() throws DALException {
            try (Connection connection = createConnection()) {
                List<MaterialDTO> materialList = new ArrayList<>();

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Materials where expired = 0");

                while (resultSet.next()) {
                    MaterialDTO material = new MaterialDTO();
                    material.setBatchId(resultSet.getInt("batchId"));
                    material.setName(resultSet.getString("name"));
                    material.setSupplier(resultSet.getString("supplier"));
                    material.setIngredientId(resultSet.getInt("ingredientId"));
                    material.setQuantity(resultSet.getInt("quantity"));

                    materialList.add(material);
                }
                return materialList;

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void updateMaterial(MaterialDTO material) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "update Materials set name = ?, supplier = ? where userId = ?");
                statement.setString(1, material.getName());
                statement.setString(2, material.getSupplier());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteMaterial(int batchId) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement("delete from Materials where batchId = ?");
                statement.setInt(1, batchId);
                statement.execute();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteMaterial(MaterialDTO material) throws DALException{
        }

        public void SUPERdeleteMaterial(int batchId) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement("delete from Materials where batchId = ?");
                statement.setInt(1, batchId);
                statement.execute();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }
    }

