package JavaNs2.Views;

import JavaNs2.Models.ModelAnalysisResult;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class MainFrame {
    private JFrame mainFrame = new JFrame();
    private JFrame graphFrame = new JFrame();

    private JPanel panel_1_ChoseTraceFile;
    private JButton btnNew, btnReset, btnLoadFile, btnLoadFolder;

    private JPanel panel_2_Performance;
    private JButton btnCalculation, btnChartGraph;

    private JPanel panel_3_MetricsOptions;
    private JButton btnSelectAll, btnMostUsed, btnClearAll;

    private JPanel panel_4_PerformanceMetrics;
    private JCheckBox chckbxPacketDeliveryRatio,
            chckbxNormalized,
            chckbxThroughput,
            chckbxOverHead,
            chckbxEndToEnd,
            chckbxSentPacket,
            chckbxReceivedPacket,
            chckbxDroppedPackets,
            chckbxDroppedBytes;
    private ArrayList<JCheckBox> metrics = new ArrayList<>();

//    private String[] list = {"Bar Chart", "Pie Chart", "Line Chart"};
    private String[] list = {"Bar Chart", "Pie Chart"};
    private JComboBox combBoxGraphType = new JComboBox<>(list);

    private JPanel panel_5_StepsMonitor;
    private JLabel LtextSelectDataFirst, LtxtHitCalculation, LtxtSelectMetrics, LtxtSelectChart;

    private JPanel panel_6_Errors;
    private JRadioButton radioFR, radioFW;

    private JPanel panel_7_version;

    private JPanel panel_graph = new JPanel(new GridLayout(2, 3)),
            panel_graphType = new JPanel(new GridLayout(1, 4));

    private JPanel panel_result;
    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel panel_info;
    private JTextArea textArea;

    private JMenuBar menuBar;
    private JMenuItem mntmReadFromFile, mntmReadFromDirectory, mntmClose, mntmCloseAll, mntmExit, mntmAbout, mntmReadMemanual;

    private JProgressBar progressBar;

    private ArrayList<ModelAnalysisResult> results = new ArrayList<>();


    public MainFrame()
    {
        initialize();
    }

    public void initialize()
    {
        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("image/Astrology-Year-Of-Horse-icon.png"));
        mainFrame.setResizable(false);
        mainFrame.getContentPane().setEnabled(false);
        mainFrame.getContentPane().setForeground(SystemColor.inactiveCaption);
        mainFrame.setTitle("NsGTFA");
        mainFrame.setBounds(100, 100, 780, 660);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);

        //load menu bars
        mainFrame.setJMenuBar(setupMenuBar());

        // load panels
        mainFrame.getContentPane().add(setupTitlePanel());
        mainFrame.getContentPane().add(setupFileSelectionPanel());
        mainFrame.getContentPane().add(setupPerformancePanel());
        mainFrame.getContentPane().add(setupMetricsOptionPanel());
        mainFrame.getContentPane().add(setupPerformanceMetricsPanel());
        mainFrame.getContentPane().add(setupStepsMonitorPanel());
        mainFrame.getContentPane().add(setupErrorPanel());
//        mainFrame.getContentPane().add(setupVersionPanel());
        mainFrame.getContentPane().add(setupResultPanel());
//        mainFrame.getContentPane().add(setupInfoPanel());
        mainFrame.getContentPane().add(setupProgressBar());

        // HW Logo
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon("image/HWU.png"));
        lblNewLabel_1.setBounds(10, 89, 370, 83);
        mainFrame.getContentPane().add(lblNewLabel_1);

        // DSG logo
        JLabel labelHWIcon = new JLabel("");
//        labelHWIcon.setHorizontalTextPosition(SwingConstants.CENTER);
        labelHWIcon.setHorizontalAlignment(SwingConstants.CENTER);
