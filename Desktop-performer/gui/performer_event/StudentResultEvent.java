/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_event;

import gui.performer_main.Student;

/**
 *
 * @author Dragan
 */
public class StudentResultEvent extends java.util.EventObject {
    
    private Student student;
    
    public StudentResultEvent (Object source, Student student)
    {
        super(source);
        this.student = student;
    }
    
    public Student getStudent()
    {
        return student;
    }
}
