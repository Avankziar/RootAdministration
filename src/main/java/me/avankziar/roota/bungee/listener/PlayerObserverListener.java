package main.java.me.avankziar.roota.bungee.listener;

import java.util.UUID;

import main.java.me.avankziar.roota.bungee.RootA;
import main.java.me.avankziar.roota.general.database.MysqlType;
import main.java.me.avankziar.roota.general.object.PlayerLocation;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerObserverListener implements Listener
{
	private RootA plugin;
	
	public PlayerObserverListener(RootA plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		if(plugin.getMysqlHandler() == null)
		{
			return;
		}
		ProxiedPlayer pp = event.getPlayer();
		plugin.getMysqlHandler().create(MysqlType.PLAYERLOCATION, new PlayerLocation(pp.getName(), pp.getUniqueId(), pp.getServer().getInfo().getName()));
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		if(plugin.getMysqlHandler() == null)
		{
			return;
		}
		final UUID uuid = event.getPlayer().getUniqueId();
		plugin.getMysqlHandler().deleteData(MysqlType.PLAYERLOCATION, "player_uuid = ?", uuid.toString());
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onServerSwitch(ServerSwitchEvent event)
	{
		if(plugin.getMysqlHandler() == null)
		{
			return;
		}
		PlayerLocation pl = (PlayerLocation) plugin.getMysqlHandler().getData(MysqlType.PLAYERLOCATION, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
		if(pl == null)
		{
			ProxiedPlayer pp = event.getPlayer();
			plugin.getMysqlHandler().create(MysqlType.PLAYERLOCATION, new PlayerLocation(pp.getName(), pp.getUniqueId(), pp.getServer().getInfo().getName()));
			return;
		}
		pl.setServer(event.getFrom().getName());
		plugin.getMysqlHandler().updateData(MysqlType.PLAYERLOCATION, pl, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
	}
}