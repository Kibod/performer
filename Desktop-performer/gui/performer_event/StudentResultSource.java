/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gui.performer_main.Student;

/**
 *
 * @author Dragan
 */
public class StudentResultSource {
  private List listeners = new ArrayList();
  private Student student;
  
  public StudentResultSource (Student student)
  {
      this.student = student;
  }
  
  public synchronized void addEventListener(StudentResultListener listener)	{
    if (!listeners.contains(listener)) listeners.add(listener);
  }
  public synchronized void removeEventListener(StudentResultListener listener)	{
    if (listeners.contains(listener)) listeners.remove(listener);
  }
  
  public Student getStudent()
  {
      return student;
  }

  // call this method whenever you want to notify
  //the event listeners of the particular event
  public synchronized void fireEvent()	{
    StudentResultEvent event = new StudentResultEvent(this,student);
    Iterator i = listeners.iterator();
    while(i.hasNext())	{
      ((StudentResultListener) i.next()).handleStudentResultEvent(event);
    }
  }
}