//        labelHWIcon.setVerticalTextPosition(SwingConstants.TOP);
        labelHWIcon.setToolTipText("");
        labelHWIcon.setIcon(new ImageIcon("image/DSG.jpg"));
        labelHWIcon.setBounds(550, 465, 215, 100);
        mainFrame.getContentPane().add(labelHWIcon);
        mainFrame.setVisible(true);

        // set up the graph window
        graphFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("image/Astrology-Year-Of-Horse-icon.png"));
        graphFrame.setResizable(false);
        graphFrame.getContentPane().setEnabled(false);
        graphFrame.getContentPane().setForeground(SystemColor.inactiveCaption);
        graphFrame.setTitle("Graph");
        graphFrame.setBounds(100, 100, 620, 755);
        graphFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        graphFrame.getContentPane().setLayout(null);
    }

    private JPanel setupFileSelectionPanel()
    {
        panel_1_ChoseTraceFile = new JPanel();
        FlowLayout fl_panel_1_ChoseTraceFile = (FlowLayout) panel_1_ChoseTraceFile.getLayout();
        fl_panel_1_ChoseTraceFile.setAlignment(FlowLayout.LEFT);
        panel_1_ChoseTraceFile.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(100, 100, 100), null, null, new Color(0, 0, 0)), "Choose the Trace File(s)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1_ChoseTraceFile.setToolTipText("");
        panel_1_ChoseTraceFile.setBounds(10, 179, 370, 62);


        // new button
        btnNew = new JButton("New");
        panel_1_ChoseTraceFile.add(btnNew);

        // reset button
        btnReset = new JButton("Reset");
        btnReset.setVisible(false);
        panel_1_ChoseTraceFile.add(btnReset);

        // load file button
        btnLoadFile = new JButton("Load a File ");
        btnLoadFile.setEnabled(false);
//        btnLoadFile.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_1_ChoseTraceFile.add(btnLoadFile);

        // load folder button
        btnLoadFolder = new JButton("Load a Folder");
