package JavaNs2.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Helper {
    private static long startTime;

    public static String getCurrentTime()
    {
        Calendar now = Calendar.getInstance();
        String CurrentTime;
        CurrentTime = (now.get(Calendar.HOUR) + "" + now.get(Calendar.MINUTE) + "" + now.get(Calendar.SECOND));
        return CurrentTime;
    }

    public static void startPerformanceTest()
    {
        startTime = System.currentTimeMillis();
        System.out.println("Start at: " + startTime);
    }

    public static void endPerformanceTest()
    {
        long endTime = System.currentTimeMillis();
        long execTime = (endTime - startTime) / 1000;
        System.out.println("End at: " + endTime);
        System.out.println("Performance: " + execTime + "s");
    }

    public static void exportResults(ModelAnalysisResult result, String outputPath) throws IOException
    {
        FileWriter fw = new FileWriter(outputPath);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.format("%6s", "Send"));
        bw.write(String.format("%10s", "Receive"));
        bw.write(String.format("%8s", "Drop"));
        bw.write(String.format("%8s", "O/H"));
        bw.write(String.format("%12s", "[kbps]"));


        bw.write(String.format("%8s", "PDR"));
        bw.write(String.format("%8s", "NRL"));
        bw.write(String.format("%8s", "EED"));


        bw.write(String.format("%12s", "startTime"));
        bw.write(String.format("%12s", "stopTime"));

        bw.write(String.format("%19s", "Date   /   Time"));

//        bw.write(String.format("%13s", "Elapsed/Sec"));
        bw.write(String.format("%17s%n", "Filename"));


        bw.write(String.format("%6d", result.getIntMetricValue("Sent Packets")));
        bw.write(String.format("%10d", result.getIntMetricValue("Received Packets")));
        bw.write(String.format("%8d", result.getIntMetricValue("Dropped Packets")));
        bw.write(String.format("%8d", result.getIntMetricValue("Overhead")));
        bw.write(String.format("%12.3f", result.getFloatMetricValue("Throughput")));

        bw.write(String.format("%8.2f", result.getFloatMetricValue("Packet Delivery Ratio")));
        bw.write(String.format("%8.2f", result.getFloatMetricValue("Normalized Routing Load")));
        bw.write(String.format("%8.2f", result.getFloatMetricValue("End to End Delay")));

        bw.write(String.format("%12.2f", result.getFloatMetricValue("Start Time")));
        bw.write(String.format("%12.2f", result.getFloatMetricValue("Stop Time")));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        bw.write(String.format("%23s", dateFormat.format(date)));

//            bw.write(String.format("%13.3f", Elapsed * 1e-9));
        bw.write(String.format("  " + "%13s %n", result.getStringMetricValue("File Name")));

        bw.close();
    }
}
