package main.java.me.avankziar.roota.bungee;

import java.util.logging.Logger;

import main.java.me.avankziar.ifh.bungee.InterfaceHub;
import main.java.me.avankziar.ifh.bungee.plugin.ServicePriority;
import main.java.me.avankziar.roota.bungee.database.YamlHandler;
import main.java.me.avankziar.roota.bungee.ifh.AdministrationProvider;
import main.java.me.avankziar.roota.bungee.metric.Metrics;
import main.java.me.avankziar.roota.general.YamlManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class RootA extends Plugin
{
	public static RootA plugin;
	public static Logger log;
	public static String pluginName = "RootAdministration";
	private static YamlHandler yamlHandler;
	private static YamlManager yamlManager;
	
	public void onEnable() 
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=SCC
		log.info(" ██████╗  ██████╗  ██████╗ ████████╗ █████╗  | Version: "+plugin.getDescription().getVersion());
		log.info(" ██╔══██╗██╔═══██╗██╔═══██╗╚══██╔══╝██╔══██╗ | Author: "+plugin.getDescription().getAuthor());
		log.info(" ██████╔╝██║   ██║██║   ██║   ██║   ███████║ | Plugin Website: ");
		log.info(" ██╔══██╗██║   ██║██║   ██║   ██║   ██╔══██║ | Depend Plugins: "+plugin.getDescription().getDepends().toString());
		log.info(" ██║  ██║╚██████╔╝╚██████╔╝   ██║   ██║  ██║ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepends().toString());
		log.info(" ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝   ╚═╝  ╚═╝ | Have Fun^^");
		
		yamlHandler = new YamlHandler(plugin);
		
		setupBstats();
		setupIFHProvider();
	}
	
	public void onDisable()
	{
		getProxy().getScheduler().cancel(plugin);		
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
		RootA.yamlManager = yamlManager;
	}
	
	private void setupIFHProvider()
	{
		Plugin ifhp = BungeeCord.getInstance().getPluginManager().getPlugin("InterfaceHub");
        if (ifhp == null) 
        {
            return;
        }
        main.java.me.avankziar.ifh.bungee.InterfaceHub ifh = (InterfaceHub) ifhp;
        try
        {
    		AdministrationProvider cp = new AdministrationProvider(plugin);
            ifh.getServicesManager().register(
             		main.java.me.avankziar.ifh.bungee.administration.Administration.class,
             		cp, plugin, ServicePriority.Normal);
            log.info(pluginName + " detected InterfaceHub >>> Administration.class is provided!");
    		
        } catch(NoClassDefFoundError e) {}
	}
	
	public void setupBstats()
	{
		int pluginId = 15926;
        new Metrics(this, pluginId);
	}
}