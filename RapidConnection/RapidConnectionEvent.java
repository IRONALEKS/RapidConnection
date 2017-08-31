package RapidConnection;

public interface RapidConnectionEvent
{
	public default void Connect(){};
	
	public default void Disconnect(){};
}
