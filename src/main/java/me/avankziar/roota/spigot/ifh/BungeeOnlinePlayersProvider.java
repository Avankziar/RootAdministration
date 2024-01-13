package main.java.me.avankziar.roota.spigot.ifh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import main.java.me.avankziar.ifh.spigot.interfaces.BungeeOnlinePlayers;
import main.java.me.avankziar.roota.spigot.RootA;
import main.java.me.avankziar.roota.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.roota.spigot.object.PlayerLocation;

public class BungeeOnlinePlayersProvider implements BungeeOnlinePlayers
{
	public Map<UUID, String> getBungeeOnlinePlayers()
	{
		Map<UUID, String> map = new HashMap<>();
		ArrayList<PlayerLocation> l = PlayerLocation.convert(RootA.getPlugin().getMysqlHandler().getFullList(Type.PLAYERLOCATION, "`id` ASC", "`id` > ?", 1));
		for(PlayerLocation pl : l)
		{
			map.put(pl.getPlayerUUID(), pl.getServer());
		}
		return Collections.unmodifiableMap(map);
	}
	
	public boolean isBungeeOnline(UUID uuid)
	{
		return RootA.getPlugin().getMysqlHandler().exist(Type.PLAYERLOCATION, "`uuid` = ?", uuid.toString());
	}
	
	public String getServer(UUID uuid)
	{
		PlayerLocation pl = (PlayerLocation) RootA.getPlugin().getMysqlHandler().getData(Type.PLAYERLOCATION, "`uuid` = ?", uuid.toString());
		return pl != null ? pl.getServer() : null;
	}
}
