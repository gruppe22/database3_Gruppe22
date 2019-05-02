package io;

import java.util.List;

public interface IUserInterface {
    void printLine(String text);
    void printList(List<String> array);
    void clearScreen();
    String getInput();
}
