package CustomExceptions;

public class InvalidOperationException extends Exception
{
    public InvalidOperationException(String gripe)
    {
        super(gripe);
    }
}