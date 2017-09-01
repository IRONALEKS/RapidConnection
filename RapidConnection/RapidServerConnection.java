package RapidConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import Utils.ConnectionMaker;
import Utils.INFOandDEBUG;

/**
 * This is the {@link RapidServerConnection} class that wraps a {@link ServerSocketChannel} and uses it as a {@link RapidConnection} connection acceptor
 * <p>
 * It is very easy to use, and very intuitive
 * <p>
 * Hope it helps you in your work :D
 * 
 * @author IRONALEKS
 */
public class RapidServerConnection
{
	private ServerSocketChannel channel = null;
	private INFOandDEBUG infoANDdebug = null;
	private boolean isReady = false;

	/**
	 * This is the constructor with INFO and DEBUG set to true
	 */
	public RapidServerConnection()
	{
		infoANDdebug = new INFOandDEBUG(true, true);
	}
	
	/**
	 * The {@link INFOandDEBUG} of this connection will be the one of the parameter
	 * <p>
	 * @param  infoANDdebugv The {@link INFOandDEBUG} to which this {@link RapidServerConnection} will be linked to
	 */
	public RapidServerConnection(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
	}
	
	/**
	 * Binds a new {@link ServerSocketChannel} to the given address and wraps the {@link ServerSocketChannel} into this {@link RapidServerConnection}
	 * @param  bind The {@link String} address to bind this {@link ServerRapidConnection} to
	 * @param  bindconnect The port to connect this {@link ServerRapidConnection} to
	 * @param  blocking The blocking mode to set the {@link ServerSocketChannel} that will be wrapped by this {@link RapidServerConnection}
	 */
	public void Bind(String bind, int bindport, boolean blocking)
	{
		if(isReady)
		{
			reset();
		}
		
		channel = ConnectionMaker.newServerSocketChannel(bind, bindport, blocking, infoANDdebug.getINFO(), infoANDdebug.getDEBUG());
		
		Link(channel);
	}

	/**
	 * Binds a new {@link ServerSocketChannel} to the given address and wraps the {@link ServerSocketChannel} into this {@link RapidServerConnection}
	 * @param  bind The {@link InetSocketAddress} address to bind this {@link ServerRapidConnection} to
	 * @param  blocking The blocking mode to set the {@link ServerSocketChannel} that will be wrapped by this {@link RapidServerConnection}
	 */
	public void Bind(InetSocketAddress bind, boolean blocking)
	{
		if(isReady)
		{
			reset();
		}
		channel = ConnectionMaker.newServerSocketChannel(bind, blocking, infoANDdebug.getINFO(), infoANDdebug.getDEBUG());
		
		Link(channel);
	}

	/**
	 * Binds a new {@link ServerSocketChannel} to the given address and wraps the {@link ServerSocketChannel} into this {@link RapidServerConnection}
	 * @param  bind The {@link String} address to bind this {@link ServerRapidConnection} to
	 * @param  bindconnect The port to connect this {@link ServerRapidConnection} to
	 * @param  backlog The max amount of connections that can wait to be accepted by this {@link ServerRapidConnection}
	 * @param  blocking The blocking mode to set the {@link ServerSocketChannel} that will be wrapped by this {@link RapidServerConnection}
	 */
	public void Bind(String bind, int bindport, int backlog, boolean blocking)
	{
		if(isReady)
		{
			reset();
		}
		
		channel = ConnectionMaker.newServerSocketChannel(bind, bindport, backlog, blocking, infoANDdebug.getINFO(), infoANDdebug.getDEBUG());
		
		Link(channel);
	}

	/**
	 * Binds a new {@link ServerSocketChannel} to the given address and wraps the {@link ServerSocketChannel} into this {@link RapidServerConnection}
	 * @param  bind The {@link InetSocketAddress} address to bind this {@link ServerRapidConnection} to
	 * @param  backlog The max amount of connections that can wait to be accepted by this {@link ServerRapidConnection}
	 * @param  blocking The blocking mode to set the {@link ServerSocketChannel} that will be wrapped by this {@link RapidServerConnection}
	 */
	public void Bind(InetSocketAddress bind, int backlog, boolean blocking)
	{
		if(isReady)
		{
			reset();
		}
		channel = ConnectionMaker.newServerSocketChannel(bind, backlog, blocking, infoANDdebug.getINFO(), infoANDdebug.getDEBUG());
		
		Link(channel);
	}

	/**
	 * Wraps the {@link ServerSocketChannel} into this {@link RapidServerConnection}
	 * @param serversocketchannel The {@link ServerSocketChannel} that will be wrapped by this {@link RapidServerConnection}
	 */
	public void Bind(ServerSocketChannel serversocketchannel)
	{
		if(isReady)
		{
			reset();
		}
		
		Link(serversocketchannel);
	}
	
