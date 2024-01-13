package main.java.me.avankziar.roota.spigot;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.ifh.spigot.comparison.ItemStackComparison;
import main.java.me.avankziar.ifh.spigot.interfaces.BungeeOnlinePlayers;
import main.java.me.avankziar.roota.general.YamlManager;
import main.java.me.avankziar.roota.spigot.database.MysqlHandler;
import main.java.me.avankziar.roota.spigot.database.MysqlSetup;
import main.java.me.avankziar.roota.spigot.database.YamlHandler;
import main.java.me.avankziar.roota.spigot.ifh.AdministrationProvider;
import main.java.me.avankziar.roota.spigot.ifh.BungeeOnlinePlayersProvider;
import main.java.me.avankziar.roota.spigot.ifh.EnumTranslationProvider;
import main.java.me.avankziar.roota.spigot.ifh.InteractionBlockerProvider;
import main.java.me.avankziar.roota.spigot.ifh.ItemStackComparisonProvider;
import main.java.me.avankziar.roota.spigot.metric.Metrics;

public class RootA extends JavaPlugin
{
	public static Logger log;
	private static RootA plugin;
	public String pluginName = "RootAdministration";
	private YamlHandler yamlHandler;
	private YamlManager yamlManager;
	private MysqlHandler mysqlHandler;
	private MysqlSetup mysqlSetup;
	
	private AdministrationProvider administrationProvider;
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=RootA
		log.info(" ██████╗  ██████╗  ██████╗ ████████╗ █████╗  | API-Version: "+plugin.getDescription().getAPIVersion());
		log.info(" ██╔══██╗██╔═══██╗██╔═══██╗╚══██╔══╝██╔══██╗ | Author: "+plugin.getDescription().getAuthors().toString());
		log.info(" ██████╔╝██║   ██║██║   ██║   ██║   ███████║ | Plugin Website: "+plugin.getDescription().getWebsite());
		log.info(" ██╔══██╗██║   ██║██║   ██║   ██║   ██╔══██║ | Depend Plugins: "+plugin.getDescription().getDepend().toString());
		log.info(" ██║  ██║╚██████╔╝╚██████╔╝   ██║   ██║  ██║ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepend().toString());
		log.info(" ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝   ╚═╝  ╚═╝ | LoadBefore: "+plugin.getDescription().getLoadBefore().toString());
		
		yamlHandler = new YamlHandler(this);
		
		String path = plugin.getYamlHandler().getConfig().getString("IFHAdministrationPath");
		boolean adm = plugin.getAdministration() != null 
				&& plugin.getYamlHandler().getConfig().getBoolean("useIFHAdministration")
				&& plugin.getAdministration().isMysqlPathActive(path);
		if(adm || yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(plugin);
			mysqlSetup = new MysqlSetup(plugin, adm, path);
		}
		
		setupIFHProviding();
		setupBstats();
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		log.info(pluginName + " is disabled!");
	}

	public static RootA getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}

	public void setYamlManager(YamlManager yamlManager)
	{
		this.yamlManager = yamlManager;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public MysqlSetup getMysqlSetup()
	{
		return mysqlSetup;
	}
	
	private void setupIFHProviding()
	{      
        if (plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
		{
        	administrationProvider = new AdministrationProvider(plugin);
        	plugin.getServer().getServicesManager().register(
        			main.java.me.avankziar.ifh.spigot.administration.Administration.class,
        			administrationProvider,
             		this,
             		ServicePriority.Normal);
            log.info(pluginName + " detected InterfaceHub >>> Administration.class is provided!");
            
            EnumTranslationProvider.init(plugin);
            EnumTranslationProvider et = new EnumTranslationProvider();
            plugin.getServer().getServicesManager().register(
        			main.java.me.avankziar.ifh.spigot.interfaces.EnumTranslation.class,
        			et,
             		this,
             		ServicePriority.Normal);
            log.info(pluginName + " detected InterfaceHub >>> EnumTranslation.class is provided!");
            
            ItemStackComparison itemStackComparisonProvider = new ItemStackComparisonProvider();
        	plugin.getServer().getServicesManager().register(
        			main.java.me.avankziar.ifh.spigot.comparison.ItemStackComparison.class,
        			itemStackComparisonProvider,
            this,
            ServicePriority.Normal);
        	log.info(pluginName + " detected InterfaceHub >>> ItemStackComparison.class is provided!");
        	
        	InteractionBlockerProvider ib = new InteractionBlockerProvider();
            plugin.getServer().getServicesManager().register(
        			main.java.me.avankziar.ifh.spigot.interactionblocker.InteractionBlocker.class,
        			ib,
             		this,
             		ServicePriority.Normal);
            log.info(pluginName + " detected InterfaceHub >>> InteractionBlocker.class is provided!");
            
        	try
			{
				if(mysqlSetup != null && mysqlSetup.getConnection() != null)
				{
					BungeeOnlinePlayers bungeeOnlinePlayers = new BungeeOnlinePlayersProvider();
		        	plugin.getServer().getServicesManager().register(
		        			main.java.me.avankziar.ifh.spigot.interfaces.BungeeOnlinePlayers.class,
		        			bungeeOnlinePlayers,
		            this,
		            ServicePriority.Normal);
		        	log.info(pluginName + " detected InterfaceHub >>> BungeeOnlinePlayers.class is provided!");
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
        }
	}
	
	public AdministrationProvider getAdministration()
	{
		return administrationProvider;
	}
	
	public void setupBstats()
	{
		int pluginId = 15925;
        new Metrics(this, pluginId);
	}
}