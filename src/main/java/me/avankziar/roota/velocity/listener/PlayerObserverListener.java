package main.java.me.avankziar.roota.velocity.listener;

import java.util.UUID;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;

import main.java.me.avankziar.roota.general.database.MysqlType;
import main.java.me.avankziar.roota.general.object.PlayerLocation;
import main.java.me.avankziar.roota.velocity.RootA;

public class PlayerObserverListener
{
	private RootA plugin;
	
	public PlayerObserverListener(RootA plugin)
	{
		this.plugin = plugin;
	}
	
	@Subscribe
	public void onPlayerQuit(DisconnectEvent event)
	{
		if(plugin.getMysqlHandler() == null)
		{
			return;
		}
		final UUID uuid = event.getPlayer().getUniqueId();
		plugin.getMysqlHandler().deleteData(MysqlType.PLAYERLOCATION, "player_uuid = ?", uuid.toString());
	}
	
	@Subscribe
	public void onPlayerSwitch(ServerConnectedEvent event)
	{
		if(plugin.getMysqlHandler() == null )
		{
			return;
		}
		PlayerLocation pl = (PlayerLocation) plugin.getMysqlHandler().getData(MysqlType.PLAYERLOCATION, "player_uuid = ?",
				event.getPlayer().getUniqueId().toString());
		if(pl == null)
		{
			plugin.getMysqlHandler().create(MysqlType.PLAYERLOCATION, new PlayerLocation(event.getPlayer().getUsername(), event.getPlayer().getUniqueId(),
					event.getServer().getServerInfo().getName()));
			return;
		}
		pl.setServer(event.getServer().getServerInfo().getName());
		plugin.getMysqlHandler().updateData(MysqlType.PLAYERLOCATION, pl, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
	}
}