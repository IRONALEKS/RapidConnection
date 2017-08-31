package Utils;

public class INFOandDEBUG
{
	private boolean INFO = false;
	private boolean DEBUG = false;
	
	public INFOandDEBUG()
	{
		
	}
	
	public INFOandDEBUG(boolean INFOv, boolean DEBUGv)
	{
		INFO = INFOv;
		DEBUG = DEBUGv;
	}
	
	public void set(boolean INFOv, boolean DEBUGv)
	{
		INFO = INFOv;
		DEBUG = DEBUGv;
	}
	
	public void setINFO(boolean INFOv)
	{
		INFO = INFOv;
	}
	
	public void setDEBUG(boolean DEBUGv)
	{
		DEBUG = DEBUGv;
	}
	
	public boolean getINFO()
	{
		return INFO;
	}
	
	public boolean getDEBUG()
	{
		return DEBUG;
	}
}
