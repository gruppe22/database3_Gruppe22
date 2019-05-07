package test;

import dal.dao.IUserDAO;
import dal.dao.UserDAO;
import dal.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    public UserDTO setupUser(){
        UserDTO user = new UserDTO();
        user.setUserId(200);
        user.setUserName("Ben Dover");
        user.setIni("BD");
        // preparing roles.
        List<String> Roles = new LinkedList<>();
        Roles.add("Administrator"); Roles.add("Laborant");
        user.setRoles(Roles);

        return user;
    }
    public void assertRolesEquals(List<String> expectedRoles , List<String> actualRoles ){
        for(int i = 0; i < actualRoles.size() ; i++){
            assertEquals(expectedRoles.get(i), actualRoles.get(i));
        }
    }

    /**
     * author: Hans P Byager
     * date : 07 / 05 / 19
     * */
    @Test
    void createUser() {
        UserDTO user = setupUser();
        UserDAO dao = new UserDAO();
        user.getRoles().add("JukeBoxer"); // Setting in a Role that dosent exists to se it isent there.

        try{
            dao.createUser(user);
            UserDTO user2 = dao.getUser(user.getUserId());

            assertEquals(user.getUserName(), user2.getUserName());
            assertEquals(user.getIni(), user2.getIni());
            assertEquals(user.getUserId(), user.getUserId());

            //ROLES CHECK.
            assertRolesEquals(user.getRoles(), user2.getRoles());


            dao.superDeleteUser(user); // REMEMBER TO DELETE THIS USER TO RUN AGAIN
        }catch (IUserDAO.DALException e){
            e.printStackTrace();
        }

    }

    @Test
    /**
     * author: Hans P Byager
     * date : 07 / 05 / 19
     * */
    void getUser() {
        try {
            UserDTO user = setupUser();
            UserDAO dao = new UserDAO();

            dao.createUser(user);
            UserDTO user2 = dao.getUser(user.getUserId());

            assertEquals(user2.getUserId(), user.getUserId());
            assertEquals(user2.getIni(), user.getIni());
            assertEquals(user2.getUserName(), user.getUserName());

            //ROLES CHECK.
            assertRolesEquals(user.getRoles(), user2.getRoles());

            dao.superDeleteUser(user); // REMEMBER TO DELETE THIS USER TO RUN AGAIN
        }catch (IUserDAO.DALException e){
            e.printStackTrace();
        }
    }

    @Test
    /**
     * author: Hans P Byager
     * date : 07 / 05 / 19
     * */
    void getUserList() {
        try {
            // the interesting thing here is that if something has bin expired it is no longer part of the list.
            UserDTO user = setupUser();
            UserDAO dao = new UserDAO();

            dao.createUser(user);
            List<UserDTO> allUsers = dao.getUserList();

            for(int i = 0 ; i < allUsers.size(); i++) { // ASSERT THE USER IS INSIDE THE LIST RETURNED
                UserDTO thisUser = allUsers.get(i);
                if(thisUser.getUserId() == user.getUserId()){
                    assert(true);
                }
            }

            dao.deleteUser(user);
            allUsers = dao.getUserList();
            for(int i = 0 ; i < allUsers.size(); i++) { // ASSERT THE USER IS -NOT- INSIDE THE LIST RETURNED
                assertNotEquals(allUsers.get(i).getUserId(), user.getUserId());
            }

            dao.superDeleteUser(user); // REMEMBER TO DELETE THIS USER TO RUN TESTS AGAIN
        }catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Test
    /**
     * author: Hans P Byager
     * date : 07 / 05 / 19
     * */
    void updateUser(){
        try {
            UserDTO user = setupUser();
            UserDAO dao = new UserDAO();

            dao.createUser(user);
            user.setUserName("Chris P. Bacon");
            user.setIni("CPB");
            dao.updateUser(user);

            UserDTO user2 = dao.getUser(user.getUserId());
            assertEquals(user.getUserName(), user2.getUserName());
            assertEquals(user.getIni(), user2.getIni());
            assertEquals(user.isExpired(), user2.isExpired());
            assertRolesEquals(user.getRoles(), user2.getRoles());

            dao.superDeleteUser(user); // REMEMBER TO DELETE THIS USER TO RUN TESTS AGAIN
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    /**
     * author: Hans P Byager
     * date : 07 / 05 / 19
     * */
    void deleteUser(){
        try {
            UserDTO user = setupUser();
            UserDAO dao = new UserDAO();

            dao.createUser(user);
            dao.deleteUser(user.getUserId());
            UserDTO user2 = dao.getUser(user.getUserId());
            assertNotEquals(user.isExpired(), user2.isExpired());

            dao.superDeleteUser(user); // REMEMBER TO DELETE THIS USER TO RUN TESTS AGAIN
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}