package main.java.me.avankziar.roota.bungee;

import java.util.logging.Logger;

import main.java.me.avankziar.roota.bungee.database.MysqlHandler;
import main.java.me.avankziar.roota.bungee.database.MysqlSetup;
import main.java.me.avankziar.roota.bungee.ifh.AdministrationProvider;
import main.java.me.avankziar.roota.bungee.listener.PlayerObserverListener;
import main.java.me.avankziar.roota.bungee.metric.Metrics;
import main.java.me.avankziar.roota.general.database.YamlHandler;
import main.java.me.avankziar.roota.general.database.YamlManager;
import me.avankziar.ifh.bungee.plugin.ServicePriority;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class RootA extends Plugin
{
	public static RootA plugin;
	public static Logger logger;
	public static String pluginName = "RootAdministration";
	private static YamlHandler yamlHandler;
	private static YamlManager yamlManager;
	private static MysqlHandler mysqlHandler;
	private static MysqlSetup mysqlSetup;
	private AdministrationProvider administrationProvider;
	
	public void onEnable() 
	{
		plugin = this;
		logger = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=SCC
		logger.info(" ██████╗  ██████╗  ██████╗ ████████╗ █████╗  | Version: "+plugin.getDescription().getVersion());
		logger.info(" ██╔══██╗██╔═══██╗██╔═══██╗╚══██╔══╝██╔══██╗ | Author: "+plugin.getDescription().getAuthor());
		logger.info(" ██████╔╝██║   ██║██║   ██║   ██║   ███████║ | Plugin Website: https://www.spigotmc.org/resources/rootadministration.104833/");
		logger.info(" ██╔══██╗██║   ██║██║   ██║   ██║   ██╔══██║ | Depend Plugins: "+plugin.getDescription().getDepends().toString());
		logger.info(" ██║  ██║╚██████╔╝╚██████╔╝   ██║   ██║  ██║ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepends().toString());
		logger.info(" ╚═╝  ╚═╝ ╚═════╝  ╚═════╝    ╚═╝   ╚═╝  ╚═╝ | Have Fun^^");
		
		yamlHandler = new YamlHandler(YamlManager.Type.BUNGEE, pluginName, logger, plugin.getDataFolder().toPath(),
        		(plugin.getAdministration() == null ? null : plugin.getAdministration().getLanguage()));
        setYamlManager(yamlHandler.getYamlManager());
		
		setupIFHProvider();
		String path = plugin.getYamlHandler().getConfig().getString("IFHAdministrationPath");
		boolean adm = plugin.getAdministration() != null 
				&& plugin.getAdministration().isMysqlPathActive(path);
		if(adm || yamlHandler.getConfig().getBoolean("Mysql.Status", false) == true)
		{
			mysqlSetup = new MysqlSetup(plugin, adm, path);
			mysqlHandler = new MysqlHandler(plugin);
		}
		ListenerSetup();
		setupBstats();
	}
	
	public void onDisable()
	{
		getProxy().getScheduler().cancel(plugin);		
		logger.info(pluginName + " is disabled!");
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
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public MysqlSetup getMysqlSetup()
	{
		return mysqlSetup;
	}
	
	public void setYamlManager(YamlManager yamlManager)
	{
		RootA.yamlManager = yamlManager;
	}
	
	public void ListenerSetup()
	{
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(plugin, new PlayerObserverListener(plugin));
	}
	
	private void setupIFHProvider()
	{
		Plugin ifhp = plugin.getProxy().getPluginManager().getPlugin("InterfaceHub");
        if (ifhp == null) 
        {
            return;
        }
        me.avankziar.ifh.bungee.IFH ifh = (me.avankziar.ifh.bungee.IFH) ifhp;
        try
        {
        	administrationProvider = new AdministrationProvider(plugin);
            ifh.getServicesManager().register(
             		me.avankziar.ifh.bungee.administration.Administration.class,
             		administrationProvider, plugin, ServicePriority.Normal);
            logger.info(pluginName + " detected InterfaceHub >>> Administration.class is provided!");
    		
        } catch(NoClassDefFoundError e){}
	}
	
	public AdministrationProvider getAdministration()
	{
		return administrationProvider;
	}
	
	public void setupBstats()
	{
		int pluginId = 15926;
        new Metrics(this, pluginId);
	}
}