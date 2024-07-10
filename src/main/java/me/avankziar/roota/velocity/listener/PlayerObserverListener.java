package main.java.me.avankziar.roota.velocity.listener;

import java.util.UUID;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;

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
	public void onPlayerJoin(PlayerChooseInitialServerEvent event)
	{
		if(plugin.getMysqlHandler() == null)
		{
			return;
		}
		Player pp = event.getPlayer();
		pp.getCurrentServer().ifPresent(y -> plugin.getMysqlHandler().create(MysqlType.PLAYERLOCATION, new PlayerLocation(pp.getUsername(), pp.getUniqueId(),
				y.getServerInfo().getName())));
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
		if(plugin.getMysqlHandler() == null || event.getPreviousServer().isEmpty())
		{
			return;
		}
		PlayerLocation pl = (PlayerLocation) plugin.getMysqlHandler().getData(MysqlType.PLAYERLOCATION, "player_uuid = ?",
				event.getPlayer().getUniqueId().toString());
		if(pl == null)
		{
			return;
		}
		pl.setServer(event.getServer().getServerInfo().getName());
		plugin.getMysqlHandler().updateData(MysqlType.PLAYERLOCATION, pl, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
	}
}