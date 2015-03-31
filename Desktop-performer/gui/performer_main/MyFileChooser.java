/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.performer_main;

/**
 *
 * @author Dragan
 */

import javax.swing.*;
import java.awt.*;
import gui.performer_plus.ExampleFileFilter;



public class MyFileChooser extends JFileChooser {
    
    private String work;
    
    public MyFileChooser (String work, String... filters)
    {
        super();
        ExampleFileFilter filter = new ExampleFileFilter();
        for (String f : filters)
        {
            filter.addExtension(f);
        }
        this.addChoosableFileFilter(filter);
        this.work = work;
    }
    
    /*
    public MyFileChooser (String work, String... exten)
    {
        super(work);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(extern);
    }
    * 
    */
    
    public int showDialog(Component parent)
    {
        return super.showDialog(parent, work);
    }   
}
