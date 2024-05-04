

/**
 * Bu sınıfta hesap oluşturma, kullanıcının bilgilerini doğrulama ve
 * kullanıcının bilgilerini saklanması için tanımlanan metodlar tanımlanmıştır
 **/

package gorevtakibi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ControllerU extends TaskManagerAbstract {

  Map<String, String> userData = new HashMap<>();
  List<Register> data = new ArrayList<>();
  private String filepath = "Userdata.txt";
  private Scanner input = new Scanner(System.in);
  private static View view = new View();
  Register register = new Register();
  private String key = " ";
  String value = "";


  //kullanıcının bilgilerini saklanan txt dosyası, doğrulama veya kullanıcı oluşturması
  // için anında kullanım için oluşturulan bir metod
  public ControllerU() {

    try {
      saved();
    } catch (Exception e) {
      System.out.println("Could not load data file");
    }
  }

  //yeni hesap oluşturma kullanıcı adı ve şifresini almak için, hash map
  // sınıfına alınması için, ve txt dosyasına saklaması için

  public void add() {

    FileWriter fw;
    try {
      fw = new FileWriter(filepath, true);

      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      pw.println("\n" + register.getUserName() + "," + register.getPassword());

      pw.flush();
      pw.close();

    } catch (IOException e) {
      System.out.println("File cannot be read");
    }
    System.out.println("##################################################");
    System.out.println("Welcome:" + register.toString());

    view.loggedIn();

  }



  //hash map listesini kontrol ederek kullanıcının bilgilerini gpğrular
  public void verifyLogin() {

    System.out.println("Enter username: ");
    String name = input.nextLine();
    System.out.println("Enter password: ");
    String pass = input.nextLine().trim();

    for (Map.Entry<String, String> entry : userData.entrySet()) {
      key = entry.getKey();
      value = entry.getValue();

      if (key.equals(name)) {
        if (value.equals(pass)) {

          view.loggedIn();
        } else {
          System.out.println("Username or password incorrect. Press 1 to register an account");
          view.begin();
        }
      }
    }

  }

  //Bu metod kullanıcının bilgilerini okuyup has mpap listesine ekler
  public void saved() {

    String username = " ";
    String password = " ";

    try {
      Scanner input = new Scanner(new File(filepath));

      input.useDelimiter("[,\n]");

      while (input.hasNext()) {

        username = input.next();
        password = input.next();

        this.userData.put(username, password);


      }

      input.close();

    } catch (FileNotFoundException e) {

      System.out.println("File not found");
    }

  }


  public void display() {
    view = new View();

    System.out.println("              User List !! There are: " + userData.size()
        + "  users \nUsername for all users:\n");

    String key = " ";

    for (Map.Entry<String, String> entry : userData.entrySet()) {
      key = entry.getKey();
      System.out.println(key + "\n");
    }

    System.out.println(
        "________________________________________________________________________________\n\t");
    view.loggedIn();
  }


  public void remove() {

    System.out.println(
        "_________________________________________________________________________________");
    System.out.println(
        "_________________________________________________________________________________\n");
    System.out.println("Enter the Username to remove.   ");

    input = new Scanner(System.in);
    String username = input.nextLine().toLowerCase().trim();

    if (userData.containsKey(username)) {
      userData.remove(username);
    } else {
      System.out.println("User not found !\n");

      System.out.println("_____________________________________\n\n\t");
    }
    System.out.println("-------------You have : " + userData.size() + " users left-------------\n");

    view.loggedIn();

  }


  public void getLostPass() {

    System.out.println("Enter username:  ");
    String username = input.nextLine().toLowerCase().trim();

    if (userData.containsKey(username)) {
      System.out.println("Your password is: " + userData.get(username));
    } else {

      System.out.println("Username not found");
    }

  }


}




