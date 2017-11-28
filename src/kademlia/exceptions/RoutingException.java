package kademlia.exceptions;

import java.io.IOException;

/**
 * An exception to be thrown whenever there is a routing problem
 *
 */
public class RoutingException extends IOException
{

    public RoutingException()
    {
        super();
    }

    public RoutingException(String message)
    {
        super(message);
    }
}
