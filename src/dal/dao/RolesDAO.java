package dal.dao;

import dal.dto.RoleDTO;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolesDAO implements I_RolesDAO  {

    private Connection createConnection() throws SQLException {
        Connector connector = new Connector();
        return connector.createConnection();
    }
    @Override
    public void createRole(RoleDTO role){
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("Insert INTO `Roles`(`roleId`,`roleName`) values (?,?)");
            statement.setInt(1,role.getRoleId());
            statement.setString(2,role.getRoleName());
            statement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteRole(int id){
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM `Roles` WHERE `roleId` = ?");
            statement.setInt(1,id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteRole(RoleDTO role){
        deleteRole(role.getRoleId());
    }
    @Override
    public RoleDTO getRole(int id){
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Roles` WHERE `roleId` = ? ");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if( result.next() ) {
                RoleDTO newRole = new RoleDTO();
                newRole.setRoleId(result.getInt("roleId"));
                newRole.setRoleName("roleName");
                return newRole;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public RoleDTO getRole(RoleDTO role){
       return getRole(role.getRoleId());
    }
    @Override
    public void updateRole(RoleDTO role){
        try (Connection connection = createConnection()) {

            PreparedStatement statement = connection.prepareStatement(" UPDATE `Roles` SET `roleName` = ? WHERE `roleId` = ? ");
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
