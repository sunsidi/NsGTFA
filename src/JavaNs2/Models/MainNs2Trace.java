package JavaNs2.Models;


import javax.swing.JLabel;
import javax.swing.JOptionPane;



import java.io.*;

public class MainNs2Trace{

    String s, thisLine, currLine,thisLine1;
    FileReader  fin;
    FileWriter fout;
    String Filename;
    BufferedWriter op;
    final int FILES = 0;
    final int MAX_PACKETS = 400000;
    float start_time[] = new float[MAX_PACKETS];
    float end_time[] = new float[MAX_PACKETS];
    String tokeNs2[] = new String[100];
    String TranceFileFormat;
    boolean FolderSelect=false;
    //String inputFileName = "H:\\examples_a/Ex7_AODV_New.tr";
    //String outputFileName = "H:\\examples_a/a1"+".txt";
    String inputFileName = "/Users/SD/Desktop/School's Lectures/Final Project/Examples/a2_AODV_new.tr";
    String outputFileName = "/Users/SD/Desktop/School's Lectures/Final Project/Examples/test.txt";

    float startTime ;  //for throughput
    float stopTime  ;  //for throughput
    int Stop=1;
    int j;
    int sends;
    int receives;
    int droppedPackets;
    int droppedBytes;
    int packet_id;
    int pkt_size;
    int hdr_size;
    int recvdSize;
    int highest_packet_id;
    int line_count;
    int current_line;
    int routing_packets;
    int count=0;
    String SequeValue;
    protected boolean newFile=true;
    protected boolean FileSelected;

    float pdfraction, time, packet_duration=0, end_to_end_delay=0,AverageThroughput;
    float avg_end_to_end_delay;
    float NormalizedRoutingload;
    double Elapsed;
    int FileNum;
    int progress;
    static JLabel lblNewLabel_2 ;
    //float recvnum=0;
    // Constructor
    MainNs2Trace()  {
        newFile=true;
        variablesReset();
    }

    void variablesReset(){

        startTime = 1000000; stopTime = 0;  j=0; sends=0;  receives=0;  droppedPackets=0;   droppedBytes=0;
        packet_id=0;  pkt_size=0;   hdr_size=0;  recvdSize=0;  highest_packet_id=0; line_count=0;
        current_line=0;  routing_packets=0;  count=0;

        pdfraction=0; time=0; packet_duration=0; end_to_end_delay=0 ; AverageThroughput=0;
        avg_end_to_end_delay=0; NormalizedRoutingload=0;  Elapsed=0;
    }
    boolean fileSelect(String Filename, String FileName_Complete_path, String Directory_path) throws IOException{
        this.Filename=Filename;
        FileSelected=true;
        // JOptionPane.showMessageDialog(null,"Yes");
        // String fileNameWithOutExt = "test.xml".replaceFirst("[.][^.]+$", "");
        String fileNameWithOutExt = Filename.replaceFirst("[.][^.]+$", "");
        this.inputFileName = FileName_Complete_path;
        if( newFile==true)  {
            //System.out.println("SequeValue"+SequeValue);
            this.outputFileName =Directory_path+"\\"+fileNameWithOutExt+"_"+SequeValue+"_"+".txt";
        }

        fout  = new FileWriter(outputFileName, true); //the true will append the new data
        op = new BufferedWriter(fout);

        return FileSelected;
    }

    void  PacketDuration(){
        // calculate the packet duration for all the packets
        for (packet_id = 0; packet_id <= highest_packet_id ; packet_id++) {
            packet_duration = end_time[packet_id] - start_time[packet_id];
            if (packet_duration >0) {end_to_end_delay += packet_duration;  }
        }
    }

    float AverageEndtoEnd_packet_delay(){
        if(receives==0)receives++;
        return avg_end_to_end_delay = end_to_end_delay / (receives);
    }

    float Normalized_routing_load(){
        if(receives==0) receives++;
        return NormalizedRoutingload = ((float)routing_packets/(float)receives);
    }

    float PacketDeliveryFraction(){
        if(sends==0) sends++;
        return pdfraction = ((float)receives/(float)(sends))*100;
    }

    float AverageThroughput(){
        //System.out.println("Recived Size " + recvdSize);
        //System.out.println(" startTime " + startTime);
        //System.out.println(" stopTime  " + stopTime);
        //System.out.println(" Stoptime - StartTime " + (stopTime - startTime));
        return(float) (recvdSize/(stopTime - startTime) * 0.008);   //recvdSize/(stopTime-start_time[0]))*(8/1000));


    }

//	void closeFile() throws IOException{
//		op.close();
//	}

