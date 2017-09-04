package Example;

import RapidConnection.RapidConnection;
import RapidConnection.RawMessage;
import Utils.INFOandDEBUG;

public class Client
{
	public static void main(String[] args)
	{
		INFOandDEBUG infoANDdebug = new INFOandDEBUG(true, true);
		
		RapidConnection connection = new RapidConnection(infoANDdebug);

		connection.Connect("localhost", 25565, false);
		
		while(connection.isConnectionPending())
		{
			connection.finishConnect();
		}
		
		int messages = 0;
		
		while(connection.isConnected()&&(connection.shouldCallRead()||connection.shouldWaitForRead()))
		{
			connection.Read();
			
			while(connection.haveMessage())
			{
				messages+=1;
				
				RawMessage message = connection.getMessage();
				
				if(messages%250000==0)
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