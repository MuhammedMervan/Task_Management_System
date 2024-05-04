

package gorevtakibi;


public class Task {

  private String title;
  private String description;
  private String priority;
  private String dueDate;


  public Task() {
  }

  public String getDueDate() {
    return dueDate;
  }


  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }


  public String toString() {

    return title + ": " + description + ":---------------------------- " + priority + " \n\t";
  }


  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public String getPriority() {
    return priority;
  }


  public void setPriority(String priority) {
    this.priority = priority;
  }


}











