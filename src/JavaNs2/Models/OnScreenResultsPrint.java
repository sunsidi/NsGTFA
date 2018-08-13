package JavaNs2.Models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OnScreenResultsPrint {

    OnScreenResultsPrint()
    {

    }


    void ResultsPrint(
            float startTime,
            float stopTime,
            int sends,
            int receives,
            int droppedPackets,
            int droppedBytes,
            int routing_packets,
            float NormalizedRoutingload,
            float pdfraction,
            float avg_end_to_end_delay,
            float AverageThroughput,
            double Elapsed,
            int recvdSize,
            float end_to_end_delay)
    {

        System.out.println("**** The updated ver.1.1 ");
        System.out.println(" \n **StartTime  .. " + startTime);
        System.out.println(" \n **StopTime  .. " + stopTime);
        System.out.println(" \n **Send Packets .. " + sends);
        System.out.println(" Receive Packets .. " + receives);
        System.out.println(" Lot  Packets .. " + (sends - receives));
        System.out.println("No. of dropped data (packets)" + droppedPackets);
        System.out.println("No. of dropped data (bytes)  " + droppedBytes);
        System.out.println(" Routing packets (OverHead) " + routing_packets);
        System.out.println(" Normalized routing load " + NormalizedRoutingload);
        System.out.println(" Packet delevery Fraction PDF .." + pdfraction);
        System.out.println(" Avg End-End delay " + avg_end_to_end_delay);
        System.out.println(" Avg End-End delay (ms)" + avg_end_to_end_delay * 1000);
        System.out.println(" Average Throughput[kbps] " + AverageThroughput);
        System.out.println("Elapsed Time = " + Elapsed * 1e-9);
        System.out.println("\n");

        System.out.println("recved size =" + recvdSize);
        System.out.println(" end_to_end_delay  " + end_to_end_delay);
    }

}



















