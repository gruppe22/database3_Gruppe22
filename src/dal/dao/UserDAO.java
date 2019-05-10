package dal.dao;

import dal.dto.RoleDTO;
import dal.dto.UserDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dal.dao.Connector.*;

public class UserDAO implements IUserDAO {

    private Connection createConnection() throws SQLException {
        return static_createConnection();
    }

    private List<String> getRolesFromInnerJoin(Connection connection, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT `roleName` FROM `Roles` INNER JOIN `relUserRoles` ON `Roles`.`roleId`=`relUserRoles`.`rolesId` WHERE `userId` = ?");
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();

        List<String> roles = new LinkedList<>();
        while (result.next()) {
            roles.add(result.getString("roleName"));
        }
        return roles;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        try (Connection conn = static_createConnection()) {
/*  Start */static_startTransAction(conn);
/*  lock  */static_lockTables(conn, "Users","Roles","relUserRoles");

            // INSERT INTO USERS TABLE
            PreparedStatement statement = conn.prepareStatement("insert into  `Users`(`userId`, `userName`,`ini`,`expired`) values (?, ?, ? ,0)");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getIni());
            statement.executeUpdate();

/*  commit  */static_commitTransAction(conn);
/*  Start   */static_startTransAction(conn);
            // FOR EACH ROLE INSERT A LINK INTO THE RELATIONAL TABLE
            for (String role : user.getRoles()) {

                // For each role we need the Role id to put into the Relational table.
                // so we search for the name of the role to se if exists. and get its ID value from the resultset
                PreparedStatement roleExists = conn.prepareStatement("SELECT * FROM  `Roles` WHERE `roleName` = ?");
                roleExists.setString(1, role);
                ResultSet result = roleExists.executeQuery();

                if(result.next()) {
                    int id = result.getInt("roleId");

                    // Now we need to enter the Id of the user and corresponding role id, in the relational table.
                    // to link between the two entities.
                    PreparedStatement relation = conn.prepareStatement("insert into `relUserRoles`(`userId`,`rolesId`) values (?, ?)");
                    relation.setInt(1, user.getUserId());
                    relation.setInt(2, id);
                    relation.execute();

                }else{
                    System.out.println("no Role Named" + role );
                }
            }

/* commit */static_commitTransAction(conn);
/* unlock */static_lockTables(conn, "Users","Roles","relUserRoles");
            // Now it ought to be done so close conn.
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        try (Connection conn = static_createConnection()){

/*  lock  */static_lockTables(conn, "Users","Roles","relUserRoles");

            // GETTING THE USER  --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
            UserDTO newUser = new UserDTO();

            PreparedStatement statement = conn.prepareStatement("select * from `Users` where `userId` = ?");
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                newUser.setUserId(result.getInt("userId"));
                newUser.setUserName(result.getString("userName"));
                newUser.setIni(result.getString("ini"));
                newUser.setExpired(result.getBoolean("expired"));

            }else{
                return null;
            }

            // GETTING THE ROLES OF THE USER --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---

            // INNER JOINS Command, then Filtering.
            // Doing this in a method, because it needs to be done several times the Exact same way.
            List<String> roles = getRolesFromInnerJoin(conn, userId);
            newUser.setRoles(roles);

/* unlock */static_unlockTables(conn);
            return newUser;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        try (Connection conn = static_createConnection()) {

/*  lock  */static_lockTables(conn,"Users","Roles","relUserRoles");

            List<UserDTO> userList = new ArrayList<>();

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `Users` WHERE `expired` = 0;");

            while (resultSet.next()) {
                // Setting up a New User DTO
                UserDTO user = new UserDTO();

                // All parameters But Roles due to it being in a differnt table
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setIni(resultSet.getString("ini"));
                user.setExpired(resultSet.getBoolean("expired"));

                // Getting Roles for this User from the relational table
                List<String> roles = getRolesFromInnerJoin(conn, user.getUserId());
                user.setRoles(roles);

                // then at the end ad to the list and do it again.
                userList.add(user);
            }
/* unlock */static_unlockTables(conn);
            return userList;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        try (Connection conn = static_createConnection()) {

/* start */static_startTransAction(conn);
/* lock  */static_lockTables(conn, "Users","Roles","relUserRoles");

            PreparedStatement statement = conn.prepareStatement("UPDATE `Users` SET `userName`= ?,`ini`= ?,`expired` = ? WHERE `userId` = ?;");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getIni());
            statement.setBoolean(3, user.isExpired());
            statement.setInt(4, user.getUserId());
            statement.executeUpdate();

/* commit */static_commitTransAction(conn);
/* unlock */ static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection conn = static_createConnection()) {

/* start */ static_startTransAction(conn);
/* lock  */ static_lockTables(conn, "Users");

            PreparedStatement statement = conn.prepareStatement("UPDATE `Users` SET `expired` = ? WHERE `userId` = ?");
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            statement.executeUpdate();

/* commit */static_commitTransAction(conn);
/* unlock */static_unlockTables(conn);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void deleteUser(UserDTO user) throws DALException{
        deleteUser(user.getUserId());
    }

    public void superDeleteUser(UserDTO user) throws DALException{
        try (Connection conn = static_createConnection()) {

/* start */static_startTransAction(conn);
/* lock  */static_lockTables(conn, "Users","Roles","relUserRoles");


            PreparedStatement statement = conn.prepareStatement("DELETE FROM relUserRoles Where `userId` = ?;");
            statement.setInt(1, user.getUserId());
            statement.execute();
            statement = conn.prepareStatement("DELETE FROM `Users` WHERE `userId` = ?;");
            statement.setInt(1, user.getUserId());
            statement.execute();

/* commit */static_commitTransAction(conn);
/* unlock */static_unlockTables(conn);

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

}