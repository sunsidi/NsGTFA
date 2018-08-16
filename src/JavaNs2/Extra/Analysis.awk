# Performance Analysis Program
# B y    I D R I S     S K L O U L    I B R A H I M

BEGIN {
      printf "%6s ", p_nam  # added by idris
      printf(" Date: %s",strftime("%c"))  # added by idris
      printf ("\t %2d ", pause)  # added by idris
      printf ("\t %2d ", speed)  # added by idris
}

BEGIN {
    sends=0.0;
    recvs=0.0;
    routing_packets=0.0;
    droppedBytes=0;
    droppedPackets=0;
    highest_packet_id =0;
    sum=0.0;
    recvnum=0.0;

    beacon_no=0;		  #for control packet
    frip_no=0;			  #for control packet
    rsup_no=0;			  #for control packet
    rrep_no=0;		      #for control packet
    rreq_no=0;			  #for control packet
    Control_Total_pkt=0;  #for control packet

    cbr_no=0;			  #for data packet
    tcp_no=0;			  #for data packet
    ack_no=0;		      #for data packet
    Data_Total_pkt=0 ;	  #for data packet

    recvdSize = 0.0;	  #for throughput
    startTime = 1e6;	  #for throughput
    stopTime = 0.0;		  #for throughput
    }
  {
   # Trace line format: normal (old trace format)
       if ($2 != "-t") {
             event = $1;  # event is the contents of field #1 in the trace file which is s,r,f or d
             time = $2;   # time = contents of 2nd field in the trace file  // added by idris
             node_id = $3;
             pkt_id = $6;
             pkt_size = $8;
             flow_t = $5;
             level = $4;
             pkt_type = $7;
       }
       # Trace line format: new
       if ($2 == "-t") {
             event = $1;     # event is the contents of field #1 in the trace file which is s,r,f or d
             time =  $3;      # time = contents of 3rd field in the trace file  // added by idris
             node_id = $5;
             flow_id = $39;
             packet_id = $41;# packet_id = contents of field #41 in the trace file // added by idris
             pkt_size = $37; # pkt_size is the contents of field #37 in the trace file
             flow_t = $45;
             level = $19;
             pkt_type= $35;  # pkt_type is the contents of field #35 in the trace file
       }

  #====== Start of Performance calculation  //idris


  # CALCULATE PACKET DELIVERY FRACTION (Data Packets)
  if (( event == "s") &&  (pkt_type == "cbr" || pkt_type == "tcp" || pkt_type == "ack") && (level=="AGT")) {  sends++; }

  if (( event == "r") &&  (pkt_type == "cbr" || pkt_type == "tcp" || pkt_type == "ack") && (level=="AGT")) {  recvs++; }

  # CALCULATE DELAY
  if ( start_time[packet_id] == 0 )  start_time[packet_id] = time;
  if (( event == "r") &&  ( pkt_type == "cbr" || pkt_type == "tcp" || pkt_type == "ack" ) && (level=="AGT")) {  end_time[packet_id] = time;  }
       else {  end_time[packet_id] = -1;  }


  # CALCULATE TOTAL OVERHEAD (Control Packets)
  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type == "DSDV" || pkt_type =="REQUEST" || pkt_type =="REPLY"|| pkt_type == "AODV" ||
       pkt_type == "DSR" || pkt_type =="message" || pkt_type =="BEACON" || pkt_type =="FRIP" || pkt_type =="RSUP" || pkt_type =="ROUTE_REQ" || pkt_type =="ROUTE_REP"))
		routing_packets++;


 #=============================================================================================
 #                This part added to ckeck exactly each control packet   //idris
  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type =="BEACON"))
      beacon_no++;

  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type =="FRIP"))
      frip_no++;

  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type =="RSUP"))
      rsup_no++;


  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type =="ROUTE_REQ"))
      rreq_no++;

  if ((event == "s" || event == "f") && (level=="RTR") && (pkt_type =="ROUTE_REP"))
      rrep_no++;

 if ((event == "s" || event == "f") && (level=="RTR" ) &&  (pkt_type != "cbr" && pkt_type != "ack" && pkt_type != "tcp"))
    {
      #printf("event %c  level %s  pkt_type %s \n",event,level,pkt_type);
      Control_Total_pkt++;
    }



 # This part added to ckeck exactly each data packet   //idris
 if ((event == "s") && (level=="AGT") && (pkt_type =="cbr"))
      cbr_no++;
 if ((event == "s") && (level=="AGT") && (pkt_type =="tcp"))
      tcp_no++;
 if ((event == "s") && (level=="AGT") && (pkt_type =="ack"))
      ack_no++;
 if ((event == "s") && (level=="AGT" ) &&  (pkt_type == "cbr" || pkt_type == "ack" || pkt_type == "tcp"))
      Data_Total_pkt++ ;
 #=============================================================================================

  # DROPPED DSR PACKETS
  if (( event == "d" ) && ( pkt_type == "cbr" || pkt_type == "tcp")  && ( time  > 0 ))
       {
             droppedBytes=droppedBytes+ pkt_size;
             droppedPackets=droppedPackets+1;
       }

 #find the number of packets in the simulation
 if (packet_id > highest_packet_id)
    highest_packet_id = packet_id;

 #====== End of Performance calculation

 #====== Start of Throughput calculation
 # Store start time
  if ((level == "AGT") && (event == "+" || event == "s") && pkt_size >= 512) {
    if (time < startTime) {
             startTime = time;
             }
       }

  # Update total received packets' size and store packets arrival time
  if ((level == "AGT") && event == "r" && pkt_size >= 512) {
       if (time > stopTime) {
             stopTime = time;
             }
       # Rip off the header
       hdr_size = pkt_size % 512;
       pkt_size -= hdr_size;
       # Store received packet's size;
       recvdSize += pkt_size;
       }
  #====== End of Throughput calculation

  }

  END {

  #====== This part for Performance calculation
  for ( i in end_time )
  {
  start = start_time[i];
  end = end_time[i];
  packet_duration = end - start;
  if ( packet_duration > 0 )
  {    sum += packet_duration;
       recvnum++;
  }

  }
     if(recvnum==0)
        recvnum++; # set to 1
     if(sends==0)
      { printf("NOTE: No Data Sent ....... ");
        sends++;}  # set to 1
     if(recvs==0)
       {printf("No Data Received   ....... ");
        recvs++;}  # set to 1
     print"\n==========================================================\n"
     delay=sum/recvnum;
     NRL = routing_packets/recvs;  #normalized routing load
     PDF = (recvs/sends)*100;  #packet delivery ratio[fraction]

     printf("No. of data send = %.2f\n",sends);
     printf("No. of data recv = %.2f\n",recvs);
     printf("No. of dropped data (packets) = %d\n",droppedPackets);
     printf("No. of dropped data (bytes)   = %d\n",droppedBytes);
     printf("lost = %.2f\n",sends-recvs-droppedPackets);
     printf("No. of routing packets = %.2f\n",routing_packets);
     printf("No. of Controll packets = %.2f\n",Control_Total_pkt);
     printf("TCP\tCBR\tACK\tTOTAL\n");
     printf("%5d\t%5d\t%5d\t%5d \n",tcp_no,cbr_no,ack_no,Data_Total_pkt);





     # printf("\t\t BEACON  FRIP    RSUP    RREQ    RREP        TOTAL\n");
     # printf(" \t\t\t\t\t\t\t\t\t\t  %d \t %d \t %d \t %d \t\t %d \t\t\t %d\n",beacon_no,frip_no,rsup_no,rrep_no,rreq_no,Control_Total_pkt);

     printf("Packet Delivery Ratio = %.2f\n",PDF);
     printf("Normalized Routing Load = %.2f\n",NRL);
     printf("E-to-E Delay = %.2f\n",delay*1000);
     printf("Throughput = %.2f\n",(recvdSize/(stopTime-startTime))*(8/1000));
     printf("Start time = %.2f, End time = %.2f \n",startTime,stopTime);
  }

