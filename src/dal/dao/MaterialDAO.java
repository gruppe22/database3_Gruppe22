package dal.dao;

import dal.dto.MaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class MaterialDAO implements IMaterialDAO{

    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
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
                statement.execute();

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public MaterialDTO getMaterial (int batchId) throws DALException {
            try (Connection c = createConnection()){
                MaterialDTO materialnotfound = new MaterialDTO(0,"NOT FOUND",0,0,"NOT FOUND",true);


                PreparedStatement statement = c.prepareStatement("select * from Materials where batchId = ?");
                statement.setInt(1, batchId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    MaterialDTO material = new MaterialDTO(
                            result.getInt("batchId"),
                            result.getString("name"),
                            result.getInt("ingredientId"),
                            result.getInt("quantity"),
                            result.getString("supplier"),
                            result.getBoolean("expired"));
                            return material;
                }

                    return materialnotfound;
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
                    MaterialDTO material = new MaterialDTO(
                            resultSet.getInt("batchId"),
                            resultSet.getString("name"),
                            resultSet.getInt("ingredientId"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("supplier"),
                            resultSet.getBoolean("expired"));

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
                        "update Materials set batchId = ?, name = ?, ingredientId = ?, quantity = ?, supplier = ?, expired = ? where batchId = ?");
                statement.setInt(1, material.getBatchId());
                statement.setString(2, material.getName());
                statement.setInt(3,material.getIngredientId());
                statement.setInt(4,material.getQuantity());
                statement.setString(5,material.getSupplier());
                statement.setBoolean(6,material.isExpired());
                statement.setInt(7,material.getBatchId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteMaterial(MaterialDTO material) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "update Materials set expired = ? where batchId = ?");
                statement.setBoolean(1, material.isExpired());
                statement.setInt(2,material.getBatchId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
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

