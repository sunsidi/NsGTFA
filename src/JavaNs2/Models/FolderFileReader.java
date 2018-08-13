//package JavaNs2.Models;
//
//import java.io.File;
//import java.io.FilenameFilter;
//
//import javax.swing.JOptionPane;
//
//
//public class FolderFileReader extends MainNs2Trace {
//    //String Filename;
//    //private Scanner keyboard = new Scanner(System.in); // for stream reading from keyboard reading
//
//    // a method to read filtered files within a folder one by one
//    public void FolderReader(String folderPath, String SValue)
//    {
//        File folder = new File(folderPath);
//        SequeValue = SValue; // Set SequeValue in the MainNs2Trace
//        try {
//            if (folder.exists()) {   // reads all files that have extension .tr one by one and stores them in an array "File"
//                File[] AllOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".tr"));
//
//                //Read files one by one
//                for (int i = 0; i < AllOfFiles.length; i++)
//                    if (AllOfFiles[i].isFile()) // go through them one by one
//                    {
//                        // System.out.println(AllOfFiles[i].getName());
//                        System.out.println("-------------------------------------");
//                        if (AllOfFiles[i].canRead()) {
//                            //totalFiles++;
//                            //ReadFileContentsLinebyLine(AllOfFiles[i]); // Read file's contents line/line
//
//                            FolderSelect = true;
//                            String selectedFile_path = AllOfFiles[i].getAbsolutePath(); // returns files's complete path
//                            String directory_path = AllOfFiles[i].getParent();   // returns directory path
//                            String fileName = AllOfFiles[i].getName();  // file's name
//                            System.out.println(AllOfFiles[i].getName() + " \n" + selectedFile_path + "\n" + directory_path);
//                            fileSelect(fileName, selectedFile_path, directory_path);
//                            calculate();
//                            // ResultsPrint(time);                 // Print all the calculated results
//
//                        } else
//                            //System.out.println("Error: Unreadable file ..");
//                            JOptionPane.showMessageDialog(null, "Unreadable file ..!");
//
//                    }
//            }// folder not exist
//            else
//                //System.out.println("Error: Wrong folder");
//                JOptionPane.showMessageDialog(null, "Error: Wrong folder");
//        } catch (Exception e) {
//            // if any error occurs
//            e.printStackTrace();
//        }
//
//    }
//
//
////	 boolean fileSelect(String Filename, String FileName_Complete_path, String Directory_path) throws IOException{
////			this.Filename=Filename;
////			FileSelected=true;
////			// JOptionPane.showMessageDialog(null,"Yes");
////	    	// String fileNameWithOutExt = "Tests.xml".replaceFirst("[.][^.]+$", "");
////	        String fileNameWithOutExt = Filename.replaceFirst("[.][^.]+$", "");
////	        this.inputFileName = FileName_Complete_path;
////	        if( newFile==true)  {
////	        	System.out.println("SequeValue"+SequeValue);
////	             this.outputFileName =Directory_path+"\\"+fileNameWithOutExt+"_"+SequeValue+"_"+".txt";
////	             System.out.println("outputFileName =!" + outputFileName);
////	        }
////
////	          fout  = new FileWriter(outputFileName, true); //the true will append the new data
////	          op = new BufferedWriter(fout);
////
////     return FileSelected;
////	 }
//
//}
//
