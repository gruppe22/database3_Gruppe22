package dal.dao;

import dal.dto.MaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dal.dao.Connector.*;

public class MaterialDAO implements IMaterialDAO{
        @Override
        public void createMaterial(MaterialDTO material) throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Materials");
/* start */ static_startTransAction(conn);


                PreparedStatement statement = conn.prepareStatement("insert into Materials values (?, ?, ?, ?, ?, ?)");
                statement.setInt(1, material.getBatchId());
                statement.setString(2, material.getName());
                statement.setInt(3,material.getIngredientId());
                statement.setInt(4, material.getQuantity());
                statement.setString(5, material.getSupplier());
                statement.setBoolean(6,material.isExpired());
                statement.execute();

/* commit */ static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);


            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public MaterialDTO getMaterial (int batchId) throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */static_lockTables(conn, "Materials");


                MaterialDTO material = null;
                PreparedStatement statement = conn.prepareStatement("select * from Materials where batchId = ?");
                statement.setInt(1, batchId);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    material = new MaterialDTO(
                            result.getInt("batchId"),
                            result.getString("name"),
                            result.getInt("ingredientId"),
                            result.getInt("quantity"),
                            result.getString("supplier"),
                            result.getBoolean("expired"));
                }


/* unlock */static_unlockTables(conn);

                return material;

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public List<MaterialDTO> getMaterialList() throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */static_lockTables(conn, "Materials");

                List<MaterialDTO> materialList = new ArrayList<>();
                Statement statement = conn.createStatement();
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

/* unlock */static_unlockTables(conn);

                return materialList;

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public void updateMaterial(MaterialDTO material) throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Materials");
/* start */ static_startTransAction(conn);

                PreparedStatement statement = conn.prepareStatement(
                        "update Materials set batchId = ?, name = ?, ingredientId = ?, quantity = ?, supplier = ?, expired = ? where batchId = ?");
                statement.setInt(1, material.getBatchId());
                statement.setString(2, material.getName());
                statement.setInt(3,material.getIngredientId());
                statement.setInt(4,material.getQuantity());
                statement.setString(5,material.getSupplier());
                statement.setBoolean(6,material.isExpired());
                statement.setInt(7,material.getBatchId());
                statement.executeUpdate();

/* commit */ static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        @Override
        public void deleteMaterial(MaterialDTO material) throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Materials");
/* start */ static_startTransAction(conn);

                PreparedStatement statement = conn.prepareStatement(
                        "update Materials set expired = ? where batchId = ?");
                statement.setBoolean(1, material.isExpired());
                statement.setInt(2,material.getBatchId());
                statement.executeUpdate();

/* commit */ static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }

        public void SUPERdeleteMaterial(int batchId) throws DALException {
            try (Connection conn = static_createConnection()) {

/* lock  */ static_lockTables(conn,"Materials");
/* start */ static_startTransAction(conn);

                PreparedStatement statement = conn.prepareStatement("delete from Materials where batchId = ?");
                statement.setInt(1, batchId);
                statement.execute();

/* commit */ static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);

            } catch (SQLException e) {
                throw new DALException(e.getMessage());
            }
        }
    }

