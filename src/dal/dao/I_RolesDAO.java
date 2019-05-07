package dal.dao;

import dal.dto.RoleDTO;

/**
 * author: Hans P Byager
 * date : 07 / 05 / 19
 * */
public interface I_RolesDAO {
    void createRole(RoleDTO role);
    void deleteRole(RoleDTO role);
    void deleteRole(int id);
    RoleDTO getRole(RoleDTO role);
    RoleDTO getRole(int id);
    void updateRole(RoleDTO role);
}
