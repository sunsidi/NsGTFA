package JavaNs2.Models;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLoader {

    public File loadFile() {
        //TODO: change the fixed file path, maybe remember the most recent file path
        JFileChooser fileChooser = new JFileChooser("/Users/SD/Desktop/School's Lectures/Final Project/Examples/a2_AODV_new.tr"); // use for testing
//        JFileChooser fileChooser = new JFileChooser();
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
            return selectedFile;
        } else {
            return null;
        }
    }

//    boolean fileSelect(String Filename, String FileName_Complete_path, String Directory_path) throws IOException
//    {
//        this.Filename = Filename;
//        FileSelected = true;
//        // JOptionPane.showMessageDialog(null,"Yes");
//        // String fileNameWithOutExt = "Tests.xml".replaceFirst("[.][^.]+$", "");
//        String fileNameWithOutExt = Filename.replaceFirst("[.][^.]+$", "");
//        this.inputFileName = FileName_Complete_path;
//        if (newFile == true) {
//            //System.out.println("SequeValue"+SequeValue);
//            this.outputFileName = Directory_path + "\\" + fileNameWithOutExt + "_" + SequeValue + "_" + ".txt";
//        }
//
//        fout = new FileWriter(outputFileName, true); //the true will append the new data
//        op = new BufferedWriter(fout);
//
//        return FileSelected;
//    }
}
