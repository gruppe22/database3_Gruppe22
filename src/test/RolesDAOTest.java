package test;

import dal.dao.RolesDAO;
import dal.dto.RoleDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolesDAOTest {

    @Test
    void createRole() {
        RolesDAO dao = new RolesDAO();
        RoleDTO myRole = new RoleDTO();
        myRole.setRoleName("gade cigarets opsamler");
        myRole.setRoleId(666);

        dao.createRole(myRole);
        RoleDTO nyRole = dao.getRole(666);

        assertEquals(myRole.getRoleName(), myRole.getRoleName());
        dao.deleteRole(myRole);
    }

    @Test
    void deleteRole() {
        RolesDAO dao = new RolesDAO();
        RoleDTO myRole = new RoleDTO();
        myRole.setRoleName("gade cigarets opsamler");
        myRole.setRoleId(666);

        dao.createRole(myRole);
        RoleDTO nyRole = dao.getRole(666);

        assertEquals(myRole.getRoleName(), myRole.getRoleName());
        dao.deleteRole(myRole);

        nyRole = dao.getRole(666);
        assertEquals(nyRole, null);

    }

    @Test
    void getRole() {
        RolesDAO dao = new RolesDAO();
        RoleDTO myRole = new RoleDTO();
        myRole.setRoleName("gade cigarets opsamler");
        myRole.setRoleId(666);

        dao.createRole(myRole);
        RoleDTO nyRole = dao.getRole(666);

        assertEquals(myRole.getRoleName(), myRole.getRoleName());
        dao.deleteRole(myRole);
    }

    @Test
    void updateRole() {
        RolesDAO dao = new RolesDAO();
        RoleDTO myRole = new RoleDTO();
        myRole.setRoleName("gade cigarets opsamler");
        myRole.setRoleId(666);

        dao.createRole(myRole);
        RoleDTO nyRole = dao.getRole(666);

        myRole.setRoleName("Ryger politi");
        dao.updateRole(myRole);

        assertNotEquals(myRole.getRoleName(), nyRole.getRoleName());
        dao.deleteRole(myRole);
    }
}