    // parse the time; get time
    float GetTheTime(String tokeN0, String tokeN1, String tokeN2){

        //System.out.println(tokeN0+ "\n"+tokeN1 + "\n" +  tokeN2);
        float time=0;
        if (tokeN1.equals("-t")){
            time = Float.valueOf(tokeN2).floatValue();
            TranceFileFormat="New Trace format";
            Stop=0;
        }
        else if (tokeN0.equals("+"))  {
            time = Float.valueOf(  tokeNs2[1]).floatValue();
            TranceFileFormat="Tagged Trace format";
            Stop=1;
        }
        else
        {
            time = Float.valueOf(  tokeNs2[1]).floatValue();
            TranceFileFormat="Old Trace format";
            Stop=1;
        }
        if(Stop==1 && FolderSelect==false){
            // System.out.println( "This is (NS2) " + TranceFileFormat );
            // System.out.println( "Error : This software for the Ns2 New Trace format only");
            time=-1; // just a flag to break the loop of file reading (Error)
            JOptionPane.showMessageDialog(null,"This is (Ns2) " + TranceFileFormat +"\n Error : This software for the Ns2 New Trace format only");
        }
        return time;
    }

  public static void main (String args[]) throws IOException {
	  // create object called Ns2 from the main class
	  MainNs2Trace Ns2 = new  MainNs2Trace();
      Ns2.calculat();
  } // end of main

