package main.java.me.avankziar.roota.spigot.ifh;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.me.avankziar.ifh.spigot.interactionblocker.InteractionBlocker;
import main.java.me.avankziar.ifh.spigot.interactionblocker.InteractionBlockerType;
import main.java.me.avankziar.roota.spigot.RootA;
import main.java.me.avankziar.roota.spigot.object.PlayerBlocker;

public class InteractionBlockerProvider implements InteractionBlocker
{
	public static LinkedHashMap<InteractionBlockerType, LinkedHashMap<UUID, PlayerBlocker>> interactionBlocker = new LinkedHashMap<>();
	
	static
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				long now = System.currentTimeMillis();
				LinkedHashMap<InteractionBlockerType, UUID> toDeblock = new LinkedHashMap<>();
				for(Entry<InteractionBlockerType, LinkedHashMap<UUID, PlayerBlocker>> e : interactionBlocker.entrySet())
				{
					LinkedHashMap<UUID, PlayerBlocker> lhm = e.getValue();
					for(Entry<UUID, PlayerBlocker> ee : lhm.entrySet())
					{
						if(now > ee.getValue().getStartTime()+ee.getValue().getBlockMilliSeconds())
						{
							toDeblock.put(e.getKey(), ee.getKey());
						}
					}
				}
				for(Entry<InteractionBlockerType, UUID> e : toDeblock.entrySet())
				{
					LinkedHashMap<UUID, PlayerBlocker> lhm = new LinkedHashMap<>();
					if(interactionBlocker.containsKey(e.getKey()))
					{
						lhm = interactionBlocker.get(e.getKey());
					}
					lhm.remove(e.getValue());
					interactionBlocker.put(e.getKey(), lhm);
				}
			}
		}.runTaskTimerAsynchronously(RootA.getPlugin(), 0L, 5L);
	}
	
	public void blockInteraction(Player player, long ticks)
	{
		for(InteractionBlockerType ibt : InteractionBlockerType.values())
		{
			LinkedHashMap<UUID, PlayerBlocker> lhm = new LinkedHashMap<>();
			if(interactionBlocker.containsKey(ibt))
			{
				lhm = interactionBlocker.get(ibt);
			}
			PlayerBlocker pb = lhm.get(player.getUniqueId());
			if(pb != null)
			{
				pb.setBlockMilliSeconds(pb.getBlockMilliSeconds()+20L*ticks);
			} else
			{
				pb = new PlayerBlocker(System.currentTimeMillis(), 20L*ticks);
			}
			lhm.put(player.getUniqueId(), pb);
			interactionBlocker.put(ibt, lhm);
		}
	}
	
	public void blockInteraction(Player player, long ticks, InteractionBlockerType interactionBlockerTyp)
	{
		LinkedHashMap<UUID, PlayerBlocker> lhm = new LinkedHashMap<>();
		if(interactionBlocker.containsKey(interactionBlockerTyp))
		{
			lhm = interactionBlocker.get(interactionBlockerTyp);
		}
		PlayerBlocker pb = lhm.get(player.getUniqueId());
		if(pb != null)
		{
			pb.setBlockMilliSeconds(pb.getBlockMilliSeconds()+20L*ticks);
		} else
		{
			pb = new PlayerBlocker(System.currentTimeMillis(), 20L*ticks);
		}
		lhm.put(player.getUniqueId(), pb);
		interactionBlocker.put(interactionBlockerTyp, lhm);
	}
	
	public void deblockInteraction(Player player)
	{
		for(InteractionBlockerType ibt : InteractionBlockerType.values())
		{
			LinkedHashMap<UUID, PlayerBlocker> lhm = new LinkedHashMap<>();
			if(interactionBlocker.containsKey(ibt))
			{
				lhm = interactionBlocker.get(ibt);
			}
			lhm.remove(player.getUniqueId());
			interactionBlocker.put(ibt, lhm);
		}
	}
	
	public void deblockInteraction(Player player, InteractionBlockerType interactionBlockerTyp)
	{
		LinkedHashMap<UUID, PlayerBlocker> lhm = new LinkedHashMap<>();
		if(interactionBlocker.containsKey(interactionBlockerTyp))
		{
			lhm = interactionBlocker.get(interactionBlockerTyp);
		}
		lhm.remove(player.getUniqueId());
		interactionBlocker.put(interactionBlockerTyp, lhm);
	}
}