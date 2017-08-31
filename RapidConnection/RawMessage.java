package RapidConnection;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Utils.BufferStuff;

public class RawMessage
{
	private ArrayList<ByteBuffer> messages;
	private int capacity = 0;
	
	public RawMessage(int capacityv)
	{
		capacity = capacityv;
		messages = new ArrayList<ByteBuffer>(capacity);
	}
	
	public void resetMessage(int messageindex)
	{
		messages.remove(messageindex);
	}
	
	public ByteBuffer getMessage(int messageindex)
	{
		return messages.get(messageindex);
	}
	
	public void setMessage(ByteBuffer string, int messageindex)
	{
		messages.set(messageindex, string);
	}
	
	public void addMessage(ByteBuffer string)
	{
		messages.add(string);
	}
	
	public RawMessage getFullCopy()
	{
		RawMessage messageresult = new RawMessage(capacity);
		
		for(int a = 0, size = messages.size();a<size;a++)
		{
			messageresult.addMessage(BufferStuff.getCopyPosAndLim(messages.get(a),false));
		}
		
		return messageresult;
	}
	
	public ByteBuffer getFullCopyInBuffer()
	{
		ByteBuffer buffer = BufferStuff.allocate(getSumSize(), false);
		
		for(int a = 0, size = messages.size();a<size;a++)
		{
			ByteBuffer temp = messages.get(a);
			
			buffer.put(temp);
			
			temp.flip();
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public RawMessage getCopy(int messageindex)
	{
		RawMessage messageresult = new RawMessage(1);
		
		messageresult.addMessage(BufferStuff.getCopyPosAndLim(messages.get(messageindex),false));
		
		return messageresult;
	}
	
	public ByteBuffer getCopyInBuffer(int messageindex)
	{
		ByteBuffer temp = messages.get(messageindex);
		
		ByteBuffer buffer = BufferStuff.getCopyPosAndLim(temp, false);
		
		return buffer;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public int getNumberOfMessages()
	{
		return messages.size();
	}
	
	public int getSumSize()
	{
		int sumsize = 0;
		
		for(int a = 0, size = messages.size();a<size;a++)
		{
			sumsize += messages.get(a).remaining();
		}
		
		return sumsize;
	}
	
	public String toUTF8String()
	{
		ByteBuffer buffer = getFullCopyInBuffer();
		
		byte copy[] = BufferStuff.getCopyInByteArrayPosAndLim(buffer);
		
		return new String(copy, StandardCharsets.UTF_8);
	}
	
	public static RawMessage fromUTF8String(String str)
	{
		byte bytes[] = str.getBytes(StandardCharsets.UTF_8);
		
		ByteBuffer temp = BufferStuff.wrap(bytes);
		
		RawMessage result  = RawMessage.createWithByteBuffer(temp,1);
		
		return result;
	}
	
	public static RawMessage createWithByteBuffer(ByteBuffer buffer, int capacity)
	{
		RawMessage temp = new RawMessage(capacity);
		temp.addMessage(buffer);
		return temp;
	}
}