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
                PreparedStatement statement = connection.prepareStatement("insert into Materials values (?, ?, ?)");
                statement.setInt(1, material.getMaterialId());
                statement.setString(2, material.getMaterialName());
                statement.setString(3, material.getSupplierName());
                statement.executeUpdate();

            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public MaterialDTO getMaterial (int materialId) throws DALException {
            try (Connection c = createConnection()){

                MaterialDTO material = new MaterialDTO();

                PreparedStatement statement = c.prepareStatement("select * from Materials where materialId = ?");
                statement.setInt(1, materialId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    material.setMaterialId(result.getInt("materialId"));
                    material.setMaterialName(result.getString("materialName"));
                    material.setSupplierName(result.getString("supplierName"));
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
                ResultSet resultSet = statement.executeQuery("select * from Materials");

                while (resultSet.next()) {
                    MaterialDTO material = new MaterialDTO();
                    material.setMaterialId(resultSet.getInt("materialId"));
                    material.setMaterialName(resultSet.getString("materialName"));
                    material.setSupplierName(resultSet.getString("supplierName"));

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
                        "update Materials set materialName = ?, supplierName = ? where userId = ?");
                statement.setString(1, material.getMaterialName());
                statement.setString(2, material.getSupplierName());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteMaterial(int materialId) throws DALException {
            try (Connection connection = createConnection()) {
                PreparedStatement statement = connection.prepareStatement("delete from Materials where materialId = ?");
                statement.setInt(1, materialId);
                statement.execute();
            } catch (SQLException ex) {
                throw new DALException(ex.getMessage());
            }
        }

        @Override
        public void deleteMaterial(MaterialDTO material) throws DALException{
        }

    }

