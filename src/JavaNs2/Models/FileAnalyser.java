/*
 * All the calculation results are saved in the object -> ModelAnalysisResult
 * These metrics' name are shown as follow, and can be called by using the get methods:
 * 1. File Name
 * 2. File Format
 * 3. Received Packet Size
 * 4. Dropped Packets
 * 5. Sent Packets
 * 6. Overhead
 * 7. Dropped Bytes
 * 8. Received Packets
 * 9. End to End Delay
 * 10. Packet Delivery Ratio
 * 11. Throughput
 * 12. Start Time
 * 13. Stop time
 * 14. Normalized Routing Load
 */
package JavaNs2.Models;

import JavaNs2.Views.MainFrame;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class FileAnalyser extends SwingWorker<ModelAnalysisResult, Integer> {

    // set up max packets number
    private final int MAX_PACKETS = 400000;
    private float start_time[] = new float[MAX_PACKETS];
    private float end_time[] = new float[MAX_PACKETS];
    private float time, startTime, stopTime;
    private int highest_packet_id;
    private ModelAnalysisResult result;
    private ArrayList<JCheckBox> metrics;

    private JProgressBar progressBar;
    private String inputFileName;
    private MainFrame view;
    private String outputPath;

    private String event;
    private String level;
    private String packetType;
    private int packetSize;
    private int packetId;
    private int fileFormat;

    public FileAnalyser(MainFrame view, String inputFileName, ArrayList<JCheckBox> metrics)
    {
        this.view = view;
        this.progressBar = view.getProgressBar();
        this.inputFileName = inputFileName;
        this.metrics = metrics;
    }

    /**
     * Return the format of the trace file
     * Old Format = 0
     * New Format = 1
     * Tagged Format = 2
     *
     * @param token0
     * @param token1
     * @return
     */
    private int checkFileFormat(String token0, String token1)
    {
        if (token1.equals("-t")) {
            return 1;
        } else if (token0.equals("+")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Get the time of the data transmission
     *
     * @param fileFormat
     * @param token
     * @return
     */
    private float getTime(int fileFormat, String token[])
    {
        float time;
        switch (fileFormat) {
            case 0:
                time = Float.valueOf(token[1]);
                return time;
            case 1:
                time = Float.valueOf(token[2]);
                return time;
            case 2:
                time = Float.valueOf(token[1]);
                return time;
            default:
                time = -1;
                return time;
        }
    }

    /**
     * Calculate End to End Delay
     *
     * @param highest_packet_id
     * @param start_time
     * @param end_time
     * @param receives
     * @return
     */
    private float getAvgEtoEDelay(int highest_packet_id, float[] start_time, float[] end_time, int receives)
    {
        float end_to_end_delay = 0;
        // calculate the packet duration for all the packets
        for (int packet_id = 0; packet_id <= highest_packet_id; packet_id++) {
            float packet_duration = end_time[packet_id] - start_time[packet_id];
            if (packet_duration > 0) {
                end_to_end_delay += packet_duration;
            }
        }
        return end_to_end_delay / receives;
    }

    /**
     * Calculate packet delivery ratio
     *
     * @param sends
     * @param receives
     * @return
     */
    private float getPacketDeliveryRatio(int sends, int receives)
    {
        return (float) receives / (float) sends * 100;
    }

    /**
     * Calculate Normalized Routing Load
     *
     * @param routing_packets
     * @param receives
     * @return
     */
    private float getNormalizedRoutingLoad(float routing_packets, int receives)
    {
        return routing_packets / receives;
    }

    /**
     * Calculate Average Throughput
     *
     * @param startTime
     * @param stopTime
     * @param receivedSize
     * @return
     */
    private float getAvgThroughput(float startTime, float stopTime, int receivedSize)
    {
        return (float) (receivedSize / (stopTime - startTime) * 0.008);
    }

    /**
     * Reset all the variables
     */
    private void initialize()
    {
        for (int i = 0; i < MAX_PACKETS; i++) {
            start_time[i] = 0;
            end_time[i] = 0;
        }
        result.resetMap();
        startTime = 1000000;
        stopTime = 0;
        highest_packet_id = 0;
    }

    /**
     * Parse a single trace file line
     *
     * @param line
     * @return token
     */
    private String[] parseLine(String line)
    {
        String[] token = new String[100];
        StringTokenizer st = new StringTokenizer(line, " "); //allows you to break a string into tokens. It is simple way to break string.
        int i = 0;
        while (st.hasMoreTokens()) { /* while 2*/
            token[i++] = st.nextToken();
        }
        return token;
    }

    private void assignVariables(String[] token, String fileName)
    {
        fileFormat = checkFileFormat(token[0], token[1]);

        if (fileFormat == 0) { //old format
            event = token[0];
            time = Float.valueOf(token[1]);
            level = token[3];
            packetId = Integer.parseInt(token[5]);
            packetType = token[6];
            packetSize = Integer.parseInt(token[7]);
        } else if (fileFormat == 1) { // new format
            event = token[0];
            time = Float.valueOf(token[2]);
            level = token[18];
            packetId = Integer.parseInt(token[40]);
            packetType = token[34];
            packetSize = Integer.parseInt(token[36]);
        } else { // error
            view.getRadioFR().setSelected(true);
            JOptionPane.showMessageDialog(null,
                    "File: "+fileName + "\nFormat Error: NsGTFA only supports Old and New trace file format.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            this.cancel(true);
        }
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     * <p>
     * <p>
     * Note that this method is executed only once.
     * <p>
     * <p>
     * Note: this method is executed in a background thread.
     *
     * @return the computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    protected ModelAnalysisResult doInBackground() throws Exception
    {
        try {
            result = new ModelAnalysisResult();
            initialize(); // initialize packet array
            File file = new File(inputFileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
//            outputPath = file.getParent() + "/Result_" + file.getName().replaceFirst("[.][^.]+$", "") + ".txt";
            result.setOuputPath(file.getParent() + "/Result_" + file.getName().replaceFirst("[.][^.]+$", "") + ".txt");
            // save the file name
            result.setMetricValue("File Name", file.getName().replaceFirst("[.][^.]+$", "")); // get rid of extension

            String thisLine; // line buffer
            String[] token; // token array
            double fileSize = file.length(); // total number of bytes in the file
            double readLength = 0; // size of the lines that already read in

            // start to count the execution time
            Helper.startPerformanceTest();

            while ((thisLine = br.readLine()) != null) { /* while 1*/
//                view.getTextArea().append(thisLine);
                // add the number of bytes of thisLine
                readLength += thisLine.length();
                token = parseLine(thisLine);

                if (token[0].equals("s") || token[0].equals("r") || token[0].equals("f") || token[0].equals("d") || token[0].equals("+")) {
                    // check the file format: old = 0, new = 1, tagged = 2
                    assignVariables(token, result.getStringMetricValue("File Name"));
                    if (fileFormat == 2) return null;
                    // save file format
                    if (Objects.equals(result.getStringMetricValue("File Format"), ""))
                        result.setMetricValue("File Format", fileFormat == 0 ? "Old" : "New");

                    // if there is dropped packet, calculate the dropped bytes
                    // and increase dropped packet number by 1
                    if ((event.equals("d")) && (packetType.equals("cbr") || packetType.equals("tcp") && (time > 0))) {

                        if (result.getIntMetricValue("Dropped Bytes") == null) {
                            result.setMetricValue("Dropped Bytes", packetSize);
                        } else {
                            result.setMetricValue("Dropped Bytes", result.getIntMetricValue("Dropped Bytes") + packetSize);
                        }

                        if (result.getIntMetricValue("Dropped Packets") == null) {
                            result.setMetricValue("Dropped Packets", 1);
                        } else {
                            result.setMetricValue("Dropped Packets", result.getIntMetricValue("Dropped Packets") + 1);
                        }
                    }

                    /* Start of Throughput Calculation */
                    if (level.equals("AGT") && (event.equals("s") || event.equals("+")) && packetSize >= 512) {
                        if (time < startTime) {
                            startTime = time;
                        }
                    }

                    // Update total received packets' size and store packets arrival time
                    if (level.equals("AGT") && event.equals("r") && packetSize >= 512) {
                        if (time > stopTime) {
                            stopTime = time;
                        }

                        // Rip off the header
                        packetSize = packetSize - packetSize % 512;
                        // Store received packet's size;
                        if (result.getIntMetricValue("Received Packet Size") == null) {
                            result.setMetricValue("Received Packet Size", packetSize);
                        } else {
                            result.setMetricValue("Received Packet Size", result.getIntMetricValue("Received Packet Size") + packetSize);
                        }
                    }


                    // calculate the sent packets
                    if (event.equals("s") && level.equals("AGT") && (packetType.equals("cbr") || packetType.equals("tcp"))) {
                        if (result.getIntMetricValue("Sent Packets") == null) {
                            result.setMetricValue("Sent Packets", 1);
                        } else {
                            result.setMetricValue("Sent Packets", result.getIntMetricValue("Sent Packets") + 1);
                        }
                    }

                    // parse the packetId
//                    if (token[39].equals("-Ii")) {
                    // find the number of packets in the simulation
                    if (packetId > highest_packet_id)
                        highest_packet_id = packetId;
                    // set the start time, only if its not already set
                    if (start_time[packetId] == 0) start_time[packetId] = time;
                    // calculate the receives and end-end delay
                    if (event.equals("r") && level.equals("AGT") && (packetType.equals("cbr") || packetType.equals("tcp"))) {

                        if (result.getIntMetricValue("Received Packets") == null) {
                            result.setMetricValue("Received Packets", 1);
                        } else {
                            result.setMetricValue("Received Packets", result.getIntMetricValue("Received Packets") + 1);
                        }
                        end_time[packetId] = time;
                    } else {
                        end_time[packetId] = -1;
                    }
//                    }

                    /* CALCULATE TOTAL OVERHEAD (Control Packets, the routing packets) */
                    if ((event.equals("s") || event.equals("f"))
                            && (level.equals("RTR"))
                            && (packetType.equals("DSDV") || packetType.equals("REQUEST") || packetType.equals("REPLY") || packetType.equals("AODV") || packetType.equals("DSR") || packetType.equals("message"))) {
                        if (result.getIntMetricValue("Overhead") == null) {
                            result.setMetricValue("Overhead", 1);
                        } else {
                            result.setMetricValue("Overhead", result.getIntMetricValue("Overhead") + 1);
                        }
                    }
                }
                publish((int) Math.round(readLength / fileSize * 100));
            } /* while 1*/

            result.setMetricValue("End to End Delay", getAvgEtoEDelay(highest_packet_id, start_time, end_time, result.getIntMetricValue("Received Packets")));
            result.setMetricValue("Packet Delivery Ratio", getPacketDeliveryRatio(result.getIntMetricValue("Sent Packets"), result.getIntMetricValue("Received Packets")));
            result.setMetricValue("Normalized Routing Load", getNormalizedRoutingLoad(result.getIntMetricValue("Overhead"), result.getIntMetricValue("Received Packets")));
            result.setMetricValue("Throughput", getAvgThroughput(startTime, stopTime, result.getIntMetricValue("Received Packet Size")));
            result.setMetricValue("Start Time", startTime);
            result.setMetricValue("Stop Time", stopTime);
            // if there is no dropped packets, save as 0
            if (result.getIntMetricValue("Dropped Bytes") == null) {
                result.setMetricValue("Dropped Bytes", 0);
            }
            if (result.getIntMetricValue("Dropped Packets") == null) {
                result.setMetricValue("Dropped Packets", 0);
            }
            Helper.endPerformanceTest();
            result.print();
            System.out.println();
            try {
                result.export();
            } catch (IOException e) {
                view.getRadioFW().setSelected(true);
                JOptionPane.showMessageDialog(null, "Export Error: Cannot export analysis result.","Error",JOptionPane.ERROR_MESSAGE);
            }
            return result;
        } //end of try
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void done()
    {
        try {
            view.updateResultPanel(get(), metrics);
//            Helper.exportResults(get(), outputPath);
        } catch (ExecutionException | InterruptedException | CancellationException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void process(List<Integer> chunks)
    {
        int i = chunks.get(chunks.size() - 1);
        progressBar.setValue(i); // update the progress bar
    }

    /**
     * For testing only
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException
//    {
//        FileAnalyser f = new FileAnalyser();
//        f.calculate("/Users/SD/Desktop/School's Lectures/Final Project/Examples/a2_AODV_new.tr");
//    }
}