    void calculat() throws IOException{

        try {
            int i=0;
            variablesReset(); // reset all variables
            //System.out.println("recved size ="+ recvdSize);
            for (i=0; i<  MAX_PACKETS ; i++){
                start_time[i] = 0;   end_time[i]=0;}
            fin =  new FileReader (inputFileName);
            BufferedReader br = new BufferedReader(fin);

            //int lines = 100;
//          		while (br.readLine() != null) lines++;
//          		//reader.close();
            //int incrementValue=lines/100;
//
            //int counter=0;
            //int value=0;
//          		System.out.println("lines .... " + lines);
//          		System.out.println("incrementValue .... " + incrementValue);
            float ElapsedStart = System.nanoTime();
            System.out.println("ElapsedStart .... " + ElapsedStart);
            while ((  thisLine = br.readLine()) != null) { /* while 1*/
                // scan it line by line
                //counter++;
                //lblNewLabel_2.setToolTipText("Lines:");
                //System.out.println(counter + " " + incrementValue);
                // if (counter==incrementValue){
//  	                	 counter=0;
                //  NsAnalyserGUI.iterate( counter/1000);
//  	                   try{Thread.sleep(0);}catch(Exception e){}
                // }
                //System.out.println(thisLine);
                //Let's  break down the line's contents into tokens "strings"
                // example of StringTokenizer class that tokenizes a string "my name is idris" on the basis of whitespace to
                // my
                //name
                //is
                //idris
                java.util.StringTokenizer st = new java.util.StringTokenizer(  thisLine, " ");     //allows you to break a string into tokens. It is simple way to break string.
                i=0;
                while(st.hasMoreTokens())  /* while 2*/
                    tokeNs2[i++]= st.nextToken();
                if (  tokeNs2[0].equals("s") ||   tokeNs2[0].equals("r")||   tokeNs2[0].equals("f") || tokeNs2[0].equals("d") ||tokeNs2[0].equals("+"))
                {
                    // parse the time; get time
                    //	System.out.println(tokeNs2[0] +"  "+  tokeNs2[1] +"  "+  tokeNs2[2] +"  "+  tokeNs2[3]);
                    time =   GetTheTime( tokeNs2[0],  tokeNs2[1],  tokeNs2[2]);
                    //System.out.println("time " + time);

                    if(time==-1)// Error wrong file
                        return;

                    pkt_size= Integer.parseInt(tokeNs2[36]) ; //


                    if (( tokeNs2[0].equals("d")) && ( tokeNs2[34].equals("cbr")||  tokeNs2[34].equals("tcp")  && ( time  > 0 )))

                    {
                        //System.out.println("tokeNs2[34]   =" + tokeNs2[34]);
                        droppedBytes=droppedBytes+ pkt_size ; //pkt_size = $6;# pkt_size is the contents of field #6 in the trace file ;
                        droppedPackets++;
                    }

                    //System.out.println("here it is time  "+   time);
                    // parse the packet_id
                    if (  tokeNs2[39].equals("-Ii"))   packet_id = Integer.valueOf(  tokeNs2[40]).intValue();


                    //System.out.println("here it is you are "+   tokeNs2[0] +" ,,," +   tokeNs2[18] +",,," +   tokeNs2[34]);
                    /// calculate the sent packets
                    if (  tokeNs2[0].equals("s")&&  tokeNs2[18].equals("AGT")&& (  tokeNs2[34].equals("cbr")||  tokeNs2[34].equals("tcp")))  sends++;

                    // find the number of packets in the simulation
                    if (  packet_id >  highest_packet_id)   highest_packet_id =   packet_id;

                    // set the start time, only if its not already set
                    if (  start_time[  packet_id] == 0)   start_time[  packet_id] =   time;

                    // calculate the receives and end-end delay
                    if (  tokeNs2[0].equals("r") &&   tokeNs2[18].equals("AGT") && (  tokeNs2[34].equals("cbr") ||   tokeNs2[34].equals("tcp")))
                    {
                        receives++;
                        end_time[  packet_id] =   time;
                    }
                    else   end_time[  packet_id] = -1;
                    // CALCULATE TOTAL OVERHEAD (Control Packets, the routing packets)
                    if (  (tokeNs2[0].equals("s") ||   tokeNs2[0].equals("f"))  &&  ( tokeNs2[18].equals("RTR"))
                            && (  tokeNs2[34].equals("DSDV") ||  tokeNs2[34].equals("REQUEST") ||   tokeNs2[34].equals("REPLY") ||
                            tokeNs2[34].equals("AODV") ||   tokeNs2[34].equals("DSR")||   tokeNs2[34].equals("message") ))
                        routing_packets++;

                    //====== Start of Throughput calculation
                    // Store start time
                    //if ((level == "AGT") && (event == "+" || event == "s") && pkt_size >= 512)
                    if ( tokeNs2[18].equals("AGT") && (tokeNs2[0].equals("s") || tokeNs2[0].equals("+")) && pkt_size >= 512) {
                        // System.out.println("Packet size ="+ pkt_size);
                        // total=total + pkt_size;
                        // System.out.println("Total Packet size ="+ (total));
                        // countx++;
                        // System.out.println("Count  ="+ countx);
                        // if(countx==1770) System.exit(0);
                        if (time < startTime) {
                            startTime = time;
                            //System.out.println("Start time "+ startTime);
                        }
                    }

                    // Update total received packets' size and store packets arrival time
                    if (tokeNs2[18].equals("AGT") && tokeNs2[0].equals("r") && pkt_size >= 512) {

                        if (time > stopTime) {
                            stopTime = time;
                        }
                        // Rip off the header
                        hdr_size = pkt_size % 512;
                        pkt_size -= hdr_size;
                        // Store received packet's size;
                        recvdSize += pkt_size;
                    }
                }
            } /* while 1*/

            PacketDuration();
            avg_end_to_end_delay=AverageEndtoEnd_packet_delay();  // calculate the average end-end packet delay
            pdfraction =PacketDeliveryFraction();				    // calculate the packet delivery fraction
            NormalizedRoutingload=Normalized_routing_load();
            AverageThroughput=AverageThroughput();



            float ElapsedEnd = System.nanoTime();;
            System.out.println("ElapsedEnd = " + ElapsedEnd);
            Elapsed = ElapsedEnd - ElapsedStart;
            //      System.out.println("Elapsed = " + Elapsed);
            //	  System.out.println("Elapsed = " + Elapsed * 1e-9);

//            if(FolderSelect==true && Stop==0){
                //System.out.println("Yes folder Pressed ********* ********* ***********");
                OnScreenResultsPrint ResultsPrint = new OnScreenResultsPrint();
//                InFileResultsWrite InFileWrite = new InFileResultsWrite();
//    			 ResultsPrint.ResultsPrint(time,stoptime,startTime,stopTime,sends,receives,droppedPackets,droppedBytes,routing_packets,
//    			 NormalizedRoutingload,pdfraction,avg_end_to_end_delay,AverageThroughput,Elapsed,recvdSize,
//    			 end_to_end_delay);     // Print all the calculated results

                //ResultsPrint();                 // Print all the calculated results
                ResultsPrint.ResultsPrint(startTime,stopTime,sends,receives,droppedPackets,droppedBytes,routing_packets,
                        NormalizedRoutingload,pdfraction,avg_end_to_end_delay,AverageThroughput,Elapsed,recvdSize,end_to_end_delay);

                //WriteResultsInFile(op);         // Write data into a File
//                FileNum= InFileWrite.WriteResultsInFile(op,startTime,stopTime,sends,receives,droppedPackets,droppedBytes,routing_packets,
//                        NormalizedRoutingload,pdfraction,avg_end_to_end_delay,AverageThroughput,Elapsed,FileNum,newFile,inputFileName);
//                newFile=false;  // field in MainNs2Trace
//                op.close();
//            }

        } //end of try
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//private long getFileSize(String inputFileName2) {
//	// TODO Auto-generated method stub
//	return 0;
//}
}// end of class
