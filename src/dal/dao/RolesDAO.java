package dal.dao;

import dal.dto.RoleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dal.dao.Connector.*;

public class RolesDAO implements I_RolesDAO  {

    @Override
    public void createRole(RoleDTO role) throws DALException {
        try(Connection conn = static_createConnection()) {
                // we dont need to do a transaction here because it is a single command.
                static_lockTables(conn, "Roles");

                PreparedStatement statement = conn.prepareStatement("Insert INTO `Roles`(`roleId`,`roleName`) values (?,?)");
                statement.setInt(1, role.getRoleId());
                statement.setString(2, role.getRoleName());
                statement.execute();


                static_unlockTables(conn);

        }catch(SQLException e){
            throw new DALException(e.getMessage());

        }
    }
    @Override
    public void deleteRole(int id) throws DALException{
        try(Connection conn = static_createConnection()) {
            static_startTransAction(conn);
            static_lockTables(conn, "Roles ");

            PreparedStatement statement = conn.prepareStatement("DELETE FROM `Roles` WHERE `roleId` = ?");
            statement.setInt(1,id);
            statement.execute();

            static_commitTransAction(conn);
            static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public void deleteRole(RoleDTO role)throws DALException{
        deleteRole(role.getRoleId());
    }
    @Override
    public RoleDTO getRole(int id)throws DALException{
        try (Connection conn = static_createConnection()) {

            static_startTransAction(conn);
            static_lockTables(conn, "Roles");

            PreparedStatement statement = conn.prepareStatement("SELECT * FROM `Roles` WHERE `roleId` = ? ");
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            static_commitTransAction(conn);
            static_unlockTables(conn);

            if( result.next() ) {
                RoleDTO newRole = new RoleDTO();
                newRole.setRoleId(result.getInt("roleId"));
                newRole.setRoleName("roleName");
                return newRole;
            }else{
                return null;
            }



        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public RoleDTO getRole(RoleDTO role)throws DALException {
       return getRole(role.getRoleId());
    }
    @Override
    public void updateRole(RoleDTO role) throws DALException{
        try (Connection conn = static_createConnection()) {
            static_startTransAction(conn);
            static_lockTables(conn,"Roles");

            PreparedStatement statement = conn.prepareStatement(" UPDATE `Roles` SET `roleName` = ? WHERE `roleId` = ? ");
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleId());
            statement.executeUpdate();

            static_commitTransAction(conn);
            static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
}
