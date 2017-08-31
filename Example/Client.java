package Example;

import java.nio.channels.SocketChannel;
import RapidConnection.RapidConnection;
import RapidConnection.RawMessage;
import Utils.ConnectionMaker;
import Utils.INFOandDEBUG;

public class Client
{
	public static void main(String[] args)
	{
		INFOandDEBUG infoANDdebug = new INFOandDEBUG(true, true);
		
		RapidConnection connection = new RapidConnection(infoANDdebug);
	
		SocketChannel channel = ConnectionMaker.newSocketChannel("localhost", 25565, false, true, true);

		connection.Connect(channel);
		
		while(connection.isConnectionPending())
		{
			connection.finishConnect();
		}
		
		int messages = 0;
		
		while((connection.shouldCallRead()||connection.shouldWaitForRead())&&connection.isConnected())
		{
			connection.Read();
			
			while(connection.haveMessage())
			{
				messages+=1;
				
				RawMessage message = connection.getMessage();
				
				if(messages%100000==0)
				{
					System.out.println("MESSAGE NUMBER: "+messages);
					System.out.println("MESSAGE: "+message.toUTF8String());
					System.out.println("///////////////////////////");
				}
			}
		}
		
		System.out.println("MESSAGES RECIEVED: "+messages);
	}
}