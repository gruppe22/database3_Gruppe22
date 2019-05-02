package business;

import dal.dao.IUserDAO;
import dal.dao.UserDAO;
import dal.dto.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLogic implements IUserLogic {
    private IUserDAO userDao;
    private UserDTO userDto;

    public UserLogic() throws IOException, ClassNotFoundException {
        userDao = new UserDAO();
    }

    @Override
    public String getRole() {
        return userDto.getRoles().get(0);
    }

    @Override
    public int getPermissionLevel() {
        List<String> roles = userDto.getRoles();

        // Todo: should loop through the list - but as long as users only have one role index 0 is fine
        if (roles.get(0).contains("Admin"))
            return 1;
        else if (roles.get(0).contains("Operator"))
            return 2;
        else if (roles.get(0).contains("Pharmacist"))
            return 3;
        else if (roles.get(0).contains("Foreman"))
            return 4;
        else
            return 0;
    }

    @Override
    public void setUserDTO(int userId) {
        try {
            userDto = userDao.getUser(userId);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getUserList() {
        List<String> list = new ArrayList<>();
        try {
            List<UserDTO> userList = userDao.getUserList();

            for (int i = 0; i < userList.size(); i++) {
                list.add(userList.get(i).toString());
            }
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public UserDTO getUser(int userId) throws IUserDAO.DALException {
        UserDTO user = userDao.getUser(userId);
        return user;
    }

    @Override
    public void createUser(int userId, String userName, String ini, String role) {

        List<String> roles = new ArrayList<>();
        roles.add(role);

        UserDTO user = new UserDTO(userId, userName, ini, roles);

        try {
            userDao.createUser(user);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(int userId, String userName, String ini, List<String> roles) throws IUserDAO.DALException {
        UserDTO user = new UserDTO(userId, userName, ini, roles);
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        try {
            UserDTO user = userDao.getUser(userId);
            userDao.deleteUser(user);
        } catch (IUserDAO.DALException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getUsername() {
        return userDto.getUserName();
    }

    @Override
    public int getUserId() {
        return userDto.getUserId();
    }

}
