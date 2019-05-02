package io;

import java.util.List;
import java.util.Scanner;

//Skal tale med IUserController - metoderne skal kalde fra controlleren til sidst
public class TUI implements IUserInterface{

    Scanner scanner = new Scanner(System.in);

    //Drop-down menu - lavet i Controller!

    public void printLine(String text){
        System.out.println(text);
    }

    public void printList(List<String> array){
        //Hvad skal array-navnet være - mere specifikt, userList?
        for(int i = 0; i < array.size(); i++){
            System.out.println(array.get(i));
        }
    }

    public void clearScreen(){
        for (int i = 1; i < 50; i++) {
            System.out.println("\b");
        }
    }

    //Skal der være flere input - int vægt? - definere med forskellige meyode-navne
    public String getInput(){
        String userInput = null;
        userInput = scanner.next();
        return userInput;
    }

    //public double getMachineInput(){
    // Scanner scanner = new Scanner(System.in);
    // Double weight = scanner.nextDouble();
    // scanner.close();
    // return weight;
    // }
}
