package kademlia.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import kademlia.file.Serializer;

/**
 * A simple message used for testing the system; Default message constructed if the message type sent is not available
 *
 * @author Joshua Kissoon
 * @created 20140217
 */
public class ResultMessage implements Message{

    /* Message constants */
    public static final byte CODE = 0x0b;
    
    private String type;
    private String data;
    
    public ResultMessage(String type, String data){
        this.type = type;
        this.data = data;
    }

    public ResultMessage(DataInputStream in)
    {
        this.fromStream(in);
    }
    
    public String getType(){
        return this.type;
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
            default:
                System.out.println("No suitable data type for "+type);
                return data;
        }
    }

    @Override
    public byte code()
    {
        return CODE;
    }

    @Override
    public void toStream(DataOutputStream out)
    {
        try
        {
            out.writeInt(this.type.length());
            out.writeBytes(this.type);
            out.writeInt(this.data.length());
            out.writeBytes(this.data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public final void fromStream(DataInputStream in)
    {
        try{
            byte[] typeBuff = new byte[in.readInt()];
            in.readFully(typeBuff);
            
            byte[] dataBuff = new byte[in.readInt()];
            in.readFully(dataBuff);
            
            this.type = new String(typeBuff);
            this.data = new String(dataBuff);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return this.type+" "+this.data;
    }
}
