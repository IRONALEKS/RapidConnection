package Example;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import RapidConnection.RapidConnection;
import RapidConnection.RawMessage;
import Utils.ConnectionMaker;
import Utils.INFOandDEBUG;

public class Server
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		INFOandDEBUG infoANDdebug = new INFOandDEBUG(true, true);
		
		RapidConnection connection = new RapidConnection(infoANDdebug);
		
		ServerSocketChannel socket = ConnectionMaker.newServerSocketChannel("localhost", 25565, true, false, false);
		
		SocketChannel channel = socket.accept();
		
		System.out.println("CONNECTION RECEIVED");
		
		connection.Connect(channel);
		
		while(connection.isConnectionPending())
		{
			connection.finishConnect();
		}
		
		connection.SetNonBlocking();
		
		RawMessage message = RawMessage.fromUTF8String("Hello there");
		
		for(int a = 0;a<1000000;a++)
		{
			connection.addToWriteQueue(message);
		}
		
		while((connection.shouldCallWrite()||connection.shouldWaitForWrite())&&connection.isConnected())
		{
			connection.Write();
		}
		
		System.out.println("END");
		
		Thread.sleep(1000);
		
		connection.Disconnect();
	}
}