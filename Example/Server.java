package Example;

import java.io.IOException;
import RapidConnection.RapidConnection;
import RapidConnection.RapidServerConnection;
import RapidConnection.RawMessage;
import Utils.INFOandDEBUG;

public class Server
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		INFOandDEBUG infoANDdebug = new INFOandDEBUG(true, true);
		
		RapidServerConnection serverconnection = new RapidServerConnection(infoANDdebug);
		serverconnection.Bind("localhost", 25565, true);
		
		RapidConnection connection = serverconnection.accept(infoANDdebug, false);
		
		System.out.println("CONNECTION RECEIVED");
		
		while(connection.isConnectionPending())
		{
			connection.finishConnect();
		}

		RawMessage message = RawMessage.fromUTF8String("Hello there");
		
		for(int times = 0;times<1000;times++)
		{
			for(int a = 0;a<100000;a++)
			{
				connection.addToWriteQueue(message);
			}
			
			while((connection.shouldCallWrite()||connection.shouldWaitForWrite())&&connection.isConnected())
			{
				connection.Write();
			}
		}
		
		System.out.println("END");
		
		Thread.sleep(1000);
		
		connection.Disconnect();
	}
}