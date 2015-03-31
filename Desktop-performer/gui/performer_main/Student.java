/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

/**
 *
 * @author Dragan
 */

import java.awt.*;
import java.util.LinkedList;
import gui.performer_event.StudentResultSource;

public class Student {
    
    //private String name;
    private int year;
    private int index;
    private String studentPath;
    private LinkedList<Assignment> assignments;
    private String[] results;
    private StudentResultSource studentEvent;
    
    
    public Student (String studentPath, int year, int index, LinkedList<Assignment> assignments)
    {
        //this.name = name;
        this.studentPath = studentPath;
        this.year = year;
        this.index = index;
        this.assignments = assignments;
        studentEvent = new StudentResultSource(this); 
        for (Assignment a : assignments)
        {
            a.setStudent(this);
        }
    }
    
    public String toString ()
    {
        return year + "/" + index;
    }
    
    public boolean equals(Object o)
    {
        if (year == ((Student)o).year && index == ((Student)o).index) return true;
        return false;
    }
    
    /*
     * get / set methods
     */


    
    public String getAssignmentPath ()
    {
        return studentPath + assignments.getFirst() + "exe";
    }
    
    public StudentResultSource getStudentEvent ()
    {
        return studentEvent;
    }
    
    public LinkedList<Assignment> getStudentAssignments ()
    {
        return assignments;
    }
    
    public Color studentColor ()
    {
        if (assignments.size() == 0) return Color.BLACK;
        if (assignments.size() == 1) return assignments.getFirst().getResultColorInterpretation();
        boolean green = false, red = false;
        for (Assignment a : assignments)
        {
            if (a.getResultColorInterpretation().equals(Color.GREEN)) green = true;
            if (a.getResultColorInterpretation().equals(Color.GREEN)) red = true;
        }
        if (red && green) return Color.YELLOW;
        else if (green) return Color.GREEN;
        else return Color.RED;
    }
            
}
