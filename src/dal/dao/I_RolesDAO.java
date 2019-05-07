package dal.dao;

import dal.dto.RoleDTO;

/**
 * author: Hans P Byager
 * date : 07 / 05 / 19
 * */
public interface I_RolesDAO {
    void createRole();
    void deleteRole();
    RoleDTO getRole();
    void updateRole();
}
