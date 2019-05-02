package test;

import business.*;
import controller.*;
import dal.dao.I_ProductionDAO;
import dal.dao.IUserDAO.DALException;
import io.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws DALException, IOException, ClassNotFoundException, I_ProductionDAO.DALException {
            IUserLogic logic = new UserLogic();
            IUserInterface tui = new TUI();
            IUserController controller = new UserController();
            controller.setLogic(logic);
            controller.setInterface(tui);
            controller.start();
        }

    }
