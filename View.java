
//This class represents the user interface , with main menu options with calls to the user controller or task controller class.
package gorevtakibi;

import gorevtakibi.ControllerT;
import java.util.Scanner;

public class View {

  public static Scanner input = new Scanner(System.in);
  ControllerU user = new ControllerU();
  ControllerT taskController = new ControllerT();


  public void begin() {

    System.out.println(
        "_____________________________________________________________________________________");

    System.out.println(
        "                           Welcome to your task manager \n\t\t\t                          Createtd By Mervan");

    System.out.println(
        "_____________________________________________________________________________________");

    System.out.println(
        "          Press -1- to LOGIN IN\n\t\t\t\t\tPress -2- to CREATE AN ACCOUNT (if you are not registered)\n\t\t\t\t\tPress -3- to GET YOUR PASSWORD(if you forgot your password)");

    int choice = input.nextInt();

    switch (choice) {

      case 1:
        user.verifyLogin();
        break;
      case 2:
        registerUser();
        break;
      case 3:
        user.getLostPass();
        break;
      default:
        System.out.println("Try again. Choose a valid number");
    }


  }


  public void loggedIn() {

    System.out.println(
        "\n\n\n____________________________________________________________________________________");

    System.out.println(
        "What would you like to do?\n\n      TASK OPTIONS !!    \n\n1 = ADD A TASK\n2 = REMOVE A TASK\n3 = SEARCH FOR A TASK\n4 = DISPLAY ALL TASKS\n5 = STATUS OF TASKS(DUE OR OVERDUE)\n\n\n        USER OPTIONS!!!   \n\n6 = DISPLAY ALL USERS\n7 = REMOVE A USER\n8 = EXIT\n---------------------\n\t");

    int choice = input.nextInt();

    switch (choice) {

      case 1:
        addTask();
        break;
      case 2:
        try {
          taskController.remove();
        } catch (Exception e) {
          System.out.println("Could not read data");
        }
        break;
      case 3:
        taskController.search();
        break;
      case 4:
        try {
          displayTask();
        } catch (Exception e) {
          System.out.println("File not found");
        }
        break;
      case 5:
        taskController.taskStatus();
        break;
      case 6:
        user.display();
        break;
      case 7:
        user.remove();
        break;
      case 8:
        System.out.println("Goodbye !!");
        System.exit(0);
        break;

      default:
        System.out.println("Try again. Choose a valid number");
    }

  }

  public void addTask() {

    input = new Scanner(System.in);

    System.out.println("Add a task TITLE:\n");
    String title = input.nextLine().toUpperCase().trim();
    taskController.myTasks.setTitle(title);
    System.out.println("Add a DESCRIPTION:\n");
    String desc = input.nextLine().toLowerCase().trim();
    taskController.myTasks.setDescription(desc);
    System.out.println("Set a PRIORITY: LOW, MEDIUM, HIGH: \n");
    String priority = input.nextLine().toUpperCase().trim();
    taskController.myTasks.setPriority(priority);
    System.out.println("Enter Task due date yyy MM dd e.g 2022-06-08:\n");
    String dd = input.nextLine();
    taskController.myTasks.setDueDate(dd);

    taskController.tasks.add(taskController.myTasks);
    taskController.add();
  }

  public void displayTask() {

    taskController.display();
  }


  public void registerUser() {

    input = new Scanner(System.in);

    System.out.println("Enter desired Username:\n");
    String username = input.nextLine().toLowerCase().trim();
    user.register.setUserName(username);
    System.out.println("Enter desired Password:\n");
    String pass = input.nextLine().toLowerCase().trim().toString();
    user.register.setPassword(pass);

    user.data.add(user.register);
    user.add();
  }
}











