package dal.dao;

import dal.dto.UserDTO;

import java.util.List;

public interface IUserDAO {

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }

    UserDTO getUser(int userId) throws DALException;
    List<UserDTO> getUserList() throws DALException;

    void createUser(UserDTO user) throws DALException;
    void updateUser(UserDTO user) throws DALException;
    void deleteUser(UserDTO user) throws DALException;
    void deleteUser(int userId) throws DALException;
}