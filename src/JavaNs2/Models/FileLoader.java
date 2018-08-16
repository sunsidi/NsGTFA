package JavaNs2.Models;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileLoader {
    private String defaultPath = "";

    public File loadFile() {
        //TODO: change the fixed file path, maybe remember the most recent file path
//        JFileChooser fileChooser = new JFileChooser("/Users/SD/Desktop/School's Lectures/Final Project/Examples/a2_AODV_new.tr"); // use for testing
        JFileChooser fileChooser = new JFileChooser(defaultPath);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Ns2 Trace Files Only", "tr");
        fileChooser.addChoosableFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) { // pop up file selection window successfully
            File selectedFile = fileChooser.getSelectedFile();
            String selectedFile_path = selectedFile.getAbsolutePath(); // returns files's complete path
            String directory_path = selectedFile.getParent();   // returns directory path
            String fileName = selectedFile.getName();  // file's name
//            System.out.println(selectedFile.getName() + " \n" + selectedFile_path + "\n" + directory_path + "\n");
            defaultPath = directory_path;
            return selectedFile;
        } else {
            return null;
        }
    }
}
