package RapidConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import Utils.BufferStuff;

/**
 * This is the {@link RawMessage} class that can be send through a {@link RapidConnection}
 * <p>
 * It is very easy to use, and very intuitive
 * <p>
 * Hope it helps you in your work :D
 * 
 * @author IRONALEKS
 */
public class RawMessage
{
	private ArrayList<ByteBuffer> buffers;

	/**
	 * Creates a new empty {@link RawMessage}
	 * @param initialCapacity The initial capacity of the {@link ArrayList} of this message that will contain one or more {@link ByteBuffer}
	 */
	public RawMessage(int initialCapacity)
	{
		buffers = new ArrayList<ByteBuffer>(initialCapacity);
	}

	/**
	 * Removes the {@link ByteBuffer} at the given index
	 * @param bufferindex The index of the buffer in this message
	 */
	public void removeByteBuffer(int bufferindex)
	{
		buffers.remove(bufferindex);
	}

	/**
	 * Removes all {@link ByteBuffer} from the message=
	 */
	public void resetMessage()
	{
		while(!buffers.isEmpty())
		{
			buffers.remove(buffers.size()-1);
		}
	}

	/**
	 * Returns the {@link ByteBuffer} at the given index
	 * @param bufferindex The index of the buffer in this message
	 * @return The {@link ByteBuffer} at the given index
	 */
	public ByteBuffer getByteBuffer(int bufferindex)
	{
		return buffers.get(bufferindex);
	}


	/**
	 * Sets the {@link ByteBuffer} at the given index
	 * @param string The {@link ByteBuffer} to be set at the bufferindex
	 * @param bufferindex The index of the buffer in this message to set
	 */
	public void setByteBuffer(ByteBuffer string, int bufferindex)
	{
		buffers.set(bufferindex, string);
	}
	
	/**
	 * Adds the {@link ByteBuffer} to the message
	 * @param string The {@link ByteBuffer} to be added to the message
	 */
	public void addByteBuffer(ByteBuffer string)
	{
		buffers.add(string);
	}
	
	/**
	 * Returns a copy of this {@link RawMessage}
	 * @return A copy of the current {@link RawMessage}
	 */
	public RawMessage getFullCopy()
	{
		RawMessage messageresult = new RawMessage(buffers.size());
		
		for(int a = 0, size = buffers.size();a<size;a++)
		{
			messageresult.addByteBuffer(BufferStuff.getCopyPosAndLim(buffers.get(a),false));
		}
		
		return messageresult;
	}
	
	/**
	 * Returns a copy of this {@link RawMessage} in one new {@link ByteBuffer}
	 * @return The copy of this {@link RawMessage} in one new {@link ByteBuffer}
	 */
	public ByteBuffer getFullCopyInBuffer()
	{
		ByteBuffer buffer = BufferStuff.allocate(getSumSize(), false);
		
		for(int a = 0, size = buffers.size();a<size;a++)
		{
			ByteBuffer temp = buffers.get(a);
			
			buffer.put(temp);
			
			temp.flip();
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * Returns a copy of a {@link ByteBuffer} in this {@link RawMessage} at the given index in a new {@link RawMessage}
	 * @param bufferindex The index of the buffer in this message
	 * @return A copy of a {@link ByteBuffer} in this {@link RawMessage} at the given index in a new {@link RawMessage}
	 */
	public RawMessage getCopy(int bufferindex)
	{
		RawMessage messageresult = new RawMessage(1);
		
		messageresult.addByteBuffer(BufferStuff.getCopyPosAndLim(buffers.get(bufferindex),false));
		
		return messageresult;
	}

	/**
	 * Returns a copy of a {@link ByteBuffer} in this {@link RawMessage} at the given index in a new {@link ByteBuffer}
	 * @param bufferindex The index of the buffer in this message
	 * @return A copy of a {@link ByteBuffer} in this {@link RawMessage} at the given index in a new {@link ByteBuffer}
	 */
	public ByteBuffer getCopyInBuffer(int bufferindex)
	{
		ByteBuffer temp = buffers.get(bufferindex);
		
		ByteBuffer buffer = BufferStuff.getCopyPosAndLim(temp, false);
		
		return buffer;
	}

	/**
	 * Returns the number of {@link ByteBuffer} in this {@link RawMessage}
	 * @return Returns an {@code int} representing the number of {@link ByteBuffer} in this {@link RawMessage}
	 */
	public int getNumberOfByteBuffers()
	{
		return buffers.size();
	}

	/**
	 * Returns the total number of bytes in all the {@link ByteBuffer} in this {@link RawMessage}
	 * @return The total number of bytes in all the {@link ByteBuffer} in this {@link RawMessage}
	 */
	public int getSumSize()
	{
		int sumsize = 0;
		
		for(int a = 0, size = buffers.size();a<size;a++)
		{
			sumsize += buffers.get(a).remaining();
		}
		
		return sumsize;
	}

	/**
	 * Returns a new {@link String} of the full message decoded in UTF-8
	 * @return The new {@link String} of the full message decoded in UTF-8
	 */
	public String toUTF8String()
	{
		ByteBuffer buffer = getFullCopyInBuffer();
		
		byte copy[] = BufferStuff.getCopyInByteArrayPosAndLim(buffer);
		
		return new String(copy, StandardCharsets.UTF_8);
	}

	/**
	 * Returns a new {@link RawMessage} that contains the {@link String} encoded in UTF-8
	 * @param str The {@link String} to be encoded in this {@link RawMessage}
	 * @return The new {@link RawMessage} that contains the {@link String} encoded in UTF-8
	 */
	public static RawMessage fromUTF8String(String str)
	{
		byte bytes[] = str.getBytes(StandardCharsets.UTF_8);
		
		ByteBuffer temp = BufferStuff.wrap(bytes);
		
		RawMessage result  = RawMessage.createWithByteBuffer(temp,1);
		
		return result;
	}

	/**
	 * Returns a new {@link RawMessage} that contains the {@link ByteBuffer}
	 * @param buffer The {@link ByteBuffer} to be contained in the new {@link RawMessage}
	 * @param capacity The initial capacity of the new {@link RawMessage}
	 * @return The new {@link RawMessage} that contains the {@link ByteBuffer}
	 */
	public static RawMessage createWithByteBuffer(ByteBuffer buffer, int initialCapacity)
	{
		RawMessage temp = new RawMessage(initialCapacity);
		temp.addByteBuffer(buffer);
		return temp;
	}
}