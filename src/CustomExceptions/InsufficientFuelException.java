package CustomExceptions;

public class InsufficientFuelException extends Exception
{
    public InsufficientFuelException(String gripe)
    {
        super(gripe);
    }

} 