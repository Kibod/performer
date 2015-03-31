/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;


/**
 *
 * @author Dragan
 */
public class StudentRepresentationModel {    
    
    private Student student;
    private CheckNode node;
    
    public StudentRepresentationModel (Student s)
    {
        student = s;
        node = new CheckNode(s.toString());
        
        for (Assignment a : s.getStudentAssignments())
            node.add(new CheckNode(a.getPath()));
    }
    
    public static CheckNode getStudentsRoot ()
    {
        Project.studentsNodes.clear();
        CheckNode studentsRoot = new CheckNode("Students");
        for (Student s : MainWindow.runningProject.getStudents())
        {
            StudentRepresentationModel srm = new StudentRepresentationModel(s);
            Project.studentsNodes.add(srm);
            studentsRoot.add(srm.node);
        }
        return studentsRoot;
    }
    
    public static CheckNode getTestRoot ()
    {
        /*
        CheckNode studentsRoot = new CheckNode("Students");
        for (StudentRepresentationModel s : Project.studentsNodes)
            studentsRoot.add(s.node);
        return studentsRoot;
        * 
        */
        return new CheckNode("Test");
    }
    
    public static CheckNode findNodeByStudent (Student s)
    {
        for (StudentRepresentationModel srm : Project.studentsNodes)
            if (srm.student.equals(s)) return srm.node;
        return null;
    }
    
    public CheckNode getCheckNode ()
    {
        return node;
    }
    
    public Student getStudent ()
    {
        return student;
    }
    
    
}