	/**
	 * Sets this {@link RapidServerConnection} to be blocking
	 * @return Returns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetBlocking()
	{
		if(isReady)
		{
			try
			{
				channel.configureBlocking(true);
				return true;
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	/**
	 * Sets this {@link RapidServerConnection} to be non-blocking
	 * @return Returns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetNonBlocking()
	{
		if(isReady)
		{
			try
			{
				channel.configureBlocking(false);
				return true;
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	/**
	 * Sets this {@link RapidServerConnection} blocking mode to the given value
	 * @param value The value to set the blocking mode of this {@link RapidServerConnection}
	 * @return Returns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetBlockingOrNonBlocking(boolean value)
	{
		if(isReady)
		{
			try
			{
				channel.configureBlocking(value);
				return true;
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
	
	/**
	 * This method sets the {@link INFOandDEBUG} link of this {@link RapidServerConnection} 
	 */
	public void setInfoAndDebug(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
	}

	/**
	 * This method returns the {@link INFOandDEBUG} link of this {@link RapidServerConnection}
	 * @return Returns the {@link INFOandDEBUG} link of this {@link RapidServerConnection}
	 */
	public INFOandDEBUG getInfoAndDebug()
	{
		return infoANDdebug;
	}

	/**
	 * This method resets the {@link RapidServerConnection} so that it can be bind again
	 */
	public void reset()
	{
		if(isReady)
		{
			try
			{
				channel.close();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			channel = null;
			
			isReady=false;
		}
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection} the INFO and DEBUG of the new {@link RapidConnection} will be both true
	 * @return Returns a {@link RapidConnection}, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept()
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection();
			
			temp1.Link(temp);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection}
	 * @param infoANDdebugv The {@link INFOandDEBUG} that the new {@link RapidConnection} will be linked to
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(INFOandDEBUG infoANDdebugv)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(infoANDdebugv);
			
			temp1.Link(temp);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection} the INFO and DEBUG of the new {@link RapidConnection} will be both true
	 * @param  read_buffer_size The read buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(int read_buffer_size, int write_buffer_size)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(read_buffer_size, write_buffer_size);
			
			temp1.Link(temp);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection}
	 * @param infoANDdebugv The {@link INFOandDEBUG} that the new {@link RapidConnection} will be linked to
	 * @param  read_buffer_size The read buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(INFOandDEBUG infoANDdebugv, int read_buffer_size, int write_buffer_size)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(infoANDdebugv, read_buffer_size, write_buffer_size);
			
			temp1.Link(temp);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection} the INFO and DEBUG of the new {@link RapidConnection} will be both true
	 * @param  blocking The blocking mode to set the {@link SocketChannel} that will be wrapped by the new {@link RapidConnection}
	 * @return Returns a {@link RapidConnection}, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(boolean blocking)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection();
			
			temp1.Link(temp, blocking);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection}
	 * @param infoANDdebugv The {@link INFOandDEBUG} that the new {@link RapidConnection} will be linked to
	 * @param  blocking The blocking mode to set the {@link SocketChannel} that will be wrapped by the new {@link RapidConnection}
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(INFOandDEBUG infoANDdebugv, boolean blocking)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(infoANDdebugv);
			
			temp1.Link(temp, blocking);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection} the INFO and DEBUG of the new {@link RapidConnection} will be both true
	 * @param  read_buffer_size The read buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  blocking The blocking mode to set the {@link SocketChannel} that will be wrapped by the new {@link RapidConnection}
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(int read_buffer_size, int write_buffer_size, boolean blocking)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(read_buffer_size, write_buffer_size);
			
			temp1.Link(temp, blocking);
			
			result = temp1;
		}
		
		return result;
	}

	/**
	 * This method accepts a connection {@link SocketChannel} and wraps it in a {@link RapidConnection}
	 * @param infoANDdebugv The {@link INFOandDEBUG} that the new {@link RapidConnection} will be linked to
	 * @param  read_buffer_size The read buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of the new {@link RapidConnection} not of the underlying socket
	 * @param  blocking The blocking mode to set the {@link SocketChannel} that will be wrapped by the new {@link RapidConnection}
	 * @return Returns a {@link RapidConnection} connection, but may also return null if this {@link RapidServerConnection} is in non-blocking mode
	 */
	public RapidConnection accept(INFOandDEBUG infoANDdebugv, int read_buffer_size, int write_buffer_size, boolean blocking)
	{
		RapidConnection result = null;
		
		if(isReady)
		{
			SocketChannel temp = null;
			
			try
			{
				temp = channel.accept();
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
			
			RapidConnection temp1 = new RapidConnection(infoANDdebugv, read_buffer_size, write_buffer_size);
			
			temp1.Link(temp, blocking);
			
			result = temp1;
		}
		
		return result;
	}
	
	private void Link(ServerSocketChannel serversocketchannel)
	{
		if(serversocketchannel!=null)
		{
			channel = serversocketchannel;
			isReady = true;
		}
	}
	
	/**
	 * This method return {@code true} if this {@link RapidServerSocket} is ready to accept new connections and {@code false} otherwise
	 * @return Returns {@code true} if this {@link RapidServerSocket} is ready to accept new connections and {@code false} otherwise
	 */
	public boolean isReady()
	{
		return isReady;
	}
}
