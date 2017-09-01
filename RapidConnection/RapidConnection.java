package RapidConnection;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;

import Utils.BufferStuff;
import Utils.INFOandDEBUG;
import Utils.IntegerStuff;

/**
 * This is the {@link RapidConnection} class that wraps a {@link SocketChannel} and uses it as a message tranportation link
 * <p>
 * It is very easy to use, and very intuitive
 * <p>
 * Hope it helps you in your work :D
 * 
 * @author IRONALEKS
 */
public class RapidConnection
{
	/**
	 * The default read and write buffer sizes
	 */
	public static final int DEFAULT_BUFFERS_SIZE = 65536;
	
	private boolean connected = false;
	private boolean connectionpending = false;
			
	private INFOandDEBUG infoANDdebug = null;

	private SocketChannel socketchannel;
	private SenderReciever senderreciever;
	
	private RapidConnectionEvent callback;
	
	//TODO
	//MAKE ATTACHEMENT
	
	/**
	 * This is the constructor with INFO and DEBUG set to true
	 * <p>
	 * Read and write buffers will be set to the default size of 65536 bytes
	 */
	public RapidConnection()
	{
		infoANDdebug = new INFOandDEBUG(true, true);
		senderreciever=new SenderReciever(this);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * The {@link INFOandDEBUG} of this connection will be the one of the parameter
	 * <p>
	 * Read and write buffers will be set to the default size of 65536 bytes
	 * @param  infoANDdebugv The info and debug to which this {@link RapidConnection} will be linked to
	 */
	public RapidConnection(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever=new SenderReciever(this);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * This {@link RapidConnection} INFO and DEBUG will be set to true
	 * <p>
	 * Read and Write buffers are of the specified sizes
	 * @param  read_buffer_size The read buffer size of this {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of this {@link RapidConnection} not of the underlying socket
	 */
	public RapidConnection(int read_buffer_size,int write_buffer_size)
	{
		infoANDdebug = new INFOandDEBUG(true, true);
		senderreciever=new SenderReciever(this,read_buffer_size,write_buffer_size);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * The {@link INFOandDEBUG} of this connection will be the one of the parameter
	 * <p>
	 * Read and Write buffers are of the specified sizes
	 * @param  infoANDdebugv The {@link INFOandDEBUG} to which this {@link RapidConnection} will be linked to
	 * @param  read_buffer_size The read buffer size of this {@link RapidConnection} not of the underlying socket
	 * @param  write_buffer_size The write buffer size of this {@link RapidConnection} not of the underlying socket
	 */
	public RapidConnection(INFOandDEBUG infoANDdebugv,int read_buffer_size,int write_buffer_size)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever=new SenderReciever(this,read_buffer_size,write_buffer_size);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * Wraps the socketchannel into this RapidConnection
	 * @param  socketchannelv The SocketChannel to be wrapped by this {@link RapidConnection}
	 */
	//CONNECTION
	public void Connect(SocketChannel socketchannelv)
	{
		if(connected)
		{
			Disconnect();
			socketchannel = socketchannelv;
			if(socketchannelv!=null)
			{
				connected=socketchannel.isConnected();
				connectionpending = socketchannel.isConnectionPending();
				if(connected&&connectionpending)
				{
					new Throwable("PROBLEM OVER HERE CONNECTED AND CONNECTION PENDING RETURN BOTH TRUE").printStackTrace();
				}
				else if(!connected&&!connectionpending)
				{
					Disconnect();
				}
			}
			else
			{
				connected=false;
				connectionpending=false;
			}
		}
		else
		{
			socketchannel = socketchannelv;
			if(socketchannelv!=null)
			{
				connected=socketchannel.isConnected();
				connectionpending = socketchannel.isConnectionPending();
				if(connected&&connectionpending)
				{
					new Throwable("PROBLEM OVER HERE CONNECTED AND CONNECTION PENDING RETURN BOTH TRUE").printStackTrace();
				}
				else if(!connected&&!connectionpending)
				{
					Disconnect();
				}
			}
			else
			{
				connected=false;
				connectionpending=false;
			}
		}
		callback.Connect();
	}
	
	/**
	 * Wraps the socketchannel into this RapidConnection and sets it's blocking mode to the given value
	 * @param  socketchannelv The SocketChannel to wrap by this RapidConnection
	 * @param  blocking The value to set the blocking mode of the given {@link RapidConnection}
	 */
	public void Connect(SocketChannel socketchannelv,boolean blocking)
	{
		if(connected)
		{
			Disconnect();
			socketchannel = socketchannelv;
			if(socketchannelv!=null)
			{
				connected=socketchannel.isConnected();
				connectionpending = socketchannel.isConnectionPending();
				if(connected&&connectionpending)
				{
					new Throwable("PROBLEM OVER HERE CONNECTED AND CONNECTION PENDING RETURN BOTH TRUE").printStackTrace();
				}
				else if(!connected&&!connectionpending)
				{
					Disconnect();
				}
			}
			else
			{
				connected=false;
				connectionpending=false;
			}
		}
		else
		{
			socketchannel = socketchannelv;
			if(socketchannelv!=null)
			{
				connected=socketchannel.isConnected();
				connectionpending = socketchannel.isConnectionPending();
				if(connected&&connectionpending)
				{
					new Throwable("PROBLEM OVER HERE CONNECTED AND CONNECTION PENDING RETURN BOTH TRUE").printStackTrace();
				}
				else if(!connected&&!connectionpending)
				{
					Disconnect();
				}
			}
			else
			{
				connected=false;
				connectionpending=false;
			}
		}
		SetBlockingOrNonBlocking(blocking);
		callback.Connect();
	}
	
	/**
	 * Call this function in case if the function {@link #isConnectionpending()} returns true
	 * This function finishes the connecting stage, but may also not finish it
	 */
	public void finishConnect()
	{
		if(connectionpending)
		{
			try
			{
				if(socketchannel.finishConnect())
				{
					connected=true;
					connectionpending=false;
				}
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
				Disconnect();
			}
		}
	}
	
	/**
	 * Closes the connection, and this RapidConnection can be reused for new connections
	 */
	public void Disconnect()
	{
		try
		{
			if(socketchannel!=null)
			{
				socketchannel.close();
			}
		}
		catch (IOException e)
		{
			if(infoANDdebug.getDEBUG())
			{
				e.printStackTrace();
			}
		}
		socketchannel=null;
		connected=false;
		connectionpending=false;
		senderreciever.resetFull();
		callback.Disconnect();
	}
	
	//BLOCKING OR NON-BLOCKING
	/**
	 * Sets this RapidConnection to be blocking
	 * @return Returns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetBlocking()
	{
		if(connected||connectionpending)
		{
			try
			{
				socketchannel.configureBlocking(true);
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
	 * Sets this RapidConnection to be non-blocking
	 * @return Returns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetNonBlocking()
	{
		if(connected||connectionpending)
		{
			try
			{
				socketchannel.configureBlocking(false);
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
	 * Sets this RapidConnection blocking mode to the given value
	 * @param value The value to set the blocking mode of this RapidConnection
	 * @return Teturns {@code true} if the opperation succeeded {@code false} otherwise
	 */
	public boolean SetBlockingOrNonBlocking(boolean value)
	{
		if(connected||connectionpending)
		{
			try
			{
				socketchannel.configureBlocking(value);
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
	
	//READ AND WRITE

	/**
	 * Adds the given {@link RawMessage} to the write queue
	 * @param rawmessage the {@link RawMessage} to be added to the write queue
	 */
	public void addToWriteQueue(RawMessage rawmessage)
	{
		senderreciever.addToWriteQueue(rawmessage);
	}

	/**
	 * This method should be called as long as the function {@link #shouldCallRead()} returns true
	 */
	public void Read()
	{
		if(connected)
		{
			senderreciever.Read(socketchannel);
		}
	}
	
	/**
	 * This method should be called as long as the function {@link #shouldCallWrite()} returns true
	 */
	public void Write()
	{
		if(connected)
		{
			senderreciever.write(socketchannel);
		}
	}
	
	/**
	 * This method tells you whether the function {@link #Read()} should be called
	 * @return Returns {@code true} if the function {@link #Read()} should be called {@code false} otherwise
	 */
	public boolean shouldCallRead()
	{
		if(connected)
		{
			return senderreciever.shouldCallRead();
		}
		
		return false;
	}

	/**
	 * This method tells you whether the function {@link #Write()} should be called
	 * @return Returns {@code true} if the function {@link #Write()} should be called {@code false} otherwise
	 */
	public boolean shouldCallWrite()
	{
		if(connected)
		{
			return senderreciever.shouldCallWrite();
		}
		
		return false;
	}
	
	/**
	 * This method tells you whether you should wait before calling the function {@link #Read()}
	 * @return Returns {@code true} if you should wait before calling the function {@link #Read()} returns {@code false} otherwise
	 */
	public boolean shouldWaitForRead()
	{
		if(connected)
		{
			return senderreciever.shouldWaitForRead();
		}
		
		return false;
	}
	
	/**
	 * This method tells you whether you should wait before calling the function {@link #Write()}
	 * @return Returns {@code true} if you should wait before calling the function {@link #Write()} returns {@code false} otherwise
	 */
	public boolean shouldWaitForWrite()
	{
		if(connected)
		{
			return senderreciever.shouldWaitForWrite();
		}
		
		return false;
	}
	
	/**
	 * This method returns the number of messages still in the write queue
	 * @return Returns an {@code int} representing the number of messages still in the write queue
	 */
	public int getWriteQueueSize()
	{
		return senderreciever.getWriteQueueSize();
	}

	/**
	 * This method returns an ArrayDeque object containing the messages in the write queue
	 * @return Returns the {@link ArrayDeque} containing messages in the write queue
	 */
	public ArrayDeque<RawMessage> getWriteQueue()
	{
		return senderreciever.getWriteQueue();
	}
	
	/**
	 * This method returns the number of messages recieved in the read queue
	 * @return Returns an {@code int} representing the number of messages in the read queue
	 */
	public int getReadQueueSize()
	{
		return senderreciever.getReadQueueSize();
	}

	/**
	 * This method returns an ArrayDeque object containing the messages in the read queue
	 * @return Returns the {@link ArrayDeque} containing messages in the read queue
	 */
	public ArrayDeque<RawMessage> getReadQueue()
	{
		return senderreciever.getReadQueue();
	}
	
	//MESSAGE STUFF
	/**
	 * This method removes a message from the read queue and returns it to the caller
	 * @return Returns a {@link RawMessage} from the read queue
	 */
	public RawMessage getMessage()
	{
		return senderreciever.getMessage();
	}

	/**
	 * This method tells whether there is one or more messages in the read queue
	 * @return Returns {@code true} if there is one or more messages in the read queue {@code false} otherwise
	 */
	public boolean haveMessage()
	{
		return senderreciever.haveMessage();
	}

	
	
	//CONNECTION STATUS
	/**
	 * This method tells whether this {@link RapidConnection} is still connected to the other side
	 * @return Returns {@code true} this {@link RapidConnection} is still connected {@code false} otherwise
	 */
	public boolean isConnected()
	{
		return connected;
	}

	/**
	 * This method tells whether this {@link RapidConnection} connection is still pending and if you should call the function {@link #finishConnect()}
	 * @return Returns {@code true} this {@link RapidConnection} connection is still pending {@code false} otherwise
	 */
	public boolean isConnectionPending()
	{
		return connectionpending;
	}
	
	//BLOCKING STATUS

	/**
	 * This method returns this {@link RapidConnection} blocking mode
	 * @return Returns {@code true} this {@link RapidConnection}is in blocking mode {@code false} otherwise
	 */
	public boolean isBlocking()
	{
		return socketchannel.isBlocking();
	}
	
	//CALLBACK
	/**
	 * This method sets this {@link RapidConnection} callback {@link RapidConnectionEvent}
	 */
	public void setCallback(RapidConnectionEvent cb)
	{
		callback = cb;
	}
	
	//INFO AND DEBUG
	/**
	 * This method sets the {@link INFOandDEBUG} link of this {@link RapidConnection} 
	 */
	public void setInfoAndDebug(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever.setInfoAndDebug(infoANDdebug);
	}

	/**
	 * This method returns the {@link INFOandDEBUG} link of this {@link RapidConnection}
	 * @return Returns the {@link INFOandDEBUG} link of this {@link RapidConnection}
	 */
	public INFOandDEBUG getInfoAndDebug()
	{
		return infoANDdebug;
	}
	
	//SET SOCKET OPTIONS
	/**
	 * This method sets the receiving buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @param size Size of the receiving buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public void SetRcvBufferSize(int size)
	{
		if(connected)
		{
			try
			{
				socketchannel.setOption(StandardSocketOptions.SO_RCVBUF, size);
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method sets the sending buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @param size Size of the sending buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public void SetSndBufferSize(int size)
	{
		if(connected)
		{
			try
			{
				socketchannel.setOption(StandardSocketOptions.SO_SNDBUF, size);
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method sets the TCP_NO_DELAY of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @param b The value of the TCP_NO_DELAY parameter of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public void SetNoDelay(boolean b)
	{
		if(connected)
		{
			try
			{
				socketchannel.setOption(StandardSocketOptions.TCP_NODELAY, b);
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method returns the receiving buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @return The Size of the receiving buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public int GetRcvBufferSize()
	{
		if(connected)
		{
			try
			{
				return socketchannel.getOption(StandardSocketOptions.SO_RCVBUF);
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	/**
	 * This method returns the sending buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @return The Size of the sending buffer size of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public int GetSndBufferSize()
	{
		if(connected)
		{
			try
			{
				return socketchannel.getOption(StandardSocketOptions.SO_SNDBUF);
			}
			catch (IOException e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
			}
		}
		return -1;
	}
	
	/**
	 * This method returns the TCP_NO_DELAY of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @return The value of the TCP_NO_DELAY parameter of the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public boolean GetNoDelay()
	{
		if(connected)
		{
			try
			{
				return socketchannel.getOption(StandardSocketOptions.TCP_NODELAY);
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
	 * This method returns the {@link SocketChannel} wrapped by this {@link RapidConnection}
	 * @return The {@link SocketChannel} wrapped by this {@link RapidConnection}
	 */
	public SocketChannel GetSocketChannel()
	{
		return socketchannel;
	}
	
	//////////////////////////////
	////////SENDERRECIEVER////////
	//////////////////////////////
	private class SenderReciever
	{
		//ENCODER AND DECODER
		private CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
		private CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
		//BUFFER STUFF
		private ByteBuffer constbuff;
		private ByteBuffer varbuff;
		
		//LENGTH STUFF
		private StringBuilder lengthbuilder;
		private int nextmessagelength;
		private int alreadyhavemessagelength;
		private boolean havelength;

		//MESSAGE STUFF
		private RawMessage rawmessage;
		
		//WRITE STUFF
		private boolean writePending=false;
		private ByteBuffer tempWriteBuffer;
		
		//READ BUFFER
		private ByteBuffer readBuffer;
		private int read_buffer_size;
		
		//WRITE BUFFER
		private WriteByteBuffer writeBuffer;
		private int write_buffer_size;
		
		//LENGTH SEPARATOR CHAR
		private static final String LENGTH_SEPARATOR_CHAR = "|";
		
		//LINK
		RapidConnection link;
		
		//MESSAGES TO WRITE
		ArrayDeque<RawMessage> writeQueue = new ArrayDeque<RawMessage>();
		//MESSAGES READ
		ArrayDeque<RawMessage> readQueue = new ArrayDeque<RawMessage>();
		
		public SenderReciever(RapidConnection linkv)
		{
			this(linkv,DEFAULT_BUFFERS_SIZE,DEFAULT_BUFFERS_SIZE);
		}
		
		public SenderReciever(RapidConnection linkv, int read_buffer_sizev,int write_buffer_sizev)
		{
			constbuff = ByteBuffer.allocate(1);
			clearBuffer();
			lengthbuilder = new StringBuilder(10);
			nextmessagelength=-1;
			havelength=false;

			rawmessage=new RawMessage(1);
			link=linkv;
			
			writePending=false;
			tempWriteBuffer=null;
			
			read_buffer_size=read_buffer_sizev;
			write_buffer_size=write_buffer_sizev;
			readBuffer = ByteBuffer.allocate(read_buffer_size);
			readBuffer.flip();
			writeBuffer = new WriteByteBuffer(write_buffer_size);
		}
		
		private void resetAfterMessageGot()
		{
			clearBuffer();
			
			lengthbuilder = new StringBuilder(10);
			nextmessagelength= -1;
			alreadyhavemessagelength=0;
			havelength=false;
			
			rawmessage=new RawMessage(1);
		}
		
		public void resetFull()
		{
			resetAfterMessageGot();
			
			writeQueue = new ArrayDeque<RawMessage>();
			readQueue = new ArrayDeque<RawMessage>();
			
			writePending=false;
			tempWriteBuffer=null;
			
			readBuffer.clear();
			readBuffer.flip();
			writeBuffer.clear();
		}
		
		//RAW READ AND WRITE
		public void Read(SocketChannel socketchannel)
		{
			int numofread = -1;
			
			if(!havelength)
			{
				try
				{
					if(!readBuffer.hasRemaining())
					{
						readBuffer.clear();
						numofread = socketchannel.read(readBuffer);
						readBuffer.flip();
					}
					else
					{
						numofread = 1;
					}
					clearBuffer();
					if(numofread>0)
					{
						numofread = BufferStuff.put(constbuff, readBuffer);
						constbuff.flip();
						CharBuffer character = null;
						character = decoder.decode(constbuff);
						if(character.toString().equals(LENGTH_SEPARATOR_CHAR))
						{
							nextmessagelength = IntegerStuff.decodeBase100(lengthbuilder.toString());
							varbuff = ByteBuffer.allocate(nextmessagelength);
							havelength = true;
						}
						else
						{
							lengthbuilder.append(character.toString());
						}
					}
					else if(numofread<0)
					{
						link.Disconnect();
					}
				}
				catch(IOException e)
				{
					if(infoANDdebug.getDEBUG())
					{
						e.printStackTrace();
					}
					link.Disconnect();
				}
			}
			else
			{
				try
				{
					if(!readBuffer.hasRemaining())
					{
						readBuffer.clear();
						numofread = socketchannel.read(readBuffer);
						readBuffer.flip();
					}
					else
					{
						numofread = 1;
					}
					if(numofread>0)
					{
						numofread = BufferStuff.put(varbuff, readBuffer);
						
						alreadyhavemessagelength+=numofread;
	
						if(alreadyhavemessagelength==nextmessagelength)
						{
							varbuff.flip();
							
							rawmessage.addByteBuffer(varbuff);
							
							RawMessage messagetemp = rawmessage;
							resetAfterMessageGot();
							readQueue.add(messagetemp);
						}
					}
					else if(numofread<0)
					{
						link.Disconnect();
					}
				}
				catch(IOException e)
				{
					if(infoANDdebug.getDEBUG())
					{
						e.printStackTrace();
					}
					link.Disconnect();
				}
			}
		}
		
		public void write(SocketChannel channel)
		{
			if(!writeBuffer.haveMoreSpace())
			{
				writeBufferPrivate(channel);
			}
			else
			{
				if(!writePending)
				{
					if(!isWriteQueueEmpty())
					{
						writePrivate(channel);
					}
					else
					{
						if(writeBuffer.haveSomething())
						{
							writeBufferPrivate(channel);
						}
					}
				}
				else
				{
					finishWritePrivate(channel);
				}
			}
		}
		
		public void addToWriteQueue(RawMessage rawmessage)
		{
			writeQueue.add(rawmessage);
		}
		
		private void writeBufferPrivate(SocketChannel channel)
		{
			try
			{
				writeBuffer.writeFromThisToSocket(channel);
			}
			catch (Exception e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
				link.Disconnect();
			}
		}
		
		private void writePrivate(SocketChannel channel)
		{
			try
			{
				RawMessage peeked = writeQueue.peek();
				
				int encoded1length = peeked.getSumSize();

				String messagedata = IntegerStuff.encodeBase100(encoded1length)+LENGTH_SEPARATOR_CHAR;
				char[] chararr2 = new char[messagedata.length()];
				messagedata.getChars(0, messagedata.length(), chararr2, 0);
				CharBuffer charbuffdata = CharBuffer.wrap(chararr2);
				ByteBuffer encoded2 = encoder.encode(charbuffdata);

				int encoded2length=encoded2.remaining();
				
				ByteBuffer full = ByteBuffer.allocate(encoded1length+encoded2length);
				
				full.put(encoded2);
				
				for(int a = 0;a<peeked.getNumberOfByteBuffers();a++)
				{
					ByteBuffer buffer = peeked.getByteBuffer(a);
					full.put(buffer);
					buffer.flip();
				}
				
				full.flip();
				
				tempWriteBuffer = full;
				
				writeQueue.remove();
				
				writeBuffer.writeFromBufferToThis(tempWriteBuffer);
				
				if(!tempWriteBuffer.hasRemaining())
				{
					writePending=false;
					tempWriteBuffer=null;
				}
				else
				{
					writePending=true;
				}
			}
			catch (Exception e)
			{
				if(infoANDdebug.getDEBUG())
				{
					e.printStackTrace();
				}
				link.Disconnect();
			}
		}
		
		public void finishWritePrivate(SocketChannel channel)
		{
			int numofwrite = -1;
			
			numofwrite = writeBuffer.writeFromBufferToThis(tempWriteBuffer);
			if(numofwrite>0)
			{
				if(!tempWriteBuffer.hasRemaining())
				{
					writePending=false;
					tempWriteBuffer=null;
				}
				else
				{
					writePending=true;
				}
			}
		}
		
		//SHOULD READ AND SHOULD WRITE
		public boolean shouldCallRead()
		{
			if(readBuffer.hasRemaining())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public boolean shouldCallWrite()
		{
			if(writeBuffer.haveMoreSpace()&&!isWriteQueueEmpty())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public boolean shouldWaitForRead()
		{
			if(!readBuffer.hasRemaining())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public boolean shouldWaitForWrite()
		{
			//IF HAS SOMETHING AND DOESN'T HAVE MORE SPACE OR WriteQueueIsEmpty
			if(!shouldCallWrite()&&writeBuffer.haveSomething())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public boolean isWriteQueueEmpty()
		{
			return writeQueue.isEmpty();
		}
		
		public int getWriteQueueSize()
		{
			return writeQueue.size();
		}
		
		public ArrayDeque<RawMessage> getWriteQueue()
		{
			return writeQueue;
		}
		
		public boolean isReadQueueEmpty()
		{
			return readQueue.isEmpty();
		}
		
		public int getReadQueueSize()
		{
			return readQueue.size();
		}
		
		public ArrayDeque<RawMessage> getReadQueue()
		{
			return readQueue;
		}
		
		//RAW MESSAGE STUFF
		public RawMessage getMessage()
		{
			return readQueue.remove();
		}
		
		//MESSAGE STUFF
		
		public boolean haveMessage()
		{
			if(!isReadQueueEmpty())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		//BUFFER STUFF
		public void clearBuffer()
		{
			constbuff.clear();
		}
		
		//INFO AND DEBUG
		public void setInfoAndDebug(INFOandDEBUG infoANDdebugv)
		{
			infoANDdebug = infoANDdebugv;
		}
		
		private class WriteByteBuffer
		{
			private ByteBuffer buffer = null;
			private int have = 0;
			private int size = 0;
			
			public WriteByteBuffer(int sizev)
			{
				size=sizev;
				buffer = ByteBuffer.allocate(size);
			}
			
			public int writeFromBufferToThis(ByteBuffer source)
			{
				int put = 0;
				
				if(haveMoreSpace())
				{
					
					buffer.position(have);
					buffer.limit(size);
					put = BufferStuff.put(buffer, source);
					have+=put;
				}
				
				return put;
			}
			
			public int writeFromThisToSocket(SocketChannel channel) throws IOException
			{
				int put = 0;
				
				if(haveSomething())
				{
					buffer.position(0);
					buffer.limit(have);
					
					put = channel.write(buffer);
					
					if(put>0)
					{
						buffer.compact();
					}
					
					have-=put;
				}
				
				return put;
			}
			
			public boolean haveSomething()
			{
				return have>0;
			}
			
			public boolean haveMoreSpace()
			{
				return have<size;
			}
			
			public void clear()
			{
				have=0;
			}
		}
	}
}