package kademlia.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import kademlia.file.Serializer;
import kademlia.node.Node;
import timeshare.RunningConfiguration;

/**
 * A message used to acknowledge a request from a node; can be used in many situations.
 * - Mainly used to acknowledge a connect message
 *
 */
public class ExecuteMessage implements Message{

    private Node origin;
    private String methodName;
    private String className;
    private String type;
    public String data;
    public static final byte CODE = 0x0C;

    public ExecuteMessage(String className,String methodName, String type, String data){
        this.methodName = methodName;
        this.className = className;
        this.type = type;
        this.data = data;
        this.origin = RunningConfiguration.LOCAL_JKNODE.getNode();;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ExecuteMessage(DataInputStream in) throws IOException{
        this.fromStream(in);
    }

    @Override
    public final void fromStream(DataInputStream in) throws IOException{
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
    public void toStream(DataOutputStream out) throws IOException{
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

    public Node getOrigin(){
        return this.origin;
    }

    @Override
    public byte code(){
        return CODE;
    }

    @Override
    public String toString(){
        return "ExecuteMessage[origin=" + origin.getNodeId() + "]";
    }
    
    public <Any>Any getData(){
        switch(this.type){
            case Serializer.INT1D:
                return (Any) Serializer.getInt1d(this.data);
            case Serializer.INT2D:
                return (Any)Serializer.getInt2d(this.data);
            case Serializer.INT3D:
                return (Any)Serializer.getInt3d(this.data);
            case Serializer.STR1D:
                return (Any)Serializer.getStr1d(this.data);
            case Serializer.STR2D:
                return (Any)Serializer.getStr2d(this.data); 
            case Serializer.STR3D:
                return (Any)Serializer.getStr3d(this.data); 
            default:
                System.out.println("No suitable data type for "+type);
                return null;
        }
    }
    
    public int[][] getIntAr(){
        return Serializer.getInt2d(this.data);
    }
}
