package kademlia.message;

import timeshare.RunningConfiguration;
import timeshare.allocator.XmlLoader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import kademlia.file.Serializer;
import kademlia.node.Node;

/**
 * A simple message used for testing the system; Default message constructed if
 * the message type sent is not available
 *
 * @author Joshua Kissoon
 * @created 20140217
 */
public class WorkloadMessage implements Message {

    /* Message constants */
    public static final byte CODE = 0x0A;

    private String methodName;
    private String className;
    private String type;
    private String data;
    private Node origin;
  
    public WorkloadMessage( String className,String methodName, String type, String data){
        this.methodName = methodName;
        this.className = className;
        this.type = type;
        this.data = data;
        this.origin = RunningConfiguration.LOCAL_JKNODE.getNode();
    }

    public WorkloadMessage(DataInputStream in) {
        this.fromStream(in);
    }

    public String getMethodName() {
        return methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getType() {
        return type;
    }

    public String getStringData() {
        return data;
    }
    
    public Node getOrigin(){
        return this.origin;
    }
    
    public Object getData(){
        switch(this.type){
            case Serializer.INT1D:
                return Serializer.getInt1d(this.data);
            case Serializer.INT2D:
                return Serializer.getInt2d(this.data);
            case Serializer.INT3D:
                return Serializer.getInt3d(this.data);
            case Serializer.STR1D:
                return Serializer.getStr1d(this.data);
            case Serializer.STR2D:
                return Serializer.getStr2d(this.data); 
            case Serializer.STR3D:
                return Serializer.getStr3d(this.data); 
            default:
                System.out.println("No suitable data type for "+type);
                return null;
        }
    }
    
    public int[][] getIntAr(){
        return Serializer.getInt2d(this.data);
    }
    
    @Override
    public byte code() {
        return CODE;
    }

    @Override
    public void toStream(DataOutputStream out) {
        try {
            out.writeInt(this.className.length());
            out.writeBytes(this.className);
            out.writeInt(this.methodName.length());
            out.writeBytes(this.methodName);
            out.writeInt(this.type.length());
            out.writeBytes(this.type);
            out.writeInt(this.data.length());
            out.writeBytes(this.data);
            origin.toStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void fromStream(DataInputStream in) {
        try {
            byte[] classBuff = new byte[in.readInt()];
            in.readFully(classBuff);
            byte[] methodBuff = new byte[in.readInt()];
            in.readFully(methodBuff);
            byte[] typeBuff = new byte[in.readInt()];
            in.readFully(typeBuff);
            byte[] dataBuff = new byte[in.readInt()];
            in.readFully(dataBuff);

            this.className = new String(classBuff);
            this.methodName = new String(methodBuff);
            this.type = new String(typeBuff);
            this.data = new String(dataBuff);
            this.origin = new Node(in);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.className+" "+ this.methodName+" "+this.type+" "+this.data;
    }
}
