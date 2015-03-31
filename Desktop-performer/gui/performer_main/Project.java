/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

/**
 *
 * @author Dragan
 */

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class Project //implements ISaveAndRestore {
{
    private String name; // name of the project
    private String location; // location on hard drive
    
    private String studentsRootFolder; // folder with students' work
    private String testsRootFolder; // folder with tests
    
    private String assignmentType;
    
    /*
     *  Project default values
     */
    static public String defaultProjectName = "NewProject";
    static public String defaultProjectLocation = "D:\\Performer\\NewProject";
    static public String defaultAssignmentsRootFolder = "D:\\Performer\\NewProject\\Assignments";
    static public String defaultTestsRootFolder = "D:\\Performer\\NewProject\\Tests";
    
    private ProjectPanel projectPanel;
    
    private StudentsAndAssignmentsCollector collector;
    
    //private static ConfigurationFile config;
    
    // for test purpose:
    //public Vector<SpecificTest> tests = new Vector<SpecificTest>();
    
    public static LinkedList<StudentRepresentationModel> studentsNodes
            = new LinkedList<StudentRepresentationModel>();
    
    // not yet
    //public static LinkedList<SpecificTest> testsNodes 
    //        = new LinkedList<SpecificTest>();
    
    public static Map<String, Student> studentCache = new HashMap<String, Student>();
    public static LinkedList<Student> studentsList = new LinkedList<Student>();
    
    public Project (String _name, String _location, String _studentsRootFolder,
            String _testsRootFolder, String _assignmentType, ProjectPanel _projectPanel)
    {
        name = _name;
        location = _location;
        studentsRootFolder = _studentsRootFolder;
        testsRootFolder = _testsRootFolder;
        assignmentType = _assignmentType;
        projectPanel = _projectPanel;
        collector = new StudentsAndAssignmentsCollector(studentsRootFolder, assignmentType);
    }
    
    public Vector<Student> getStudents ()
    {
        /*
        try{
            collector.run();
            collector.join();       
            }
        catch (InterruptedException e) {}
        */
        collector.findStudents(); 
        return StudentsAndAssignmentsCollector.allStudents;
    }
    
    public StudentsAndAssignmentsCollector getCollector ()
    {
        return collector;
    }        
    
    // get/set methods
    
    public String getName ()
    {
        return name;
    }
    
    public void setName (String name)
    {
        this.name = name;
    }
    
    public String getLocation ()
    {
        return location;
    }
    
    public void setLocation (String location)
    {
        this.location = location;
    }
    
    public String getStudentsRootFolder ()
    {
        return studentsRootFolder;
    }
    
    public void setStudentsRootFolder (String studentsRootFolder)
    {
        this.studentsRootFolder = studentsRootFolder;
    }
    
    public String getTestsRootFolder ()
    {
        return testsRootFolder;
    }
    
    public void setTestsRootFolder (String testsRootFolder)
    {
        this.testsRootFolder = testsRootFolder;
    }
    
    public String getAssignmentType ()
    {
        return assignmentType;
    }
    
    public void setAssignmentType (String assignmentType)
    {
        this.assignmentType = assignmentType;
    }
    
    public ProjectPanel getProjectPanel ()
    {
        return projectPanel;
    }
    
    
    
    /*
    public void save () throws IOException  
    {
        
        FileOutputStream output = new FileOutputStream("D:\\Performer\\NewProject\\project.xml");
        XMLEncoder encoder = new XMLEncoder(output);
        encoder.writeObject(this);
        encoder.close();
        output.close();
    }
    
    public void restore () throws IOException
    {
        FileInputStream input = new FileInputStream("D:\\Performer\\NewProject\\project.xml");
        XMLDecoder decoder = new XMLDecoder(input);
        Project tempProject = (Project)decoder.readObject();
        this.setName(tempProject.getName());
        this.setLocation(tempProject.getLocation());
        this.setStudentsRootFolder(tempProject.getStudentsRootFolder());
        this.setTestsRootFolder(tempProject.getTestsRootFolder());
        decoder.close();        
        input.close();
    }
    */
}
