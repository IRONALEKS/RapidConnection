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

public class RapidConnection
{
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
	 * The INFO and DEBUG of this connection will be linked to the one of the parameter
	 * <p>
	 * Read and write buffers will be set to the default size of 65536 bytes
	 * @param  infoANDdebugv  the info and debug to which this connectionV10 will be linked to
	 */
	public RapidConnection(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever=new SenderReciever(this);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * This ConnectionV10 INFO and DEBUG will be set to true
	 * <p>
	 * Read and Write buffers are of the specified sizes
	 * @param  read_buffer_size  the read buffer size of this ConnectionV10 not of the underlying socket
	 * @param  write_buffer_size  the write buffer size of this ConnectionV10 not of the underlying socket
	 */
	public RapidConnection(int read_buffer_size,int write_buffer_size)
	{
		infoANDdebug = new INFOandDEBUG(true, true);
		senderreciever=new SenderReciever(this,read_buffer_size,write_buffer_size);
		callback = new RapidConnectionEvent(){};
	}
	
	/**
	 * The infoANDdebugv of this connection will be the one of the parameter
	 * <p>
	 * Read and Write buffers are of the specified sizes
	 * @param  infoANDdebugv  the info and debug to which this connectionV10 will be linked to
	 * @param  read_buffer_size  the read buffer size of this ConnectionV10 not of the underlying socket
	 * @param  write_buffer_size  the write buffer size of this ConnectionV10 not of the underlying socket
	 */
	public RapidConnection(INFOandDEBUG infoANDdebugv,int read_buffer_size,int write_buffer_size)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever=new SenderReciever(this,read_buffer_size,write_buffer_size);
		callback = new RapidConnectionEvent(){};
	}
	
	
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
	
	public void addToWriteQueue(RawMessage rawmessage)
	{
		senderreciever.addToWriteQueue(rawmessage);
	}
	
	public void Read()
	{
		if(connected)
		{
			senderreciever.Read(socketchannel);
		}
	}
	
	public void Write()
	{
		if(connected)
		{
			senderreciever.write(socketchannel);
		}
	}
	
	public boolean shouldCallRead()
	{
		if(connected)
		{
			return senderreciever.shouldCallRead();
		}
		
		return false;
	}
	
	public boolean shouldCallWrite()
	{
		if(connected)
		{
			return senderreciever.shouldCallWrite();
		}
		
		return false;
	}
	
	public boolean shouldWaitForRead()
	{
		if(connected)
		{
			return senderreciever.shouldWaitForRead();
		}
		
		return false;
	}
	
	public boolean shouldWaitForWrite()
	{
		if(connected)
		{
			return senderreciever.shouldWaitForWrite();
		}
		
		return false;
	}
	
	
	public int getWriteQueueSize()
	{
		return senderreciever.getWriteQueueSize();
	}
	
	public ArrayDeque<RawMessage> getWriteQueue()
	{
		return senderreciever.getWriteQueue();
	}
	
	public int getReadQueueSize()
	{
		return senderreciever.getReadQueueSize();
	}
	
	public ArrayDeque<RawMessage> getReadQueue()
	{
		return senderreciever.getReadQueue();
	}
	
	//MESSAGE STUFF
	public RawMessage getMessage()
	{
		return senderreciever.getMessage();
	}
	
	public boolean haveMessage()
	{
		return senderreciever.haveMessage();
	}

	
	
	//CONNECTION STATUS
	public boolean isConnected()
	{
		return connected;
	}
	
	public boolean isConnectionPending()
	{
		return connectionpending;
	}
	
	//BLOCKING STATUS
	public boolean isBlocking()
	{
		return socketchannel.isBlocking();
	}
	
	//CALLBACK
	public void setCallback(RapidConnectionEvent cb)
	{
		callback = cb;
	}
	
	//INFO AND DEBUG
	public void setInfoAndDebug(INFOandDEBUG infoANDdebugv)
	{
		infoANDdebug = infoANDdebugv;
		senderreciever.setInfoAndDebug(infoANDdebug);
	}
	
	public INFOandDEBUG getInfoAndDebug()
	{
		return infoANDdebug;
	}
	
	//SET SOCKET OPTIONS
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
							
							rawmessage.addMessage(varbuff);
							
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
				
				for(int a = 0;a<peeked.getNumberOfMessages();a++)
				{
					ByteBuffer buffer = peeked.getMessage(a);
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