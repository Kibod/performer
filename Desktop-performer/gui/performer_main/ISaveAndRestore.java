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

public interface ISaveAndRestore {
    void save () throws IOException;
    void restore ()throws IOException;
}
