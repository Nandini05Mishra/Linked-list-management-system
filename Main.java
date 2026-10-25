import java.util.NoSuchElementException;
import java.util.Scanner;

/** Entry point: Linked List Management System (menu driven). */
public class Main {

    private static void printMainMenu() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("          LINKED LIST MANAGEMENT SYSTEM");
        System.out.println("==================================================");
        System.out.println("1. Singly Linked List");
        System.out.println("2. Doubly Linked List");
        System.out.println("3. Compare Singly vs Doubly Linked List");
        System.out.println("4. Exit");
    }

    public static void main(String[] args) {
        for (String a : args) if (a.equals("--echo")) InputUtil.echo = true;

        Scanner sc = new Scanner(System.in);
        SinglyLinkedList singly = new SinglyLinkedList();   // lists keep their data while the program runs
        DoublyLinkedList doubly = new DoublyLinkedList();

        try {
            int choice;
            do {
                printMainMenu();
                choice = InputUtil.readInt(sc, "Enter your choice: ");
                switch (choice) {
                    case 1: singly.menu(sc); break;
                    case 2: doubly.menu(sc); break;
                    case 3: ListComparison.run(); break;
                    case 4: System.out.println("Thank you for using the Linked List Management System. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Please enter 1-4.");
                }
            } while (choice != 4);
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Input ended. Exiting.");
        }
        sc.close();
    }
}
