package JavaNs2.Controllers;

import JavaNs2.Models.FileAnalyser;
import JavaNs2.Models.FileLoader;
import JavaNs2.Views.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerMainFrame {
    private MainFrame view;
    private FileLoader fileLoader;
    private File file;
    private ArrayList<String> filePaths = new ArrayList<>();

    public ControllerMainFrame(MainFrame view, FileLoader fileLoader)
    {
        this.view = view;
        this.fileLoader = fileLoader;
//        this.fileAnalyser = fileAnalyser;
        this.setStart();
    }

    /**
     * Initialize button status after the GUI is launched
     */
    private void setStart()
    {
        view.getBtnNew().setEnabled(true);
        view.getBtnLoadFile().setEnabled(false);
        view.getBtnLoadFolder().setEnabled(false);
        view.getBtnCalculation().setEnabled(false);
        view.getBtnChartGraph().setEnabled(false);
        view.getBtnSelectAll().setEnabled(false);
        view.getBtnMostUsed().setEnabled(false);
        view.getBtnClearAll().setEnabled(false);
        view.setCheckboxSelected(false);
        view.setCheckboxEnabled(false);
    }

    public void actionListener()
    {
        // buttons
        view.getBtnNew().addActionListener(e -> newAnalyse());
        view.getBtnReset().addActionListener(e -> reset());
        view.getBtnLoadFile().addActionListener(e -> loadFile());
//        view.getBtnLoadFolder().addActionListener();
        for (JCheckBox tmpBox : view.getMetrics()) {
            tmpBox.addActionListener(e -> {
                view.setLabel_Step2();
                // enable calculation button
                view.getBtnCalculation().setEnabled(true);
            });
        }
        view.getBtnCalculation().addActionListener(e -> {
            try {
                analyseFile(filePaths);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        view.getBtnChartGraph().addActionListener(e -> generateGraph());
        view.getBtnSelectAll().addActionListener(e -> selectAllMetrics());
        view.getBtnMostUsed().addActionListener(e -> selectMostUsedMetrics());
        view.getBtnClearAll().addActionListener(e -> clearAll());
        view.getCombBoxGraphType().addActionListener(e -> selectGraphType());
        // menu
        view.getMntmExit().addActionListener(e -> System.exit(0));
        view.getMntmAbout().addActionListener(e -> view.about());
        view.getMntmReadMemanual().addActionListener(e -> view.userManual());
    }

    private void reset()
    {
        // reset GUI
        view.resetGUI();
        // clear all saved file path
        filePaths.clear();
    }

    private void newAnalyse()
    {
        view.getBtnLoadFile().setEnabled(true);
        // not supported in this version
//        view.getBtnLoadFolder().setEnabled(true);
        view.getBtnNew().setEnabled(false);
        view.getBtnNew().setVisible(false);
        view.getBtnReset().setVisible(true);
    }

    private void loadFile()
    {
//        setMetricOptions(false);
//        btnLoadFile.setEnabled(true);
//        btnLoadFolder.setEnabled(false);
//        btnNew.setEnabled(true);
        file = fileLoader.loadFile();
        if (file != null)
            filePaths.add(file.getAbsolutePath());
        // enable metrics button
        view.getBtnSelectAll().setEnabled(true);
        view.getBtnMostUsed().setEnabled(true);
        view.getBtnClearAll().setEnabled(true);
        // enable metrics checkboxes
        view.setCheckboxEnabled(true);
        view.setLabel_Step1();
    }

    private void loadFolder()
    {
        int num = 10;

        //progressBar.setEnabled(true);
        //progressBar.setVisible(true);
//            setMetricOptions(false);
//            btnLoadFile.setEnabled(false);
//            btnLoadFolder.setEnabled(true);
//            btnNew.setEnabled(true);

        String chooserTitle = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // disable the "All files" option.
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
        } else {
            //System.out.println("No Selection ");
            String st = "No Selection";
            JOptionPane.showMessageDialog(null, st);
        }

        //String folderCompletePath = chooser.getSelectedFile().getAbsolutePath();
        //System.out.println("folderPath =" + folderCompletePath );
//        FFReader.FolderReader(chooser.getSelectedFile().getAbsolutePath(), SequeValue);
    }

    private void analyseFile(ArrayList<String> filePaths) throws IOException
    {
        ArrayList<JCheckBox> metrics = view.getMetrics();
        ArrayList<JCheckBox> tmp = new ArrayList<>();

        metrics.forEach((JCheckBox tmpBox) -> {
            if (tmpBox.isSelected()) {
                tmp.add(tmpBox);
            }
        });

        for (String filePath : filePaths) {
            new FileAnalyser(view, filePath, tmp).execute();
        }

        view.getBtnLoadFile().setEnabled(false);
        view.getBtnLoadFolder().setEnabled(false);
        view.getBtnChartGraph().setEnabled(true);
        view.getBtnSelectAll().setEnabled(false);
        view.getBtnMostUsed().setEnabled(false);
        view.getBtnClearAll().setEnabled(false);
        view.getBtnCalculation().setEnabled(false);
        view.setCheckboxEnabled(false);
        view.setLabel_Step3();
    }

    private void selectAllMetrics()
    {
        view.setCheckboxSelected(true);
        // enable calculation button
        view.getBtnCalculation().setEnabled(true);
        view.setLabel_Step2();
    }

    private void selectMostUsedMetrics()
    {
        view.selectMostUsedMetrics();
        // enable calculation button
        view.getBtnCalculation().setEnabled(true);
        view.setLabel_Step2();
    }

    private void clearAll()
    {
        view.setCheckboxSelected(false);
        // disable calculation button
        view.getBtnCalculation().setEnabled(false);
    }

    private void generateGraph()
    {
        String[] metrics = {"Packet Delivery Ratio", "Normalized Routing Load", "Throughput", "End to End Delay", "Overhead", "Dropped Packets"};

        view.setupGraphPanel(metrics, 0);
        view.getBtnCalculation().setEnabled(false);
        view.setLabel_Step4();
    }

    private void selectGraphType()
    {
        String[] metrics = {"Packet Delivery Ratio", "Normalized Routing Load", "Throughput", "End to End Delay", "Overhead", "Dropped Packets"};
        int index = view.getCombBoxGraphType().getSelectedIndex();

//        view.setupGraphPanel(results, metrics, index);
        view.updateGraph(metrics, index);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame v = new MainFrame();
                FileLoader f = new FileLoader();
//                FileAnalyser fileAnalyser = new FileAnalyser();
                ControllerMainFrame c = new ControllerMainFrame(v, f);
                c.actionListener();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
