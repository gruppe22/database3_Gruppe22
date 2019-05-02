package controller;

import business.IUserLogic;
import dal.dao.IUserDAO;
import io.IUserInterface;

import java.io.IOException;

public interface IUserController {

    void setInterface(IUserInterface ui);
    void setLogic(IUserLogic logic);
    void start() throws IUserDAO.DALException, IOException;
    void showMenu() throws IUserDAO.DALException, IOException;
    void showSubMenu(int userChoice) throws IUserDAO.DALException, IOException;
}
