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
		
		int messages = 100000000;
		int batch = 1000;

		RawMessage message = RawMessage.fromUTF8String("Hello there");
		
		for(int batches = 0,times=messages/batch; batches<times; batches++)
		{
			for(int a = 0;a<batch;a++)
			{
				connection.addToWriteQueue(message);
			}
			
			while(connection.isConnected()&&(connection.shouldCallWrite()||connection.shouldWaitForWrite()))
			{
				connection.Write();
			}
		}
		
		System.out.println("END");
		
		Thread.sleep(1000);
		
		connection.Disconnect();
	}
}