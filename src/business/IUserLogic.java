package business;

import dal.dao.IUserDAO;
import dal.dto.UserDTO;

import java.io.IOException;
import java.util.List;

public interface IUserLogic {

    //Todo: methods for logic and interaction with domain (DTO) goes here..

    String getRole();
    int getPermissionLevel();

    void setUserDTO(int userId);
    String getUsername();
    int getUserId();
    List<String> getUserList();
    UserDTO getUser(int userId) throws IUserDAO.DALException;
    void createUser(int userId, String userName, String ini, String role);
    void updateUser(int userId, String userName, String ini, List<String> roles) throws IUserDAO.DALException;
    void deleteUser(int userId);

    //void saveData(String data) throws IOException;
}
