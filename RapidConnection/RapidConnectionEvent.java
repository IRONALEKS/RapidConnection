package RapidConnection;

/**
 * This is the {@link RapidConnectionEvent} interface that can be used for connect and disconnect events
 * <p>
 * It is very easy to use, and very intuitive
 * <p>
 * Hope it helps you in your work :D
 * 
 * @author IRONALEKS
 */
public interface RapidConnectionEvent
{
	/**
	 * This is called whenever one of the {@link RapidConnection} Connect methods is called(internally or externally) no matter if the connection succeeded
	 */
	public default void Connect(){};
	
	/**
	 * This is called whenever one of the {@link RapidConnection} Disconnect methods is called(internally or externally) no matter if the disconnection succeeded
	 */
	public default void Disconnect(){};
}