//        btnLoadFolder.setHorizontalAlignment(SwingConstants.RIGHT);
        btnLoadFolder.setEnabled(false);

        panel_1_ChoseTraceFile.add(btnLoadFolder);
        return panel_1_ChoseTraceFile;
    }

    private JPanel setupPerformancePanel()
    {
        // set up performance panel
        panel_2_Performance = new JPanel();
        FlowLayout fl_panel_2_Performance = (FlowLayout) panel_2_Performance.getLayout();
        fl_panel_2_Performance.setAlignment(FlowLayout.LEFT);
        panel_2_Performance.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(100, 100, 100), null, null, new Color(0, 0, 0)), "Performance", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel_2_Performance.setBounds(10, 246, 370, 67);


        // calculation button
        btnCalculation = new JButton("Calculation");
        btnCalculation.setEnabled(false);
        btnCalculation.setIcon(null);

        panel_2_Performance.add(btnCalculation);

        // graph button
        btnChartGraph = new JButton("Chart / Graph");
        panel_2_Performance.add(btnChartGraph);

        return panel_2_Performance;
    }

    private JPanel setupMetricsOptionPanel()
    {
        // set up metrics options panel
        panel_3_MetricsOptions = new JPanel();
        panel_3_MetricsOptions.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        FlowLayout fl_panel_3_MetricsOptions = (FlowLayout) panel_3_MetricsOptions.getLayout();
        fl_panel_3_MetricsOptions.setAlignment(FlowLayout.LEADING);
        panel_3_MetricsOptions.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(100, 100, 100), null, null, new Color(0, 0, 0)), "Metrics Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
        panel_3_MetricsOptions.setBounds(10, 319, 370, 55);

        // set up select all button
        btnSelectAll = new JButton("Select All");
        btnSelectAll.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSelectAll.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel_3_MetricsOptions.add(btnSelectAll);

        // set up most used button
        btnMostUsed = new JButton("Most Used");
        panel_3_MetricsOptions.add(btnMostUsed);

        // set up clear all button
        btnClearAll = new JButton("Clear All");
        panel_3_MetricsOptions.add(btnClearAll);
        return panel_3_MetricsOptions;
    }

    private JPanel setupPerformanceMetricsPanel()
    {
        // set up performance metrics panel
        panel_4_PerformanceMetrics = new JPanel(new GridLayout(3, 3));
//        FlowLayout fl_panel_4_PerformanceMetrics = (FlowLayout) panel_4_PerformanceMetrics.getLayout();
//        fl_panel_4_PerformanceMetrics.setAlignment(FlowLayout.LEFT);
        panel_4_PerformanceMetrics.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), new Color(0, 0, 0), new Color(128, 128, 128), new Color(128, 128, 128)), "Performance Metrics", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 0, 0)));
        panel_4_PerformanceMetrics.setBounds(10, 380, 760, 80);

        chckbxSentPacket = new JCheckBox("Sent Packets");
        chckbxSentPacket.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxSentPacket);
        metrics.add(chckbxSentPacket);

        chckbxReceivedPacket = new JCheckBox("Received Packets");
        chckbxReceivedPacket.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxReceivedPacket);
        metrics.add(chckbxReceivedPacket);

        chckbxDroppedPackets = new JCheckBox("Dropped Packets");
        chckbxDroppedPackets.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxDroppedPackets);
        metrics.add(chckbxDroppedPackets);

        chckbxOverHead = new JCheckBox("Overhead");
        chckbxOverHead.setSelected(true);
        chckbxOverHead.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxOverHead);
        metrics.add(chckbxOverHead);

        chckbxDroppedBytes = new JCheckBox("Dropped Bytes");
        chckbxDroppedBytes.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxDroppedBytes);
        metrics.add(chckbxDroppedBytes);

        chckbxEndToEnd = new JCheckBox("End to End Delay");
        chckbxEndToEnd.setSelected(true);
        chckbxEndToEnd.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxEndToEnd);
        metrics.add(chckbxEndToEnd);

        chckbxPacketDeliveryRatio = new JCheckBox("Packet Delivery Ratio");
        chckbxPacketDeliveryRatio.setSelected(true);
        chckbxPacketDeliveryRatio.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxPacketDeliveryRatio);
        metrics.add(chckbxPacketDeliveryRatio);

        chckbxThroughput = new JCheckBox("Throughput");
        chckbxThroughput.setSelected(true);
        chckbxThroughput.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxThroughput);
        metrics.add(chckbxThroughput);

        chckbxNormalized = new JCheckBox("Normalized Routing Load");
        chckbxNormalized.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4_PerformanceMetrics.add(chckbxNormalized);
        metrics.add(chckbxNormalized);

        return panel_4_PerformanceMetrics;
    }

    private JPanel setupStepsMonitorPanel()
    {
        // set up steps monitor panel
        panel_5_StepsMonitor = new JPanel(new GridLayout(4, 1));
        panel_5_StepsMonitor.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "Steps/Monitor", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
        panel_5_StepsMonitor.setBounds(10, 466, 250, 100);

        LtextSelectDataFirst = new JLabel();
        LtextSelectDataFirst.setForeground(Color.BLUE);
        LtextSelectDataFirst.setText("1. Select Data First");
        LtextSelectDataFirst.setHorizontalAlignment(SwingConstants.LEFT);

        panel_5_StepsMonitor.add(LtextSelectDataFirst);

        LtxtSelectMetrics = new JLabel();
        LtxtSelectMetrics.setForeground(Color.BLUE);
        LtxtSelectMetrics.setEnabled(false);

        LtxtSelectMetrics.setText("2. Select Metrics");
        LtxtSelectMetrics.setHorizontalAlignment(SwingConstants.LEFT);

        panel_5_StepsMonitor.add(LtxtSelectMetrics);

        LtxtHitCalculation = new JLabel();
        LtxtHitCalculation.setForeground(Color.BLUE);
        LtxtHitCalculation.setEnabled(false);

        LtxtHitCalculation.setText("3. Hit Calculation");
        LtxtHitCalculation.setHorizontalAlignment(SwingConstants.LEFT);

        panel_5_StepsMonitor.add(LtxtHitCalculation);

        LtxtSelectChart = new JLabel();
        LtxtSelectChart.setForeground(Color.BLUE);
        LtxtSelectChart.setEnabled(false);

        LtxtSelectChart.setText("4. Select Chart/Graph");
        LtxtSelectChart.setHorizontalAlignment(SwingConstants.LEFT);

        panel_5_StepsMonitor.add(LtxtSelectChart);
        return panel_5_StepsMonitor;
    }

    private JPanel setupErrorPanel()
    {
        // set up error panel
        panel_6_Errors = new JPanel();
        FlowLayout fl_panel_6_Errors = (FlowLayout) panel_6_Errors.getLayout();
        fl_panel_6_Errors.setAlignment(FlowLayout.LEFT);
        panel_6_Errors.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "Error", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel_6_Errors.setBounds(280, 466, 250, 100);

        radioFR = new JRadioButton("File Read");
//        radioFR.setEnabled(false);
        panel_6_Errors.add(radioFR);

        radioFW = new JRadioButton("File Write");
//        radioFW.setEnabled(false);
        panel_6_Errors.add(radioFW);

        return panel_6_Errors;
    }

    private JPanel setupVersionPanel()
    {
        // version panel
        panel_7_version = new JPanel();
        FlowLayout flowLayout_5 = (FlowLayout) panel_7_version.getLayout();
        panel_7_version.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_7_version.setBounds(10, 572, 370, 35);

        JLabel lblMarchVer = new JLabel("March 2014 Ver 3.0   Java GUI");
        lblMarchVer.setFont(new Font("Times New Roman", Font.BOLD, 14));
        panel_7_version.add(lblMarchVer);

        return panel_7_version;
    }

    private JMenuBar setupMenuBar()
    {
        // menu options
        menuBar = new JMenuBar();

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmReadFromFile = new JMenuItem("Read a File");
        mnFile.add(mntmReadFromFile);

        mntmReadFromDirectory = new JMenuItem("Read a Directory");
        mnFile.add(mntmReadFromDirectory);

//        JSeparator separator = new JSeparator();
//        mnFile.add(separator);
        mnFile.addSeparator();

        mntmClose = new JMenuItem("Close");
        mnFile.add(mntmClose);

        mntmCloseAll = new JMenuItem("Close All");
        mnFile.add(mntmCloseAll);

        mntmExit = new JMenuItem("Exit");

//        JSeparator separator_2 = new JSeparator();
//        mnFile.add(separator_2);
        mnFile.addSeparator();
        mnFile.add(mntmExit);

//        JSeparator separator_1 = new JSeparator();
//        mnFile.add(separator_1);
        mnFile.addSeparator();


        JMenu mnView = new JMenu("View");
        menuBar.add(mnView);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        mntmAbout = new JMenuItem("About");
        mnHelp.add(mntmAbout);

        mntmReadMemanual = new JMenuItem("Read me (manual)");
        mnHelp.add(mntmReadMemanual);

        return menuBar;
    }

    private JPanel setupTitlePanel()
    {
        // App title
        JPanel panel_title = new JPanel();
        panel_title.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_title.setBounds(10, 33, 370, 45);

        JLabel lblNewLabel = new JLabel("Ns2 Trace File Analyzer");
        panel_title.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Vivaldi", Font.BOLD, 25));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return panel_title;
    }

    public JPanel setupResultPanel()
    {
        panel_result = new JPanel(new GridLayout(1, 1));
        panel_result.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "Analysis Result", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_result.setBounds(400, 28, 370, 350);

//        JPanel template = new JPanel(new GridLayout(12, 2));
//        template.add(new JCheckBox("File Name"), true);
//        template.add(new JLabel("--"));

//        tabbedPane.addTab("Tab 1", null);
//        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        panel_result.add(tabbedPane);
        return panel_result;
    }

    public void updateResultPanel(ModelAnalysisResult result, ArrayList<JCheckBox> metrics)
    {
        results.add(result); // save result in arrayList for generating graphs later

        tabbedPane.addTab(result.getStringMetricValue("File Name"), makeTextPanel(result, metrics));

        panel_result.add(tabbedPane);
        panel_result.revalidate();
        panel_result.repaint();
//        mainFrame.add(panel_result);
    }


    private JComponent makeTextPanel(ModelAnalysisResult result, ArrayList<JCheckBox> metrics)
    {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(0, 2));

        LinkedHashMap<String, String> fileInfo = result.getStringMap();

        // file name, file format
        fileInfo.forEach((String checkBox, String label) -> {
            JCheckBox tmp = new JCheckBox(checkBox, true);
            tmp.setEnabled(false);
            panel.add(tmp);
            panel.add(new JLabel(label));
        });

        // append results according to the selected metrics

        metrics.forEach((JCheckBox tmpBox) -> {
            String chkBoxText = tmpBox.getText();
            JCheckBox tmp;
            switch (chkBoxText) {
                case "Sent Packets":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    String label = String.format("%,d", result.getIntMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Received Packets":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,d", result.getIntMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Dropped Packets":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,d", result.getIntMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Overhead":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,d", result.getIntMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Dropped Bytes":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,d", result.getIntMetricValue(chkBoxText));
                    panel.add(new JLabel(label + " bytes"));
                    break;
                case "End to End Delay":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,.2f", result.getFloatMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Packet Delivery Ratio":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,.2f", result.getFloatMetricValue(chkBoxText));
                    panel.add(new JLabel(label+"%"));
                    break;
                case "Throughput":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,.2f", result.getFloatMetricValue(chkBoxText));
                    panel.add(new JLabel(label));
                    break;
                case "Normalized Routing Load":
                    tmp = new JCheckBox(chkBoxText, true);
                    tmp.setEnabled(false);
                    panel.add(tmp);
                    label = String.format("%,.2f", result.getFloatMetricValue(chkBoxText));
                    panel.add(new JLabel(label+"%"));
                    break;
            }
        });

        return panel;
    }

    public JPanel setupInfoPanel()
    {
        panel_info = new JPanel();
        panel_info.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "File Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_info.setBounds(400, 378, 370, 410);
        textArea = new JTextArea(23, 29);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        panel_info.add(scrollPane);
        return panel_info;
    }

    public JPanel setupProgressBar()
    {
        JPanel panel_pb = new JPanel(new GridLayout(1, 4));
        panel_pb.setBounds(10, 572, 760, 35);
        progressBar = new JProgressBar();
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.GREEN);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setSize(370, 35);
        panel_pb.add(progressBar);

        return panel_pb;
    }

    public void setupGraphPanel(String[] metrics, int graphType)
    {
        panel_graphType.setBounds(10, 3, 120, 25);
        panel_graphType.add(combBoxGraphType);
        panel_graph.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), null, null, new Color(128, 128, 128)), "Performance Charts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_graph.setBounds(10, 28, 600, 700);

        GraphFactory graphFactory = new GraphFactory(results, metrics, graphType);

        ArrayList<ChartPanel> graphs = graphFactory.generateGraphs();
        for (ChartPanel graph : graphs) {
            panel_graph.add(graph);
        }

        graphFrame.add(panel_graphType);
        graphFrame.add(panel_graph);
        graphFrame.setVisible(true);
    }

    public void updateGraph(String[] metrics, int graphType)
    {
        panel_graph.removeAll();
        GraphFactory graphFactory = new GraphFactory(results, metrics, graphType);

        ArrayList<ChartPanel> graphs = graphFactory.generateGraphs();
        for (ChartPanel graph : graphs) {
            panel_graph.add(graph);
        }
        panel_graph.revalidate();
        panel_graph.repaint();
    }

    public JMenuItem getMntmReadFromFile()
    {
        return mntmReadFromFile;
    }

    public JMenuItem getMntmReadFromDirectory()
    {
        return mntmReadFromDirectory;
    }

    public JMenuItem getMntmClose()
    {
        return mntmClose;
    }

    public JMenuItem getMntmCloseAll()
    {
        return mntmCloseAll;
    }

    public JMenuItem getMntmExit()
    {
        return mntmExit;
    }

    public JMenuItem getMntmAbout()
    {
        return mntmAbout;
    }

    public JMenuItem getMntmReadMemanual()
    {
        return mntmReadMemanual;
    }

    public JButton getBtnNew()
    {
        return btnNew;
    }

    public JButton getBtnLoadFile()
    {
        return btnLoadFile;
    }

    public JButton getBtnLoadFolder()
    {
        return btnLoadFolder;
    }

    public JButton getBtnCalculation()
    {
        return btnCalculation;
    }

    public JButton getBtnChartGraph()
    {
        return btnChartGraph;
    }

    public JButton getBtnSelectAll()
    {
        return btnSelectAll;
    }

    public JButton getBtnMostUsed()
    {
        return btnMostUsed;
    }

    public JButton getBtnClearAll()
    {
        return btnClearAll;
    }

    public JFrame getMainFrame()
    {
        return mainFrame;
    }

    public JComboBox getCombBoxGraphType()
    {
        return combBoxGraphType;
    }

    public JProgressBar getProgressBar()
    {
        return progressBar;
    }

    public JRadioButton getRadioFR()
    {
        return radioFR;
    }

    public ArrayList<JCheckBox> getMetrics()
    {
        return metrics;
    }

    public JRadioButton getRadioFW()
    {
        return radioFW;
    }

    public JButton getBtnReset()
    {
        return btnReset;
    }

    public void setMainFrameSize(int width, int height)
    {
        mainFrame.setSize(new Dimension(width, height));
    }

    /**
     * enable all the metrics checkbox
     *
     * @param Option
     */
    public void setCheckboxEnabled(boolean Option)
    {
        chckbxPacketDeliveryRatio.setEnabled(Option);
        chckbxNormalized.setEnabled(Option);
        chckbxThroughput.setEnabled(Option);
        chckbxOverHead.setEnabled(Option);
        chckbxEndToEnd.setEnabled(Option);
        chckbxSentPacket.setEnabled(Option);
        chckbxReceivedPacket.setEnabled(Option);
        chckbxDroppedPackets.setEnabled(Option);
        chckbxDroppedBytes.setEnabled(Option);
    }

    /**
     * metrics option: select/clear all metrics
     *
     * @param option
     */
    public void setCheckboxSelected(boolean option)
    {
        chckbxPacketDeliveryRatio.setSelected(option);
        chckbxNormalized.setSelected(option);
        chckbxThroughput.setSelected(option);
        chckbxOverHead.setSelected(option);
        chckbxEndToEnd.setSelected(option);
        chckbxSentPacket.setSelected(option);
        chckbxReceivedPacket.setSelected(option);
        chckbxDroppedPackets.setSelected(option);
        chckbxDroppedBytes.setSelected(option);
    }

    public void selectMostUsedMetrics()
    {
        chckbxPacketDeliveryRatio.setSelected(true);
        chckbxThroughput.setSelected(true);
        chckbxOverHead.setSelected(true);
        chckbxEndToEnd.setSelected(true);
        chckbxNormalized.setSelected(false);
        chckbxSentPacket.setSelected(false);
        chckbxReceivedPacket.setSelected(false);
        chckbxDroppedPackets.setSelected(false);
        chckbxDroppedBytes.setSelected(false);
    }

    /**
     * Current stage: data selection
     */
    public void setLabel_Step1()
    {
        LtextSelectDataFirst.setEnabled(true);
        //LtextSelectDataFirst.setForeground(Color.GREEN);
        LtextSelectDataFirst.setText("1. Data Selected");
        LtextSelectDataFirst.setEnabled(false);

        LtxtSelectMetrics.setEnabled(true);
    }

    /**
     * Current stage: data calculation
     */
    public void setLabel_Step2()
    {
        LtxtSelectMetrics.setText("2. Metrics Selected");
        LtxtSelectMetrics.setEnabled(false);

        LtxtHitCalculation.setEnabled(true);
    }

    /**
     * Current stage: select performance metrics
     */
    public void setLabel_Step3()
    {
        //LtxtHitCalculation.setForeground(Color.GREEN);
        LtxtHitCalculation.setText("3. Data Calculated");
        LtxtHitCalculation.setEnabled(false);

        LtxtSelectChart.setEnabled(true);
    }

    /**
     * Current Stage: display chart
     */
    public void setLabel_Step4()
    {
        LtxtSelectChart.setText("4. Chart Displayed");
        LtxtSelectChart.setEnabled(false);
    }

    /**
     * Reset the GUI when reset button is hit
     */
    public void resetGUI()
    {
        //setup file selection panel
        btnReset.setVisible(false);
        btnNew.setVisible(true);
        btnNew.setEnabled(true);
        btnLoadFile.setEnabled(false);
        btnLoadFolder.setEnabled(false);

        //setup performance panel
        btnCalculation.setEnabled(false);
        btnChartGraph.setEnabled(false);

        //setup metrics option panel
        btnSelectAll.setEnabled(false);
        btnMostUsed.setEnabled(false);
        btnClearAll.setEnabled(false);

        //setup performance metrics
        setCheckboxEnabled(false);
        setCheckboxSelected(false);

        //setup step monitor panel
        LtextSelectDataFirst.setEnabled(true);
        LtextSelectDataFirst.setText("1. Select Data First");

        LtxtSelectMetrics.setEnabled(false);
        LtxtSelectMetrics.setText("2. Select Metrics");

        LtxtHitCalculation.setEnabled(false);
        LtxtHitCalculation.setText("3. Hit Calculation");

        LtxtSelectChart.setEnabled(false);
        LtxtSelectChart.setText("4. Select Chart/Graph");

        //setup error panel
        radioFR.setSelected(false);
        radioFW.setSelected(false);

        //setup progress bar
        progressBar.setValue(0);

        //setup result panel
        tabbedPane.removeAll();

        //setup graph window
        panel_graph.removeAll();
        panel_graphType.removeAll();

        //remove all the saved results
        results.clear();
    }

    public static void main(String[] args)
    {
        MainFrame m = new MainFrame();

    }
}
