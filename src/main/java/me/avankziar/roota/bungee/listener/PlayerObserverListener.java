package main.java.me.avankziar.roota.bungee.listener;

import java.util.UUID;

import main.java.me.avankziar.roota.bungee.RootA;
import main.java.me.avankziar.roota.bungee.database.MysqlHandler.Type;
import main.java.me.avankziar.roota.bungee.object.PlayerLocation;
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
		ProxiedPlayer pp = event.getPlayer();
		plugin.getMysqlHandler().create(Type.PLAYERLOCATION, new PlayerLocation(pp.getName(), pp.getUniqueId(), pp.getServer().getInfo().getName()));
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		final UUID uuid = event.getPlayer().getUniqueId();
		plugin.getMysqlHandler().deleteData(Type.PLAYERLOCATION, "player_uuid = ?", uuid.toString());
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onServerSwitch(ServerSwitchEvent event)
	{
		PlayerLocation pl = (PlayerLocation) plugin.getMysqlHandler().getData(Type.PLAYERLOCATION, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
		pl.setServer(event.getFrom().getName());
		plugin.getMysqlHandler().updateData(Type.PLAYERLOCATION, pl, "player_uuid = ?", event.getPlayer().getUniqueId().toString());
	}
}