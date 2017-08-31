package Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ConnectionMaker
{
	public static final int DEFAULT_BACKLOG = 256;
	
	public ConnectionMaker()
	{
		
	}
	
	public static SocketChannel newSocketChannel(InetSocketAddress connect,boolean blocking,boolean INFO,boolean DEBUG)
	{
		SocketChannel socket = null;
		
		try
		{
			socket = SocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.connect(connect);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static SocketChannel newSocketChannel(String connect,int portconnect,boolean blocking,boolean INFO,boolean DEBUG)
	{
		SocketChannel socket = null;
		
		try
		{
			socket = SocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.connect(new InetSocketAddress(connect, portconnect));
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static SocketChannel newSocketChannel(InetSocketAddress bind, InetSocketAddress connect,boolean blocking,boolean INFO,boolean DEBUG)
	{
		SocketChannel socket = null;
		
		try
		{
			socket = SocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(bind);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.connect(connect);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static SocketChannel newSocketChannel(String bind, int portbind, String connect,int portconnect,boolean blocking,boolean INFO,boolean DEBUG)
	{
		SocketChannel socket = null;
		
		try
		{
			socket = SocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(new InetSocketAddress(bind, portbind));
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.connect(new InetSocketAddress(connect, portconnect));
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static ServerSocketChannel newServerSocketChannel(String bind,int portbind,boolean blocking,boolean INFO,boolean DEBUG)
	{
		ServerSocketChannel socket = null;
		
		try
		{
			socket = ServerSocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(new InetSocketAddress(bind, portbind));
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static ServerSocketChannel newServerSocketChannel(InetSocketAddress bind, boolean blocking,boolean INFO,boolean DEBUG)
	{
		ServerSocketChannel socket = null;
		
		try
		{
			socket = ServerSocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(bind);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static ServerSocketChannel newServerSocketChannel(String bind,int portbind,int backlog, boolean blocking,boolean INFO,boolean DEBUG)
	{
		ServerSocketChannel socket = null;
		
		try
		{
			socket = ServerSocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(new InetSocketAddress(bind, portbind),backlog);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
	
	public static ServerSocketChannel newServerSocketChannel(InetSocketAddress bind, int backlog, boolean blocking,boolean INFO,boolean DEBUG)
	{
		ServerSocketChannel socket = null;
		
		try
		{
			socket = ServerSocketChannel.open();
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.configureBlocking(blocking);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			socket.bind(bind,backlog);
		}
		catch (IOException e)
		{
			if(DEBUG)
			{
				e.printStackTrace();
			}
		}
		
		return socket;
	}
}
