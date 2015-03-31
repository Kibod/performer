/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Dragan
 */
public class ConfigurationFile implements ISaveAndRestore {

    public String projectName;
    public String projectLocation;
    public String studentsFolder;
    public String testsFolder;
    public String assignmentType;
    
    public ConfigurationFile (String projectName,
                              String projectLocation,
                              String studentsFolder,
                              String testsFolder,
                              String assignmentsType)
    {
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.studentsFolder = studentsFolder;
        this.testsFolder = testsFolder;
    }
    
    @Override
    public void save() throws IOException {
        File xml = new File(projectLocation  + "\\config.xml");
        if (xml.exists()) xml.delete(); 
        FileOutputStream output = new FileOutputStream(projectLocation  + "\\config.xml");
        XMLEncoder encoder = new XMLEncoder(output);
        encoder.writeObject(projectName);
        encoder.writeObject(projectLocation);
        encoder.writeObject(studentsFolder);
        encoder.writeObject(testsFolder);
        encoder.writeObject(assignmentType);
        encoder.close();
        output.close();
    }

    @Override
    public void restore() throws IOException {
        FileInputStream input = new FileInputStream(projectLocation + "\\config.xml");
        XMLDecoder decoder = new XMLDecoder(input);
        projectName = (String)decoder.readObject();
        projectLocation = (String)decoder.readObject();
        studentsFolder = (String)decoder.readObject();
        testsFolder = (String)decoder.readObject();
        assignmentType = "exe";
        decoder.close(); 
    }
    
    
}
