
// Bu sınıfta kullanıcının görevleri ile alakalı bütün işler içermektedir

package gorevtakibi;

import gorevtakibi.Task;
import gorevtakibi.TaskManagerAbstract;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class ControllerT extends TaskManagerAbstract {

  private static String filepath = "task.txt";
  List<Task> tasks = new ArrayList<>();
  Map<String, String> taskData = new TreeMap<>();
  private Scanner input;
  private FileWriter fw;
  private BufferedWriter bw;
  private PrintWriter pw;
  private static View view = new View();
  Task myTasks = new Task();

  // Bu metod parametre almamaktadır. Bu metod hash map listesini kontrol edip mevcüt
  // olan görevleri gösterir


  public ControllerT() {

    getFilepath();
    try {
      saved();
    } catch (Exception e) {
      System.out.println("File could not be read");
    }


  }


  public static String getFilepath() {
    return filepath;


  }


  /**
   * Kullanıcının girdiği görevler bir Arraylist'te alınmaktadır ve bir txt dosyasında
   * saklanmaktadır, aynı zamanda programında farklı aşamalarında kullanılacak olan hash map
   * listesinde de saklanır
   **/
  void add() {

    // yeni eklenen görevlerle hash map'ı güncellemek
    String title = myTasks.getTitle().toString();
    String description =
        "|" + myTasks.getDescription().toString() + ">>>>>> " + "Priority: " + myTasks.getPriority()
            .toString() + ">>>>> " + " Due Date " + "|" + myTasks.getDueDate().toString();
    this.taskData.put(title, description);

    try {
      if (myTasks != null) {

        fw = new FileWriter(filepath, true);
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw);

        pw.println(myTasks.getTitle() + "|" + myTasks.getDescription() + ">>>>>> " + "Priority: "
            + myTasks.getPriority() + ">>>>> " + " Due Date " + "|" + myTasks.getDueDate());

        pw.flush();
        pw.close();

      }

    } catch (IOException e) {

      System.out.println("No task added");
    }

    System.out.println(
        "|Task added !! \n| " + "The Task title: " + myTasks.getTitle().toString() + "\n"
            + "|Description: " + myTasks.getDescription() + "\n" + "Priority: "
            + myTasks.getPriority() + "\n" + "Due Date " + myTasks.getDueDate());
    System.out.println("_____________________________________\n\n\t");

    view.loggedIn();

  }

  // Bu metod eklenen bütün metodların ve kaç tane olduğunu göstermektedir

  void display() {

    System.out.println("-------------You have : " + taskData.size() + " Tasks-------------\n");

    System.out.println("              Your To Do List !!");

    String key = " ";
    for (Map.Entry<String, String> entry : taskData.entrySet()) {
      key = entry.getKey();
      System.out.println("Task Title:" + key + "\n" + "Description: " + taskData.get(key));
    }

    System.out.println(
        "________________________________________________________________________________\n\t");
    view.loggedIn();

  }


  void remove() {

    System.out.println(
        "_________________________________________________________________________________");
    System.out.println(
        "_________________________________________________________________________________\n");
    System.out.println("Enter task TITLE to remove.   ");

    input = new Scanner(System.in);
    String title = input.nextLine().toUpperCase().trim();

    if (taskData.containsKey(title)) {

      taskData.remove(title);

      System.out.println("Task has been removed");
    } else {
      System.out.println("Task not found !\n");

      System.out.println("_____________________________________\n\n\t");
    }
    System.out.println("-------------You have : " + taskData.size() + " Tasks left-------------\n");

    view.loggedIn();
  }

  //hash map ta aramak için görevin başlığını istenir ve arama işlemi gerçekleştirilir

  void search() {

    System.out.println("____________WELCOME TO SEARCH !!________\n\n\t");
    System.out.println("Enter TASK TITLE to search for: \n-----------------------------------");

    input = new Scanner(System.in);
    String searchTerm = input.nextLine().toUpperCase().trim();
    if (taskData.containsKey(searchTerm)) {
      //String match = taskData.get(searchTerm);
      System.out.println(taskData.get(searchTerm));

    } else {

      System.out.println("Task not found");
    }

    System.out.println("_____________________________________\n\n\t");
    view.loggedIn();
  }

  //Bu metod txt dosyasından bilgi okuyup hash map listesine ekler

  public void saved() {

    String title = " ";
    String description = " ";

    try {
      input = new Scanner(new File(filepath));

      input.useDelimiter("[\n]");

      String row = "";

      while (input.hasNext()) {

        row = input.next();

        // title |, description| due_date
        title = row.substring(0, row.indexOf('|'));
        description = row.substring(row.indexOf("|"));

        this.taskData.put(title, description);
      }
      input.close();

    } catch (Exception e) {

      System.out.println("File not found");
    }

  }


  // Bu metod bugünkünün tarihini yazar, eklenen görevlerin tarihleri ile kontrol eder ve görevlerin yüzdesi kaç süresi dolan ve daha zamanı var olan yazar
  public void taskStatus() {

    String taskTitle = " ";
    String dueDate = " ";
    String description = " ";
    double overDueCount = 0;
    double pendingCount = 0;
    double dueTodayCount = 0;
    double total = taskData.size();

    try {
      input = new Scanner(new File(filepath));
    } catch (FileNotFoundException e) {
      System.out.println("Cannot read data file");
    }

    input.useDelimiter("[\n]");

    String row = null;
    System.out.println(
        "________________________________YOUR TASKS Status___________________________________");

    while ((input.hasNext())) {
      row = input.next();
      try {

        // görevin başlığı ve detayları zaman sınırından ayırmak için bir metod

        taskTitle = row.substring(0, row.indexOf('|'));
        String temp = row.substring(row.indexOf("|") + 1);
        description = temp.substring(0, temp.indexOf("|") + 1);
        dueDate = temp.substring(temp.indexOf("|") + 1);

        System.out.println("Task Title:" + taskTitle);

        // bugünkünün tarihi görevin zaman sınırı ile karıştırmak
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        Date taskDate = null;
        Date today = new Date();

        taskDate = sdf.parse(dueDate);

        if (today.before(taskDate)) {

          System.out.println("Task is pending");
          System.out.println("due date is: " + dueDate + "\n" + "Todays date is:" + today);
          System.out.println("_______________________________");

          pendingCount++;

        } else if (taskDate.before(today)) {

          System.out.println("Task is overdue");
          System.out.println("due date is: " + dueDate + "\n" + "Todays date is:" + today);
          System.out.println("_______________________________");
          overDueCount++;

        } else {
          System.out.println("Task is due today");
          System.out.println("due date is: " + dueDate + "\n" + "Todays date is:" + today);
          System.out.println("_______________________________");
          dueTodayCount++;

        }

      } catch (Exception e) {

        System.out.println("No more elements to read");
      }
    }
    input.close();

    // Süresi dolan, daha zamanı var olan, ve süresi bugün dolacak olan görevlerin yüzdesini hesaplamak

    double percentPending = Math.round(((pendingCount / total) * 100) * 100.0) / 100;
    double percentOverDue = Math.round(((overDueCount / total) * 100) * 100.0) / 100;
    double percentToday = Math.round(((dueTodayCount / total) * 100) * 100.0) / 100;

    System.out.println("====================================================================\n");
    System.out.println(
        "                      SUMMARY                     \n                  You have: "
            + taskData.size() + " Tasks\n\n" + "   PENDING: " + pendingCount + " - "
            + percentPending + "% , " + "   OVERDUE: " + overDueCount + " - " + percentOverDue
            + "%," + "   DUE TODAY: " + dueTodayCount + " - " + percentToday + "%");
    System.out.println("====================================================================\n");
    view.loggedIn();


  }

}





