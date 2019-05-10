package dal.dao;

import dal.dto.RoleDTO;

/**
 * author: Hans P Byager
 * date : 07 / 05 / 19
 * */
public interface I_RolesDAO {
    void createRole(RoleDTO role) throws DALException;
    void deleteRole(RoleDTO role) throws DALException;
    void deleteRole(int id) throws DALException;
    RoleDTO getRole(RoleDTO role) throws DALException;
    RoleDTO getRole(int id) throws DALException;
    void updateRole(RoleDTO role) throws DALException;

    class DALException extends Exception {
        public DALException(String msg, Throwable e) {
            super(msg,e);
        }
        public DALException(String msg) {
            super(msg);
        }
    }
}
