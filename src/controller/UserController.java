package controller;

import business.IUserLogic;
import dal.dao.IUserDAO;
import io.*;

import java.io.IOException;

public class UserController implements IUserController {
    private IUserInterface ui;
    private IUserLogic logic;


    @Override
    public void setInterface(IUserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void setLogic(IUserLogic logic) {
        this.logic = logic;
    }

    @Override
    public void start() throws IUserDAO.DALException, IOException {
        ui.printLine("Velkommen");
        ui.printLine("Indtast bruger-id:");
        logic.setUserDTO(Integer.parseInt(ui.getInput()));
        ui.clearScreen();
        showMenu();
    }

    @Override
    public void showMenu() throws IUserDAO.DALException, IOException {
        showUserContext();
        ui.printLine("Hovedmenu");
        ui.printLine("1. Opret ny bruger");
        ui.printLine("2. List brugere");
        ui.printLine("3. Ret bruger");
        ui.printLine("4. Slet bruger");
        ui.printLine("5. Afslut program");
        ui.printLine("\nIndtast valg:");
        int choice = Integer.parseInt(ui.getInput());
        ui.clearScreen();
        showSubMenu(choice);
    }

    @Override
    public void showSubMenu(int userChoice) throws IUserDAO.DALException, IOException {
        showUserContext();
        switch (userChoice){
            case 1:
                subCreateUser();
                break;
            case 2:
                subListUsers();
                break;
            case 3:
                subUpdateUser();
                break;
            case 4:
                subDeleteUser();
                break;
            case 5:
                subCloseProgram();
                break;
            default:
                subTryAgain();
        }
    }

    private void subCreateUser() throws IUserDAO.DALException, IOException {
        if (logic.getPermissionLevel() == 1) {
            ui.printLine("Opret ny bruger");
            ui.printLine("\nIndtast bruger-ID (11-99):");
            int userId = Integer.parseInt(ui.getInput());
            ui.printLine("\nIndtast brugernavn:");
            String userName = ui.getInput();
            ui.printLine("\nIndtast initialer:");
            String ini = ui.getInput();
            ui.printLine("\nIndtast rolle (Admin, Pharmacist, Foreman, Operator:");
            String role = ui.getInput();
            logic.createUser(userId, userName, ini, role);
            ui.clearScreen();
            showMenu();
        } else {
            showPermissionDenied();
        }
    }

    private void subUpdateUser() throws IUserDAO.DALException, IOException {
        if (logic.getPermissionLevel() == 1) {
            ui.printLine("Ret bruger");
            ui.printLine("\nIndtast bruger-ID, for bruger der skal rettes (11-99):");
            int userId = Integer.parseInt(ui.getInput());

            ui.printLine("\nBruger: " + logic.getUser(userId).toString());
            ui.printLine("Indtast (nye) brugernavn:");
            String userName = ui.getInput();
            ui.printLine("\nIndtast (nye) initialer:");
            String ini = ui.getInput();
            logic.updateUser(userId, userName, ini, logic.getUser(userId).getRoles());
            ui.clearScreen();
            showMenu();
        } else {
            showPermissionDenied();
        }
    }

    private void subDeleteUser() throws IUserDAO.DALException, IOException {
        if (logic.getPermissionLevel() == 1) {
            ui.printLine("Slet bruger");
            ui.printLine("\nIndtast bruger-ID (11-99:");
            int id = Integer.parseInt(ui.getInput());
            logic.deleteUser(id);
            ui.clearScreen();
            showMenu();
        } else {
            showPermissionDenied();
        }
    }

    private void subListUsers() throws IUserDAO.DALException, IOException {
        ui.printLine("Brugere:\n");
        ui.printList(logic.getUserList());
        ui.printLine("\nTast 1 for at gå til hovedmenu.");
        int i = Integer.parseInt(ui.getInput());
        while (i != 1) {
            i = Integer.parseInt(ui.getInput());
        }
        ui.clearScreen();
        showMenu();
    }

    private void subCloseProgram() throws IOException {
        ui.clearScreen();
        ui.printLine("Du er nu logget ud!");
       // logic.saveData("Data.txt");
        System.exit(0);
    }

    private void showUserContext() {
        ui.printLine("Du er logget på som: " + logic.getUsername()
                + ", ID: " + logic.getUserId()
                + ", Rolle: " + logic.getRole()
                + "\n");
    }

    private void showPermissionDenied() throws IUserDAO.DALException, IOException {
        ui.clearScreen();
        ui.printLine("Fejl: manglende rettigheder!");
        showMenu();
    }

    private void subTryAgain() throws IUserDAO.DALException, IOException {
        ui.clearScreen();
        ui.printLine("Vælg venligst et gyldigt valg");
        showMenu();
    }
}
