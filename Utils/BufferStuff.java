package Utils;

import java.nio.ByteBuffer;

public class BufferStuff
{
	public static ByteBuffer allocate(int capacity,boolean direct)
	{
		ByteBuffer result = null;
		
		if(!direct)
		{
			result = ByteBuffer.allocate(capacity);
		}
		else
		{
			result = ByteBuffer.allocateDirect(capacity);
		}
		
		return result;
	}
	
	public static ByteBuffer getCopy(ByteBuffer buffertocopy,int start,int end,boolean direct)
	{
		ByteBuffer copy;
		
		int tempstart = buffertocopy.position();
		int tempend = buffertocopy.limit();
		int length = tempend-tempstart;
		
		if(!direct)
		{
			copy = ByteBuffer.allocate(length);
		}
		else
		{
			copy = ByteBuffer.allocateDirect(length);
		}
		
		buffertocopy.position(start);
		buffertocopy.limit(end);
		
		copy.put(buffertocopy);
		copy.flip();
		
		buffertocopy.position(tempstart);
		buffertocopy.limit(tempend);
		
		return copy;
	}
	
	public static ByteBuffer getCopyPosAndLim(ByteBuffer buffertocopy,boolean direct)
	{
		ByteBuffer copy;
		
		int length = buffertocopy.remaining();
		
		if(!direct)
		{
			copy = ByteBuffer.allocate(length);
		}
		else
		{
			copy = ByteBuffer.allocateDirect(length);
		}
		
		copy.put(buffertocopy);
		buffertocopy.flip();
		copy.flip();
		
		return copy;
	}
	
	public static ByteBuffer appendBuffersPosAndLim(ByteBuffer bufferone, ByteBuffer buffertwo,boolean direct)
	{
		ByteBuffer result = null;
		
		int totalsize = bufferone.remaining()+buffertwo.remaining();
		
		if(!direct)
		{
			result = ByteBuffer.allocate(totalsize);
		}
		else
		{
			result = ByteBuffer.allocateDirect(totalsize);
		}
		
		result.put(bufferone);
		result.put(buffertwo);
		bufferone.flip();
		buffertwo.flip();
		result.flip();
		
		
		return result;
	}
	
	public static ByteBuffer appendBuffers(ByteBuffer bufferone,int onestart, int oneend, ByteBuffer buffertwo,int twostart, int twoend,boolean direct)
	{
		ByteBuffer result = null;
		
		int lengthoffirst = oneend-onestart;
		int lengthofsecond = twoend-twostart;
		int totalsize = lengthoffirst+lengthofsecond;
		
		int tempstartone = bufferone.position();
		int tempendone = bufferone.limit();
		

		int tempstarttwo = buffertwo.position();
		int tempendtwo = buffertwo.limit();
		
		if(!direct)
		{
			result = ByteBuffer.allocate(totalsize);
		}
		else
		{
			result = ByteBuffer.allocateDirect(totalsize);
		}
		
		bufferone.position(onestart);
		bufferone.limit(oneend);
		
		buffertwo.position(twostart);
		buffertwo.limit(twoend);
		
		result.put(bufferone);
		result.put(buffertwo);
		result.flip();
		
		bufferone.position(tempstartone);
		bufferone.limit(tempendone);
		
		buffertwo.position(tempstarttwo);
		buffertwo.limit(tempendtwo);
		
		return result;
	}
	
	public static int put(ByteBuffer destination,ByteBuffer source)
	{
		int bytesput = Math.min(destination.remaining(), source.remaining());
		
		if(destination.remaining()<source.remaining())
		{
			int limit = source.limit();
			source.limit(source.position()+destination.remaining());
			destination.put(source);
			source.limit(limit);
		}
		else
		{
			destination.put(source);
		}
		
		return bytesput;
	}
	
	public static byte[] getCopyInByteArrayPosAndLim(ByteBuffer buffer)
	{
		byte[] array = new byte[buffer.remaining()];
		
		buffer.get(array);
		buffer.flip();
		
		return array;
	}
	
	public static byte[] getCopyInByteArray(ByteBuffer buffer,int start,int end)
	{
		byte[] array = new byte[end-start];
		
		buffer.get(array, start, end-start);
		buffer.flip();
		
		return array;
	}
	
	public static ByteBuffer wrap(byte[] array,int start,int end)
	{
		return ByteBuffer.wrap(array, start, end);
	}
	
	public static ByteBuffer wrap(byte[] array)
	{
		return ByteBuffer.wrap(array);
	}
	
	public static String getInfo(ByteBuffer buffer)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(buffer.getClass().getName());
        sb.append("[pos=");
        sb.append(buffer.position());
        sb.append(" lim=");
        sb.append(buffer.limit());
        sb.append(" cap=");
        sb.append(buffer.capacity());
        sb.append(" remaining=");
        sb.append(buffer.remaining());
        sb.append("]");
        return sb.toString();
	}
	
	public static void printInfo(ByteBuffer buffer)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(buffer.getClass().getName());
        sb.append("[pos=");
        sb.append(buffer.position());
        sb.append(" lim=");
        sb.append(buffer.limit());
        sb.append(" cap=");
        sb.append(buffer.capacity());
        sb.append(" remaining=");
        sb.append(buffer.remaining());
        sb.append("]");
        System.out.println(sb.toString());
	}
}
