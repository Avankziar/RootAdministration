package main.java.me.avankziar.roota.spigot.ifh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import main.java.me.avankziar.roota.general.database.MysqlType;
import main.java.me.avankziar.roota.general.object.PlayerLocation;
import main.java.me.avankziar.roota.spigot.RootA;
import me.avankziar.ifh.spigot.interfaces.ProxyOnlinePlayers;

public class ProxyOnlinePlayersProvider implements ProxyOnlinePlayers
{
	public Map<UUID, String> getProxyOnlinePlayers()
	{
		Map<UUID, String> map = new HashMap<>();
		ArrayList<PlayerLocation> l = PlayerLocation.convert(RootA.getPlugin().getMysqlHandler().getFullList(MysqlType.PLAYERLOCATION, "`id` ASC", "`id` > ?", 1));
		for(PlayerLocation pl : l)
		{
			map.put(pl.getPlayerUUID(), pl.getServer());
		}
		return Collections.unmodifiableMap(map);
	}
	
	public boolean isProxyOnline(UUID uuid)
	{
		return RootA.getPlugin().getMysqlHandler().exist(MysqlType.PLAYERLOCATION, "`uuid` = ?", uuid.toString());
	}
	
	public String getServer(UUID uuid)
	{
		PlayerLocation pl = (PlayerLocation) RootA.getPlugin().getMysqlHandler().getData(MysqlType.PLAYERLOCATION, "`uuid` = ?", uuid.toString());
		return pl != null ? pl.getServer() : null;
	}
}
