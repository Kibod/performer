/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

/**
 *
 * @author Dragan
 */

import java.io.*;
import java.util.*;

public class StudentsAndAssignmentsCollector{// extends Thread {
    
    private File studentsRootFolder;
    private String assignmentType; //extension?
    
    public static Vector<Student> allStudents = new Vector<Student>();
    
    //public static Map<File, Student> allStudentsHash = new HashMap<File, Student>();
    
    public StudentsAndAssignmentsCollector (String rootFolder, String assignmentType)
    {
        studentsRootFolder = new File(rootFolder);
        this.assignmentType = assignmentType;
    }
    
    public void findStudents ()
    //public void run()
    {
        File[] fileList = studentsRootFolder.listFiles();
        
        for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				try 
                                {
					// create new student
                                    
                                        String studentPath = fileList[i].getName();
                                        
					//String name = fileList[i].getName().substring(0, 2);
					int year = Integer.parseInt(fileList[i].getName().substring(2, 4));
					int index = Integer.parseInt(fileList[i].getName().substring(4, 8));
                                       
                                        LinkedList<Assignment> assignments = new LinkedList<Assignment>();
                                        
                                        String[] contains = fileList[i].list();
                                        
                                        for(String s : contains)
                                        {
                                            if (s.endsWith(assignmentType)) assignments.add(new Assignment(s));                                                                                        
                                        }
                                        
                                        Student s = new Student(studentPath,year,index,assignments);
                                        if (!allStudents.contains(s))
                                        {
                                            Project.studentsNodes.add(new StudentRepresentationModel(s));
                                            s.getStudentEvent().addEventListener(StudentRepresentationModel.findNodeByStudent(s));
                                            allStudents.add(s);                                            
                                        }
                                        
                                }
                                catch(Exception e) {}
                                
                        }
        }
    }
    
    public Vector<Student> getStudents ()
    {
        return allStudents;
    }
    
    public void writeDown ()
    {
        for(Student s : allStudents)
            System.out.println(s);
    }
}
