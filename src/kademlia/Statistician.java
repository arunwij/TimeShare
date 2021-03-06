package kademlia;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that keeps statistics for this Kademlia instance.
 *
 * These statistics are temporary and will be lost when Kad is shut down.
 *
 * @author Artista
 */
public class Statistician implements KadStatistician{

    /* How much data was sent and received by the server over the network */
    private long totalDataSent, totalDataReceived;
    private long numDataSent, numDataReceived;

    /* Bootstrap timings */
    private long bootstrapTime;
    private long onlineTime;

    /* Content lookup operation timing & route length */
    private int numContentLookups, numFailedContentLookups;
    private long totalContentLookupTime;
    private long totalRouteLength;
    private long bootstrapTimestamp;
    private long shutdownTimestamp;
    
    {
        this.totalDataSent = 0;
        this.totalDataReceived = 0;
        this.bootstrapTime = 0;
        this.numContentLookups = 0;
        this.totalContentLookupTime = 0;
        this.totalRouteLength = 0;
        this.bootstrapTimestamp = 0;
        this.shutdownTimestamp = 0;
    }
    
    @Override
    public long getOnlineTime(){
        return (this.bootstrapTimestamp - this.shutdownTimestamp) / 1000;
    }
    
    @Override
    public long getBootstrapTimestamp() {
        return bootstrapTimestamp;
    }

    @Override
    public void setBootstrapTimestamp(long bootstrapTimestamp) {
        this.bootstrapTimestamp = bootstrapTimestamp;
    }

    @Override
    public long getShutdownTimestamp() {
        return shutdownTimestamp;
    }

    @Override
    public void setShutdownTimestamp(long shutdownTimestamp) {
        this.shutdownTimestamp = shutdownTimestamp;
    }

    @Override
    public void sentData(long size){
        this.totalDataSent += size;
        this.numDataSent++;
    }

    @Override
    public long getTotalDataSent(){
        if (this.totalDataSent == 0)
        {
            return 0L;
        }
        
        return this.totalDataSent / 1000L;
    }

    @Override
    public void receivedData(long size){
        this.totalDataReceived += size;
        this.numDataReceived++;
    }

    @Override
    public long getTotalDataReceived(){
        if (this.totalDataReceived == 0)
        {
            return 0L;
        }
        return this.totalDataReceived / 1000L;
    }

    @Override
    public void setBootstrapTime(long time){
        this.bootstrapTime = time;
    }

    @Override
    public long getBootstrapTime(){
        return this.bootstrapTime / 1000000L;
    }

    @Override
    public void addContentLookup(long time, int routeLength, boolean isSuccessful){
        if (isSuccessful)
        {
            this.numContentLookups++;
            this.totalContentLookupTime += time;
            this.totalRouteLength += routeLength;
        }
        else
        {
            this.numFailedContentLookups++;
        }
    }

    @Override
    public int numContentLookups(){
        return this.numContentLookups;
    }

    @Override
    public int numFailedContentLookups(){
        return this.numFailedContentLookups;
    }

    @Override
    public long totalContentLookupTime(){
        return this.totalContentLookupTime;
    }

    @Override
    public double averageContentLookupTime(){
        if (this.numContentLookups == 0)
        {
            return 0D;
        }

        double avg = (double) ((double) this.totalContentLookupTime / (double) this.numContentLookups) / 1000000D;
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(avg));
    }

    @Override
    public double averageContentLookupRouteLength(){
        if (this.numContentLookups == 0)
        {
            return 0D;
        }
        double avg = (double) ((double) this.totalRouteLength / (double) this.numContentLookups);
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(avg));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Statistician: [");

        sb.append("Bootstrap Time: ");
        sb.append(this.getBootstrapTime());
        sb.append("; ");

        sb.append("Data Sent: ");
        sb.append("(");
        sb.append(this.numDataSent);
        sb.append(") ");
        sb.append(this.getTotalDataSent());
        sb.append(" bytes; ");

        sb.append("Data Received: ");
        sb.append("(");
        sb.append(this.numDataReceived);
        sb.append(") ");
        sb.append(this.getTotalDataReceived());
        sb.append(" bytes; ");

        sb.append("Num Content Lookups: ");
        sb.append(this.numContentLookups());
        sb.append("; ");

        sb.append("Avg Content Lookup Time: ");
        sb.append(this.averageContentLookupTime());
        sb.append("; ");

        sb.append("Avg Content Lookup Route Lth: ");
        sb.append(this.averageContentLookupRouteLength());
        sb.append("; ");

        sb.append("]");

        return sb.toString();
    }
    
    @Override
    public void createLog(){
        Path logPath = Paths.get("logs/statistician.csv");
        
        if(!Files.exists(logPath)){
            try {
                new File("logs/statistician.csv").createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Statistician.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        StringBuilder newRow = new StringBuilder("\n");
        newRow.append(this.getBootstrapTime());
        newRow.append(",");
        newRow.append(this.numDataSent);
        newRow.append(",");
        newRow.append(this.numDataReceived);
        newRow.append(",");
        newRow.append(this.getTotalDataSent());
        newRow.append(",");
        newRow.append(this.getTotalDataReceived());
        newRow.append(",");
        newRow.append(this.onlineTime);
        
        try {
            List<String> existingRecords = Files.readAllLines(logPath);
            existingRecords.add(newRow.toString());
            Files.write(logPath,existingRecords);
        } catch (IOException ex) {
            Logger.getLogger(Statistician.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