#================================================= AWK script for compute delay jitter ========================================================
#  Jitter.awk

  BEGIN {
         num_recv=0
   }

  {
       # Trace line format: normal
       if ($2 != "-t") {
             event = $1
             time = $2
             if (event == "+" || event == "-") node_id = $3
             if (event == "r" || event == "d") node_id = $4
             flow_id = $8
             pkt_id = $12
             pkt_size = $6
             flow_t = $5
             level = "AGT"
       }
       # Trace line format: new
       if ($2 == "-t") {
             event = $1
             time = $3
             node_id = $5
             flow_id = $39
             pkt_id = $41
             pkt_size = $37
             flow_t = $45
             level = $19
       }

  # Store packets send time
  if ((level == "AGT") && sendTime[pkt_id] == 0 && (event == "+" || event == "s") && pkt_size >= 512) {
       sendTime[pkt_id] = time
  }

  # Store packets arrival time
  if ((level == "AGT")&& event == "r" && pkt_size >= 512) {
             recvTime[pkt_id] = time
             num_recv++
       }
  }

  END {
       # Compute average jitter
       jitter1 = jitter2 = tmp_recv = 0
       prev_time = delay = prev_delay = processed = 0
       prev_delay = -1
       for (i=0; processed<num_recv; i++) {
             if(recvTime[i] != 0) {
                     tmp_recv++
                  if(prev_time != 0) {
                       delay = recvTime[i] - prev_time
                       e2eDelay = recvTime[i] - sendTime[i]
                       if(delay < 0) delay = 0
                       if(prev_delay != -1) {
                       jitter1 += abs(e2eDelay - prev_e2eDelay)
                       jitter2 += abs(delay-prev_delay)
                       }
                       prev_delay = delay
                       prev_e2eDelay = e2eDelay
                  }
                  prev_time = recvTime[i]
             }
             processed++
       }
  }

  END {
         #printf("\t %.2f",jitter1*1000/tmp_recv);
         #printf("\t %.2f \n",jitter2*1000/tmp_recv);
  }

  function abs(value) {
       if (value < 0) value = 0-value
       return value
  }