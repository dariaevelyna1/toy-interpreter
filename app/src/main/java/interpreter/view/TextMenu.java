package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    IStmt selectedProgram;
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println();
        System.out.println("1. Select a program");
        System.out.println("2. Run the selected program");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    public void runMainMenu() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                selectProgram();
            } else if (choice.equals("2")) {
                runProgram();
            } else if (choice.equals("0")) {
                break;
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }

    public void selectProgram () {
        System.out.println("1. " + Examples.ex1);
        System.out.println("2. " + Examples.ex2);
        System.out.println("3. " + Examples.ex3);
        System.out.println("4. " + Examples.ex4);
        System.out.print("Program: ");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            selectedProgram = Examples.ex1;
        } else if (choice.equals("2")) {
            selectedProgram = Examples.ex2;
        } else if (choice.equals("3")) {
            selectedProgram = Examples.ex3;
        } else if (choice.equals("4")) {
            selectedProgram = Examples.ex4;
        }
        else {
            System.out.println("Invalid program");
            return;
        }
        System.out.println("Selected: " + selectedProgram);
    }

    public void runProgram () {
        if (selectedProgram == null) {
            System.out.println("No program selected");
            return;
        }
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);
        IRepository repo = new Repository(state);
        Controller controller = new Controller(repo, true);
        try{
            controller.allStep();

        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
