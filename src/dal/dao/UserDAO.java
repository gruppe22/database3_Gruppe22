package dal.dao;

import dal.dto.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private Connection createConnection() throws SQLException {
        return  DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185015"
                + "?user=s185015&password=629FTiYG3DNSPQV3r4YIU");
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("insert into Users values (?, ?, ?)");
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getIni());
            statement.executeUpdate();

            for (String role : user.getRoles()) {
                statement = connection.prepareStatement("insert into relUserRoles values (?, ?)");
                statement.setInt(1, user.getUserId());

                if (role.contains("administrator"))
                    statement.setInt(2, 10);
                else if (role.contains("pedel"))
                    statement.setInt(2, 11);

                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        try (Connection c = createConnection()){

            UserDTO user = new UserDTO();
            List<String> roles = new ArrayList<>();

            PreparedStatement statement = c.prepareStatement("select * from Users where userId = ?");
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user.setUserId(result.getInt("userId"));
                user.setUserName(result.getString("userName"));
                user.setIni(result.getString("ini"));
            }

            statement = c.prepareStatement(
                    "select * from relUserRoles rr join role r on rr.roleId = r.roleId where rr.userId = ?");
            statement.setInt(1, userId);
            result = statement.executeQuery();

            while (result.next()) {
                roles.add(result.getString("role"));
            }
            user.setRoles(roles);
            return user;

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        try (Connection connection = createConnection()) {
            List<UserDTO> userList = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Users");

            while (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setIni(resultSet.getString("ini"));

                PreparedStatement s = connection.prepareStatement(
                        "select * from relUserRoles rr join role r on rr.roleId = r.roleId where rr.userId = ?");
                s.setInt(1, user.getUserId());
                ResultSet result = s.executeQuery();

                List<String> roles = new ArrayList<>();

                while (result.next()) {
                    roles.add(result.getString("role"));
                }
                user.setRoles(roles);
                userList.add(user);
            }
            return userList;

        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "update Users set userName = ?, ini = ? where userId = ?");
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getIni());
            statement.setInt(3, user.getUserId());
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "delete from relUserRoles where userId = ?");
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();

            for (String role : user.getRoles()) {
                statement = connection.prepareStatement("insert into relUserRoles values (?, ?)");
                statement.setInt(1, user.getUserId());

                if (role.contains("administrator"))
                    statement.setInt(2, 10);
                else if (role.contains("pedel"))
                    statement.setInt(2, 11);

                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from relUserRole where userId = ?");
            statement.setInt(1, userId);
            statement.execute();

            statement = connection.prepareStatement("delete from Users where userId = ?");
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException ex) {
            throw new DALException(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(UserDTO user) throws DALException{

    }
}