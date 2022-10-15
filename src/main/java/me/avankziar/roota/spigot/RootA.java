package main.java.me.avankziar.roota.spigot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.roota.general.YamlManager;
import main.java.me.avankziar.roota.spigot.database.YamlHandler;
import main.java.me.avankziar.roota.spigot.ifh.AdministrationProvider;
import main.java.me.avankziar.roota.spigot.ifh.EnumTranslationProvider;
import main.java.me.avankziar.roota.spigot.metric.Metrics;

public class RootA extends JavaPlugin
{
	public static Logger log;
	private static RootA plugin;
	public String pluginName = "RootAdministration";
	private YamlHandler yamlHandler;
	private YamlManager yamlManager;
	
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
	private void setupIFHProviding()
	{      
        if (plugin.getServer().getPluginManager().isPluginEnabled("InterfaceHub")) 
		{
        	AdministrationProvider a = new AdministrationProvider(plugin);
        	plugin.getServer().getServicesManager().register(
        			main.java.me.avankziar.ifh.spigot.administration.Administration.class,
             		a,
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
        }
	}
	
	public void setupBstats()
	{
		int pluginId = 15925;
        new Metrics(this, pluginId);
	}
}