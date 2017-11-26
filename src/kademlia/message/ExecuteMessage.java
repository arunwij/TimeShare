package kademlia.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import kademlia.node.Node;

/**
 * A message used to acknowledge a request from a node; can be used in many situations.
 * - Mainly used to acknowledge a connect message
 *
 * @author Joshua Kissoon
 * @created 20140218
 */
public class ExecuteMessage implements Message
{

    private Node origin;
    public static final byte CODE = 0x0B;

    public ExecuteMessage(Node origin)
    {
        this.origin = origin;
    }

    public ExecuteMessage(DataInputStream in) throws IOException
    {
        this.fromStream(in);
    }

    @Override
    public final void fromStream(DataInputStream in) throws IOException
    {
        this.origin = new Node(in);
    }

    @Override
    public void toStream(DataOutputStream out) throws IOException
    {
        origin.toStream(out);
    }

    public Node getOrigin()
    {
        return this.origin;
    }

    @Override
    public byte code()
    {
        return CODE;
    }

    @Override
    public String toString()
    {
        return "AcknowledgeMessage[origin=" + origin.getNodeId() + "]";
    }
}
