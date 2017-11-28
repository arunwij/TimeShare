package kademlia.exceptions;

/**
 * An exception used to indicate an unknown message type or communication identifier
 *
 */
public class UnknownMessageException extends RuntimeException
{

    public UnknownMessageException()
    {
        super();
    }

    public UnknownMessageException(String message)
    {
        super(message);
    }
}
