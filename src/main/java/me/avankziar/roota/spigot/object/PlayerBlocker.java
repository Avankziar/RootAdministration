package main.java.me.avankziar.roota.spigot.object;

public class PlayerBlocker
{
	private long blockMilliSeconds;
	private long startTime;
	
	public PlayerBlocker(long startTime, long blockMilliSeconds)
	{
		setStartTime(startTime);
		setBlockMilliSeconds(blockMilliSeconds);
	}

	public long getBlockMilliSeconds()
	{
		return blockMilliSeconds;
	}

	public void setBlockMilliSeconds(long blockMilliSeconds)
	{
		this.blockMilliSeconds = blockMilliSeconds;
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}

}
