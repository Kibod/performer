/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Dragan
 */
public class Assignment {
    
        public static class ResultColorInterpretation { 
        
        Color color;
        
        public ResultColorInterpretation()
        {
            color = Color.BLACK;
        }
        
        public ResultColorInterpretation(String result)
        {
            if (result.equals("PASS")) color = Color.GREEN;
            if (result.equals("IN_PROGRESS")) color = Color.YELLOW;
            if (result.equals("FAILED")) color = Color.RED;            
        }
        
        public Color getColor ()
        {
            return color;
        }
        
    };
    
    private String path;
    private LinkedList<String> result = new LinkedList<String>();
    private ResultColorInterpretation resultColor;
    private Student student;
    
    public Assignment (String path)
    {
        this.path = path;
        resultColor = new ResultColorInterpretation();
    }
    
    public void setStudent (Student student)
    {
        this.student = student;
    }
    
    //public void setResult(LinkedList<String> results)
    public void setResult(String result)
    {
        //this.result = results;
        /*
        boolean flag = true;
        this.results = new String[results.length];
        for (int i=0; i<results.length; i++)
        {
            this.results[i] = results[i];
            if (results[i].equals("false")) 
            {
                resultColor = new ResultColorInterpretation("FAILED");
                flag = false;
            }
        }
        if (flag) this.resultColor = new ResultColorInterpretation("PASS");
        student.getStudentEvent().fireEvent();
        resultColor = new ResultColorInterpretation(result)
        * 
        */
        /*
        for (String s : results)
            if (s.equals("false"))
            {
                resultColor = new ResultColorInterpretation("FAILED");
                student.getStudentEvent().fireEvent();
                return;
            }
        resultColor = new ResultColorInterpretation("PASS");
        student.getStudentEvent().fireEvent();
        * 
        */
        resultColor = new ResultColorInterpretation(result);
        student.getStudentEvent().fireEvent();
    }
    
    public String getResultTextRepresentation()
    {
        /*
        StringBuilder s = new StringBuilder();
        s.append("ErrorTest: " + results[0] + "\n" +
                 "TimeLimitTest: " + results[1] + "\n" +
                 "CorrectOutputTest: " + results[2] + "\n" +
                 "TimeTest: " + results[3] + " ms\n");
        return s.toString();
        * *
        */
        return "0";
    }
    
    public Color getResultColorInterpretation()
    {
        return resultColor.getColor();
    }
    
    public String getPath()
    {
        return path;
    }
    
    /*
     *  public void toString()
     * {
     * 
     * }
     */
    
    
    
}
