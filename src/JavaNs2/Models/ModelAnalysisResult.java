package JavaNs2.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModelAnalysisResult {

    private LinkedHashMap<String, String> stringMap;
    private HashMap<String, Integer> intMap;
    private HashMap<String, Float> floatMap;
    private String outputPath;

    public ModelAnalysisResult()
    {
        this.stringMap = new LinkedHashMap<>();
        this.intMap = new HashMap<>();
        this.floatMap = new HashMap<>();
    }

    public String getStringMetricValue(String metric)
    {
        return stringMap.get(metric) == null ? "" : stringMap.get(metric);
    }

    public Integer getIntMetricValue(String metric)
    {
        return intMap.get(metric) == null ? 0 : intMap.get(metric);
    }

    public Float getFloatMetricValue(String metric)
    {
        return floatMap.get(metric) == null ? (float) 0 : floatMap.get(metric);
    }

    public void setMetricValue(String metric, int value)
    {
        intMap.put(metric, value);
    }

    public void setMetricValue(String metric, float value)
    {
        floatMap.put(metric, value);
    }

    public void setMetricValue(String metric, String value)
    {
        stringMap.put(metric, value);
    }

    public void resetMap()
    {
        intMap.clear();
        floatMap.clear();
    }

    public LinkedHashMap<String, String> getStringMap()
    {
        return stringMap;
    }

    public HashMap<String, Integer> getIntMap()
    {
        return intMap;
    }

    public HashMap<String, Float> getFloatMap()
    {
        return floatMap;
    }

    public void print()
    {
        stringMap.forEach((String metric, String value) -> System.out.println(metric + ": " + value));
        intMap.forEach((String metric, Integer value) -> System.out.println(metric + ": " + value));
        floatMap.forEach((String metric, Float value) -> System.out.println(metric + ": " + value));
    }

    public void setOuputPath(String outputPath)
    {
        this.outputPath = outputPath;
    }

    public void export() throws IOException
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


        bw.write(String.format("%6d", this.getIntMetricValue("Sent Packets")));
        bw.write(String.format("%10d", this.getIntMetricValue("Received Packets")));
        bw.write(String.format("%8d", this.getIntMetricValue("Dropped Packets")));
        bw.write(String.format("%8d", this.getIntMetricValue("Overhead")));
        bw.write(String.format("%12.3f", this.getFloatMetricValue("Throughput")));

        bw.write(String.format("%8.2f", this.getFloatMetricValue("Packet Delivery Ratio")));
        bw.write(String.format("%8.2f", this.getFloatMetricValue("Normalized Routing Load")));
        bw.write(String.format("%8.2f", this.getFloatMetricValue("End to End Delay")));

        bw.write(String.format("%12.2f", this.getFloatMetricValue("Start Time")));
        bw.write(String.format("%12.2f", this.getFloatMetricValue("Stop Time")));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        bw.write(String.format("%23s", dateFormat.format(date)));

//            bw.write(String.format("%13.3f", Elapsed * 1e-9));
        bw.write(String.format("  " + "%13s %n", this.getStringMetricValue("File Name")));

        bw.close();
    }
}
